package com.example.demo.controller;

import com.example.demo.config.RestExceptionHandler;
import com.example.demo.domain.GPX;
import com.example.demo.dto.GPXDto;
import com.example.demo.dto.PageDataDto;
import com.example.demo.dto.PaginationDto;
import com.example.demo.service.GPXService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.topografix.gpx._1._1.GpxType;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@WebMvcTest(GPSTrackController.class)
public class GPSTrackControllerTest {
  private MockMvc mockMvc;
  @Autowired
  private GPSTrackController gpsTrackController;
  @Autowired
  private RestExceptionHandler restExceptionHandler;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private GPXService gpxService;

  private static final PodamFactory PODAM = new PodamFactoryImpl();

  @Before
  public void init() {
    mockMvc = standaloneSetup(gpsTrackController)
        .setControllerAdvice(restExceptionHandler)
        .build();
  }

  @Test
  public void shouldUpload() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", "sample.gpx", "text/plain",
        getClass().getClassLoader().getResourceAsStream("templates/sample.gpx"));

    GPX gpx = PODAM.manufacturePojo(GPX.class);
    given(gpxService.saveGPX(any())).willReturn(gpx);

    MvcResult result = mockMvc.perform(multipart("/v1/tracks/upload")
        .file(multipartFile)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
    GPXDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), GPXDto.class);
    Assertions.assertThat(actual).isEqualToIgnoringGivenFields(gpx, "metadataDto", "wayPointDtos");
    Assertions.assertThat(actual.getMetadataDto())
        .isEqualToIgnoringGivenFields(gpx.getMetadata(), "dateTime", "description");
    ArgumentCaptor<GpxType> gpxTypeArgumentCaptor = ArgumentCaptor.forClass(GpxType.class);
    verify(gpxService).saveGPX(gpxTypeArgumentCaptor.capture());
    Assertions.assertThat(gpxTypeArgumentCaptor.getValue())
        .extracting("version", "creator")
        .containsExactly("1.1", "OruxMaps v.7.1.6 Donate");

    Assertions.assertThat(gpxTypeArgumentCaptor.getValue().getMetadata())
        .extracting("name")
        .as("Bardenas Reales: Piskerra y el Paso de los Ciervos");
    Assertions.assertThat(gpxTypeArgumentCaptor.getValue().getWpt()).hasSize(15);
  }

  @Test
  public void shouldGetTracks() throws Exception {
    PageDataDto<GPXDto> pageDataDto = new PageDataDto<>();
    pageDataDto.setContents(Arrays.asList(PODAM.manufacturePojo(GPXDto.class)));
    PaginationDto paginationDto = new PaginationDto(2, 3, 4);
    pageDataDto.setPage(paginationDto);
    given(gpxService.findAllGPXs(any())).willReturn(pageDataDto);

    MvcResult result = mockMvc.perform(get("/v1/tracks")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
    TypeReference<PageDataDto<GPXDto>> type = new TypeReference<PageDataDto<GPXDto>>() {};
    PageDataDto<GPXDto> actual = objectMapper.readValue(result.getResponse().getContentAsString(), type);

    Assertions.assertThat(actual.getContents()).hasSize(1);
    Assertions.assertThat(actual.getContents().get(0))
        .isEqualToIgnoringGivenFields(pageDataDto.getContents().get(0), "metadataDto", "wayPointDtos");
    Assertions.assertThat(actual.getPage()).isEqualToComparingFieldByField(paginationDto);
    ArgumentCaptor<PageRequest> pageRequestArgumentCaptor = ArgumentCaptor.forClass(PageRequest.class);
    verify(gpxService).findAllGPXs(pageRequestArgumentCaptor.capture());
    Assertions.assertThat(pageRequestArgumentCaptor.getValue().toString())
        .isEqualTo("Page request [number: 1, size 10, sort: lastModifiedDate: DESC]");
  }

  @Test
  public void shouldGetTrack() throws Exception {
    int id = 1;
    boolean isShowWayPoint = false;
    GPXDto gpxDto = PODAM.manufacturePojo(GPXDto.class);
    given(gpxService.findGPXById(id, isShowWayPoint)).willReturn(gpxDto);

    MvcResult result = mockMvc.perform(get("/v1/tracks/1?isShowWayPoint=false")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
    GPXDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), GPXDto.class);
    Assertions.assertThat(actual).isEqualToIgnoringGivenFields(gpxDto, "metadataDto", "wayPointDtos");
    Assertions.assertThat(actual.getMetadataDto()).isEqualToComparingFieldByField(gpxDto.getMetadataDto());
    Assertions.assertThat(actual.getWayPointDtos()).hasSize(0);
  }
}
