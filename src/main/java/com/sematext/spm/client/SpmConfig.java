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
