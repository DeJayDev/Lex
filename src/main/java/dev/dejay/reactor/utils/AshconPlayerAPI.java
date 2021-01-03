package dev.dejay.reactor.utils;

import dev.dejay.reactor.Reactor;
import dev.dejay.reactor.utils.api.AshconAPI;
import dev.dejay.reactor.utils.api.TexturedPlayer;
import java.util.UUID;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestParsingException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AshconPlayerAPI {

    public static TexturedPlayer getPlayerSkin(String player) throws Exception {
        AshconAPI response = getPlayer(player).getBody();

        return new TexturedPlayer(
            response.username,
            response.textures.raw.value,
            response.textures.raw.signature);
    }

    public static String getPlayerName(UUID uuid) throws Exception {
        return getPlayer(uuid.toString()).getBody().username;
    }

    public static UUID getPlayerUUID(String player) throws Exception {
        return getPlayer(player).getBody().uuid;
    }

    private static HttpResponse<AshconAPI> getPlayer(String player) throws Exception {
        HttpResponse<AshconAPI> response = Unirest.get("https://api.ashcon.app/mojang/v2/user/{player}")
            .routeParam("player", player)
            .header("User-Agent", "Reactor/v1.0.0 (+https://github.com/DeJayDev)")
            .asObject(AshconAPI.class);

        if(response.getBody() == null) {
            if(response.getParsingError().isPresent()) {
                UnirestParsingException ex = response.getParsingError().get();
                Reactor.getLogger().error(ex.getOriginalBody());
                throw new Exception("Request Failed (Invalid User?)");
            }
            throw new Exception("Something went terribly wrong...");
        }

        return response;

    }

}