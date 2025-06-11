package com.lucasbmmn.timetracker.model;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

public class TaskTimeEntry {
    @NotNull
    private final UUID uuid;
    @NotNull
    private Task task;
    @NotNull
    private Duration duration;
    @NotNull
    private Date created_at;


    @NotNull
    public Task getTask() {
        return task;
    }

    public void setTask(@NotNull Task task) {
        // TODO: Update DB
        this.task = task;
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

    public TaskTimeEntry(@NotNull UUID uuid, @NotNull Task task, @NotNull Duration duration, @NotNull Date created_at) {
        // TODO: Select from DB
        this.uuid = uuid;
        this.task = task;
        this.duration = duration;
        this.created_at = created_at;
    }

    public TaskTimeEntry(@NotNull Task task, @NotNull Duration duration, @NotNull Date created_at) {
        // TODO: Insert into DB
        this(UUID.randomUUID(), task, duration, created_at);
    }

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
