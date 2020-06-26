package com.unisys.vertx.microservice.sd;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;
import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

class CircuitBreakerVerticle extends BaseMicroServiceVerticle {

  @Override
  public void start() throws Exception {
    super.start();
    //Create Breaker Object

    //how to employ safty to my code when failure dedecuted

    circuitBreaker.executeWithFallback(
      future -> {
        WebClient client = WebClient.create(vertx);
//
//// Send a GET request
        client
          .get("jsonplaceholder.typicode.com", "/postsdd")
          .send(ar -> {
            // Obtain response
            HttpResponse<Buffer> response = ar.result();
            if (response.statusCode() != 200) {
              future.fail("HTTP error");
            } else {
              future.complete(response.bodyAsJsonArray().encode());
            }

          });
      }, v -> {
        // Executed when the circuit is opened
        return "Hello, I am fallback";
      })
      .onComplete(ar -> {
        // Do something with the result
        if (ar.succeeded()) {
          System.out.println(ar.result());
        } else {
          System.out.println(ar.cause());

        }
      });


  }
}


public class FalutApp extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(FalutApp.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new CircuitBreakerVerticle());
  }
}
