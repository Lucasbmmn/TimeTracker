package com.lucasbmmn.timetracker.model;

import com.lucasbmmn.timetracker.model.exception.IllegalFixedPriceException;
import com.lucasbmmn.timetracker.model.exception.IllegalHourlyRateException;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * Represents a project in the time tracker system.
 * A project can be associated with a client and includes information such as name,
 * description, estimated time to complete, pricing details (hourly or fixed),
 * creation date, and a deadline.
 */
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


    /**
     * Returns the client associated with the project.
     *
     * @return the client
     */
    @NotNull
    public Client getClient() {
        return client;
    }

    /**
     * Sets the client associated with the project.
     *
     * @param client the client to set
     */
    public void setClient(@NotNull Client client) {
        // TODO: Update DB
        this.client = client;
    }

    /**
     * Returns the name of the project.
     *
     * @return the project name
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the project.
     *
     * @param name the new name
     */
    public void setName(@NotNull String name) {
        // TODO: Update DB
        this.name = name;
    }

    /**
     * Returns the description of the project.
     *
     * @return the project description
     */
    @NotNull
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the project.
     *
     * @param description the new description
     */
    public void setDescription(@NotNull String description) {
        // TODO: Update DB
        this.description = description;
    }

    /**
     * Returns the estimated time required to complete the project.
     *
     * @return the estimated duration
     */
    @NotNull
    public Duration getEstimatedTime() {
        return estimatedTime;
    }

    /**
     * Sets the estimated time to complete the project.
     *
     * @param estimatedTime the estimated duration
     */
    public void setEstimatedTime(@NotNull Duration estimatedTime) {
        // TODO: Update DB
        this.estimatedTime = estimatedTime;
    }

    /**
     * Returns the hourly rate for the project.
     *
     * @return the hourly rate
     */
    public double getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Sets the hourly rate for the project. Must be non-negative.
     *
     * @param hourlyRate the new hourly rate
     * @throws IllegalHourlyRateException if the rate is negative
     */
    public void setHourlyRate(double hourlyRate) {
        if (hourlyRate < 0)
            throw new IllegalHourlyRateException("The hourly rate must be greater or equals to " +
                    "0: " + hourlyRate);

        // TODO: Update DB
        this.hourlyRate = hourlyRate;
    }

    /**
     * Returns the fixed price for the project.
     *
     * @return the fixed price
     */
    public double getFixedPrice() {
        return fixedPrice;
    }

    /**
     * Sets the fixed price for the project. Must be non-negative.
     *
     * @param fixedPrice the new fixed price
     * @throws IllegalFixedPriceException if the price is negative
     */
    public void setFixedPrice(double fixedPrice) {
        if (fixedPrice < 0)
            throw new IllegalFixedPriceException("The hourly rate must be greater or equals to " +
                    "0: " + fixedPrice);

        // TODO: Update DB
        this.fixedPrice = fixedPrice;
    }


    /**
     * Returns the date the project was created.
     *
     * @return the creation date
     */
    @NotNull
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation date of the project.
     *
     * @param createdAt the new creation date
     */
    public void setCreatedAt(@NotNull Date createdAt) {
        // TODO: Update DB
        this.createdAt = createdAt;
    }

    /**
     * Returns the project's deadline.
     *
     * @return the deadline
     */
    @NotNull
    public Date getDeadline() {
        return deadline;
    }

    /**
     * Sets the project's deadline.
     *
     * @param deadline the new deadline
     */
    public void setDeadline(@NotNull Date deadline) {
        // TODO: Update DB
        this.deadline = deadline;
    }


    /**
     * Constructs a Project using an existing UUID.
     * This constructor is typically used when loading task types from a database.
     *
     * @param uuid          the unique identifier of the project
     * @param client        the associated client
     * @param name          the project's name
     * @param description   a brief description of the project
     * @param estimatedTime the estimated time to complete the project
     * @param hourlyRate    the hourly billing rate
     * @param fixedPrice    the fixed price for the project
     * @param createdAt     the date the project was created
     * @param deadline      the project deadline
     */
    public Project(@NotNull UUID uuid, @NotNull Client client, @NotNull String name,
                   @NotNull String description, @NotNull Duration estimatedTime,
                   double hourlyRate, double fixedPrice, @NotNull Date createdAt,
                   @NotNull Date deadline) {
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

    /**
     * Constructs a new Project with a generated UUID.
     * This constructor is typically used when creating new task types.
     *
     * @param client        the associated client
     * @param name          the project's name
     * @param description   a brief description of the project
     * @param estimatedTime the estimated time to complete the project
     * @param hourlyRate    the hourly billing rate
     * @param fixedPrice    the fixed price for the project
     * @param createdAt     the date the project was created
     * @param deadline      the project deadline
     */
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


    /**
     * Returns a string representation of the project including all its details.
     *
     * @return a string representation of the project
     */
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
