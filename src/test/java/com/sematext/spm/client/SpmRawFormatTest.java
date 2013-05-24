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

import java.util.Arrays;
import java.util.Collections;

import static com.sematext.spm.client.SpmRawFormat.serialize;
import static junit.framework.Assert.assertEquals;

public class SpmRawFormatTest {
  @Test
  public void testSerializeDatapoint() {
    String serialized = serialize(SpmDatapoint.name("users_registered")
        .filter1("free_account")
        .filter2("male")
        .timestamp(123L)
        .value(1d)
        .useAvgAggregation().build());

    assertEquals("123\tusers_registered\t1.0\tavg\tfree_account\tmale", serialized);

    serialized = serialize(SpmDatapoint.name("users_registered")
        .value(1d)
        .filter1("free_account")
        .timestamp(123L)
        .useMinAggregation().build());

    assertEquals("123\tusers_registered\t1.0\tmin\tfree_account\t", serialized);

    serialized = serialize(SpmDatapoint.name("users_registered")
        .value(1d)
        .filter2("male")
        .timestamp(123L)
        .useMaxAggregation().build());

    assertEquals("123\tusers_registered\t1.0\tmax\t\tmale", serialized);

    serialized = serialize(SpmDatapoint.name("users_registered")
        .value(1d)
        .timestamp(123L)
        .useSumAggregation().build());

    assertEquals("123\tusers_registered\t1.0\tsum\t\t", serialized);
  }

  @Test
  public void testSerializeDatapoints() {
    SpmDatapoint datapoint1 = SpmDatapoint.name("users_registered")
        .filter1("free_account")
        .filter2("male")
        .timestamp(123L)
        .value(1d)
        .useAvgAggregation().build();

    String datapoint1Serialized = "123\tusers_registered\t1.0\tavg\tfree_account\tmale";

    SpmDatapoint datapoint2 = SpmDatapoint.name("users_registered")
        .value(1d)
        .filter1("free_account")
        .timestamp(123L)
        .useMinAggregation().build();

    String datapoint2Serialized = "123\tusers_registered\t1.0\tmin\tfree_account\t";

    SpmDatapoint datapoint3 = SpmDatapoint.name("users_registered")
        .value(1d)
        .filter2("male")
        .timestamp(123L)
        .useMaxAggregation().build();

    String datapoint3Serialized = "123\tusers_registered\t1.0\tmax\t\tmale";

    assertEquals("", serialize(Collections.<SpmDatapoint>emptyList()));

    assertEquals(datapoint1Serialized, serialize(Arrays.asList(datapoint1)));

    assertEquals(datapoint1Serialized + "\n" + datapoint2Serialized, serialize(Arrays.asList(datapoint1, datapoint2)));

    assertEquals(datapoint1Serialized + "\n" + datapoint2Serialized + "\n" + datapoint3Serialized, serialize(Arrays.asList(datapoint1, datapoint2, datapoint3)));
  }
}
