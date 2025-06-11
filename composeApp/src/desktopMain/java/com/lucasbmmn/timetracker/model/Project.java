package com.lucasbmmn.timetracker.model;

import com.lucasbmmn.timetracker.model.exception.IllegalFixedPriceException;
import com.lucasbmmn.timetracker.model.exception.IllegalHourlyRateException;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

public class Project {
    @NotNull
    private final UUID uuid;
    private  Client client;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Duration estimatedTime;
    private double hourlyRate;
    private double fixedPrice;
    @NotNull
    private Date createdAt;
    @NotNull
    private Date deadline;


    @NotNull
    public Client getClient() {
        return client;
    }

    public void setClient(@NotNull Client client) {
        // TODO: Update DB
        this.client = client;
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
    public String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        // TODO: Update DB
        this.description = description;
    }

    @NotNull
    public Duration getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(@NotNull Duration estimatedTime) {
        // TODO: Update DB
        this.estimatedTime = estimatedTime;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        if (hourlyRate < 0)
            throw new IllegalHourlyRateException("The hourly rate must be greater or equals to " +
                    "0: " + hourlyRate);

        // TODO: Update DB
        this.hourlyRate = hourlyRate;
    }

    public double getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(double fixedPrice) {
        if (fixedPrice < 0)
            throw new IllegalFixedPriceException("The hourly rate must be greater or equals to " +
                    "0: " + fixedPrice);

        // TODO: Update DB
        this.fixedPrice = fixedPrice;
    }

    @NotNull
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull Date createdAt) {
        // TODO: Update DB
        this.createdAt = createdAt;
    }

    @NotNull
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(@NotNull Date deadline) {
        // TODO: Update DB
        this.deadline = deadline;
    }

    public Project(@NotNull UUID uuid, @NotNull Client client, @NotNull String name,
                   @NotNull String description, @NotNull Duration estimatedTime,
                   double hourlyRate, double fixedPrice, @NotNull Date createdAt,
                   @NotNull Date deadline) {
        // TODO: Select from DB
        this.uuid = uuid;
        this.client = client;
        this.name = name;
        this.description = description;
        this.estimatedTime = estimatedTime;
        this.hourlyRate = hourlyRate;
        this.fixedPrice = fixedPrice;
        this.createdAt = createdAt;
        this.deadline = deadline;
    }

    public Project(@NotNull Client client, @NotNull String name, @NotNull String description,
                   @NotNull Duration estimatedTime, double hourlyRate, double fixedPrice,
                   @NotNull Date createdAt, @NotNull Date deadline) {
        // TODO: Insert into DB
        this.uuid = UUID.randomUUID();
        this.client = client;
        this.name = name;
        this.description = description;
        this.estimatedTime = estimatedTime;
        this.hourlyRate = hourlyRate;
        this.fixedPrice = fixedPrice;
        this.createdAt = createdAt;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Project{" +
                "uuid=" + uuid +
                ", client=" + client +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", estimatedTime=" + estimatedTime +
                ", hourlyRate=" + hourlyRate +
                ", fixedPrice=" + fixedPrice +
                ", createdAt=" + createdAt +
                ", deadline=" + deadline +
                '}';
    }
}
