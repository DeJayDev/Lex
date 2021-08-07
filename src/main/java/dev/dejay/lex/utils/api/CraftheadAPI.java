package dev.dejay.lex.utils.api;

import java.util.UUID;

public class CraftheadAPI {

    public UUID id;
    public String name;
    public MojangSkin properties;

    public class MojangSkin {

        public String name;
        public String value;
        public String signature;
    }

    @Override
    public String toString() {
        return "CraftheadAPI{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", properties='" + properties + '\'' +
            '}';
    }
}