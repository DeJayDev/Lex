package dev.dejay.lex.utils;

import com.destroystokyo.paper.profile.ProfileProperty;
import dev.dejay.lex.Lex;
import dev.dejay.lex.utils.api.CraftheadAPI;
import dev.dejay.lex.utils.api.TexturedPlayer;
import java.util.UUID;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestParsingException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CraftheadPlayerAPI {

    public static TexturedPlayer getPlayerSkin(String player) throws Exception {
        CraftheadAPI response = getPlayer(player).getBody();

        return new TexturedPlayer(
            response.name,
            response.properties.value,
            response.properties.signature);
    }

    public static String getPlayerName(UUID uuid) throws Exception {
        return getPlayer(uuid.toString()).getBody().name;
    }

    public static UUID getPlayerUUID(String player) throws Exception {
        return getPlayer(player).getBody().id;
    }

    public static ProfileProperty getProfile(String player) throws Exception {
        CraftheadAPI response = getPlayer(player).getBody();

        return new ProfileProperty(response.name,
            response.properties.value,
            response.properties.signature);
    }

    private static HttpResponse<CraftheadAPI> getPlayer(String player) throws Exception {
        HttpResponse<CraftheadAPI> response = Unirest.get("https://crafthead.net/profile/{player}?unsigned=false")
            .routeParam("player", player)
            .header("User-Agent", "Lex/v1.0.0 (+https://github.com/DeJayDev)")
            .asObject(CraftheadAPI.class);

        if (response.getBody() == null) {
            if (response.getParsingError().isPresent()) {
                UnirestParsingException ex = response.getParsingError().get();
                Lex.getLogger().error(ex.getOriginalBody());
                throw new Exception("Request Failed (Invalid User?)");
            }
            throw new Exception("Something went terribly wrong...");
        }

        return response;

    }

}
