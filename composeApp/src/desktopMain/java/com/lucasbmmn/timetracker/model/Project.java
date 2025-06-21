package com.lucasbmmn.timetracker.model;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
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
    private Duration estimatedTime;
    private double hourlyRate;
    private double fixedPrice;
    @NotNull
    private Date createdAt;
    private Date deadline;


    /**
     * Returns the project's uuid
     *
     * @return the uuid of the project
     */
    public @NotNull UUID getUuid() {
        return uuid;
    }

    /**
     * Returns the client associated with the project.
     *
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Sets the client associated with the project.
     *
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Returns the name of the project.
     *
     * @return the project name, never null
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the project.
     *
     * @param name the new name, must not be null
     */
    public void setName(@NotNull String name) {
        if (name == null) throw new IllegalArgumentException("Project's name can't be null");
        this.name = name;
    }

    /**
     * Returns the description of the project.
     *
     * @return the project description, never null
     */
    @NotNull
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the project.
     *
     * @param description the new description, must not be null
     */
    public void setDescription(@NotNull String description) {
        if (description == null) throw new IllegalArgumentException("Project's description can't be null");
        this.description = description;
    }

    /**
     * Returns the estimated time required to complete the project.
     *
     * @return the estimated duration
     */
    public Duration getEstimatedTime() {
        return estimatedTime;
    }

    /**
     * Sets the estimated time to complete the project.
     *
     * @param estimatedTime the estimated duration
     */
    public void setEstimatedTime(Duration estimatedTime) {
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
     * @throws IllegalArgumentException if the rate is negative
     */
    public void setHourlyRate(double hourlyRate) {
        if (hourlyRate < 0)
            throw new IllegalArgumentException("The hourly rate must be greater or equals to " +
                    "0: " + hourlyRate);
        this.hourlyRate = hourlyRate;
    }

    /**
     * Returns the fixed price for the project.
     *
     * @return the fixed price, never null
     */
    public double getFixedPrice() {
        return fixedPrice;
    }

    /**
     * Sets the fixed price for the project. Must be non-negative.
     *
     * @param fixedPrice the new fixed price
     * @throws IllegalArgumentException if the price is negative
     */
    public void setFixedPrice(double fixedPrice) {
        if (fixedPrice < 0)
            throw new IllegalArgumentException("The hourly rate must be greater or equals to " +
                    "0: " + fixedPrice);
        this.fixedPrice = fixedPrice;
    }


    /**
     * Returns the date the project was created.
     *
     * @return the creation date, never null
     */
    @NotNull
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation date of the project.
     *
     * @param createdAt the new creation date, must not be null
     */
    public void setCreatedAt(@NotNull Date createdAt) {
        if (createdAt == null) throw new IllegalArgumentException("Project's creating date can't be null");
        this.createdAt = createdAt;
    }

    /**
     * Returns the project's deadline.
     *
     * @return the deadline
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * Sets the project's deadline.
     *
     * @param deadline the new deadline
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }


    /**
     * Constructs a Project using an existing UUID.
     * This constructor is typically used when loading task types from a database.
     *
     * @param uuid          the unique identifier of the project, must not be null
     * @param client        the associated client
     * @param name          the project's name, must not be null
     * @param description   a brief description of the project, must not be null
     * @param estimatedTime the estimated time to complete the project
     * @param hourlyRate    the hourly billing rate
     * @param fixedPrice    the fixed price for the project
     * @param createdAt     the date the project was created, must not be null
     * @param deadline      the project deadline
     */
    public Project(@NotNull UUID uuid, Client client, @NotNull String name,
                   @NotNull String description, Duration estimatedTime,
                   double hourlyRate, double fixedPrice, @NotNull Date createdAt,
                   Date deadline) {
        if (uuid == null) throw new IllegalArgumentException("Project's uuid can't be null");
        this.uuid = uuid;
        this.client = client;
        this.setName(name);
        this.setDescription(description);
        this.estimatedTime = estimatedTime;
        this.hourlyRate = hourlyRate;
        this.fixedPrice = fixedPrice;
        this.setCreatedAt(createdAt);
        this.deadline = deadline;
    }

    /**
     * Constructs a new Project with a generated UUID.
     * This constructor is typically used when creating new task types.
     *
     * @param client        the associated client
     * @param name          the project's name, must not be null
     * @param description   a brief description of the project, must not be null
     * @param estimatedTime the estimated time to complete the project
     * @param hourlyRate    the hourly billing rate
     * @param fixedPrice    the fixed price for the project
     * @param createdAt     the date the project was created, must not be null
     * @param deadline      the project deadline
     */
    public Project(Client client, @NotNull String name, @NotNull String description,
                   Duration estimatedTime, double hourlyRate, double fixedPrice,
                   @NotNull Date createdAt, Date deadline) {
        this(UUID.randomUUID(), client, name, description, estimatedTime, hourlyRate, fixedPrice,
                createdAt, deadline);
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

    /**
     * Indicates whether some other object is "equal to" the project.
     *
     * @param o the reference object with which to compare
     * @return {@code true} if the project is the same as the {@code o} argument; {@code false}
     * otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Double.compare(getHourlyRate(), project.getHourlyRate()) == 0 && Double.compare(getFixedPrice(), project.getFixedPrice()) == 0 && Objects.equals(getUuid(), project.getUuid()) && Objects.equals(getClient(), project.getClient()) && Objects.equals(getName(), project.getName()) && Objects.equals(getDescription(), project.getDescription()) && Objects.equals(getEstimatedTime(), project.getEstimatedTime()) && Objects.equals(getCreatedAt(), project.getCreatedAt()) && Objects.equals(getDeadline(), project.getDeadline());
    }

    /**
     * Returns a hash code value for the project.
     *
     * @return a hash code value for the project
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getClient(), getName(), getDescription(), getEstimatedTime(), getHourlyRate(), getFixedPrice(), getCreatedAt(), getDeadline());
    }
}
