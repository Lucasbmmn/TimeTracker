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
import java.util.Objects;
import java.util.UUID;

/**
 * Data Access Object that provides CRUD (Create, Read, Update, Delete) operations for
 * {@link TaskTimeEntryDao}.
 */
public class TaskTimeEntryDao implements Dao<TaskTimeEntry> {
    private final DatabaseManager dbManager;

    /**
     * Constructs a new {@code TaskTimeEntryDao} object.
     */
    public TaskTimeEntryDao() {
        this.dbManager = new DatabaseManager();
    }

    /**
     * Retrieves all {@code TaskTimeEntry} from the data source.
     *
     * @return a list of all {@code TaskTimeEntry}; never {@code null}, may be empty
     */
    @Override
    public @NotNull List<TaskTimeEntry> getAll() {
        @Language("SQL")
        String sql = "SELECT * FROM Task_Time_Entries";
        return dbManager.executeQuery(sql, this::mapRow);
    }

    /**
     * Retrieves an {@code TaskTimeEntry} by its unique identifier represented as a String.
     *
     * @param uuid the unique identifier of the {@code TaskTimeEntry}; must not be {@code null}
     * @return the {@code TaskTimeEntry} matching the given UUID, or {@code null} if none found
        Objects.requireNonNull(uuid, "uuid must not be null");
     */
    @Override
    public TaskTimeEntry getById(@NotNull String uuid) {
        Objects.requireNonNull(uuid, " must not be null");

        @Language("SQL")
        String sql = "SELECT * FROM Task_Time_Entries WHERE id=?";
        List<TaskTimeEntry> taskTimeEntries = dbManager.executeQuery(sql, this::mapRow, uuid);

        TaskTimeEntry res = null;
        if (!taskTimeEntries.isEmpty()) res = taskTimeEntries.getFirst();
        return res;
    }

    /**
     * Retrieves an {@code TaskTimeEntry} by its unique identifier represented as a {@link UUID}.
     *
     * @param uuid the unique identifier of the {@code TaskTimeEntry}; must not be {@code null}
     * @return the {@code TaskTimeEntry} matching the given UUID, or {@code null} if none found
        Objects.requireNonNull(uuid, "uuid must not be null");
     */
    @Override
    public TaskTimeEntry getById(@NotNull UUID uuid) {
        Objects.requireNonNull(uuid, " must not be null");
        return this.getById(uuid.toString());
    }

    /**
     * Inserts a new {@code TaskTimeEntry} into the data source.
     *
     * @param entity the {@code TaskTimeEntry} to insert; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void insert(@NotNull TaskTimeEntry entity) {
        Objects.requireNonNull(entity, " must not be null");

        this.insertTask(entity);

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

    /**
     * Deletes an existing {@code TaskTimeEntry} from the data source.
     *
     * @param entity the {@code TaskTimeEntry} to delete; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void delete(@NotNull TaskTimeEntry entity) {
        Objects.requireNonNull(entity, " must not be null");

        @Language("SQL")
        String sql = "DELETE FROM Task_Time_Entries WHERE id=?";
        dbManager.executeUpdate(sql, entity.getUuid());
    }

    /**
     * Updates an existing {@code TaskTimeEntry} in the data source.
     *
     * @param entity the {@code TaskTimeEntry} to update; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void update(@NotNull TaskTimeEntry entity) {
        Objects.requireNonNull(entity, " must not be null");

        this.insertTask(entity);

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

    /**
     * Inserts TaskTimeEntry's task into the database
     *
     * @param taskTimeEntry TaskTimeEntry whose task we want to insert into the database, must not
     *                     be null
     * @throws NullPointerException if the ProjectTimeEntry is null
     */
    private void insertTask(@NotNull TaskTimeEntry taskTimeEntry) {
        Objects.requireNonNull(taskTimeEntry, "taskTimeEntry must not be null");

        TaskDao taskDao = new TaskDao();
        // TaskDao.getById returns null if the task is not in the database
        if (taskDao.getById(taskTimeEntry.getTask().getUuid()) == null) {
            taskDao.insert(taskTimeEntry.getTask());
        }
    }
}
