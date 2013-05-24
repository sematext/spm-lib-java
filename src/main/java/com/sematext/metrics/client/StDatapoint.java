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

/**
 * <p>Represents datapoint, which contains next parameters:</p>.
 * <ul>
 *   <li><b>Timestamp</b> - timestamp in millis (required)</li>
 *   <li><b>Name</b> - metric's name. String of length <= 255 (required)</li>
 *   <li><b>Filter1</b> - metric's first filter value. String of length <= 255 (optional)</li>
 *   <li><b>Filter2</b> - metric's second filter value. String of length <= 255 (optional)</li>
 *   <li><b>Value</b> - value of datapoint. String of length <= 255 (required)</li>
 *   <li><b>Aggregation type</b> - aggregation type of datapoint. 'sum', 'min', 'max' or 'avg' (required)</li>
 * </ul>
 */
public class StDatapoint {
  private Long timestamp;
  private String name;
  private String filter1;
  private String filter2;
  private Double value;
  private AggType aggregationType;

  private StDatapoint() { }

  /**
   * @return timestamp
   */
  public Long getTimestamp() {
    return timestamp;
  }

  /**
   * @return metric's name
   */
  public String getName() {
    return name;
  }

  /**
   * @return metric's filter1
   */
  public String getFilter1() {
    return filter1;
  }

  /**
   * @return metric's filter2
   */
  public String getFilter2() {
    return filter2;
  }

  /**
   * @return metric's value
   */
  public Double getValue() {
    return value;
  }

  public AggType getAggregationType() {
    return aggregationType;
  }

  /**
   * Datapoint's builder.
   */
  public static class Builder {
    private StDatapoint datapoint = new StDatapoint();

    private Builder(String name) {
      datapoint.name = name;
    }

    /**
     * Set timestamp in milliseconds.
     *
     * @param timestamp timestamp
     * @return builder
     */
    public Builder timestamp(Long timestamp) {
      datapoint.timestamp = timestamp;
      return this;
    }

    /**
     * Set filter1
     *
     * @param filter1 filter1
     * @return builder
     */
    public Builder filter1(String filter1) {
      datapoint.filter1 = filter1;
      return this;
    }

    /**
     * Set filter2
     *
     * @param filter2 filter2
     * @return builder
     */
    public Builder filter2(String filter2) {
      datapoint.filter2 = filter2;
      return this;
    }

    /**
     * Set value
     *
     * @param value value
     * @return builder
     */
    public Builder value(Double value) {
      datapoint.value = value;
      return this;
    }

    /**
     * Set aggregation type.
     *
     * @param aggType aggregation type
     * @return builder
     */
    public Builder aggType(AggType aggType) {
      datapoint.aggregationType = aggType;
      return this;
    }

    public Builder useMinAggregation() {
      datapoint.aggregationType = AggType.MIN;
      return this;
    }

    public Builder useMaxAggregation() {
      datapoint.aggregationType = AggType.MAX;
      return this;
    }

    public Builder useAvgAggregation() {
      datapoint.aggregationType = AggType.AVG;
      return this;
    }

    public Builder useSumAggregation() {
      datapoint.aggregationType = AggType.SUM;
      return this;
    }

    /**
     * Build datapoint. If timestamp value is {@code null} ({@link #timestamp(Long)}), then
     * value of {@code System.currentTimeMillis()} will be used.
     *
     * @return datapoint
     * @throws IllegalArgumentException if aggregation type ({@link #aggType(AggType)} or value {@link #value(Double)}
     * is not set
     */
    public StDatapoint build() {
      if (datapoint.timestamp == null) {
        datapoint.timestamp = System.currentTimeMillis();
      }
      if (datapoint.aggregationType == null) {
        throw new IllegalArgumentException("Aggregation type should be defined.");
      }
      if (datapoint.value == null) {
        throw new IllegalArgumentException("Value should be defined.");
      }
      return datapoint;
    }
  }

  /**
   * Create new datapoint builder for metric's name.
   * @param name metric's name
   * @return datapoint builder instance
   * @throws IllegalArgumentException if name is empty or {@code null}
   */
  public static Builder name(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name should be defined.");
    }
    return new Builder(name);
  }

  @Override
  public String toString() {
    return new StringBuilder("StDatapoint [")
        .append("name = ").append(name).append(", ")
        .append("timestamp = ").append(timestamp).append(", ")
        .append("value = ").append(value).append(", ")
        .append("filter1 = ").append(filter1).append(", ")
        .append("filter2 = ").append(filter2).append(", ")
        .append("aggregation = ").append(aggregationType)
        .append("]").toString();
  }
}
