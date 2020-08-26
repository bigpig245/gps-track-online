package com.example.demo.service;

import com.example.demo.domain.WayPoint;
import com.example.demo.dto.WayPointDto;
import com.example.demo.repository.WayPointRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Example;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WayPointServiceTest {
  @Mock
  private WayPointRepository wayPointRepository;

  @InjectMocks
  private WayPointService wayPointService;
  private static final PodamFactory PODAM = new PodamFactoryImpl();

  @Test
  public void shouldFindAllByGpxId() {
    int gpxId = 1;
    WayPoint wayPoint = PODAM.manufacturePojo(WayPoint.class);
    BDDMockito.given(wayPointRepository.findAll(any(Example.class))).willReturn(Arrays.asList(wayPoint));
    List<WayPointDto> wayPointDtoList = wayPointService.findAllByGpxId(gpxId);
    assertThat(wayPointDtoList).hasSize(1)
        .extracting("id", "name", "sym",
            "lat", "lon", "createdDate", "lastModifiedDate")
        .containsExactly(Tuple.tuple(wayPoint.getId(), wayPoint.getName(), wayPoint.getSym(),
            wayPoint.getLat(), wayPoint.getLon(), wayPoint.getCreatedDate(), wayPoint.getLastModifiedDate()));
    ArgumentCaptor<Example> exampleArgumentCaptor = ArgumentCaptor.forClass(Example.class);
    verify(wayPointRepository).findAll(exampleArgumentCaptor.capture());
    Assertions.assertThat(exampleArgumentCaptor.getValue().getProbe())
        .extracting("gpxId", "name").containsExactly(gpxId, null);
  }

}
