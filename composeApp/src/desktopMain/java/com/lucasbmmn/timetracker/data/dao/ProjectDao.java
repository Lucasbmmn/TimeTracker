package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.Project;
import com.lucasbmmn.timetracker.model.TaskStatus;
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
 * {@link ProjectDao}.
 */
public class ProjectDao implements Dao<Project> {
    private final DatabaseManager dbManager;

    /**
     * Constructs a new {@code ProjectDao} object.
     */
    public ProjectDao() {
        this.dbManager = new DatabaseManager();
    }

    /**
     * Retrieves all {@code Project} from the data source.
     *
     * @return a list of all {@code Project}; never {@code null}, may be empty
     */
    @Override
    public @NotNull List<Project> getAll() {
        @Language("SQL")
        String sql = "SELECT * FROM Projects";
        return dbManager.executeQuery(sql, this::mapRow);
    }

    /**
     * Retrieves an {@code Project} by its unique identifier represented as a String.
     *
     * @param uuid the unique identifier of the {@code Project}
     * @return the {@code Project} matching the given UUID, or {@code null} if none found
     */
    @Override
    public Project getById(String uuid) {
        Project res = null;

        if (uuid != null) {
            @Language("SQL")
            String sql = "SELECT * FROM Projects WHERE id=?";
            List<Project> projects = dbManager.executeQuery(sql, this::mapRow, uuid);

            if (!projects.isEmpty()) res = projects.getFirst();
        }
        return res;
    }

    /**
     * Retrieves an {@code Project} by its unique identifier represented as a {@link UUID}.
     *
     * @param uuid the unique identifier of the {@code Project}
     * @return the {@code Project} matching the given UUID, or {@code null} if none found
     */
    @Override
    public Project getById(UUID uuid) {
        Project res = null;
        if (uuid != null) res = this.getById(uuid.toString());
        return res;
    }

    /**
     * Inserts a new {@code Project} into the data source.
     *
     * @param entity the {@code Project} to insert; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void insert(@NotNull Project entity) {
        Objects.requireNonNull(entity, "entity must not be null");

        this.insertClient(entity);

        @Language("SQL")
        String sql = "INSERT INTO Projects (id, client_id, name, description, estimated_time, " +
                "hourly_rate, fixed_price, created_at, deadline) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        dbManager.executeUpdate(
                sql,
                entity.getUuid(),
                entity.getClient() == null ? null : entity.getClient().getUuid(),
                entity.getName(),
                entity.getDescription(),
                entity.getEstimatedTime() == null ? null : entity.getEstimatedTime().toSeconds(),
                entity.getHourlyRate(),
                entity.getFixedPrice(),
                entity.getCreatedAt().getTime(),
                entity.getDeadline() == null ? null : entity.getDeadline().getTime()
        );
    }

    /**
     * Deletes an existing {@code Project} from the data source.
     *
     * @param entity the {@code Project} to delete; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void delete(@NotNull Project entity) {
        Objects.requireNonNull(entity, "entity must not be null");

        @Language("SQL")
        String sql = "DELETE FROM Projects WHERE id=?";
        dbManager.executeUpdate(sql, entity.getUuid());
    }

    /**
     * Updates an existing {@code Project} in the data source.
     *
     * @param entity the {@code Project} to update; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void update(@NotNull Project entity) {
        Objects.requireNonNull(entity, "entity must not be null");

        this.insertClient(entity);

        @Language("SQL")
        String sql = """
            UPDATE Projects
            SET client_id = ?,
                name = ?,
                description = ?,
                estimated_time = ?,
                hourly_rate  = ?,
                fixed_price = ?,
                created_at = ?,
                deadline = ?
            WHERE id = ?
            """;
        dbManager.executeUpdate(
                sql,
                entity.getClient() == null ? null : entity.getClient().getUuid(),
                entity.getName(),
                entity.getDescription(),
                entity.getEstimatedTime() == null ? null : entity.getEstimatedTime().toSeconds(),
                entity.getHourlyRate(),
                entity.getFixedPrice(),
                entity.getCreatedAt().getTime(),
                entity.getDeadline() == null ? null : entity.getDeadline().getTime(),
                entity.getUuid()
        );
    }

    /**
     * Maps the current row of the given {@link ResultSet} to a {@link Project} object.
     *
     * <p>This method reads the relevant columns from the {@code ResultSet} and constructs
     * a new {@code Project} instance with the retrieved data.</p>
     *
     * @param rs the {@code ResultSet} positioned at the current row; must not be {@code null}
     * @return a {@code Project} object populated with data from the current row of the result set
     * @throws RuntimeException if a {@link SQLException} occurs while accessing the result set
     */
    private Project mapRow(ResultSet rs) {
        try {
            ClientDao clientDao = new ClientDao();
            return new Project(
                    UUID.fromString(rs.getString("id")),
                    clientDao.getById(rs.getString("client_id")),
                    rs.getString("name"),
                    rs.getString("description"),
                    Duration.ofSeconds(rs.getLong("estimated_time")),
                    rs.getLong("hourly_rate"),
                    rs.getLong("fixed_price"),
                    new Date(rs.getLong("created_at")),
                    new Date(rs.getLong("deadline"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserts project's client into the database.
     *
     * @param project Project whose client we want to insert into the database, must not be
     * {@code null}
     * @throws NullPointerException if the Project is {@code null}
     */
    private void insertClient(@NotNull Project project) {
        Objects.requireNonNull(project, "project must not be null");

        if (project.getClient() != null) {
            ClientDao clientDao = new ClientDao();
            // ClientDao.getById returns null if the client is not in the database
            if (clientDao.getById(project.getClient().getUuid()) == null) {
                clientDao.insert(project.getClient());
            }
        }
    }
}
