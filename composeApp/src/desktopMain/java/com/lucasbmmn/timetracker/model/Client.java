package com.lucasbmmn.timetracker.model;

import com.lucasbmmn.timetracker.model.exception.IllegalEmailException;
import com.lucasbmmn.timetracker.model.exception.IllegalPhoneNumberException;
import com.lucasbmmn.timetracker.model.exception.IllegalTimezoneException;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


/**
 * Represents a client in the time tracker application.
 * Each client has a unique ID, associated company, contact name, email, phone number, and timezone.
 */
public class Client {
    @NotNull
    private final UUID uuid;
    @NotNull
    private String company;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String timezone;


    /**
     * Returns the company name associated with the client.
     *
     * @return the company name
     */
    @NotNull
    public String getCompany() {
        return company;
    }

    /**
     * Sets the company name for the client.
     *
     * @param company the new company name
     */
    public void setCompany(@NotNull String company) {
        // TODO: Update DB
        this.company = company;
    }

    /**
     * Returns the client's name.
     *
     * @return the name of the client
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the client.
     *
     * @param name the new client name
     */
    public void setName(@NotNull String name) {
        // TODO: Update DB
        this.name = name;
    }

    /**
     * Returns the client's email address.
     *
     * @return the email address
     */
    @NotNull
    public String getEmail() {
        return email;
    }

    /**
     * Sets the client's email address. Validates the format.
     *
     * @param email the new email address
     * @throws IllegalEmailException if the email format is invalid
     */
    public void setEmail(@NotNull String email) {
        if (!email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"))
            throw new IllegalEmailException("Illegal client email: " + email);

        // TODO: Update DB
        this.email = email;
    }

    /**
     * Returns the client's phone number.
     *
     * @return the phone number
     */
    @NotNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the client's phone number. Validates the format.
     *
     * @param phoneNumber the new phone number
     * @throws IllegalPhoneNumberException if the phone number format is invalid
     */
    public void setPhoneNumber(@NotNull String phoneNumber) {
        if (!phoneNumber.matches("^\\+?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$"))
            throw new IllegalPhoneNumberException("Illegal client phone number: " + phoneNumber);

        // TODO: Update DB
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the client's timezone.
     *
     * @return the timezone
     */
    @NotNull
    public String getTimezone() {
        return timezone;
    }

    /**
     * Sets the client's timezone. Validates the format.
     *
     * @param timezone the new timezone (e.g., +02:00, -03:30)
     * @throws IllegalTimezoneException if the timezone format is invalid
     */
    public void setTimezone(@NotNull String timezone) {
        if (timezone.matches("^[-+][01][0-9]:[03]0$"))
            throw new IllegalTimezoneException("Illegal client timezone: " + timezone);

        // TODO: Update DB
        this.timezone = timezone;
    }


    /**
     * Constructs a client with an existing UUID and provided details.
     * This constructor is typically used when loading task types from a database.
     *
     * @param uuid        the unique identifier for the client
     * @param company     the company the client is associated with
     * @param name        the client's name
     * @param email       the client's email address
     * @param phoneNumber the client's phone number
     * @param timezone    the client's timezone
     */
    public Client(@NotNull UUID uuid, @NotNull String company, @NotNull String name,
                  @NotNull String email, @NotNull String phoneNumber, @NotNull String timezone) {
        // TODO: Select from DB
        this.uuid = uuid;
        this.company = company;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.timezone = timezone;
    }

    /**
     * Constructs a new client with a generated UUID and provided details.
     * This constructor is typically used when creating new task types.
     *
     * @param company     the company the client is associated with
     * @param name        the client's name
     * @param email       the client's email address
     * @param phoneNumber the client's phone number
     * @param timezone    the client's timezone
     */
    public Client(@NotNull String company, @NotNull String name, @NotNull String email,
                  @NotNull String phoneNumber, @NotNull String timezone) {
        // TODO: Insert into DB
        this.uuid = UUID.randomUUID();
        this.company = company;
        this.name = name;
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
        this.setTimezone(timezone);
    }


    /**
     * Returns a string representation of the client.
     *
     * @return a string containing the client's details
     */
    @Override
    public String toString() {
        return "Client{" +
                "uuid=" + uuid +
                ", company='" + company + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", timezone='" + timezone + '\'' +
                '}';
    }
}
