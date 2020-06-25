package com.unisys.vertx.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.Router;


class HelloController extends AbstractVerticle {

  //router configuration
  public Router getHelloConfig() {
    //sub Routers
    Router router = Router.router(vertx);

    //end points
    router.get("/list").handler(ctx -> {
      ctx.response().end("Hello");
    });

    return router;
  }

  @Override
  public void start() throws Exception {
    super.start();
  }
}



class HaiController extends AbstractVerticle {

  //router configuration
  public Router getHaiConfig() {
    //sub Routers
    Router router = Router.router(vertx);
    //end points
    router.get("/list").handler(ctx -> {
      ctx.response().end("Hi ");
    });
    return router;
  }

  @Override
  public void start() throws Exception {
    super.start();
  }
}

//Front Controller
class AppController extends AbstractVerticle {

  HttpServer server;
  HttpServerOptions options;

  private void startApp() {
    options = new HttpServerOptions().setPort(3001).setHost("localhost");
    server = vertx.createHttpServer(options);
    //Routers : router
    Router appRouter = Router.router(vertx);
    //bind controlers with appRouter
    appRouter.mountSubRouter("/api/hello", new HelloController().getHelloConfig());
    appRouter.mountSubRouter("/api/hai", new HaiController().getHaiConfig());

    server.requestHandler(appRouter);

    //start
    server.listen(server -> {
      if (server.succeeded()) {
        System.out.println("REST Api Server is Ready!");
      }
    });
  }


  @Override
  public void start() throws Exception {
    super.start();
    startApp();
  }
}


public class RestApp extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(RestApp.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new AppController());
  }
}
