package com.groupthree.mancala.models.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.groupthree.mancala.models.Admin;
import com.groupthree.mancala.models.serializers.AdminSerializer;

import java.io.IOException;

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

        return new Admin(username, firstname, lastname, profileImage);


    }
}
