package dev.dejay.reactor.utils.api;

import java.util.UUID;

public class AshconAPI {

    public UUID uuid;
    public String username;
    public MojangSkins textures;

    public class MojangSkins {
        public Boolean custom;
        public Boolean slim;
        public MojangSkin skin;
        public MojangSkinRaw raw;
    }

    public class MojangSkin {
        public String url;
        public String data;
    }

    public class MojangSkinRaw {
        public String value;
        public String signature;
    }

    @Override
    public String toString() {
        return "AshconAPI{" +
            "uuid=" + uuid +
            ", username='" + username + '\'' +
            ", textures='" + textures + '\'' +
            ", rawTextures=" + textures.raw +
            '}';
    }
}