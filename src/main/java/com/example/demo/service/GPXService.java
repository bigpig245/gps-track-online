package com.example.demo.service;

import com.example.demo.domain.GPX;
import com.example.demo.domain.Metadata;
import com.example.demo.domain.WayPoint;
import com.example.demo.dto.GPXDto;
import com.example.demo.dto.PageDataDto;
import com.example.demo.dto.PaginationDto;
import com.example.demo.exception.GPXException;
import com.example.demo.repository.GPXRepository;
import com.example.demo.repository.WayPointRepository;
import com.example.demo.util.Message;
import com.topografix.gpx._1._1.GpxType;
import com.topografix.gpx._1._1.WptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GPXService {
  @Autowired
  private GPXRepository gpxRepository;
  @Autowired
  private WayPointRepository wayPointRepository;
  @Autowired
  private WayPointService wayPointService;

  @Transactional
  public GPX saveGPX(GpxType gpxType) {
    GPX gpx = buildGpx(gpxType);

    Metadata metadata = buildMetadata(gpxType);
    metadata.setGpx(gpx);
    gpx.setMetadata(metadata);
    gpxRepository.save(gpx);

    List<WayPoint> wayPoints = new ArrayList<>();
    for (WptType wptType : gpxType.getWpt()) {
      WayPoint wayPoint = buildWayPoint(gpx, wptType);
      wayPoints.add(wayPoint);
    }
    wayPointRepository.saveAll(wayPoints);
    return gpx;
  }

  private WayPoint buildWayPoint(GPX gpx, WptType wptType) {
    WayPoint wayPoint = new WayPoint();
    wayPoint.setGpxId(gpx.getId());
    wayPoint.setName(wptType.getName());
    wayPoint.setSym(wptType.getSym());
    wayPoint.setLat(wptType.getLat());
    wayPoint.setLon(wptType.getLon());
    return wayPoint;
  }

  private GPX buildGpx(GpxType gpxType) {
    GPX gpx = new GPX();
    gpx.setVersion(gpxType.getVersion());
    gpx.setCreator(gpxType.getCreator());
    return gpx;
  }

  private Metadata buildMetadata(GpxType gpxType) {
    Metadata metadata = new Metadata();
    metadata.setName(gpxType.getMetadata().getName());
    metadata.setDescription(gpxType.getMetadata().getDesc());
    metadata.setDateTime(gpxType.getMetadata().getTime() == null ? null :
        gpxType.getMetadata()
            .getTime()
            .toGregorianCalendar()
            .toZonedDateTime()
            .toLocalDateTime());
    metadata.setKeywords(gpxType.getMetadata().getKeywords());
    return metadata;
  }

  public PageDataDto<GPXDto> findAllGPXs(PageRequest pageRequest) {
    Page<GPX> gpxes = gpxRepository.findAll(pageRequest);
    List<GPXDto> gpxDtos = gpxes.getContent().stream().map(GPXDto::new)
        .collect(Collectors.toList());
    PaginationDto paginationDto = new PaginationDto(gpxes.getTotalPages(),
        gpxes.getTotalElements(), gpxes.getNumberOfElements());
    return new PageDataDto<>(gpxDtos, paginationDto);
  }

  public GPXDto findGPXById(int id, boolean isShowWayPoint) {
    GPXDto gpx = gpxRepository.findById(id)
        .map(GPXDto::new)
        .orElseThrow(() -> new GPXException(Message.GPX_NOT_FOUND));

    if (isShowWayPoint) {
      gpx.setWayPointDtos(wayPointService.findAllByGpxId(id));
    }
    return gpx;
  }
}
