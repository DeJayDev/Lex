package dev.dejay.reactor;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reactor {

    public final static String VERSION = "0.0.3";
    private static Logger logger = LogManager.getLogger(Reactor.class);

    public static void main(String[] args) {
        System.out.println("Welcome to Reactor v" + VERSION);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static Gson getGson() {
        return new Gson();
    }

}
