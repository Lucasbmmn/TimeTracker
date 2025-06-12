package com.lucasbmmn.timetracker.model;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a time entry for a specific task in the time tracker application.
 * A TaskTimeEntry records the amount of time spent on a particular task,
 * along with metadata about when the entry was created.
 */
public class TaskTimeEntry {
    @NotNull
    private final UUID uuid;
    @NotNull
    private Task task;
    @NotNull
    private Duration duration;
    @NotNull
    private Date createdAt;


    /**
     * Returns the TaskTimeEntry's uuid
     *
     * @return the uuid of the TaskTimeEntry
     */
    public @NotNull UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the task associated with this time entry.
     *
     * @return the task for this time entry, never null
     */
    @NotNull
    public Task getTask() {
        return task;
    }

    /**
     * Sets the task associated with this time entry.
     *
     * @param task the task to associate with this time entry, must not be null
     */
    public void setTask(@NotNull Task task) {
        if (task == null) throw new IllegalArgumentException("TaskTimeEntry's task can't be null");
        // TODO: Update DB
        this.task = task;
    }

    /**
     * Gets the duration of time spent on the task.
     *
     * @return the duration of this time entry, never null
     */
    @NotNull
    public Duration getDuration() {
        return duration;
    }

    /**
     * Sets the duration of time spent on the task.
     * This allows correcting or updating the time spent if necessary.
     *
     * @param duration the duration to set for this time entry, must not be null
     */
    public void setDuration(@NotNull Duration duration) {
        if (duration == null) throw new IllegalArgumentException("TaskTimeEntry's duration can't be null");
        // TODO: Update DB
        this.duration = duration;
    }

    /**
     * Gets the creation timestamp of this time entry.
     *
     * @return the date and time when this entry was created, never null
     */
    @NotNull
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of this time entry.
     * This allows correcting the timestamp if necessary.
     *
     * @param createdAt the creation timestamp to set, must not be null
     */
    public void setCreatedAt(@NotNull Date createdAt) {
        if (createdAt == null) throw new IllegalArgumentException("TaskTimeEntry's creation date can't be null");
        // TODO: Update DB
        this.createdAt = createdAt;
    }

    /**
     * Constructs a TaskTimeEntry with an existing UUID.
     * This constructor is typically used when loading time entries from a database.
     *
     * @param uuid the unique identifier for this time entry, must not be null
     * @param task the task this time entry is associated with, must not be null
     * @param duration the duration of time spent on the task, must not be null
     * @param createdAt the timestamp when this entry was created, must not be null
     */
    public TaskTimeEntry(@NotNull UUID uuid, @NotNull Task task, @NotNull Duration duration, @NotNull Date createdAt) {
        if (uuid == null) throw new IllegalArgumentException("TaskTimeEntry's uuid can't be null");
        this.uuid = uuid;
        this.setTask(task);
        this.setDuration(duration);
        this.setCreatedAt(createdAt);
    }

    /**
     * Constructs a new TaskTimeEntry with a generated UUID.
     * This constructor is typically used when creating new time entries.
     *
     * @param task the task this time entry is associated with, must not be null
     * @param duration the duration of time spent on the task, must not be null
     * @param createdAt the timestamp when this entry was created, must not be null
     */
    public TaskTimeEntry(@NotNull Task task, @NotNull Duration duration, @NotNull Date createdAt) {
        // TODO: Insert into DB
        this(UUID.randomUUID(), task, duration, createdAt);
    }

    /**
     * Returns a string representation of the time entry including its properties.
     *
     * @return a string representation of the time entry
     */
    @Override
    public String toString() {
        return "TaskTimeEntry{" +
                "uuid=" + uuid +
                ", task=" + task +
                ", duration=" + duration +
                ", created_at=" + createdAt +
                '}';
    }
}
