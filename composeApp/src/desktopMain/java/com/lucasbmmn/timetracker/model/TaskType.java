package com.lucasbmmn.timetracker.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a task type in the time tracker application.
 * A task type is a categorization that defines the nature or kind of work
 * being performed, such as "Development", "Testing", "Documentation", or "Meeting".
 *
 * <p>Each TaskType maintains a unique identifier, a human-readable label,
 * and a collection of tasks that are classified under this type.
 * Task types help organize and categorize work for better reporting and analysis.
 */
public class TaskType {
    @NotNull
    private final UUID uuid;
    @NotNull
    private String label;
    @NotNull
    private final ArrayList<Task> tasks;


    /**
     * Returns the TaskType's uuid
     *
     * @return the uuid of the TaskType
     */
    public @NotNull UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the label of this task type.
     *
     * @return the label of this task type, never null
     */
    @NotNull
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of this task type.
     * This allows renaming the task type while preserving its identity and associations.
     *
     * @param label the new label for this task type, must not be null
     */
    public void setLabel(@NotNull String label) {
        Objects.requireNonNull(label, "label must not be null");
        this.label = label;
    }


    /**
     * Constructs a TaskType with an existing UUID and label.
     * This constructor is typically used when loading task types from a database.
     *
     * @param uuid the unique identifier for this task type, must not be null
     * @param label the label for this task type, must not be null
     * @throws NullPointerException if the uuid or label is null
     */
    public TaskType(@NotNull UUID uuid,@NotNull String label) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        this.uuid = uuid;
        this.setLabel(label);
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a new TaskType with a generated UUID and the specified label.
     * This constructor is typically used when creating new task types.
     *
     * @param label the label for this task type, must not be null
     * @throws NullPointerException if the label is null
     */
    public TaskType(@NotNull String label) {
        this(UUID.randomUUID(), label);
    }


    /**
     * Adds a task to this task type.
     * The task will be classified under this type and included in the
     * internal collection of tasks.
     *
     * @param task the task to add to this type, must not be null
     * @throws NullPointerException if the task is null
     */
    public void addTask(@NotNull Task task) {
        Objects.requireNonNull(task, "task must not be null");
        this.tasks.add(task);
    }

    /**
     * Removes a task from this task type.
     * If the task is not currently classified under this type,
     * this method has no effect.
     *
     * @param task the task to remove from this type, must not be null
     */
    public void removeTask(Task task) {
        if (task == null) throw new IllegalArgumentException("Can't remove null task from TaskType");
        this.tasks.remove(task);
    }

    /**
     * Returns a string representation of this TaskType.
     * The string includes the UUID, label, and list of tasks.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "TaskType{" +
                "uuid=" + uuid +
                ", label='" + label + '\'' +
                '}';
    }

    /**
     * Indicates whether some other object is "equal to" the TaskType.
     *
     * @param o the reference object with which to compare
     * @return {@code true} if the TaskType is the same as the {@code o} argument; {@code false}
     * otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskType taskType = (TaskType) o;
        return Objects.equals(getUuid(), taskType.getUuid()) && Objects.equals(getLabel(), taskType.getLabel());
    }

    /**
     * Returns a hash code value for the TaskType.
     *
     * @return a hash code value for the TaskType
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getLabel());
    }
}
