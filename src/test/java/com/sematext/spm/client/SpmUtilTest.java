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
package com.sematext.spm.client;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class SpmUtilTest {
  @Test
  public void testPartition() {
    assertEquals(asList(), SpmUtil.partition(asList(), 10));
    assertEquals(asList(asList(1, 2, 3, 4, 5)), SpmUtil.partition(asList(1, 2, 3, 4, 5), 10));
    assertEquals(asList(asList(1, 2, 3), asList(4, 5)), SpmUtil.partition(asList(1, 2, 3, 4, 5), 3));
  }
}
