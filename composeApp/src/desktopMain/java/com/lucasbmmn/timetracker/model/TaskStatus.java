package com.lucasbmmn.timetracker.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a task status in the time tracker application.
 * A task status is a categorization or state that can be assigned to tasks,
 * such as "To Do", "In Progress", or "Completed".
 *
 * <p>Each TaskStatus maintains a unique identifier, a human-readable label,
 * and a collection of tasks that are currently assigned to this status.
 */
public class TaskStatus {
    @NotNull
    private final UUID uuid;
    @NotNull
    private String label;
    @NotNull
    private final ArrayList<Task> tasks;


    /**
     * Returns the TaskStatus's uuid
     *
     * @return the uuid of the TaskStatus
     */
    public @NotNull UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the label of this task status.
     *
     * @return the label of this task status
     */
    @NotNull
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of this task status.
     *
     * @param label the new label for this task status
     */
    public void setLabel(@NotNull String label) {
        if (label == null) throw new IllegalArgumentException("TaskStatus' label can't be null");
        // TODO: Update DB
        this.label = label;
    }


    /**
     * Constructs a TaskStatus with an existing UUID and label.
     * This constructor is typically used when loading task statuses from a database.
     *
     * @param uuid the unique identifier for this task status
     * @param label the label for this task status
     */
    public TaskStatus(@NotNull UUID uuid, @NotNull String label) {
        if (uuid == null) throw new IllegalArgumentException("TaskStatus' uuid can't be null");
        this.uuid = uuid;
        this.setLabel(label);
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a new TaskStatus with a generated UUID and the specified label.
     * This constructor is typically used when creating new task statuses.
     *
     * @param label the label for this task status
     */
    public TaskStatus(@NotNull String label) {
        // TODO: Insert into DB
        this(UUID.randomUUID(), label);
    }


    /**
     * Adds a task to this task status.
     * The task will be associated with this status and included in the
     * internal collection of tasks.
     *
     * @param task the task to add to this status
     */
    public void addTask(@NotNull Task task) {
        if (task == null) throw new IllegalArgumentException("Can't add null task to TaskStatus");
        this.tasks.add(task);
    }

    /**
     * Removes a task from this task status.
     * If the task is not currently associated with this status,
     * this method has no effect.
     *
     * @param task the task to remove from this status
     */
    public void removeTask(@NotNull Task task) {
        if (task == null) throw new IllegalArgumentException("Can't remove null task from TaskStatus");
        this.tasks.remove(task);
    }

    /**
     * Returns a string representation of this TaskStatus.
     * The string includes the UUID, label, and list of tasks.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "TaskStatus{" +
                "uuid=" + uuid +
                ", label='" + label + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
