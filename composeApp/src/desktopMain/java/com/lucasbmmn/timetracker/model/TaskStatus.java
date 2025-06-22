package com.lucasbmmn.timetracker.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
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
     * @throws NullPointerException if the label is null
     */
    public void setLabel(@NotNull String label) {
        Objects.requireNonNull(label, "label must not be null");
        this.label = label;
    }


    /**
     * Constructs a TaskStatus with an existing UUID and label.
     * This constructor is typically used when loading task statuses from a database.
     *
     * @param uuid the unique identifier for this task status
     * @param label the label for this task status
     * @throws NullPointerException if the uuid or label is null
     */
    public TaskStatus(@NotNull UUID uuid, @NotNull String label) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        this.uuid = uuid;
        this.setLabel(label);
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a new TaskStatus with a generated UUID and the specified label.
     * This constructor is typically used when creating new task statuses.
     *
     * @param label the label for this task status
     * @throws NullPointerException if the label is null
     */
    public TaskStatus(@NotNull String label) {
        this(UUID.randomUUID(), label);
    }


    /**
     * Adds a task to this task status.
     * The task will be associated with this status and included in the
     * internal collection of tasks.
     *
     * @param task the task to add to this status
     * @throws NullPointerException if the task is null
     */
    public void addTask(@NotNull Task task) {
        Objects.requireNonNull(task, "task must not be null");
        this.tasks.add(task);
    }

    /**
     * Removes a task from this task status.
     * If the task is not currently associated with this status,
     * this method has no effect.
     *
     * @param task the task to remove from this status
     * @throws NullPointerException if the task is null
     */
    public void removeTask(Task task) {
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
                '}';
    }

    /**
     * Indicates whether some other object is "equal to" the TaskStatus.
     *
     * @param o the reference object with which to compare
     * @return {@code true} if the TaskStatus is the same as the {@code o} argument; {@code false}
     * otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskStatus that = (TaskStatus) o;
        return Objects.equals(getUuid(), that.getUuid()) && Objects.equals(getLabel(), that.getLabel());
    }

    /**
     * Returns a hash code value for the TaskStatus.
     *
     * @return a hash code value for the TaskStatus
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getLabel());
    }
}
