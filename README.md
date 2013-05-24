sematext-metrics
============

Java Library for talking to [SPM](http://sematext.com/spm/index.html) API


## Quick start

Initialize client at application initialization point. For web applications it can be ServletContextListener.
    
    SematextClient.initialize("[your token goes here]");

Create and send datapoints:

    StDatapoint datapoint = StDatapoint.name("registrations")
      .filter1("free-account").filter2("male").value(1d).aggType(AggType.SUM).build();

    SematextClient.client().send(datapoint);

You can send send datapoints list too:

    StDatapoint building1 = StDatapoint.name("coffee-consumed")
      .filter1("building-1").filter2("machine-2").value(42d).aggType(AggType.SUM).build();

    StDatapoint building2 = StDatapoint.name("coffee-consumed")
      .filter1("building-2").filter2("machine-2").value(24d).aggType(AggType.SUM).build();

    SematextClient.client().send(Arrays.asList(building1, building2));


## Configuration

If you want to use different tokens for different applications, you can use `newInstance` factory method:

    SematextClient jvmMetrics = SematextClient.newInstance("[jvm_app_token]");

    SematextClient solrMetrics = SematextClient.newInstance("[solr_app_token]");

    jvmMetrics.send(jvmMetrics);
    
    solrMetrics.send(solrMetrics);

SpmClient uses `java.util.logging.Logger`to produce error messages, by default it turned off, but you can enable it (useful for troubleshooting):

    SematextClient.enableLogging();

Behind scenes client uses `ExecutorService` to submit datapoints in background, by default used `Executors.newFixedThreadPool(4)`, but if you want, you can specify other:

    SematextClient.newInstance("[token]", Executors.newCachedThreadPool());


## License

Copyright 2013 Sematext International

Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
