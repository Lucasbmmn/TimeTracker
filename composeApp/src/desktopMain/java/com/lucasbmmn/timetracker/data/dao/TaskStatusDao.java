package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.TaskStatus;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class TaskStatusDao implements Dao<TaskStatus> {
    private final DatabaseManager dbManager;

    /**
     * Constructs a new {@code TaskStatusDao} object
     */
    public TaskStatusDao() {
        this.dbManager = new DatabaseManager();
    }

    @Override
    public @NotNull List<TaskStatus> getAll() {
        return List.of();
    }

    @Override
    public TaskStatus getById(@NotNull String uuid) {
        String sql = "SELECT * FROM Task_Statuses WHERE id='" + uuid + "'";
        List<TaskStatus> taskStatuses = dbManager.executeQuery(sql, this::mapRow);

        TaskStatus res = null;
        if (!taskStatuses.isEmpty()) res = taskStatuses.getFirst();
        return res;
    }

    @Override
    public TaskStatus getById(@NotNull UUID uuid) {
        return this.getById(uuid.toString());
    }

    @Override
    public void insert(@NotNull TaskStatus entity) {
        // TODO: 14/06/2025 Implement method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void update(@NotNull TaskStatus entity) {
        // TODO: 14/06/2025 Implement method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(@NotNull TaskStatus entity) {
        // TODO: 14/06/2025 Implement method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Maps the current row of the given {@link ResultSet} to a {@link TaskStatusDao} object.
     *
     * <p>This method reads the relevant columns from the {@code ResultSet} and constructs
     * a new {@code TaskStatusDao} instance with the retrieved data.</p>
     *
     * @param rs the {@code ResultSet} positioned at the current row; must not be {@code null}
     * @return a {@code TaskStatusDao} object populated with data from the current row of the result set
     * @throws RuntimeException if a {@link SQLException} occurs while accessing the result set
     */
    private TaskStatus mapRow(ResultSet rs) {
        try {
            return new TaskStatus(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("label")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
