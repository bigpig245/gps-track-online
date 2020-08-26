package com.example.demo.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Constants {
  public static final int DEFAULT_PAGE = 0;
  public static final int MAX_PAGE = 10;
  public static final XmlMapper MAPPER = new XmlMapper();

  static {
    JacksonXmlModule module = new JacksonXmlModule();
    module.setDefaultUseWrapper(false);
    MAPPER.registerModule(module);
    MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
  }
}
