package com.example.demo.controller;

import com.example.demo.domain.GPX;
import com.example.demo.dto.GPXDto;
import com.example.demo.dto.PageDataDto;
import com.example.demo.service.GPXService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.topografix.gpx._1._1.GpxType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.demo.util.Constants.DEFAULT_PAGE;
import static com.example.demo.util.Constants.MAX_PAGE;

@Controller
@RequestMapping("/v1/tracks")
public class GPSTrackController {
  @Autowired
  private GPXService gpxService;

  @PostMapping(value = "/upload")
  public ResponseEntity<GPXDto> uploadTrack(@RequestParam("file") MultipartFile file) throws IOException {
    JacksonXmlModule module = new JacksonXmlModule();
    module.setDefaultUseWrapper(false);
    XmlMapper xmlMapper = new XmlMapper(module);
    xmlMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    GpxType gpxType = xmlMapper.readValue(file.getBytes(), GpxType.class);
    GPX gpx = gpxService.saveGPX(gpxType);
    return ResponseEntity.ok(new GPXDto(gpx));
  }

  @GetMapping
  public ResponseEntity<PageDataDto<GPXDto>> getTracks(@RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "limit", defaultValue = "10") int limit) {
    PageRequest pageable = PageRequest.of(
        page < 0 ? DEFAULT_PAGE : page,
        limit > MAX_PAGE ? MAX_PAGE : limit,
        Sort.by(Sort.Direction.DESC, "lastModifiedDate"));
    return ResponseEntity.ok(gpxService.findAllGPXs(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GPXDto> getTrack(
      @PathVariable("id") int id,
      @RequestParam(value = "isShowWayPoint", defaultValue = "true") boolean isShowWayPoint) {
    return ResponseEntity.ok(gpxService.findGPXById(id, isShowWayPoint));
  }
}
