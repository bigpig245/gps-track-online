package com.example.demo.service;

import com.example.demo.domain.GPX;
import com.example.demo.dto.GPXDto;
import com.example.demo.dto.PageDataDto;
import com.example.demo.dto.WayPointDto;
import com.example.demo.exception.GPXException;
import com.example.demo.repository.GPXRepository;
import com.example.demo.repository.WayPointRepository;
import com.example.demo.util.Message;
import com.topografix.gpx._1._1.GpxType;
import com.topografix.gpx._1._1.MetadataType;
import com.topografix.gpx._1._1.WptType;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GPXServiceTest {
  @Mock
  private GPXRepository gpxRepository;
  @Mock
  private WayPointRepository wayPointRepository;
  @Mock
  private WayPointService wayPointService;

  @InjectMocks
  private GPXService gpxService;

  private static PodamFactory PODAM = new PodamFactoryImpl();

  @Test
  public void shouldSaveGPX() {
    GpxType gpxType = new GpxType();
    gpxType.setVersion("1.0");
    gpxType.setCreator("trangnt");
    MetadataType metadataType = PODAM.manufacturePojo(MetadataType.class);
    gpxType.setMetadata(metadataType);
    WptType wptType = PODAM.manufacturePojo(WptType.class);
    gpxType.getWpt().add(wptType);
    gpxService.saveGPX(gpxType);

    ArgumentCaptor<GPX> gpxArgumentCaptor = ArgumentCaptor.forClass(GPX.class);
    verify(gpxRepository).save(gpxArgumentCaptor.capture());
    Assertions.assertThat(gpxArgumentCaptor.getValue())
        .extracting("version", "creator")
        .containsExactly(gpxType.getVersion(), gpxType.getCreator());

    Assertions.assertThat(gpxArgumentCaptor.getValue().getMetadata())
        .extracting("name", "description", "dateTime", "keywords")
        .containsExactly(metadataType.getName(), metadataType.getDesc(),
            metadataType.getTime(), metadataType.getKeywords());
    ArgumentCaptor<List> wayPointsCaptor = ArgumentCaptor.forClass(List.class);
    verify(wayPointRepository).saveAll(wayPointsCaptor.capture());
    Assertions.assertThat(wayPointsCaptor.getValue())
        .hasSize(1)
        .extracting("name", "sym", "lat", "lon")
        .containsExactly(
            Tuple.tuple(wptType.getName(), wptType.getSym(), wptType.getLat(), wptType.getLon()));

  }

  @Test
  public void shouldFindAllGPXs() {
    PageRequest pageRequest = PageRequest.of(1, 3);
    GPX gpx1 = PODAM.manufacturePojo(GPX.class);
    GPX gpx2 = PODAM.manufacturePojo(GPX.class);
    Page<GPX> page = new PageImpl<>(Arrays.asList(gpx1, gpx2));
    given(gpxRepository.findAll(pageRequest)).willReturn(page);

    PageDataDto<GPXDto> pageDataDto = gpxService.findAllGPXs(pageRequest);
    Assertions.assertThat(pageDataDto.getPage())
        .extracting("totalPages", "totalRecords", "limit")
        .containsExactly(1, 2L, 2);
    Assertions.assertThat(pageDataDto.getContents()).hasSize(2)
        .extracting("id", "version", "creator")
        .containsExactly(Tuple.tuple(gpx1.getId(), gpx1.getVersion(), gpx1.getCreator()),
            Tuple.tuple(gpx2.getId(), gpx2.getVersion(), gpx2.getCreator()));

  }

  @Test
  public void shouldNotFindGPXById() {
    int id = 1;
    boolean isShowWayPoint = false;

    given(gpxRepository.findById(id)).willReturn(Optional.empty());

    Assertions.assertThatThrownBy(() -> gpxService.findGPXById(id, isShowWayPoint))
        .isInstanceOf(GPXException.class)
        .hasMessage(Message.GPX_NOT_FOUND.getMessage());
  }

  @Test
  public void shouldFindGPXByIdWithoutWayPoint() {
    int id = 1;
    boolean isShowWayPoint = false;
    GPX gpx = PODAM.manufacturePojo(GPX.class);
    given(gpxRepository.findById(id)).willReturn(Optional.of(gpx));

    GPXDto dto = gpxService.findGPXById(id, isShowWayPoint);
    Assertions.assertThat(dto)
        .isEqualToIgnoringGivenFields(gpx, "metadataDto", "wayPointDtos");
    Assertions.assertThat(dto.getWayPointDtos()).isEmpty();
    verify(wayPointService, never()).findAllByGpxId(anyInt());
  }

  @Test
  public void shouldFindGPXByIdWithWayPoint() {
    int id = 1;
    boolean isShowWayPoint = true;
    GPX gpx = PODAM.manufacturePojo(GPX.class);
    given(gpxRepository.findById(id)).willReturn(Optional.of(gpx));

    WayPointDto wayPointDto = PODAM.manufacturePojo(WayPointDto.class);
    given(wayPointService.findAllByGpxId(id)).willReturn(Arrays.asList(wayPointDto));
    GPXDto dto = gpxService.findGPXById(id, isShowWayPoint);
    Assertions.assertThat(dto)
        .isEqualToIgnoringGivenFields(gpx, "metadataDto", "wayPointDtos");
    Assertions.assertThat(dto.getWayPointDtos()).hasSize(1).containsExactly(wayPointDto);
  }

}
