package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.Task;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Data Access Object that provides CRUD (Create, Read, Update, Delete) operations for
 * {@link TaskDao}.
 */
public class TaskDao implements Dao<Task> {
    private final DatabaseManager dbManager;

    /**
     * Constructs a new {@code TaskDao} object.
     */
    public TaskDao() {
        this.dbManager = new DatabaseManager();
    }

    /**
     * Retrieves all {@code Task} from the data source.
     *
     * @return a list of all {@code Task}; never {@code null}, may be empty
     */
    @Override
    public @NotNull List<Task> getAll() {
        @Language("SQL")
        String sql = "SELECT * FROM Tasks";
        return dbManager.executeQuery(sql, this::mapRow);
    }

    /**
     * Retrieves an {@code Task} by its unique identifier represented as a String.
     *
     * @param uuid the unique identifier of the {@code Task}; must not be {@code null}
     * @return the {@code Task} matching the given UUID, or {@code null} if none found
        Objects.requireNonNull(uuid, "uuid must not be null");
     */
    @Override
    public Task getById(@NotNull String uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");

        @Language("SQL")
        String sql = "SELECT * FROM Tasks WHERE id=?";
        List<Task> tasks = dbManager.executeQuery(sql, this::mapRow, uuid);

        Task res = null;
        if (!tasks.isEmpty()) res = tasks.getFirst();
        return res;
    }

    /**
     * Retrieves an {@code Task} by its unique identifier represented as a {@link UUID}.
     *
     * @param uuid the unique identifier of the {@code Task}; must not be {@code null}
     * @return the {@code Task} matching the given UUID, or {@code null} if none found
        Objects.requireNonNull(uuid, "uuid must not be null");
     */
    @Override
    public Task getById(@NotNull UUID uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        return this.getById(uuid.toString());
    }

    /**
     * Inserts a new {@code Task} into the data source.
     *
     * @param entity the {@code Task} to insert; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void insert(@NotNull Task entity) {
        Objects.requireNonNull(entity, "entity must not be null");

        this.insertRelatedEntities(entity);

        @Language("SQL")
        String sql = "INSERT INTO Tasks (id, project_id, task_status_id, task_type_id, name, " +
                "description, estimated_time, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        dbManager.executeUpdate(
                sql,
                entity.getUuid(),
                entity.getProject().getUuid(),
                entity.getStatus() == null ? null : entity.getStatus().getUuid(),
                entity.getType() == null ? null : entity.getType().getUuid(),
                entity.getName(),
                entity.getDescription(),
                entity.getEstimatedTime() == null ? null : entity.getEstimatedTime().toSeconds(),
                entity.getCreatedAt().getTime()
        );
    }

    /**
     * Deletes an existing entity from the data source.
     *
     * @param entity the entity to delete; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void delete(@NotNull Task entity) {
        Objects.requireNonNull(entity, "entity must not be null");

        @Language("SQL")
        String sql = "DELETE FROM Tasks WHERE id=?";
        dbManager.executeUpdate(sql, entity.getUuid());
    }

    /**
     * Updates an existing entity in the data source.
     *
     * @param entity the entity to update; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void update(@NotNull Task entity) {
        Objects.requireNonNull(entity, "entity must not be null");

        this.insertRelatedEntities(entity);

        @Language("SQL")
        String sql = """
            UPDATE Tasks
            SET project_id = ?,
                task_status_id = ?,
                task_type_id = ?,
                name = ?,
                description = ?,
                estimated_time = ?,
                created_at = ?
            WHERE id = ?
            """;
        dbManager.executeUpdate(
                sql,
                entity.getProject().getUuid(),
                entity.getStatus() == null ? null : entity.getStatus().getUuid(),
                entity.getType() == null ? null : entity.getType().getUuid(),
                entity.getName(),
                entity.getDescription(),
                entity.getEstimatedTime() == null ? null : entity.getEstimatedTime().toSeconds(),
                entity.getCreatedAt().getTime(),
                entity.getUuid()
        );
    }

    /**
     * Maps the current row of the given {@link ResultSet} to a {@link TaskDao} object.
     *
     * <p>This method reads the relevant columns from the {@code ResultSet} and constructs
     * a new {@code TaskDao} instance with the retrieved data.</p>
     *
     * @param rs the {@code ResultSet} positioned at the current row; must not be {@code null}
     * @return a {@code TaskDao} object populated with data from the current row of the result set
     * @throws RuntimeException if a {@link SQLException} occurs while accessing the result set
     */
    private Task mapRow(ResultSet rs) {
        ProjectDao projectDao = new ProjectDao();
        TaskStatusDao taskStatusDao = new TaskStatusDao();
        TaskTypeDao taskTypeDao = new TaskTypeDao();
        try {
            return new Task(
                    UUID.fromString(rs.getString("id")),
                    projectDao.getById(rs.getString("project_id")),
                    rs.getString("name"),
                    rs.getString("description"),
                    Duration.ofSeconds(rs.getLong("estimated_time")),
                    taskStatusDao.getById(rs.getString("task_status_id")),
                    taskTypeDao.getById(rs.getString("task_type_id")),
                    new Date(rs.getLong("created_at"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserts task's project, status and type into the database.
     *
     * @param task Task whose project, status and type we want to insert into the database,
     * must not be {@code null}
     * @throws NullPointerException if the task is {@code null}
     */
    private void insertRelatedEntities(@NotNull Task task) {
        Objects.requireNonNull(task, "task must not be null");

        ProjectDao projectDao = new ProjectDao();
        // ProjectDao.getById returns null if the project is not in the database
        if (projectDao.getById(task.getProject().getUuid()) == null) {
            projectDao.insert(task.getProject());
        }

        if (task.getStatus() != null) {
            TaskStatusDao taskStatusDao = new TaskStatusDao();
            // TaskStatusDao.getById returns null if the TaskStatus is not in the database
            if (taskStatusDao.getById(task.getStatus().getUuid()) == null) {
                taskStatusDao.insert(task.getStatus());
            }
        }

        if (task.getType() != null) {
            TaskTypeDao taskTypeDao = new TaskTypeDao();
            // TaskTypeDao.getById returns null if the TaskType is not in the database
            if (taskTypeDao.getById(task.getType().getUuid()) == null) {
                taskTypeDao.insert(task.getType());
            }
        }
    }
}
