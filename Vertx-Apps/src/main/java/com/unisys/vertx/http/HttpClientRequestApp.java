package com.unisys.vertx.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.example.util.Runner;

public class HttpClientRequestApp extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(HttpClientRequestApp.class);
  }

  @Override
  public void start() throws Exception {
    super.start();

    //HttpServer options
    HttpServerOptions options = new HttpServerOptions();
    options.setPort(3002);
    //create WebServer , Handle request  and Send Response

    HttpServer server = vertx.createHttpServer(options);

    //Handle Request
    server.requestHandler(context -> {

      //handle client request
      context.handler(chunk -> {
        System.out.println(chunk);
        context.response().end(chunk);
      });

    });


    //start server
    server.listen(serverHandler -> {
      if (serverHandler.succeeded()) {
        System.out.println("Http Server is up and running");
      } else {
        System.out.println(serverHandler.cause().getMessage());
      }
    });
  }
}
