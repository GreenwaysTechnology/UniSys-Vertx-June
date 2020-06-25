package com.unisys.data.format;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

class VisitorFuture extends AbstractVerticle {

  public Future<JsonObject> getVisitor() {
    Promise promise = Promise.promise();
    JsonObject address = new JsonObject();
    address.put("city", "coimbatore").put("street", "10th street").put("state", "Tamil Nadu");
    JsonObject visitor = new JsonObject();
    visitor.put("name", "subramanian").put("address", address);
    promise.complete(visitor);

    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    getVisitor().onComplete(ar -> {
      if (ar.succeeded()) {
        System.out.println("Visitor future");
        System.out.println(ar.result().encodePrettily());
      } else {
        System.out.println(ar.cause().getMessage());
      }
    });
  }
}


public class JSONDemo extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(JSONDemo.class);
  }

  @Override
  public void start() throws Exception {
    super.start();

    //json object creation.
    JsonObject address = new JsonObject();
    address.put("city", "coimbatore").put("street", "10th street").put("state", "Tamil Nadu");
    System.out.println(address.encode());
    System.out.println(address.encodePrettily());


    JsonObject visitor = new JsonObject();
    visitor.put("name", "subramanian").put("address", address);
    System.out.println(visitor.encodePrettily());

    //Getting key-values from the json
    System.out.println("City " + address.getString("city"));
    System.out.println(visitor.getString("name") + visitor.getJsonObject("address").getString("city"));

    //convert json into map -  set
    System.out.println("Set Representation");
    System.out.println(visitor.getMap().entrySet());


    //JSON ARRAYS
    JsonArray list = new JsonArray();
    list.add(visitor).add(visitor);
    System.out.println(list.encodePrettily());

    //read data from json array
    list.forEach(v -> {
      System.out.println(v);
    });
    vertx.deployVerticle(new VisitorFuture());

  }
}
