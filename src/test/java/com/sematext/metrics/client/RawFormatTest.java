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

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.sematext.metrics.client.RawFormat.serialize;
import static junit.framework.Assert.assertEquals;

public class RawFormatTest {
  @Test
  public void testSerializeDatapoint() {
    String serialized = serialize(StDatapoint.name("users_registered")
        .filter1("free_account")
        .filter2("male")
        .timestamp(123L)
        .value(1d)
        .useAvgAggregation().build());

    assertEquals("123\tusers_registered\t1.0\tavg\tfree_account\tmale", serialized);

    serialized = serialize(StDatapoint.name("users_registered")
        .value(1d)
        .filter1("free_account")
        .timestamp(123L)
        .useMinAggregation().build());

    assertEquals("123\tusers_registered\t1.0\tmin\tfree_account\t", serialized);

    serialized = serialize(StDatapoint.name("users_registered")
        .value(1d)
        .filter2("male")
        .timestamp(123L)
        .useMaxAggregation().build());

    assertEquals("123\tusers_registered\t1.0\tmax\t\tmale", serialized);

    serialized = serialize(StDatapoint.name("users_registered")
        .value(1d)
        .timestamp(123L)
        .useSumAggregation().build());

    assertEquals("123\tusers_registered\t1.0\tsum\t\t", serialized);
  }

  @Test
  public void testSerializeDatapoints() {
    StDatapoint datapoint1 = StDatapoint.name("users_registered")
        .filter1("free_account")
        .filter2("male")
        .timestamp(123L)
        .value(1d)
        .useAvgAggregation().build();

    String datapoint1Serialized = "123\tusers_registered\t1.0\tavg\tfree_account\tmale";

    StDatapoint datapoint2 = StDatapoint.name("users_registered")
        .value(1d)
        .filter1("free_account")
        .timestamp(123L)
        .useMinAggregation().build();

    String datapoint2Serialized = "123\tusers_registered\t1.0\tmin\tfree_account\t";

    StDatapoint datapoint3 = StDatapoint.name("users_registered")
        .value(1d)
        .filter2("male")
        .timestamp(123L)
        .useMaxAggregation().build();

    String datapoint3Serialized = "123\tusers_registered\t1.0\tmax\t\tmale";

    assertEquals("", serialize(Collections.<StDatapoint>emptyList()));

    assertEquals(datapoint1Serialized, serialize(Arrays.asList(datapoint1)));

    assertEquals(datapoint1Serialized + "\n" + datapoint2Serialized, serialize(Arrays.asList(datapoint1, datapoint2)));

    assertEquals(datapoint1Serialized + "\n" + datapoint2Serialized + "\n" + datapoint3Serialized, serialize(Arrays.asList(datapoint1, datapoint2, datapoint3)));
  }
}
