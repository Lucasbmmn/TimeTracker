package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.Task;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskDao implements Dao<Task> {
    private final DatabaseManager dbManager;

    /**
     * Constructs a new {@code TaskDao} object
     */
    public TaskDao() {
        this.dbManager = new DatabaseManager();
    }

    @Override
    public @NotNull List<Task> getAll() {
        return List.of();
    }

    @Override
    public Task getById(@NotNull String uuid) {
        String sql = "SELECT * FROM Tasks WHERE id='" + uuid + "'";
        List<Task> tasks = dbManager.executeQuery(sql, this::mapRow);

        Task res = null;
        if (!tasks.isEmpty()) res = tasks.getFirst();
        return res;
    }

    @Override
    public Task getById(@NotNull UUID uuid) {
        return this.getById(uuid.toString());
    }

    @Override
    public void insert(@NotNull Task entity) {
        // TODO: 14/06/2025 Implement method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void update(@NotNull Task entity) {
        // TODO: 14/06/2025 Implement method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(@NotNull Task entity) {
        // TODO: 14/06/2025 Implement method
        throw new UnsupportedOperationException("Not implemented yet");
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
