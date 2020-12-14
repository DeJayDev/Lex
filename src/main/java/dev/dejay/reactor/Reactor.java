package dev.dejay.reactor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reactor {

    public final static String VERSION = "0.0.1";
    private static Logger logger = LogManager.getLogger(Reactor.class);

    public static void main(String[] args) {
        System.out.println("Welcome to Reactor v" + VERSION);
    }

    public static Logger getLogger() {
        return logger;
    }

}
