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

import java.util.logging.Level;

final class StLogger {
  private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger("sematext-metrics");

  static {
    LOG.setLevel(Level.OFF);
  }

  static void setEnabled(boolean enabled) {
    LOG.setLevel(enabled ? Level.WARNING : Level.OFF);
  }

  static java.util.logging.Logger getLogger() {
    return LOG;
  }
}
