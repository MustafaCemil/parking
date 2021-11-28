package com.parking.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class GeneralOperations {

  public static String exceptionConvertToString(Exception exception) {
    final StringWriter sw = new StringWriter();
    exception.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }
}
