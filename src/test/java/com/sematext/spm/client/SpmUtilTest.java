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
