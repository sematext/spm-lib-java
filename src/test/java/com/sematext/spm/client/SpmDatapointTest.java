/*
 * Copyright (c) Sematext International
 * All Rights Reserved
 * <p/>
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Sematext International
 * The copyright notice above does not evidence any
 * actual or intended publication of such source code.
 */
package com.sematext.spm.client;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class SpmDatapointTest {

  private static void assertValid(SpmDatapoint.Builder builder) {
    try {
      builder.build();
    } catch (IllegalArgumentException e) {
      fail("Datapoint should be valid.");
    }
  }

  private static void assertInvalid(SpmDatapoint.Builder builder) {
    try {
      builder.build();
      fail("Datapoint should be invalid.");
    } catch (IllegalArgumentException e) { }
  }

  @Test
  public void testBuild() {
    assertValid(SpmDatapoint.name("custom").value(1d).useAvgAggregation());

    assertInvalid(SpmDatapoint.name("custom").value(1d));
    assertInvalid(SpmDatapoint.name("custom").useAvgAggregation());
    assertInvalid(SpmDatapoint.name("custom").aggType(null));

    assertNotNull("Timestamp should be set", SpmDatapoint.name("custom").value(1d).useAvgAggregation().build().getTimestamp());

    try {
      SpmDatapoint.name("");
      fail("Empty datapoint name is not allowed.");
    } catch (IllegalArgumentException e) { }

    try {
      SpmDatapoint.name(null);
      fail("Null datapoint name is not allowed.");
    } catch (IllegalArgumentException e) { }
  }
}
