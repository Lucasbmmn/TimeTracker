package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.TaskType;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Data Access Object that provides CRUD (Create, Read, Update, Delete) operations for
 * {@link TaskTypeDao}.
 */
public class TaskTypeDao implements Dao<TaskType> {
    private final DatabaseManager dbManager;

    /**
     * Constructs a new {@code TaskTypeDao} object.
     */
    public TaskTypeDao() {
        this.dbManager = new DatabaseManager();
    }

    /**
     * Retrieves all {@code TaskType} from the data source.
     *
     * @return a list of all {@code TaskType}; never {@code null}, may be empty
     */
    @Override
    public @NotNull List<TaskType> getAll() {
        return List.of();
    }

    /**
     * Retrieves an {@code TaskType} by its unique identifier represented as a String.
     *
     * @param uuid the unique identifier of the {@code TaskType}; must not be {@code null}
     * @return the {@code TaskType} matching the given UUID, or {@code null} if none found
        Objects.requireNonNull(uuid, "uuid must not be null");
     */
    @Override
    public TaskType getById(@NotNull String uuid) {
        Objects.requireNonNull(uuid, " must not be null");

        @Language("SQL")
        String sql = "SELECT * FROM Task_Types WHERE id=?";
        List<TaskType> taskTypes = this.dbManager.executeQuery(sql, this::mapRow, uuid);

        TaskType res = null;
        if (!taskTypes.isEmpty()) res = taskTypes.getFirst();
        return res;
    }

    /**
     * Retrieves an {@code TaskType} by its unique identifier represented as a {@link UUID}.
     *
     * @param uuid the unique identifier of the {@code TaskType}; must not be {@code null}
     * @return the {@code TaskType} matching the given UUID, or {@code null} if none found
        Objects.requireNonNull(uuid, "uuid must not be null");
     */
    @Override
    public TaskType getById(@NotNull UUID uuid) {
        Objects.requireNonNull(uuid, " must not be null");
        return this.getById(uuid.toString());
    }

    /**
     * Inserts a new TaskType into the data source.
     *
     * @param entity the TaskType to insert; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void insert(@NotNull TaskType entity) {
        Objects.requireNonNull(entity, " must not be null");

        @Language("SQL")
        String sql = "INSERT INTO Task_Types (id, label) " +
                "VALUES (?, ?)";
        dbManager.executeUpdate(
                sql,
                entity.getUuid(),
                entity.getLabel()
        );
    }

    /**
     * Deletes an existing {@code TaskType} from the data source.
     *
     * @param entity the {@code TaskType} to delete; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void delete(@NotNull TaskType entity) {
        Objects.requireNonNull(entity, " must not be null");

        @Language("SQL")
        String sql = "DELETE FROM Task_Types WHERE id=?";
        dbManager.executeUpdate(sql, entity.getUuid());
    }

    /**
     * Updates an existing {@code TaskType} in the data source.
     *
     * @param entity the {@code TaskType} to update; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void update(@NotNull TaskType entity) {
        Objects.requireNonNull(entity, " must not be null");

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
