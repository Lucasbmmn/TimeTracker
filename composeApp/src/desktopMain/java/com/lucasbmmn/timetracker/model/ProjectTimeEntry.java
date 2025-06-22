package com.lucasbmmn.timetracker.model;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
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
    private Date createdAt;
    private boolean isBillable;


    /**
     * Returns the ProjectTimeEntry's uuid
     *
     * @return the uuid of the ProjectTimeEntry
     */
    public @NotNull UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the project associated with this time entry.
     *
     * @return the project for this time entry, never null
     */
    @NotNull
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project associated with this time entry.
     *
     * @param project the project to associate with this time entry, must not be null
     * @throws NullPointerException if the project is null
     */
    public void setProject(@NotNull Project project) {
        Objects.requireNonNull(project, "project must not be null");
        this.project = project;
    }

    /**
     * Gets the duration of time spent on the project.
     *
     * @return the duration of this time entry, never null
     */
    @NotNull
    public Duration getDuration() {
        return duration;
    }

    /**
     * Sets the duration of time spent on the project.
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
     *
     * @param createdAt the creation timestamp to set, must not be null
     * @throws NullPointerException if the creation timestamp is null
     */
    public void setCreatedAt(@NotNull Date createdAt) {
        Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.createdAt = createdAt;
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
        isBillable = billable;
    }


    /**
     * Constructs a ProjectTimeEntry with an existing UUID.
     * This constructor is typically used when loading task types from a database.
     *
     * @param uuid       the unique identifier for the time entry, must not be null
     * @param project    the associated project, must not be null
     * @param duration   the duration of the time entry, must not be null
     * @param createdAt  the date/time when the entry was created, must not be null
     * @param isBillable whether the time is billable {@code true} if billable, {@code false} otherwise
     * @throws NullPointerException if the uuid, project, duration or creation date is null
     */
    public ProjectTimeEntry(@NotNull UUID uuid, @NotNull Project project,
                            @NotNull Duration duration, @NotNull Date createdAt,
                            boolean isBillable) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        this.uuid = uuid;
        this.setProject(project);
        this.setDuration(duration);
        this.setCreatedAt(createdAt);
        this.isBillable = isBillable;
    }

    /**
     * Constructs a new ProjectTimeEntry with a generated UUID.
     * This constructor is typically used when creating new task types.
     *
     * @param project    the associated project, must not be null
     * @param duration   the duration of the time entry, must not be null
     * @param createdAt the date/time when the entry was created, must not be null
     * @param isBillable whether the time is billable {@code true} if billable, {@code false} otherwise
     * @throws NullPointerException if the project, duration or creation date is null
     */
    public ProjectTimeEntry(@NotNull Project project, @NotNull Duration duration,
                            @NotNull Date createdAt, boolean isBillable) {
        this(UUID.randomUUID(), project, duration, createdAt, isBillable);
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
                ", created_at=" + createdAt +
                ", isBillable=" + isBillable +
                '}';
    }

    /**
     * Indicates whether some other object is "equal to" the ProjectTimeEntry.
     *
     * @param o the reference object with which to compare
     * @return {@code true} if the ProjectTimeEntry is the same as the {@code o} argument; {@code
     * false}
     * otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProjectTimeEntry that = (ProjectTimeEntry) o;
        return isBillable() == that.isBillable() && Objects.equals(getUuid(), that.getUuid()) && Objects.equals(getProject(), that.getProject()) && Objects.equals(getDuration(), that.getDuration()) && Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    /**
     * Returns a hash code value for the ProjectTimeEntry.
     *
     * @return a hash code value for the ProjectTimeEntry
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getProject(), getDuration(), getCreatedAt(), isBillable());
    }
}
