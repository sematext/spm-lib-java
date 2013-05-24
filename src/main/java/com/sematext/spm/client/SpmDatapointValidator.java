/*
 * Copyright (c) Sematext International
 * All Rights Reserved
 * <p/>
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Sematext International
 * The copyright notice above does not evidence any
 * actual or intended publication of such source code.
 */
package com.sematext.spm.client;

final class SpmDatapointValidator {
  private SpmDatapointValidator() { }

  private static final int MAX_STRING_LENGTH = 255;

  static void checkBounds(String fieldName, String value) {
    if (value != null && value.length() > MAX_STRING_LENGTH) {
      throw new IllegalArgumentException(fieldName + " value can't be longer than 255 characters.");
    }
  }

  static void validate(SpmDatapoint datapoint) {
    checkBounds("Metric name", datapoint.getName());
    checkBounds("Metric filter1", datapoint.getFilter1());
    checkBounds("Metric filter2", datapoint.getFilter2());
  }
}
