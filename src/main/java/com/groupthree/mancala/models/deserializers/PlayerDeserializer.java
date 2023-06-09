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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is responsible for deserializing a JSON representation of a player into a Player object.
 * The deserialization process extracts information from the JSON tree and sets the corresponding fields of a Player object
 * and returns the deserialized Player object.
 *
 * @author mofe
 * @version 1.0
 */
public class PlayerDeserializer extends StdDeserializer<Player> {
    /**
     * Constructs a PlayerDeserializer instance.
     *
     * @param vc The class to be deserialized.
     */
    protected PlayerDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * Constructs a PlayerDeserializer instance.
     * It calls the other constructor with a null argument.
     */
    protected PlayerDeserializer() {
        this(null);
    }

    /**
     * Deserializes a JSON representation of a player into a Player object.
     * The deserialization process extracts information from the JSON tree and sets the corresponding fields of a Player object
     * and returns the deserialized Player object.
     *
     * @param jsonParser             The JsonParser used for deserialization.
     * @param deserializationContext The DeserializationContext used for deserialization.
     * @return The deserialized Player object.
     * @throws IOException If an I/O error occurs while parsing the JSON data.
     */
    @Override
    public Player deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String firstname = node.get("firstname").asText();
        String lastname = node.get("lastname").asText();
        String username = node.get("username").asText();
        String profileImage = node.get("profileImage").asText();
        boolean isApproved = node.get("isApproved").asBoolean();
        int numberOfWins = node.get("gamePlayRecord").get("numberOfWins").asInt();
        int numberOfLosses = node.get("gamePlayRecord").get("numberOfLosses").asInt();
        int numberOfGames = node.get("gamePlayRecord").get("numberOfGames").asInt();

        String lastLoggedIn = node.get("lastLoggedIn").asText();
        String[] dateStringArr = lastLoggedIn.split("T");
        String dateString = dateStringArr[0];
        String[] dateParts = dateString.split("-");
        String year = dateParts[0];
        String month = dateParts[1];
        String day = dateParts[2];
        LocalDate date = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));

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
        Player deserializedPlayer = new Player(username, firstname, lastname, profileImage, date);

        deserializedPlayer.setApproved(isApproved);
        deserializedPlayer.setFavorites(userFavorites);
        deserializedPlayer.setRecord(playRecord);
        return deserializedPlayer;
    }
}
