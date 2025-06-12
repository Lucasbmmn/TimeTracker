package com.lucasbmmn.timetracker.model;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a time entry for a specific project in the time tracker application.
 * A TaskTimeEntry records the amount of time spent on a particular task,
 * along with metadata about when the entry was created and whether the time is considered
 * billable or not.
 */
public class ProjectTimeEntry {
    @NotNull
    private final UUID uuid;
    @NotNull
    private Project project;
    @NotNull
    private Duration duration;
    @NotNull
    private Date created_at;
    private boolean isBillable;


    /**
     * Gets the project associated with this time entry.
     *
     * @return the project for this time entry
     */
    @NotNull
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project associated with this time entry.
     *
     * @param project the project to associate with this time entry
     */
    public void setProject(@NotNull Project project) {
        // TODO: Update DB
        this.project = project;
    }

    /**
     * Gets the duration of time spent on the project.
     *
     * @return the duration of this time entry
     */
    @NotNull
    public Duration getDuration() {
        return duration;
    }

    /**
     * Sets the duration of time spent on the project.
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
     *
     * @param created_at the creation timestamp to set
     */
    public void setCreated_at(@NotNull Date created_at) {
        // TODO: Update DB
        this.created_at = created_at;
    }

    /**
     * Indicates whether the time entry is billable.
     *
     * @return {@code true} if billable, {@code false} otherwise
     */
    public boolean isBillable() {
        return isBillable;
    }

    /**
     * Sets whether the time entry is billable.
     *
     * @param billable {@code true} if billable, {@code false} otherwise
     */
    public void setBillable(boolean billable) {
        // TODO: Update DB
        isBillable = billable;
    }


    /**
     * Constructs a ProjectTimeEntry with an existing UUID.
     * This constructor is typically used when loading task types from a database.
     *
     * @param uuid       the unique identifier for the time entry
     * @param project    the associated project
     * @param duration   the duration of the time entry
     * @param created_at the date/time when the entry was created
     * @param isBillable whether the time is billable
     */
    public ProjectTimeEntry(@NotNull UUID uuid, @NotNull Project project, @NotNull Duration duration,
                            @NotNull Date created_at, boolean isBillable) {
        // TODO: Select from DB
        this.uuid = uuid;
        this.project = project;
        this.duration = duration;
        this.created_at = created_at;
        this.isBillable = isBillable;
    }

    /**
     * Constructs a new ProjectTimeEntry with a generated UUID.
     * This constructor is typically used when creating new task types.
     *
     * @param project    the associated project
     * @param duration   the duration of the time entry
     * @param created_at the date/time when the entry was created
     * @param isBillable whether the time is billable
     */
    public ProjectTimeEntry(@NotNull Project project, @NotNull Duration duration,
                            @NotNull Date created_at, boolean isBillable) {
        // TODO: Insert into DB
        this.uuid = UUID.randomUUID();
        this.project = project;
        this.duration = duration;
        this.created_at = created_at;
        this.isBillable = isBillable;
    }


    /**
     * Returns a string representation of the time entry including its properties.
     *
     * @return a string representation of the time entry
     */
    @Override
    public String toString() {
        return "ProjectTimeEntry{" +
                "uuid=" + uuid +
                ", project=" + project +
                ", duration=" + duration +
                ", created_at=" + created_at +
                ", isBillable=" + isBillable +
                '}';
    }
}
