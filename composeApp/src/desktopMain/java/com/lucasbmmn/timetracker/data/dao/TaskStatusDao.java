package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.TaskStatus;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Data Access Object that provides CRUD (Create, Read, Update, Delete) operations for
 * {@link TaskStatusDao}.
 */
public class TaskStatusDao implements Dao<TaskStatus> {
    private final DatabaseManager dbManager;

    /**
     * Constructs a new {@code TaskStatusDao} object.
     */
    public TaskStatusDao() {
        this.dbManager = new DatabaseManager();
    }

    /**
     * Retrieves all {@code TaskStatus} from the data source.
     *
     * @return a list of all {@code TaskStatus}; never {@code null}, may be empty
     */
    @Override
    public @NotNull List<TaskStatus> getAll() {
        @Language("SQL")
        String sql = "SELECT * FROM Task_Statuses";
        return dbManager.executeQuery(sql, this::mapRow);
    }

    /**
     * Retrieves an {@code TaskStatus} by its unique identifier represented as a String.
     *
     * @param uuid the unique identifier of the {@code TaskStatus}
     * @return the {@code TaskStatus} matching the given UUID, or {@code null} if none found
     */
    @Override
    public TaskStatus getById(String uuid) {
        TaskStatus res = null;

        if (uuid != null) {
            @Language("SQL")
            String sql = "SELECT * FROM Task_Statuses WHERE id=?";
            List<TaskStatus> taskStatuses = dbManager.executeQuery(sql, this::mapRow, uuid);

            if (!taskStatuses.isEmpty()) res = taskStatuses.getFirst();
        }
        return res;
    }

    /**
     * Retrieves an {@code TaskStatus} by its unique identifier represented as a {@link UUID}.
     *
     * @param uuid the unique identifier of the {@code TaskStatus}
     * @return the {@code TaskStatus} matching the given UUID, or {@code null} if none found
     */
    @Override
    public TaskStatus getById(UUID uuid) {
        TaskStatus res = null;
        if (uuid != null) res = this.getById(uuid.toString());
        return res;
    }

    /**
     * Inserts a new {@code TaskStatus} into the data source.
     *
     * @param entity the {@code TaskStatus} to insert; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void insert(@NotNull TaskStatus entity) {
        Objects.requireNonNull(entity, "entity must not be null");

        @Language("SQL")
        String sql = "INSERT INTO Task_Statuses (id, label) " +
                "VALUES (?, ?)";
        dbManager.executeUpdate(
                sql,
                entity.getUuid(),
                entity.getLabel()
        );
    }

    /**
     * Deletes an existing {@code TaskStatus} from the data source.
     *
     * @param entity the {@code TaskStatus} to delete; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void delete(@NotNull TaskStatus entity) {
        Objects.requireNonNull(entity, "entity must not be null");

        @Language("SQL")
        String sql = "DELETE FROM Task_Statuses WHERE id=?";
        dbManager.executeUpdate(sql, entity.getUuid());
    }

    /**
     * Updates an existing {@code TaskStatus} in the data source.
     *
     * @param entity the {@code TaskStatus} to update; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void update(@NotNull TaskStatus entity) {
        Objects.requireNonNull(entity, "entity must not be null");

        @Language("SQL")
        String sql = """
            UPDATE Task_Statuses
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
