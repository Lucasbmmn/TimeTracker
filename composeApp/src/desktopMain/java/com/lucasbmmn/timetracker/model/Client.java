package com.lucasbmmn.timetracker.model;

import com.lucasbmmn.timetracker.model.exception.IllegalEmailException;
import com.lucasbmmn.timetracker.model.exception.IllegalPhoneNumberException;
import com.lucasbmmn.timetracker.model.exception.IllegalTimezoneException;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

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


    @NotNull
    public String getCompany() {
        return company;
    }

    public void setCompany(@NotNull String company) {
        // TODO: Update DB
        this.company = company;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        // TODO: Update DB
        this.name = name;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        if (!email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"))
            throw new IllegalEmailException("Illegal client email: " + email);

        // TODO: Update DB
        this.email = email;
    }

    @NotNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotNull String phoneNumber) {
        if (!phoneNumber.matches("^\\+?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$"))
            throw new IllegalPhoneNumberException("Illegal client phone number: " + phoneNumber);

        // TODO: Update DB
        this.phoneNumber = phoneNumber;
    }

    @NotNull
    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(@NotNull String timezone) {
        if (timezone.matches("^[-+][01][0-9]:[03]0$"))
            throw new IllegalTimezoneException("Illegal client timezone: " + timezone);

        // TODO: Update DB
        this.timezone = timezone;
    }

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
