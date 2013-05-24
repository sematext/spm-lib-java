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

import java.util.concurrent.ExecutorService;

/**
 * Client properties.
 */
public final class ClientProperties {
  private final String token;
  private final String url;
  private final ExecutorService executor;

  /**
   * Constructs a new properties.
   * If {@code null} is passed for url or executor, then default values will
   * be used.
   * @param token token
   * @param url receiver's url
   * @param executor executor service
   * @throws IllegalArgumentException when token is not defined
   */
  public ClientProperties(String token, String url, ExecutorService executor) {
    if (token == null) {
      throw new IllegalArgumentException("Token should be defined");
    }
    this.token = token;
    if (url == null) {
      this.url = Config.SPM_RECEIVER_URL;
    } else {
      this.url = url;
    }
    if (executor == null) {
      this.executor = Config.newThreadPool();
    } else {
      this.executor = executor;
    }
  }

  public String getToken() {
    return token;
  }

  public String getReceiverUrl() {
    return url;
  }

  public ExecutorService getExecutor() {
    return executor;
  }
}
