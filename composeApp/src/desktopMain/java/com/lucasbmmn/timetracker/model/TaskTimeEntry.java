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
    private Date created_at;


    /**
     * Gets the task associated with this time entry.
     *
     * @return the task for this time entry
     */
    @NotNull
    public Task getTask() {
        return task;
    }

    /**
     * Sets the task associated with this time entry.
     *
     * @param task the task to associate with this time entry
     */
    public void setTask(@NotNull Task task) {
        // TODO: Update DB
        this.task = task;
    }

    /**
     * Gets the duration of time spent on the task.
     *
     * @return the duration of this time entry
     */
    @NotNull
    public Duration getDuration() {
        return duration;
    }

    /**
     * Sets the duration of time spent on the task.
     * This allows correcting or updating the time spent if necessary.
     *
     * @param duration the duration to set for this time entry
     */
    public void setDuration(@NotNull Duration duration) {
        // TODO: Update DB
        this.duration = duration;
    }

    /**
     * Gets the creation timestamp of this time entry.
     *
     * @return the date and time when this entry was created
     */
    @NotNull
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * Sets the creation timestamp of this time entry.
     * This allows correcting the timestamp if necessary.
     *
     * @param created_at the creation timestamp to set
     */
    public void setCreated_at(@NotNull Date created_at) {
        // TODO: Update DB
        this.created_at = created_at;
    }

    /**
     * Constructs a TaskTimeEntry with an existing UUID.
     * This constructor is typically used when loading time entries from a database.
     *
     * @param uuid the unique identifier for this time entry
     * @param task the task this time entry is associated with
     * @param duration the duration of time spent on the task
     * @param created_at the timestamp when this entry was created
     */
    public TaskTimeEntry(@NotNull UUID uuid, @NotNull Task task, @NotNull Duration duration, @NotNull Date created_at) {
        this.uuid = uuid;
        this.task = task;
        this.duration = duration;
        this.created_at = created_at;
    }

    /**
     * Constructs a new TaskTimeEntry with a generated UUID.
     * This constructor is typically used when creating new time entries.
     *
     * @param task the task this time entry is associated with
     * @param duration the duration of time spent on the task
     * @param created_at the timestamp when this entry was created
     */
    public TaskTimeEntry(@NotNull Task task, @NotNull Duration duration, @NotNull Date created_at) {
        // TODO: Insert into DB
        this(UUID.randomUUID(), task, duration, created_at);
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
                ", created_at=" + created_at +
                '}';
    }
}
