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

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * <p>Class provides methods for sending custom datapoints to
 * <a href="http://sematext.com/spm/index.html">Scalable Performance Monitoring</a></p>
 *
 * <p>Basic Usage:</p>
 *
 * <pre>
 *   SpmClient.initialize("[your token]");
 *
 *   SpmDatapoint datapoint = SpmDatapoint.name("registrations")
 *       .filter1("main-page")
 *       .filter2("free")
 *       .value(1d)
 *       .aggType(AggType.SUM).build();
 *
 *   SpmClient.client().send(datapoint);
 * </pre>
 *
 * <p>Using client with different tokens:</p>
 *
 * <pre>
 *   SpmClient jvmApp = SpmClient.newInstance("[jvm_app_token]");
 *   SpmClient solrApp = SpmClient.newInstance("[solr_app_token]");
 *
 *   jvmApp.send(...);
 *
 *   solrApp.send(...)
 * </pre>
 */
public class SpmClient {
  private static SpmClient INSTANCE;

  private SpmSender sender;

  private SpmClient(SpmProperties properties) {
    this.sender = new SpmSender(properties);
  }

  /**
   * Send datapoint.
   * @param datapoint datapoint
   * @throws IllegalArgumentException if datapoint is invalid
   */
  public void send(SpmDatapoint datapoint) {
    SpmDatapointValidator.validate(datapoint);
    sender.send(Collections.singletonList(datapoint));
  }

  /**
   * Send datapoints list.
   * @param datapoints datapoints
   * @throws IllegalArgumentException if one of datapoints is invalid
   */
  public void send(List<SpmDatapoint> datapoints) {
    for (SpmDatapoint metric : datapoints) {
      SpmDatapointValidator.validate(metric);
    }
    sender.send(datapoints);
  }

  /**
   * Enable logging. Client uses {@link java.util.logging.Logger} to log failure sending attempts.
   */
  public static void enableLogging() {
    SpmLogger.setEnabled(true);
  }

  /**
   * Disable logging.
   */
  public static void disableLogging() {
    SpmLogger.setEnabled(false);
  }

  /**
   * Get client instance.
   * @return client
   * @throws IllegalStateException if client is not initialized yet.
   * {@link #initialize(String)}, {@link #initialize(SpmProperties)}, {@link #initialize(String, ExecutorService)}.
   */
  public static SpmClient client() {
    SpmClient client = INSTANCE;
    if (client == null) {
      throw new IllegalStateException("SpmClient not initialized (you should call SpmClient.initialize method).");
    }
    return client;
  }

  /**
   * Initialize client.
   * @param properties properties
   * @throws IllegalArgumentException when properties is {@code null}
   */
  public static void initialize(SpmProperties properties) {
    if (properties == null) {
      throw new IllegalArgumentException("Properties should be defined");
    }
    INSTANCE = newInstance(properties);
  }

  /**
   * Initialize client.
   * @param token token
   * @param executor custom executor service
   * @throws IllegalArgumentException when token or executor is {@code null}.
   */
  public static void initialize(String token, ExecutorService executor) {
    if (token == null) {
      throw new IllegalArgumentException("Token should be defined.");
    }
    if (executor == null) {
      throw new IllegalArgumentException("Executor should be defined.");
    }
    initialize(new SpmProperties(token, null, executor));
  }

  /**
   * Initialize client.
   * @param token token
   * @throws IllegalArgumentException when token is {@code null}
   */
  public static void initialize(String token) {
    if (token == null) {
      throw new IllegalArgumentException("Token should be defined.");
    }
    initialize(new SpmProperties(token, null, null));
  }

  /**
   * Create new instance of client.
   * @param properties properties
   * @return new client instance
   * @throws IllegalArgumentException when properties is {@code null}
   */
  public static SpmClient newInstance(SpmProperties properties) {
    if (properties == null) {
      throw new IllegalArgumentException("Properties should be defined.");
    }
    return new SpmClient(properties);
  }

  /**
   * Create new instance of client.
   * @param token token
   * @param executor executor
   * @return new client instance
   * @throws IllegalArgumentException when token or executor is {@code null}
   */
  public static SpmClient newInstance(String token, ExecutorService executor) {
    if (token == null) {
      throw new IllegalArgumentException("Token should be defined.");
    }
    if (executor == null) {
      throw new IllegalArgumentException("Executor should be defined.");
    }
    return new SpmClient(new SpmProperties(token, null, executor));
  }

  /**
   * Create new instance of client.
   * @param token token
   * @return new client instance
   * @throws IllegalArgumentException when token or executor is {@code null}
   */
  public static SpmClient newInstance(String token) {
    if (token == null) {
      throw new IllegalArgumentException("Token should be defined.");
    }
    return newInstance(new SpmProperties(token, null, null));
  }
}
