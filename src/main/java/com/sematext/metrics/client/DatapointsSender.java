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

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

class DatapointsSender {
  private final ClientProperties properties;

  DatapointsSender(ClientProperties properties) {
    this.properties = properties;
  }

  void send(List<StDatapoint> datapoints) {
    for (List<StDatapoint> partition : Util.partition(datapoints, Config.MAX_DATAPOINTS_PER_REQUEST)) {
      properties.getExecutor().execute(new SenderThread(RawFormat.serialize(partition), properties));
    }
  }

  private static final class SenderThread implements Runnable {
    private final String data;
    private final ClientProperties properties;

    private SenderThread(String data, ClientProperties properties) {
      this.data = data;
      this.properties = properties;
    }

    @Override
    public void run() {
      try {
        URL receiverUrl = new URL(properties.getReceiverUrl() + "?token=" + properties.getToken());
        HttpURLConnection connection = (HttpURLConnection) receiverUrl.openConnection();
        connection.addRequestProperty("Content-Type", "text/plain");
        connection.addRequestProperty("User-Agent", Config.USER_AGENT);
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
          StLogger.getLogger().severe("Error while sending datapoints. " + connection.getResponseCode() + " " + connection.getResponseMessage() + ".");
        }
      } catch (MalformedURLException e) {
        StLogger.getLogger().severe("Error while sending datapoints:" + e);
      } catch (IOException e) {
        StLogger.getLogger().severe("Error while sending datapoints:" + e);
      }
    }
  }
}
