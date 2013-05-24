/*
 * Copyright (c) Sematext International
 * All Rights Reserved
 * <p/>
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Sematext International
 * The copyright notice above does not evidence any
 * actual or intended publication of such source code.
 */
package com.sematext.spm.client;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

final class SpmConfig {
  private static final String USER_AGENT_NAME = "spm-lib-java";
  static final String SPM_RECEIVER_URL = "http://spm-receiver.sematext.com/receiver/custom/receive.raw";
  static final int MAX_DATAPOINTS_PER_REQUEST = 100;
  static final String USER_AGENT;

  static {
    Properties properties = new Properties();
    try {
      properties.load(SpmConfig.class.getResourceAsStream("/spm-lib-java.properties"));
    } catch (IOException e) {
      //ignore
    } catch (NullPointerException e) {
      //ignore missing property
    }
    USER_AGENT = USER_AGENT_NAME + " " + properties.getProperty("version", "unknown version");
  }

  static ExecutorService newThreadPool() {
    return Executors.newFixedThreadPool(4);
  }
}
