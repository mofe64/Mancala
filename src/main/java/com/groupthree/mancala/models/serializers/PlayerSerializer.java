package com.groupthree.mancala.models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.groupthree.mancala.models.Player;
import com.groupthree.mancala.models.PublicInfo;

import java.io.IOException;
import java.util.List;

/**
 * Serializer class for Player objects. Serializes an Player object into JSON format.
 *
 * @author mofe
 * @version 1.0
 */
public class PlayerSerializer extends StdSerializer<Player> {

    /**
     * Default constructor for the PlayerSerializer class.
     */
    public PlayerSerializer() {
        this(null);
    }

    /**
     * Constructor for the PlayerSerializer class.
     *
     * @param p the Class of the Player object to be serialized
     */
    public PlayerSerializer(Class<Player> p) {
        super(p);
    }


    /**
     * Serializes a Player object into JSON format.
     *
     * @param player             the Player object to be serialized
     * @param jsonGenerator      the JSON generator object
     * @param serializerProvider the serializer provider object
     * @throws IOException if an I/O error occurs during serialization
     */
    @Override
    public void serialize(Player player, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeBooleanField("isApproved", player.isApproved());
        jsonGenerator.writeStringField("firstname", player.getProfile().getFirstname());
        jsonGenerator.writeStringField("lastname", player.getProfile().getLastname());
        jsonGenerator.writeStringField("username", player.getProfile().getUsername());
        jsonGenerator.writeStringField("profileImage", player.getProfile().getProfileImage());
        jsonGenerator.writeStringField("lastLoggedIn", player.getProfile().getLastLoggedIn().toString());


        jsonGenerator.writeObjectFieldStart("gamePlayRecord");
        jsonGenerator.writeNumberField("numberOfWins", player.getRecord().getNumberOfWins());
        jsonGenerator.writeNumberField("numberOfLosses", player.getRecord().getNumberOfLosses());
        jsonGenerator.writeNumberField("numberOfGames", player.getRecord().getNumberOfGames());
        jsonGenerator.writeEndObject();


        jsonGenerator.writeArrayFieldStart("favorites");
        List<PublicInfo> favs = player.getFavorites();
        for (PublicInfo info : favs) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("username", info.username());
            jsonGenerator.writeStringField("profileImage", info.profileImage());
            jsonGenerator.writeNumberField("winPercentage", info.winPercentage());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();

    }


}
