/*
 * Copyright (c) Sematext International
 * All Rights Reserved
 * <p/>
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Sematext International
 * The copyright notice above does not evidence any
 * actual or intended publication of such source code.
 */
package com.sematext.spm.client;

/**
 * Aggregation types.
 */
public enum AggType {
  SUM("sum"),
  AVG("avg"),
  MIN("min"),
  MAX("max");

  private final String name;

  private AggType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
