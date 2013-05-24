/**
 * Copyright 2013 Sematext International
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sematext.metrics.client;

import java.util.Iterator;
import java.util.List;

final class RawFormat {
  private static final String DELIMITER = "\t";
  private static final char NEW_LINE = '\n';

  private RawFormat() { }

  static String serialize(StDatapoint metric) {
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

  static String serialize(List<StDatapoint> metrics) {
    if (metrics  == null) {
      throw new IllegalArgumentException("Metrics should be defined");
    }
    Iterator<StDatapoint> iterator = metrics.iterator();
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
