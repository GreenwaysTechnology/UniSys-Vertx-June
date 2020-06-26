package com.unisys.vertx.microservice.config;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;


class HttpServerWithConfig extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    super.start();
    //Config Store Options
    //Add Storage options: type, format,file path
    ConfigStoreOptions options = new ConfigStoreOptions();
    options.setType("file");
    // options.setFormat("json");
    options.setConfig(new JsonObject().put("path", "application.json"));
    ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(options));

    retriever.getConfig(config -> {
      if (config.succeeded()) {
        System.out.println("Config is Ready");
        //System.out.println(config.result());
        JsonObject configRes = config.result();
        System.out.println(configRes.getJsonObject("application").getString("name"));
        System.out.println(configRes.getJsonObject("application").getInteger("port"));

        vertx.createHttpServer().
          requestHandler(res -> res.response().end(configRes.getJsonObject("application").getString("name")))
          .listen(configRes.getJsonObject("application").getInteger("port"));

      } else {
        System.out.println("Config Error : " + config.cause());
      }
    });

  }
}

class JSONFileSystemConfig extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    //Config Store Options
    //Add Storage options: type, format,file path
    ConfigStoreOptions options = new ConfigStoreOptions();
    options.setType("file");
    // options.setFormat("json");
    options.setConfig(new JsonObject().put("path", "application.json"));
    ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(options));

    //add configuration in memory
    config().put("foo","foovalue");
    JsonObject localconfig = config();
    System.out.println("Config method result" + localconfig.encodePrettily());


    retriever.getConfig(config -> {
      if (config.succeeded()) {
        System.out.println("Config is Ready");
        //System.out.println(config.result());
        JsonObject configRes = config.result();
        System.out.println(configRes.getJsonObject("application").getString("name"));
        System.out.println(configRes.getJsonObject("application").getInteger("port"));

      } else {
        System.out.println("Config Error : " + config.cause());
      }
    });


  }
}


public class ConfigMainApp extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ConfigMainApp.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
      vertx.deployVerticle(new JSONFileSystemConfig());
    //vertx.deployVerticle(new HttpServerWithConfig());
  }
}

