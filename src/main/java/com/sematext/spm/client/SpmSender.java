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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

class SpmSender {
  private final SpmProperties properties;

  SpmSender(SpmProperties properties) {
    this.properties = properties;
  }

  void send(List<SpmDatapoint> datapoints) {
    for (List<SpmDatapoint> partition : SpmUtil.partition(datapoints, SpmConfig.MAX_DATAPOINTS_PER_REQUEST)) {
      properties.getExecutor().execute(new SenderThread(SpmRawFormat.serialize(partition), properties));
    }
  }

  private static final class SenderThread implements Runnable {
    private final String data;
    private final SpmProperties properties;

    private SenderThread(String data, SpmProperties properties) {
      this.data = data;
      this.properties = properties;
    }

    @Override
    public void run() {
      try {
        URL receiverUrl = new URL(properties.getReceiverUrl() + "?token=" + properties.getToken());
        HttpURLConnection connection = (HttpURLConnection) receiverUrl.openConnection();
        connection.addRequestProperty("Content-Type", "text/plain");
        connection.addRequestProperty("User-Agent", SpmConfig.USER_AGENT);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        OutputStream os = null;
        try {
          os = connection.getOutputStream();
          os.write(data.getBytes());
          os.flush();
        } finally {
          if (os != null) {
            os.close();
          }
        }

        if (connection.getResponseCode() != 201) {
          SpmLogger.getLogger().severe("Error while sending datapoints. " + connection.getResponseCode() + " " + connection.getResponseMessage() + ".");
        }
      } catch (MalformedURLException e) {
        SpmLogger.getLogger().severe("Error while sending datapoints:" + e);
      } catch (IOException e) {
        SpmLogger.getLogger().severe("Error while sending datapoints:" + e);
      }
    }
  }
}
