package com.unisys.vertx.web.dataacces;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import io.vertx.ext.mongo.MongoClient;
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

//Book Repository
class BookRepository {
  MongoClient mongoClient;

  public BookRepository() {
  }

  public BookRepository(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  //api talk to mongo and get data
  public Future<String> findAll() {
    Promise promise = Promise.promise();
    JsonObject query = new JsonObject();
    mongoClient.find("books", query, lookup -> {
      if (lookup.succeeded()) {
        //Store results in jsonArray
        JsonArray documents = new JsonArray();
        for (JsonObject document : lookup.result()) {
          documents.add(document);
        }
        promise.complete(documents.encodePrettily());

      } else {
        promise.fail(lookup.cause());
        return;
      }

    });


    return promise.future();
  }


}

///
class BooksController extends AbstractVerticle {
  private BookRepository bookRepository;

  public BooksController() {
  }

  public BooksController(MongoClient mongoClient) {
    bookRepository = new BookRepository(mongoClient);
  }

  //booksRouter
  public Router getBooksConfig() {
    //sub Routers
    Router router = Router.router(vertx);
    //end points
    router.get("/list").handler(ctx -> {

      //talk to book repo class to get records async, once the result is ready,send to client
      bookRepository.findAll().onComplete(ar -> {
        //verify result
        if (ar.succeeded()) {
          //set Header content type
          ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
          //flush the response
          ctx.response().end(ar.result());
        } else {
          ctx.response().end("Sorry Somthing went Wrong!!!!");
        }

      });


    });

    return router;
  }

}


//Front Controller
class AppController extends AbstractVerticle {

  HttpServer server;
  HttpServerOptions options;
  MongoClient mongoClient;

  private void startApp() {
    options = new HttpServerOptions().setPort(3001).setHost("localhost");
    server = vertx.createHttpServer(options);

    //connecting mongo db
    mongoClient = MongoClient.createShared(vertx, new JsonObject().put("db_name", "BooksDb"));
    BooksController booksController = new BooksController(mongoClient);
    //Routers : router
    Router appRouter = Router.router(vertx);
    //bind controlers with appRouter
    appRouter.mountSubRouter("/api/hello", new HelloController().getHelloConfig());
    appRouter.mountSubRouter("/api/hai", new HaiController().getHaiConfig());
    appRouter.mountSubRouter("/api/books", booksController.getBooksConfig());

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


public class RESTDataAccess extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(RESTDataAccess.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new AppController());
  }
}
