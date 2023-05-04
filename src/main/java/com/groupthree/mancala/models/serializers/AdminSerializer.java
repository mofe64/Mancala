package com.groupthree.mancala.models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.groupthree.mancala.models.Admin;
import com.groupthree.mancala.models.Player;

import java.io.IOException;


/**
 * Serializer class for Admin objects. Serializes an Admin object into JSON format.
 *
 * @author mofe
 * @version 1.0
 */
public class AdminSerializer extends StdSerializer<Admin> {


    /**
     * Default constructor for the AdminSerializer class.
     */
    public AdminSerializer() {
        this(null);
    }

    /**
     * Constructor for the AdminSerializer class.
     *
     * @param a the Class of the Admin object to be serialized
     */
    public AdminSerializer(Class<Admin> a) {
        super(a);
    }

    /**
     * Serializes an Admin object into JSON format.
     *
     * @param admin              the Admin object to be serialized
     * @param jsonGenerator      the JSON generator object
     * @param serializerProvider the serializer provider object
     * @throws IOException if an I/O error occurs during serialization
     */
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
