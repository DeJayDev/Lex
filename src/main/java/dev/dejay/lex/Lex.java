package dev.dejay.lex;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Lex {

    public static Gson gson = new Gson();
    public final static String VERSION = "$version";
    private static final Logger logger = LogManager.getLogger(Lex.class);

    public static void main(String[] args) {
        System.out.println("This is Lex v" + VERSION);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static Gson getGson() {
        return gson;
    }

}