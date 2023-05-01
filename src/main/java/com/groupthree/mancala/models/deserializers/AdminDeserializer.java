package com.groupthree.mancala.models.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.groupthree.mancala.models.Admin;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

public class AdminDeserializer extends StdDeserializer<Admin> {

    protected AdminDeserializer() {
        this(null);
    }

    protected AdminDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Admin deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
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
