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

import static com.sematext.spm.client.SpmDatapointValidator.validate;
import static junit.framework.Assert.fail;

public class SpmMetricValidatorTest {

  public static String repeat(String str, int count) {
    String repeated = "";
    for (int i = 0; i < count; i++) {
      repeated += str;
    }
    return repeated;
  }

  public static void assertValid(SpmDatapoint metric) {
    try {
      validate(metric);
    } catch (IllegalArgumentException e) {
      fail(metric + " should be valid.");
    }
  }

  public static void assertInvalid(SpmDatapoint metric) {
    try {
      validate(metric);
      fail(metric + " should be invalid.");
    } catch (IllegalArgumentException e) { }
  }

  @Test
  public void testValidate() {
    //using vars to avoid confusion with IllegalArgumentException from SpmDatapoint builder.
    SpmDatapoint metric = SpmDatapoint.name(repeat("c", 255))
        .filter1(repeat("b", 255))
        .filter2(repeat("c", 255))
        .value(1d).useSumAggregation().build();
    assertValid(metric);

     metric = SpmDatapoint.name(repeat("c", 255))
        .filter1(null)
        .filter2(null)
        .value(1d).useSumAggregation().build();
    assertValid(metric);

    metric = SpmDatapoint.name(repeat("c", 256))
        .value(1d).useSumAggregation().build();
    assertInvalid(metric);

    metric = SpmDatapoint.name("custom")
        .filter1(repeat("a", 256))
        .value(1d).useSumAggregation().build();
    assertInvalid(metric);

    metric = SpmDatapoint.name("custom")
        .filter2(repeat("a", 256))
        .value(1d).useSumAggregation().build();
    assertInvalid(metric);
  }
}
