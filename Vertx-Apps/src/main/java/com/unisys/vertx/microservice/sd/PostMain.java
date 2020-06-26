package com.unisys.vertx.microservice.sd;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.servicediscovery.types.HttpEndpoint;

class PostVerticleService extends BaseMicroServiceVerticle {

  @Override
  public void start() throws Exception {
    super.start();
    config().put("api.name", "posts");
    publishHttpEndpoint("posts-services", true, "jsonplaceholder.typicode.com", 443);

    config().put("api.name", "users");
    publishHttpEndpoint("users-services", true, "jsonplaceholder.typicode.com", 443);


    vertx.setTimer(5000, res -> {
      //posts
      HttpEndpoint.getWebClient(discovery, new JsonObject().put("name", "posts-services"), ar -> {
        if (ar.succeeded()) {
          WebClient client = ar.result();
          client.get("/" + config().getString("api.name")).send(response -> {
            System.out.println("Response is ready!");
            System.out.println(response.succeeded());
            System.out.println(response.result().bodyAsJsonArray().encodePrettily());
          });
        }
      });
    });

    vertx.setTimer(5000, res -> {
      //posts
      HttpEndpoint.getWebClient(discovery, new JsonObject().put("name", "users-services"), ar -> {
        if (ar.succeeded()) {
          WebClient client = ar.result();
          client.get("/" + config().getString("api.name")).send(response -> {
            System.out.println("Response is ready!");
            System.out.println(response.succeeded());
            System.out.println(response.result().bodyAsJsonArray().encodePrettily());
          });
        }
      });
    });
  }
}

public class PostMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(PostMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new PostVerticleService());
  }
}
