package com.lucasbmmn.timetracker.model;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Date;
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
    @NotNull
    private Duration estimatedTime;
    private TaskStatus status;
    private TaskType type;
    @NotNull
    private Date createdAt;


    /**
     * Gets the project to which this task belongs.
     *
     * @return the project
     */
    @NotNull
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project for this task.
     *
     * @param project the project to assign
     */
    public void setProject(@NotNull Project project) {
        // TODO: Update DB
        this.project = project;
    }

    /**
     * Gets the name of this task.
     *
     * @return the task name
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this task.
     *
     * @param name the new task name
     */
    public void setName(@NotNull String name) {
        // TODO: Update DB
        this.name = name;
    }

    /**
     * Gets the task description.
     *
     * @return the description
     */
    @NotNull
    public String getDescription() {
        return description;
    }

    /**
     * Sets the task description.
     *
     * @param description the new description
     */
    public void setDescription(@NotNull String description) {
        // TODO: Update DB
        this.description = description;
    }

    /**
     * Gets the estimated time to complete this task.
     *
     * @return the estimated duration
     */
    @NotNull
    public Duration getEstimatedTime() {
        return estimatedTime;
    }

    /**
     * Sets the estimated time to complete this task.
     *
     * @param estimatedTime the new estimated duration
     */
    public void setEstimatedTime(@NotNull Duration estimatedTime) {
        // TODO: Update DB
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
        // TODO: Update DB
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
        // TODO: Update DB
        if (this.type != null) this.type.removeTask(this);
        if (type != null) type.addTask(this);
        this.type = type;
    }

    /**
     * Gets the creation date of this task.
     *
     * @return the creation timestamp
     */
    @NotNull
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation date of this task.
     *
     * @param createdAt the new creation date
     */
    public void setCreatedAt(@NotNull Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Constructs a Task with an existing UUID.
     * This constructor is typically used when loading task types from a database.
     *
     * @param uuid          the unique identifier
     * @param project       the project the task belongs to
     * @param name          the task name
     * @param description   the task description
     * @param estimatedTime the estimated duration
     * @param status        the task status
     * @param type          the task type
     * @param createdAt     the creation timestamp
     */
    public Task(@NotNull UUID uuid, @NotNull Project project, @NotNull String name,
                @NotNull String description, @NotNull Duration estimatedTime, TaskStatus status,
                TaskType type, @NotNull Date createdAt) {
        // TODO: Select from DB
        this.uuid = uuid;
        this.project = project;
        this.name = name;
        this.description = description;
        this.estimatedTime = estimatedTime;
        this.setStatus(status);
        this.setType(type);
        this.createdAt = createdAt;
    }

    /**
     * Constructs a new Task with a generated UUID.
     * This constructor is typically used when creating new task types.
     *
     * @param project       the project the task belongs to
     * @param name          the task name
     * @param description   the task description
     * @param estimatedTime the estimated duration
     * @param status        the task status
     * @param type          the task type
     * @param createdAt     the creation timestamp
     */
    public Task(@NotNull Project project, @NotNull String name, @NotNull String description,
                @NotNull Duration estimatedTime, TaskStatus status, TaskType type,
                @NotNull Date createdAt) {
        // TODO: Insert into DB
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
}
