/**
 * Copyright 2013 Sematext Group, Inc.
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

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class StDatapointTest {

  private static void assertValid(StDatapoint.Builder builder) {
    try {
      builder.build();
    } catch (IllegalArgumentException e) {
      fail("Datapoint should be valid.");
    }
  }

  private static void assertInvalid(StDatapoint.Builder builder) {
    try {
      builder.build();
      fail("Datapoint should be invalid.");
    } catch (IllegalArgumentException e) { }
  }

  @Test
  public void testBuild() {
    assertValid(StDatapoint.name("custom").value(1d).useAvgAggregation());

    assertInvalid(StDatapoint.name("custom").value(1d));
    assertInvalid(StDatapoint.name("custom").useAvgAggregation());
    assertInvalid(StDatapoint.name("custom").aggType(null));

    assertNotNull("Timestamp should be set", StDatapoint.name("custom").value(1d).useAvgAggregation().build().getTimestamp());

    try {
      StDatapoint.name("");
      fail("Empty datapoint name is not allowed.");
    } catch (IllegalArgumentException e) { }

    try {
      StDatapoint.name(null);
      fail("Null datapoint name is not allowed.");
    } catch (IllegalArgumentException e) { }
  }
}
