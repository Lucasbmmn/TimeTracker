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
     */
    @Override
    public Task getById(@NotNull String uuid) {
        String sql = "SELECT * FROM Tasks WHERE id='" + uuid + "'";
        List<Task> tasks = dbManager.executeQuery(sql, this::mapRow);

        Task res = null;
        if (!tasks.isEmpty()) res = tasks.getFirst();
        return res;
    }

    /**
     * Retrieves an {@code Task} by its unique identifier represented as a {@link UUID}.
     *
     * @param uuid the unique identifier of the {@code Task}; must not be {@code null}
     * @return the {@code Task} matching the given UUID, or {@code null} if none found
     */
    @Override
    public Task getById(@NotNull UUID uuid) {
        return this.getById(uuid.toString());
    }

    /**
     * Inserts a new {@code Task} into the data source.
     *
     * @param entity the {@code Task} to insert; must not be {@code null}
     * @throws IllegalArgumentException if {@code entity} is {@code null}
     */
    @Override
    public void insert(@NotNull Task entity) {
        if (entity == null) throw new IllegalArgumentException("Can't insert task project into the database");

        @Language("SQL")
        String sql = "INSERT INTO Tasks (id, project_id, task_status_id, task_type_id, name, " +
                "description, estimated_time, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        dbManager.executeUpdate(
                sql,
                entity.getUuid(),
                entity.getProject().getUuid(),
                entity.getName(),
                entity.getDescription(),
                entity.getEstimatedTime() == null ? null : entity.getEstimatedTime().toSeconds(),
                entity.getStatus() == null ? null : entity.getStatus().getUuid(),
                entity.getType() == null ? null : entity.getType().getUuid(),
                entity.getCreatedAt().getTime()
        );
    }

    /**
     * Deletes an existing entity from the data source.
     *
     * @param entity the entity to delete; must not be {@code null}
     * @throws IllegalArgumentException if {@code entity} is {@code null}
     */
    @Override
    public void delete(@NotNull Task entity) {
        if (entity == null) throw new IllegalArgumentException("Can't delete task project from the database");

        @Language("SQL")
        String sql = "DELETE FROM Tasks WHERE id=?";
        dbManager.executeUpdate(sql, entity.getUuid());
    }

    /**
     * Updates an existing entity in the data source.
     *
     * @param entity the entity to update; must not be {@code null}
     * @throws IllegalArgumentException if {@code entity} is {@code null}
     */
    @Override
    public void update(@NotNull Task entity) {
        if (entity == null) throw new IllegalArgumentException("Can't update task project");

        @Language("SQL")
        String sql = """
            UPDATE Tasks
            SET id = ?,
                project_id = ?,
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
                entity.getName(),
                entity.getDescription(),
                entity.getEstimatedTime() == null ? null : entity.getEstimatedTime().toSeconds(),
                entity.getStatus() == null ? null : entity.getStatus().getUuid(),
                entity.getType() == null ? null : entity.getType().getUuid(),
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
}
