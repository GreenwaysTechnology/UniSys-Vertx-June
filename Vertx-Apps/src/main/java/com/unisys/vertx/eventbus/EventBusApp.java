package com.unisys.vertx.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

class VisitorVerticle extends AbstractVerticle {
  private void getData() {
    EventBus eventBus = vertx.eventBus();
    //Declare Consumer
    MessageConsumer<JsonObject> consumer = eventBus.consumer("notification.tn.vistors");
    //handle/process the message/news
    consumer.handler(news -> {
      System.out.println("Vistors From Outside : ");
      System.out.println(news.body().encodePrettily());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    getData();
  }
}


//request-reply
class HealthEmerencyVerticle extends AbstractVerticle {

  private void requestReply() {
    EventBus eventBus = vertx.eventBus();
    //Declare Consumer
    MessageConsumer<String> consumer = eventBus.consumer("alert.tn.covid");
    //handle/process the message/news
    consumer.handler(alert -> {
      System.out.println("Alert From Hosiptial : " + alert.body());
      alert.reply("Patient is crictal!! Need More Attention!!!");
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    requestReply();
  }
}


class CentralFinanceVerticle extends AbstractVerticle {

  private void alertNotification() {
    EventBus eventBus = vertx.eventBus();
    //Declare Consumer
    MessageConsumer<String> consumer = eventBus.consumer("notification.tn.covid");
    //handle/process the message/news
    consumer.handler(news -> {
      System.out.println("Alert From Tamil Nadu : " + news.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    alertNotification();
  }
}


//Subscriber -1
class NewsSevenVerticle extends AbstractVerticle {

  //listen for message
  private void consumeNews() {
    EventBus eventBus = vertx.eventBus();
    //Declare Consumer
    MessageConsumer<String> consumer = eventBus.consumer("news.in.covid");
    //handle/process the message/news
    consumer.handler(news -> {
      System.out.println("News 7's Today News : " + news.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consumeNews();
  }
}

//Subscriber 2
class BBCVerticle extends AbstractVerticle {

  //listen for message
  private void consumeNews() {
    EventBus eventBus = vertx.eventBus();
    //Declare Consumer
    MessageConsumer<String> consumer = eventBus.consumer("news.in.covid");
    //handle/process the message/news
    consumer.handler(news -> {
      System.out.println("BBC's Today News : " + news.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consumeNews();
  }
}

//Publisher
class NewsPublisherVerticle extends AbstractVerticle {

  //api to publish message
  private void publishNews() {
    //publish news after some delay
    vertx.setTimer(1000, h -> {
      //publish : pub-sub
      vertx.eventBus().publish("news.in.covid", "Last 24 hrs, 15000 covid patients in India");

    });

  }

  private void sendNotification() {
    vertx.setTimer(1500, h -> {
      //point-to-point
      vertx.eventBus().send("notification.tn.covid", "We have not received any update on Fund!");
    });
  }

  private void sendAlert() {
    vertx.setTimer(100, h -> {
      //request-reply
      vertx.eventBus().request("alert.tn.covid", "We have send medical Reports of Mr X", ar -> {
        if (ar.succeeded()) {
          System.out.println("Reply/Response : " + ar.result().body());
        }
      });
    });
  }

  private void sendVisitorDetails() {
    JsonObject address = new JsonObject();
    address.put("city", "coimbatore").put("street", "10th street").put("state", "Tamil Nadu");
    System.out.println(address.encode());
    System.out.println(address.encodePrettily());

    JsonObject visitor = new JsonObject();
    visitor.put("name", "subramanian").put("address", address);

    vertx.setTimer(1500, h -> {
      //point-to-point
      vertx.eventBus().send("notification.tn.vistors", visitor);
    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    //call publish news
    publishNews();
    //point to point
    sendNotification();
    //request-reply
    sendAlert();

    //publish json data to visitor
    sendVisitorDetails();
  }
}

public class EventBusApp extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(EventBusApp.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new NewsPublisherVerticle());
    vertx.deployVerticle(new NewsSevenVerticle());
    vertx.deployVerticle(new BBCVerticle());
    vertx.deployVerticle(new CentralFinanceVerticle());
    vertx.deployVerticle(new HealthEmerencyVerticle());
    vertx.deployVerticle(new VisitorVerticle());
  }
}
