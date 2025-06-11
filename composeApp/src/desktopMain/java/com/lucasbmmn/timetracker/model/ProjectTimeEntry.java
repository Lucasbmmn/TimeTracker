package com.lucasbmmn.timetracker.model;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

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


    @NotNull
    public Project getProject() {
        return project;
    }

    public void setProject(@NotNull Project project) {
        // TODO: Update DB
        this.project = project;
    }

    @NotNull
    public Duration getDuration() {
        return duration;
    }

    public void setDuration(@NotNull Duration duration) {
        // TODO: Update DB
        this.duration = duration;
    }

    @NotNull
    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(@NotNull Date created_at) {
        // TODO: Update DB
        this.created_at = created_at;
    }

    public boolean isBillable() {
        return isBillable;
    }

    public void setBillable(boolean billable) {
        // TODO: Update DB
        isBillable = billable;
    }

    public ProjectTimeEntry(@NotNull UUID uuid, @NotNull Project project, @NotNull Duration duration,
                            @NotNull Date created_at, boolean isBillable) {
        // TODO: Select from DB
        this.uuid = uuid;
        this.project = project;
        this.duration = duration;
        this.created_at = created_at;
        this.isBillable = isBillable;
    }

    public ProjectTimeEntry(@NotNull Project project, @NotNull Duration duration,
                            @NotNull Date created_at, boolean isBillable) {
        // TODO: Insert into DB
        this.uuid = UUID.randomUUID();
        this.project = project;
        this.duration = duration;
        this.created_at = created_at;
        this.isBillable = isBillable;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProjectTimeEntry.class.getSimpleName() + "[", "]")
                .add("uuid=" + uuid)
                .add("task=" + project)
                .add("duration=" + duration)
                .add("created_at=" + created_at)
                .add("isBillable=" + isBillable)
                .toString();
    }
}
