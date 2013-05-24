/*
 * Copyright (c) Sematext International
 * All Rights Reserved
 * <p/>
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Sematext International
 * The copyright notice above does not evidence any
 * actual or intended publication of such source code.
 */
package com.sematext.spm.client;

import java.util.Iterator;
import java.util.List;

final class SpmRawFormat {
  private static final String DELIMITER = "\t";
  private static final char NEW_LINE = '\n';

  private SpmRawFormat() { }

  static String serialize(SpmDatapoint metric) {
    StringBuilder builder = new StringBuilder(256)
        .append(metric.getTimestamp())
        .append(DELIMITER)
        .append(metric.getName())
        .append(DELIMITER)
        .append(metric.getValue())
        .append(DELIMITER)
        .append(metric.getAggregationType().getName())
        .append(DELIMITER);
    if (metric.getFilter1() != null) {
      builder.append(metric.getFilter1());
    }
    builder.append(DELIMITER);
    if (metric.getFilter2() != null) {
      builder.append(metric.getFilter2());
    }
    return builder.toString();
  }

  static String serialize(List<SpmDatapoint> metrics) {
    if (metrics  == null) {
      throw new IllegalArgumentException("Metrics should be defined");
    }
    Iterator<SpmDatapoint> iterator = metrics.iterator();
    if (!iterator.hasNext()) {
      return "";
    }
    final StringBuilder builder = new StringBuilder(256);
    builder.append(serialize(iterator.next()));
    while (iterator.hasNext()) {
      builder.append(NEW_LINE)
          .append(serialize(iterator.next()));
    }
    return builder.toString();
  }
}
