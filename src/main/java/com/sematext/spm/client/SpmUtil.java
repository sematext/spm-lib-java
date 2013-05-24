/*
 * Copyright (c) Sematext International
 * All Rights Reserved
 * <p/>
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Sematext International
 * The copyright notice above does not evidence any
 * actual or intended publication of such source code.
 */
package com.sematext.spm.client;

import java.util.ArrayList;
import java.util.List;

final class SpmUtil {
  private SpmUtil() { }

  static <T> List<List<T>> partition(List<T> list, int size) {
    if (size <= 0) {
      throw new IllegalArgumentException("Size should be >= 0");
    }
    if (list == null) {
      throw new IllegalArgumentException("List can't be null");
    }
    List<List<T>> partitions = new ArrayList<List<T>>(list.size() / size);
    List<T> partition = new ArrayList<T>(size);
    for (T elem : list) {
      if (partition.size() < size) {
        partition.add(elem);
      } else {
        partitions.add(partition);
        partition = new ArrayList<T>(size);
        partition.add(elem);
      }
    }
    if (!partition.isEmpty()) {
      partitions.add(partition);
    }
    return partitions;
  }
}
