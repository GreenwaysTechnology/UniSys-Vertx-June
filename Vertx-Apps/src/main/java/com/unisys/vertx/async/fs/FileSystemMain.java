package com.unisys.vertx.async.fs;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.file.FileSystem;
import io.vertx.example.util.Runner;

class FileServiceVerticle extends AbstractVerticle {

  private Future<String> readFile() {
    Promise promise = Promise.promise();
    //Async file read operation
    FileSystem fs = vertx.fileSystem();
    fs.readFile("assets/hello.txt", fileHandler -> {
      if (fileHandler.succeeded()) {
        System.out.println("File is ready!");
        promise.complete(fileHandler.result().toString());
      } else {
        promise.fail(fileHandler.cause());
      }
    });


    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Start");
    readFile().onComplete(far -> {
      if (far.succeeded()) {
        System.out.println(far.result());
      } else {
        System.out.println(far.cause().getMessage());
      }
    });
    System.out.println("end");
  }
}


public class FileSystemMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(FileSystemMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new FileServiceVerticle());
  }
}
