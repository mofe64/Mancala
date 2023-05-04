package com.groupthree.mancala.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.groupthree.mancala.models.deserializers.AdminDeserializer;
import com.groupthree.mancala.models.serializers.AdminSerializer;

import java.time.LocalDate;


/**
 * This class represents an admin user which is a subclass of the {@link User} class
 * The admin user is a privileged user with additional functionalities such as the ability to modify
 * user profiles, view system logs, and manage the system.
 *
 * @author Ope
 * @version 1.0
 **/
@JsonSerialize(using = AdminSerializer.class)
@JsonDeserialize(using = AdminDeserializer.class)
public class Admin extends User {


    /**
     * Constructor for the Admin class
     *
     * @param username     : The username of the admin user
     * @param firstname    : the firstname of the admin user
     * @param lastname     : the lastname of the admin user
     * @param profileImage : the profile image of the admin user
     * @param localDate    : the creation date of the admin user
     **/
    public Admin(String username,
                 String firstname,
                 String lastname,
                 String profileImage,
                 LocalDate localDate) {
        super(username, firstname, lastname, profileImage, localDate);
    }


    /**
     * Returns a string representation of the admin object.
     *
     * @return a string representation of the admin object.
     */
    @Override
    public String toString() {
        return "Admin{} " + super.toString();
    }
}
