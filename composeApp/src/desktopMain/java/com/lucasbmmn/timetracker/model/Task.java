package com.lucasbmmn.timetracker.model;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a task associated with a specific project.
 * Tasks have properties such as estimated time, status, type, and creation timestamp.
 */
public class Task {
    @NotNull
    private final UUID uuid;
    @NotNull
    private Project project;
    @NotNull
    private String name;
    @NotNull
    private String description;
    private Duration estimatedTime;
    private TaskStatus status;
    private TaskType type;
    @NotNull
    private Date createdAt;


    /**
     * Returns the task's uuid
     *
     * @return the uuid of the task
     */
    public @NotNull UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the project to which this task belongs.
     *
     * @return the project, never null
     */
    @NotNull
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project for this task.
     *
     * @param project the project to assign, must not be null
     * @throws NullPointerException if the project is null
     */
    public void setProject(@NotNull Project project) {
        Objects.requireNonNull(project, "project must not be null");
        this.project = project;
    }

    /**
     * Gets the name of this task.
     *
     * @return the task name, never null
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this task.
     *
     * @param name the new task name, must not be null
     * @throws NullPointerException if the name is null
     */
    public void setName(@NotNull String name) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
    }

    /**
     * Gets the task description.
     *
     * @return the description, never null
     */
    @NotNull
    public String getDescription() {
        return description;
    }

    /**
     * Sets the task description.
     *
     * @param description the new description, must not be null
     * @throws NullPointerException if the description is null
     */
    public void setDescription(@NotNull String description) {
        Objects.requireNonNull(description, "description must not be null");
        this.description = description;
    }

    /**
     * Gets the estimated time to complete this task.
     *
     * @return the estimated duration
     */
    public Duration getEstimatedTime() {
        return estimatedTime;
    }

    /**
     * Sets the estimated time to complete this task.
     *
     * @param estimatedTime the new estimated duration
     */
    public void setEstimatedTime(Duration estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    /**
     * Gets the current status of this task.
     *
     * @return the task status
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of this task.
     *
     * @param status the new status
     */
    public void setStatus(TaskStatus status) {
        if (this.status != null) this.status.removeTask(this);
        if (status != null) status.addTask(this);
        this.status = status;
    }

    /**
     * Gets the type of this task.
     *
     * @return the task type
     */
    public TaskType getType() {
        return type;
    }

    /**
     * Sets the type of this task.
     *
     * @param type the new task type
     */
    public void setType(TaskType type) {
        if (this.type != null) this.type.removeTask(this);
        if (type != null) type.addTask(this);
        this.type = type;
    }

    /**
     * Gets the creation date of this task.
     *
     * @return the creation timestamp, never null
     */
    @NotNull
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation date of this task.
     *
     * @param createdAt the new creation date, must not be null
     * @throws NullPointerException if the creation timestamp is null
     */
    public void setCreatedAt(@NotNull Date createdAt) {
        Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.createdAt = createdAt;
    }

    /**
     * Constructs a Task with an existing UUID.
     * This constructor is typically used when loading task types from a database.
     *
     * @param uuid          the unique identifier, must not be null
     * @param project       the project the task belongs to, must not be null
     * @param name          the task name, must not be null
     * @param description   the task description, must not be null
     * @param estimatedTime the estimated duration
     * @param status        the task status
     * @param type          the task type
     * @param createdAt     the creation timestamp, must not be null
     * @throws NullPointerException if the uuid, project, name, description, duration or creation
     * date is null
     */
    public Task(@NotNull UUID uuid, @NotNull Project project, @NotNull String name,
                @NotNull String description, Duration estimatedTime, TaskStatus status,
                TaskType type, @NotNull Date createdAt) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        this.uuid = uuid;
        this.setProject(project);
        this.setName(name);
        this.setDescription(description);
        this.estimatedTime = estimatedTime;
        this.setStatus(status);
        this.setType(type);
        this.setCreatedAt(createdAt);
    }

    /**
     * Constructs a new Task with a generated UUID.
     * This constructor is typically used when creating new task types.
     *
     * @param project       the project the task belongs to, must not be null
     * @param name          the task name, must not be null
     * @param description   the task description, must not be null
     * @param estimatedTime the estimated duration
     * @param status        the task status
     * @param type          the task type
     * @throws NullPointerException if the project, name, description, duration or creation date
     * is null
     */
    public Task(@NotNull Project project, @NotNull String name, @NotNull String description,
                Duration estimatedTime, TaskStatus status, TaskType type, @NotNull Date createdAt) {
        this(UUID.randomUUID(), project, name, description, estimatedTime, status, type, createdAt);
    }

    /**
     * Returns a string representation of the task including its attributes.
     *
     * @return a string representation of the task
     */
    @Override
    public String toString() {
        return "Task{" +
                "uuid=" + uuid +
                ", project=" + project +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", estimatedTime=" + estimatedTime +
                ", status=" + status +
                ", type=" + type +
                ", createdAt=" + createdAt +
                '}';
    }

    /**
     * Indicates whether some other object is "equal to" the task.
     *
     * @param o the reference object with which to compare
     * @return {@code true} if the task is the same as the {@code o} argument; {@code false}
     * otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(getUuid(), task.getUuid()) && Objects.equals(getProject(), task.getProject()) && Objects.equals(getName(), task.getName()) && Objects.equals(getDescription(), task.getDescription()) && Objects.equals(getEstimatedTime(), task.getEstimatedTime()) && Objects.equals(getStatus(), task.getStatus()) && Objects.equals(getType(), task.getType()) && Objects.equals(getCreatedAt(), task.getCreatedAt());
    }

    /**
     * Returns a hash code value for the task.
     *
     * @return a hash code value for the task
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getProject(), getName(), getDescription(), getEstimatedTime(), getStatus(), getType(), getCreatedAt());
    }
}
