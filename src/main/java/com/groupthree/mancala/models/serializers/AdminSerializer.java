package com.groupthree.mancala.models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.groupthree.mancala.models.Admin;
import com.groupthree.mancala.models.Player;

import java.io.IOException;

public class AdminSerializer extends StdSerializer<Admin> {


    public AdminSerializer() {
        this(null);
    }

    public AdminSerializer(Class<Admin> a) {
        super(a);
    }

    @Override
    public void serialize(Admin admin, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("firstname", admin.getProfile().getFirstname());
        jsonGenerator.writeStringField("lastname", admin.getProfile().getLastname());
        jsonGenerator.writeStringField("username", admin.getProfile().getUsername());
        jsonGenerator.writeStringField("profileImage", admin.getProfile().getProfileImage());
        jsonGenerator.writeStringField("lastLoggedIn", admin.getProfile().getLastLoggedIn().toString());
        jsonGenerator.writeEndObject();

    }
}
