sematext-metrics [![Build Status](https://travis-ci.org/sematext/sematext-metrics.png?branch=master)](https://travis-ci.org/sematext/sematext-metrics)
============

Java library for talking to [SPM](http://sematext.com/spm/index.html) API for sending [Custom Metrics](https://sematext.atlassian.net/wiki/display/PUBSPM/Custom+Metrics).

## Quick start

Add maven dependency

    <dependency>
      <groupId>com.sematext</groupId>
      <artifactId>sematext-metrics</artifactId>
      <version>0.1</version>
    </dependency>

Initialize client at application initialization point. For web applications it can be ServletContextListener.
    
    SematextClient.initialize("[your token goes here]");

Create and send datapoints:

    StDatapoint datapoint = StDatapoint.name("user registrations")
      .filter1("account.type=free")
      .filter2("user.gender=male")
      .value(1d)
      .aggType(AggType.SUM).build();

    SematextClient.client().send(datapoint);

You can send send datapoints list too:

    StDatapoint building1 = StDatapoint.name("coffee.coffee-consumed")
      .filter1("floor=1")
      .filter2("machine=2")
      .value(42d)
      .aggType(AggType.SUM).build();

    StDatapoint building2 = StDatapoint.name("coffee-consumed")
      .filter1("floor=1")
      .filter2("machine=2")
      .value(77d)
      .aggType(AggType.SUM).build();

    SematextClient.client().send(Arrays.asList(building1, building2));


## Configuration

To use different tokens for different applications, use `newInstance` factory method:

    // create clients
    SematextClient userMetrics = SematextClient.newInstance("[elasticsearch_app_token]");
    SematextClient searchMetrics = SematextClient.newInstance("[web_app_token]");

    // create userDatapoints and searchDatapoints here

    // send the data
    userMetrics.send(userDatapoints);
    searchMetrics.send(searchDatapoints);

SpmClient uses `java.util.logging.Logger` for logging. Logging is disabled by default. To enable it:

    SematextClient.enableLogging();

Behind the scenes `ExecutorService` is used to send data in background. `Executors.newFixedThreadPool(4)` is the default, but can be changed:

    SematextClient.newInstance("[token]", Executors.newCachedThreadPool());


## Further reading

[Wiki page about custom metrics feature](https://sematext.atlassian.net/wiki/display/PUBSPM/Custom+Metrics).

## License

Copyright 2013 Sematext Group, Inc.

Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
