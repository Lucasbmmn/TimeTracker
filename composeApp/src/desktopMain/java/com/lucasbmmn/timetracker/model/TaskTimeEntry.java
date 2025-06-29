package com.lucasbmmn.timetracker.model;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
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
     * @throws NullPointerException if the null is null
     */
    public void setTask(@NotNull Task task) {
        Objects.requireNonNull(task, "task must not be null");
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
     * @throws NullPointerException if the duration is null
     */
    public void setDuration(@NotNull Duration duration) {
        Objects.requireNonNull(duration, "duration must not be null");
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
     * @throws NullPointerException if the creation timestamp is null
     */
    public void setCreatedAt(@NotNull Date createdAt) {
        Objects.requireNonNull(createdAt, "createdAt must not be null");
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
     * @throws NullPointerException if the uuid, task, duration or creation timestamp is null
     */
    public TaskTimeEntry(@NotNull UUID uuid, @NotNull Task task, @NotNull Duration duration,
                         @NotNull Date createdAt) {
        Objects.requireNonNull(uuid, "uuid must not be null");
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
     * @throws NullPointerException if the task, duration or creation timestamp is null
     */
    public TaskTimeEntry(@NotNull Task task, @NotNull Duration duration, @NotNull Date createdAt) {
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

    /**
     * Indicates whether some other object is "equal to" the TaskTimeEntry.
     *
     * @param o the reference object with which to compare
     * @return {@code true} if the TaskTimeEntry is the same as the {@code o} argument; {@code
     * false}
     * otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskTimeEntry that = (TaskTimeEntry) o;
        return Objects.equals(getUuid(), that.getUuid()) && Objects.equals(getTask(), that.getTask()) && Objects.equals(getDuration(), that.getDuration()) && Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    /**
     * Returns a hash code value for the TaskTimeEntry.
     *
     * @return a hash code value for the TaskTimeEntry
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getTask(), getDuration(), getCreatedAt());
    }
}
