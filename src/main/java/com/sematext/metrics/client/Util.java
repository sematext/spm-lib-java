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

import java.util.ArrayList;
import java.util.List;

final class Util {
  private Util() { }

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
