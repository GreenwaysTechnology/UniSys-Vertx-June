package com.unisys.fp.lambda;

import java.util.Arrays;
import java.util.List;

@FunctionalInterface
interface Handler {
    void connect();
}

//class : Database
class Database {
    //ConnectDb takes Handler as type
    public void connectDb(Handler handler) {
        //invoke handler connect function
        handler.connect();
    }
}

//old
class DatabaseImpl implements Handler {
    @Override
    public void connect() {
        System.out.println("Database connection is ready!");
    }
}

//Function as parameter with input
@FunctionalInterface
interface HTTPGetHandler {
    void get(Object response);
}

class HttpServer {
    public void fetch(HTTPGetHandler httpGetHandler) {
        //fake response
        List<String> response = Arrays.asList("Subramanian", "Geetha", "Srisha", "DhivayaSree");
        //return the response
        httpGetHandler.get(response);
    }

}

public class FunctionAsParameter {
    public static void main(String[] args) {
        //Create Database Instance
        Database database = new Database();
        //Way 1 :using external class
        //call connectDB method and pass Handler reference
        Handler databaseImpl = new DatabaseImpl();
        database.connectDb(databaseImpl);

        //Way 2: using anonymous inner class
        database.connectDb(new Handler() {
            @Override
            public void connect() {
                System.out.println("Database connection is ready inside anonymous class");
            }
        });

        //Way 3 : using lambda ; function as parameter
        //3.1 : write lambda separately and pass the reference
        Handler handler = () -> System.out.println("Database connection is ready inside Lambda ");
        database.connectDb(handler);
        //3.2: inline lambda
        database.connectDb(() -> System.out.println("Database connection is ready inside lambda inline"));
        /////////////////////////////////////////////////////////////////////////////////////////////////
        HttpServer httpServer = new HttpServer();
        //3.1 : write lambda separately and pass the reference
        HTTPGetHandler httpGetHandler = res -> System.out.println(res);
        httpServer.fetch(httpGetHandler);
        //3.2: inline lambda
        httpServer.fetch(res-> System.out.println(res));

    }
}
