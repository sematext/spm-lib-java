spm-lib-java
============

Java Library for talking to [SPM](http://sematext.com/spm/index.html) API


## Quick start

Initialize client at application initialization point. For web applications it can be ServletContextListener.
    
    SpmClient.initialize("[your token goes here]");

Create and send datapoints:

    SpmDatapoint datapoint = SpmDatapoint.name("registrations")
      .filter1("free-account").filter2("male").value(1d).aggType(AggType.SUM).build();

    SpmClient.client().send(datapoint);

You can send send datapoints list too:

    SpmDatapont building1 = SpmDatapoint.name("coffee-consumed")
      .filter1("building-1").filter2("machine-2").value(42d).aggType(AggType.SUM).build();

    SpmDatapont building2 = SpmDatapoint.name("coffee-consumed")
      .filter1("building-2").filter2("machine-2").value(24d).aggType(AggType.SUM).build();

    SpmClient.client().send(Arrays.asList(building1, building2));


## Configuration

If you want to use different tokens for different applications, you can use `newInstance` factory method:

    SpmClient jvmMetrics = SpmClient.newInstance("[jvm_app_token]");

    SpmClient solrMetrics = SpmClient.newInstance("[solr_app_token]");

    jvmMetrics.send(jvmDatapoints);
    
    solrMetrics.send(solrDatapoints);

SpmClient uses `java.util.logging.Logger`to produce error messages, by default it turned off, but you can enable it (useful for troubleshooting):

    SpmClient.enableLogging();

Behind scenes client uses `ExecutorService` to submit datapoints in background, by default used `Executors.newFixedThreadPool(4)`, but if you want, you can specify other:

    SpmClient.newInstance("[token]", Executors.newCachedThreadPool())
