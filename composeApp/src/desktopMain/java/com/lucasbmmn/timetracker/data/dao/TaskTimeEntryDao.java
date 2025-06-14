package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.TaskTimeEntry;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskTimeEntryDao implements Dao<TaskTimeEntry> {
    private final DatabaseManager dbManager;

    /**
     * Constructs a new {@code TaskTimeEntryDao} object
     */
    public TaskTimeEntryDao() {
        this.dbManager = new DatabaseManager();
    }

    @Override
    public @NotNull List<TaskTimeEntry> getAll() {
        @Language("SQL")
        String sql = "SELECT * FROM Task_Time_Entries";
        return dbManager.executeQuery(sql, this::mapRow);
    }

    @Override
    public TaskTimeEntry getById(@NotNull String uuid) {
        String sql = "SELECT * FROM Task_Time_Entries WHERE id='" + uuid + "'";
        List<TaskTimeEntry> taskTimeEntries = dbManager.executeQuery(sql, this::mapRow);

        TaskTimeEntry res = null;
        if (!taskTimeEntries.isEmpty()) res = taskTimeEntries.getFirst();
        return res;
    }

    @Override
    public TaskTimeEntry getById(@NotNull UUID uuid) {
        return this.getById(uuid.toString());
    }

    @Override
    public void insert(@NotNull TaskTimeEntry entity) {
        if (entity == null) throw new IllegalArgumentException("Can't insert null TaskTimeEntry into the database");

        // TODO: 14/06/2025 Insert task into the db if it's not already

        @Language("SQL")
        String sql = "INSERT INTO Task_Time_Entries (id, task_id, duration, created_at) " +
                "VALUES (?, ?, ?, ?)";
        dbManager.executeUpdate(
                sql,
                entity.getUuid(),
                entity.getDuration().getSeconds(),
                entity.getCreatedAt().getTime()
        );
    }

    @Override
    public void delete(@NotNull TaskTimeEntry entity) {
        if (entity == null) throw new IllegalArgumentException("Can't delete null TaskTimeEntry from the database");

        @Language("SQL")
        String sql = "DELETE FROM Task_Time_Entries WHERE id=?";
        dbManager.executeUpdate(sql, entity.getUuid());
    }

    @Override
    public void update(@NotNull TaskTimeEntry entity) {
        if (entity == null) throw new IllegalArgumentException("Can't update null TaskTimeEntry");

        // TODO: 14/06/2025 Insert task into the db if it's not already

        @Language("SQL")
        String sql = """
            UPDATE Task_Time_Entries
            SET task_id = ?,
                duration = ?,
                created_at = ?
            WHERE id = ?
            """;
        dbManager.executeUpdate(
                sql,
                entity.getTask().getUuid(),
                entity.getDuration().getSeconds(),
                entity.getCreatedAt().getTime(),
                entity.getUuid()
        );
    }

    /**
     * Maps the current row of the given {@link ResultSet} to a {@link TaskTimeEntryDao} object.
     *
     * <p>This method reads the relevant columns from the {@code ResultSet} and constructs
     * a new {@code TaskTimeEntryDao} instance with the retrieved data.</p>
     *
     * @param rs the {@code ResultSet} positioned at the current row; must not be {@code null}
     * @return a {@code TaskTimeEntryDao} object populated with data from the current row of the result set
     * @throws RuntimeException if a {@link SQLException} occurs while accessing the result set
     */
    private TaskTimeEntry mapRow(ResultSet rs) {
        TaskDao taskDao = new TaskDao();
        try {
            return new TaskTimeEntry(
                    UUID.fromString(rs.getString("id")),
                    taskDao.getById(rs.getString("task_id")),
                    Duration.ofSeconds(rs.getLong("duration")),
                    new Date(rs.getLong("created_at"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
