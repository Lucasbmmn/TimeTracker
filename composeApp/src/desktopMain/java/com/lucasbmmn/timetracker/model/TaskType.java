package com.lucasbmmn.timetracker.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.UUID;

public class TaskType {
    @NotNull
    private final UUID uuid;
    @NotNull
    private String label;
    @NotNull
    private final ArrayList<Task> tasks;


    @NotNull
    public String getLabel() {
        return label;
    }

    public void setLabel(@NotNull String label) {
        // TODO: Update DB
        this.label = label;
    }


    public TaskType(@NotNull UUID uuid,@NotNull String label) {
        // TODO: Select from DB
        this.uuid = uuid;
        this.label = label;
        this.tasks = new ArrayList<>();
    }

    public TaskType(@NotNull String label) {
        // TODO: Insert into DB
        this.uuid = UUID.randomUUID();
        this.label = label;
        this.tasks = new ArrayList<>();
    }


    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    @Override
    public String toString() {
        return "TaskType{" +
                "uuid=" + uuid +
                ", label='" + label + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
