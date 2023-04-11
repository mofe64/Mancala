package com.groupthree.mancala.models.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.groupthree.mancala.models.GamePlayRecord;
import com.groupthree.mancala.models.Player;
import com.groupthree.mancala.models.PublicInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDeserializer extends StdDeserializer<Player> {
    protected PlayerDeserializer(Class<?> vc) {
        super(vc);
    }

    protected PlayerDeserializer() {
        this(null);
    }

    @Override
    public Player deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String firstname = node.get("firstname").asText();
        String lastname = node.get("lastname").asText();
        String username = node.get("username").asText();
        String profileImage = node.get("profileImage").asText();
        boolean isApproved = node.get("isApproved").asBoolean();
        int numberOfWins = node.get("gamePlayRecord").get("numberOfWins").asInt();
        int numberOfLosses = node.get("gamePlayRecord").get("numberOfLosses").asInt();
        int numberOfGames = node.get("gamePlayRecord").get("numberOfGames").asInt();
        List<PublicInfo> userFavorites = new ArrayList<>();
        for (JsonNode val : node.get("favorites")) {
            String favUsername = val.get("username").asText();
            String favProfileImage = val.get("profileImage").asText();
            double favWinPercentage = val.get("winPercentage").asDouble();
            PublicInfo info = new PublicInfo(favUsername, favProfileImage, favWinPercentage);
            userFavorites.add(info);
        }

        GamePlayRecord playRecord = new GamePlayRecord();
        playRecord.setNumberOfGames(numberOfGames);
        playRecord.setNumberOfWins(numberOfWins);
        playRecord.setNumberOfLosses(numberOfLosses);
        Player deserializedPlayer = new Player(username, firstname, lastname, profileImage);

        deserializedPlayer.setApproved(isApproved);
        deserializedPlayer.setFavorites(userFavorites);
        deserializedPlayer.setRecord(playRecord);
        return deserializedPlayer;
    }
}
