/*
 * Copyright (c) Sematext International
 * All Rights Reserved
 * <p/>
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Sematext International
 * The copyright notice above does not evidence any
 * actual or intended publication of such source code.
 */
package com.sematext.spm.client;

import java.util.concurrent.ExecutorService;

/**
 * Client properties.
 */
public final class SpmProperties {
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
  public SpmProperties(String token, String url, ExecutorService executor) {
    if (token == null) {
      throw new IllegalArgumentException("Token should be defined");
    }
    this.token = token;
    if (url == null) {
      this.url = SpmConfig.SPM_RECEIVER_URL;
    } else {
      this.url = url;
    }
    if (executor == null) {
      this.executor = SpmConfig.newThreadPool();
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
