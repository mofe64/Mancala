package com.groupthree.mancala.models.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.groupthree.mancala.models.Admin;

import java.io.IOException;
import java.time.LocalDate;

/**
 * AdminDeserializer is a custom deserializer for the Admin class. It extends the StdDeserializer class
 * and implements the deserialize method to convert a JSON representation of an Admin object back into an
 * Admin Java object.
 *
 * @author mofe
 * @version 1.0
 */
public class AdminDeserializer extends StdDeserializer<Admin> {
    /**
     * Creates a new AdminDeserializer object.
     */
    protected AdminDeserializer() {
        this(null);
    }

    /**
     * Creates a new AdminDeserializer object.
     *
     * @param vc the Class object that the deserializer handles
     */
    protected AdminDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * Deserializes a JSON representation of an Admin object into an Admin Java object.
     *
     * @param jsonParser             the JSON parser to read from
     * @param deserializationContext the context for deserialization
     * @return an Admin Java object
     * @throws IOException if an I/O error occurs during deserialization
     */
    @Override
    public Admin deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String firstname = node.get("firstname").asText();
        String lastname = node.get("lastname").asText();
        String username = node.get("username").asText();
        String profileImage = node.get("profileImage").asText();
        String lastLoggedIn = node.get("lastLoggedIn").asText();
        String[] dateStringArr = lastLoggedIn.split("T");
        String dateString = dateStringArr[0];
        String[] dateParts = dateString.split("-");
        String year = dateParts[0];
        String month = dateParts[1];
        String day = dateParts[2];
        LocalDate date = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        return new Admin(username, firstname, lastname, profileImage, date);


    }
}
