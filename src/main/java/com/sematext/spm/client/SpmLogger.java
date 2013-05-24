/*
 * Copyright (c) Sematext International
 * All Rights Reserved
 * <p/>
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Sematext International
 * The copyright notice above does not evidence any
 * actual or intended publication of such source code.
 */
package com.sematext.spm.client;

import java.util.logging.Level;
import java.util.logging.Logger;

final class SpmLogger {
  private static final Logger LOG = Logger.getLogger("spm-lib-java");

  static {
    LOG.setLevel(Level.OFF);
  }

  static void setEnabled(boolean enabled) {
    LOG.setLevel(enabled ? Level.WARNING : Level.OFF);
  }

  static Logger getLogger() {
    return LOG;
  }
}
