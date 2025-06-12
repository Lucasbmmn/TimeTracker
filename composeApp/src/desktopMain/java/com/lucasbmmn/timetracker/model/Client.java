package com.lucasbmmn.timetracker.model;

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
     * Returns the client's uuid
     *
     * @return the uuid of the client
     */
    public @NotNull UUID getUuid() {
        return uuid;
    }

    /**
     * Returns the company name associated with the client.
     *
     * @return the company name, never null
     */
    @NotNull
    public String getCompany() {
        return company;
    }

    /**
     * Sets the company name for the client.
     *
     * @param company the new company name, must not be null
     * @throws IllegalArgumentException if the company name is null
     */
    public void setCompany(@NotNull String company) {
        if (company == null) throw new IllegalArgumentException("Client's company name can't be null");
        // TODO: Update DB
        this.company = company;
    }

    /**
     * Returns the client's name.
     *
     * @return the name of the client, never null
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the client.
     *
     * @param name the new client name, must not be null
     * @throws IllegalArgumentException if the client name is null
     */
    public void setName(@NotNull String name) {
        if (name == null) throw new IllegalArgumentException("Client name can't be null");
        // TODO: Update DB
        this.name = name;
    }

    /**
     * Returns the client's email address.
     *
     * @return the email address, never null
     */
    @NotNull
    public String getEmail() {
        return email;
    }

    /**
     * Sets the client's email address. Validates the format.
     *
     * @param email the new email address, must not be null
     * @throws IllegalArgumentException if the email format is invalid or null
     */
    public void setEmail(@NotNull String email) {
        if (email == null) throw new IllegalArgumentException("Client's email can't be null");
        if (!email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"))
            throw new IllegalArgumentException("Illegal client's email: " + email);

        // TODO: Update DB
        this.email = email;
    }

    /**
     * Returns the client's phone number.
     *
     * @return the phone number, never null
     */
    @NotNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the client's phone number. Validates the format.
     *
     * @param phoneNumber the new phone number, must not be null
     * @throws IllegalArgumentException if the phone number format is invalid or null
     */
    public void setPhoneNumber(@NotNull String phoneNumber) {
        if (phoneNumber == null) throw new IllegalArgumentException("Client's phone number can't be null");
        if (!phoneNumber.matches("^\\+?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$"))
            throw new IllegalArgumentException("Illegal client's phone number: " + phoneNumber);

        // TODO: Update DB
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the client's timezone.
     *
     * @return the timezone, never null
     */
    @NotNull
    public String getTimezone() {
        return timezone;
    }

    /**
     * Sets the client's timezone. Validates the format.
     *
     * @param timezone the new timezone (e.g., +02:00, -03:30), must not be null
     * @throws IllegalArgumentException if the timezone format is invalid or null
     */
    public void setTimezone(@NotNull String timezone) {
        if (timezone == null) throw new IllegalArgumentException("Client's timezone can't be null");
        if (timezone.matches("^[-+][01][0-9]:[03]0$"))
            throw new IllegalArgumentException("Illegal client's timezone: " + timezone);

        // TODO: Update DB
        this.timezone = timezone;
    }


    /**
     * Constructs a client with an existing UUID and provided details.
     * This constructor is typically used when loading task types from a database.
     *
     * @param uuid        the unique identifier for the client, must not be null
     * @param company     the company the client is associated with, must not be null
     * @param name        the client's name, must not be null
     * @param email       the client's email address, must not be null
     * @param phoneNumber the client's phone number, must not be null
     * @param timezone    the client's timezone, must not be null
     */
    public Client(@NotNull UUID uuid, @NotNull String company, @NotNull String name,
                  @NotNull String email, @NotNull String phoneNumber, @NotNull String timezone) {
        if (uuid == null) throw new IllegalArgumentException("Client's uuid can't be null");
        this.uuid = uuid;
        this.setCompany(company);
        this.setName(name);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
        this.setTimezone(timezone);
    }

    /**
     * Constructs a new client with a generated UUID and provided details.
     * This constructor is typically used when creating new task types.
     *
     * @param company     the company the client is associated with, must not be null
     * @param name        the client's name, must not be null
     * @param email       the client's email address, must not be null
     * @param phoneNumber the client's phone number, must not be null
     * @param timezone    the client's timezone, must not be null
     */
    public Client(@NotNull String company, @NotNull String name, @NotNull String email,
                  @NotNull String phoneNumber, @NotNull String timezone) {
        // TODO: Insert into DB
        this.uuid = UUID.randomUUID();
        this.setCompany(company);
        this.setName(name);
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
