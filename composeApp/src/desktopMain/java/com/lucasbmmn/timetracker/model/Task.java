package com.lucasbmmn.timetracker.model;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

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

    @NotNull
    public Project getProject() {
        return project;
    }

    public void setProject(@NotNull Project project) {
        // TODO: Update DB
        this.project = project;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        // TODO: Update DB
        this.name = name;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        // TODO: Update DB
        this.description = description;
    }

    @NotNull
    public Duration getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(@NotNull Duration estimatedTime) {
        // TODO: Update DB
        this.estimatedTime = estimatedTime;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        // TODO: Update DB
        if (this.status != null) this.status.removeTask(this);
        if (status != null) status.addTask(this);
        this.status = status;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        // TODO: Update DB
        if (this.type != null) this.type.removeTask(this);
        if (type != null) type.addTask(this);
        this.type = type;
    }

    @NotNull
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull Date createdAt) {
        this.createdAt = createdAt;
    }

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

    public Task(@NotNull Project project, @NotNull String name, @NotNull String description,
                @NotNull Duration estimatedTime, TaskStatus status, TaskType type,
                @NotNull Date createdAt) {
        // TODO: Insert into DB
        this(UUID.randomUUID(), project, name, description, estimatedTime, status, type, createdAt);
    }

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
