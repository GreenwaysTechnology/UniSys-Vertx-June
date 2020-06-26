package com.unisys.vertx.rx;

import io.vertx.example.util.Runner;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpClient;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.core.http.HttpServerResponse;

public class RxVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Runner.runExample(RxVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    HttpServer server = vertx.createHttpServer();

    server.requestStream().toFlowable().subscribe(req -> {
      HttpServerResponse resp = req.response();

      String contentType = req.getHeader("Content-Type");
      if (contentType != null) {
        resp.putHeader("Content-Type", contentType);
      }
      resp.setChunked(true);

      req.toFlowable().subscribe(
        resp::write,
        err -> {
        },
        resp::end
      );
    });
    server.listen(3000);

    vertx.setTimer(1000, ar -> {
      HttpClient client = vertx.createHttpClient();
      client.put(3000, "localhost", "/", resp -> {
        System.out.println("Got response " + resp.statusCode());
        resp.handler(buf -> System.out.println(buf.toString("UTF-8")));
      }).setChunked(true).putHeader("Content-Type", "text/plain").write("Hello reactive").end();
    });
  }
}
