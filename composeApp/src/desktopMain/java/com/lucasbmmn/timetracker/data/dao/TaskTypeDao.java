package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.TaskType;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class TaskTypeDao implements Dao<TaskType> {
    private final DatabaseManager dbManager;

    /**
     * Constructs a new {@code TaskTypeDao} object
     */
    public TaskTypeDao() {
        this.dbManager = new DatabaseManager();
    }

    @Override
    public @NotNull List<TaskType> getAll() {
        return List.of();
    }

    @Override
    public TaskType getById(@NotNull String uuid) {
        String sql = "SELECT * FROM Task_Types WHERE id='" + uuid + "'";
        List<TaskType> taskTypes = this.dbManager.executeQuery(sql, this::mapRow);

        TaskType res = null;
        if (!taskTypes.isEmpty()) res = taskTypes.getFirst();
        return res;
    }

    @Override
    public TaskType getById(@NotNull UUID uuid) {
        return this.getById(uuid.toString());
    }

    @Override
    public void insert(@NotNull TaskType entity) {
        if (entity == null) throw new IllegalArgumentException("Can't insert null TaskType into the database");

        @Language("SQL")
        String sql = "INSERT INTO Task_Types (id, label) " +
                "VALUES (?, ?)";
        dbManager.executeUpdate(
                sql,
                entity.getUuid(),
                entity.getLabel()
        );
    }

    @Override
    public void delete(@NotNull TaskType entity) {
        if (entity == null) throw new IllegalArgumentException("Can't delete null TaskType from the database");

        @Language("SQL")
        String sql = "DELETE FROM Task_Types WHERE id=?";
        dbManager.executeUpdate(sql, entity.getUuid());
    }

    @Override
    public void update(@NotNull TaskType entity) {
        if (entity == null) throw new IllegalArgumentException("Can't update null TaskType");

        @Language("SQL")
        String sql = """
            UPDATE Task_Types
            SET label = ?
            WHERE id = ?
            """;
        dbManager.executeUpdate(
                sql,
                entity.getLabel(),
                entity.getUuid()
        );
    }

    /**
     * Maps the current row of the given {@link ResultSet} to a {@link TaskTypeDao} object.
     *
     * <p>This method reads the relevant columns from the {@code ResultSet} and constructs
     * a new {@code TaskTypeDao} instance with the retrieved data.</p>
     *
     * @param rs the {@code ResultSet} positioned at the current row; must not be {@code null}
     * @return a {@code TaskTypeDao} object populated with data from the current row of the result set
     * @throws RuntimeException if a {@link SQLException} occurs while accessing the result set
     */
    private TaskType mapRow(ResultSet rs) {
        try {
            return new TaskType(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("label")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
