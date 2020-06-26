package com.unisys.vertx.microservice.sd;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.HttpEndpoint;
import io.vertx.example.util.Runner;

class PublishAndDiscoverHTTPEndPoint extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    ServiceDiscoveryOptions discoveryOptions = new ServiceDiscoveryOptions();

    //enable discovery server : apache zoo keeper
    discoveryOptions.setBackendConfiguration(new JsonObject()
      .put("connection", "127.0.0.1:2181")
      .put("ephemeral", true)
      .put("guaranteed", true)
      .put("basePath", "/services/my-backend")
    );
    ServiceDiscovery discovery = ServiceDiscovery.create(vertx, discoveryOptions);

    //Record Creation
    Record httpEndPointRecord = HttpEndpoint
      .createRecord("http-posts-service",
        true, "jsonplaceholder.typicode.com", 443, "/posts", new JsonObject());

    discovery.publish(httpEndPointRecord, ar -> {
      if (ar.succeeded()) {
        System.out.println("Successfully published to Zookeeper...>>>>" + ar.result().toJson());
      } else {
        System.out.println(" Not Published " + ar.cause());
      }
    });

    //Consume :
    vertx.setTimer(5000, ar -> {

      //discover service
      HttpEndpoint.getWebClient(discovery, new JsonObject().put("name", "http-posts-service"), sar -> {
        //Get Resource from discover server
        WebClient client = sar.result();
        client.get("/posts").send(res -> {
          System.out.println("Response is ready!");
          System.out.println(res.result().bodyAsJsonArray());
          //remove /release discovery record
          ServiceDiscovery.releaseServiceObject(discovery, client);
        });

      });

    });
  }
}


public class ServiceDiscoveryMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ServiceDiscoveryMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new PublishAndDiscoverHTTPEndPoint());
  }
}
