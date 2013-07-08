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
 *   SematextClient.initialize("[your token]");
 *
 *   StDatapoint datapoint = StDatapoint.name("registrations")
 *       .filter1("main-page")
 *       .filter2("free")
 *       .value(1d)
 *       .aggType(AggType.SUM).build();
 *
 *   SematextClient.client().send(datapoint);
 * </pre>
 *
 * <p>Using client with different tokens:</p>
 *
 * <pre>
 *   SematextClient jvmApp = SematextClient.newInstance("[jvm_app_token]");
 *   SematextClient solrApp = SematextClient.newInstance("[solr_app_token]");
 *
 *   jvmApp.send(...);
 *
 *   solrApp.send(...)
 * </pre>
 */
public class SematextClient {
  private static SematextClient INSTANCE;

  private DatapointsSender sender;

  private SematextClient(ClientProperties properties) {
    this.sender = new DatapointsSender(properties);
  }

  /**
   * Send datapoint.
   * @param datapoint datapoint
   * @throws IllegalArgumentException if datapoint is invalid
   */
  public void send(StDatapoint datapoint) {
    StDatapointValidator.validate(datapoint);
    sender.send(Collections.singletonList(datapoint));
  }

  /**
   * Send datapoints list.
   * @param datapoints datapoints
   * @throws IllegalArgumentException if one of datapoints is invalid
   */
  public void send(List<StDatapoint> datapoints) {
    for (StDatapoint metric : datapoints) {
      StDatapointValidator.validate(metric);
    }
    sender.send(datapoints);
  }

  /**
   * Enable logging. Client uses {@link java.util.logging.Logger} to log failure sending attempts.
   */
  public static void enableLogging() {
    StLogger.setEnabled(true);
  }

  /**
   * Disable logging.
   */
  public static void disableLogging() {
    StLogger.setEnabled(false);
  }

  /**
   * Checks if client was initialized.
   * @return {@code true} if client was initialized.
   */
  public static boolean isInitialized() {
    return INSTANCE != null;
  }

  /**
   * Get client instance.
   * @return client
   * @throws IllegalStateException if client is not initialized yet.
   * {@link #initialize(String)}, {@link #initialize(ClientProperties)}, {@link #initialize(String, ExecutorService)}.
   */
  public static SematextClient client() {
    SematextClient client = INSTANCE;
    if (client == null) {
      throw new IllegalStateException("SematextClient not initialized (you should call SematextClient.initialize method).");
    }
    return client;
  }

  /**
   * Initialize client.
   * @param properties properties
   * @throws IllegalArgumentException when properties is {@code null}
   */
  public static void initialize(ClientProperties properties) {
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
    initialize(new ClientProperties(token, null, executor));
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
    initialize(new ClientProperties(token, null, null));
  }

  /**
   * Create new instance of client.
   * @param properties properties
   * @return new client instance
   * @throws IllegalArgumentException when properties is {@code null}
   */
  public static SematextClient newInstance(ClientProperties properties) {
    if (properties == null) {
      throw new IllegalArgumentException("Properties should be defined.");
    }
    return new SematextClient(properties);
  }

  /**
   * Create new instance of client.
   * @param token token
   * @param executor executor
   * @return new client instance
   * @throws IllegalArgumentException when token or executor is {@code null}
   */
  public static SematextClient newInstance(String token, ExecutorService executor) {
    if (token == null) {
      throw new IllegalArgumentException("Token should be defined.");
    }
    if (executor == null) {
      throw new IllegalArgumentException("Executor should be defined.");
    }
    return new SematextClient(new ClientProperties(token, null, executor));
  }

  /**
   * Create new instance of client.
   * @param token token
   * @return new client instance
   * @throws IllegalArgumentException when token or executor is {@code null}
   */
  public static SematextClient newInstance(String token) {
    if (token == null) {
      throw new IllegalArgumentException("Token should be defined.");
    }
    return newInstance(new ClientProperties(token, null, null));
  }
}
