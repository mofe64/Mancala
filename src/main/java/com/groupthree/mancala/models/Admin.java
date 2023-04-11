package com.groupthree.mancala.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.groupthree.mancala.models.deserializers.AdminDeserializer;
import com.groupthree.mancala.models.deserializers.PlayerDeserializer;
import com.groupthree.mancala.models.serializers.AdminSerializer;
import com.groupthree.mancala.models.serializers.PlayerSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JsonSerialize(using = AdminSerializer.class)
@JsonDeserialize(using = AdminDeserializer.class)
public class Admin extends User {


    public Admin(String username,
                 String firstname,
                 String lastname,
                 String profileImage) {
        super(username, firstname, lastname, profileImage);
    }


    @Override
    public String toString() {
        return "Admin{} " + super.toString();
    }
}
