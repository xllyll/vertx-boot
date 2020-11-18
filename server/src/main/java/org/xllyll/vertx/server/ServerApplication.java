package org.xllyll.vertx.server;

import io.github.xllyll.vertx.boot.VertxApplication;
import io.github.xllyll.vertx.boot.listener.ApplicationRunner;

public class ServerApplication implements ApplicationRunner {

    public static void main(String[] args) {
        VertxApplication.run(ServerApplication.class);
    }

    @Override
    public void run() throws Exception {
        System.out.println("===== finish =====");
    }
}
