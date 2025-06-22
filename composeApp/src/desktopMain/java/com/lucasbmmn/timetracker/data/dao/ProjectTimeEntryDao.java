package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.ProjectTimeEntry;
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
 * {@link ProjectTimeEntry}.
 */
public class ProjectTimeEntryDao implements Dao<ProjectTimeEntry> {
    private final DatabaseManager dbManager;

    /**
     * Constructs a new {@code ProjectTimeEntryDao} object.
     */
    public ProjectTimeEntryDao() {
        this.dbManager = new DatabaseManager();
    }

    /**
     * Retrieves all {@code ProjectTimeEntry} from the data source.
     *
     * @return a list of all {@code ProjectTimeEntry}; never {@code null}, may be empty
     */
    @Override
    public @NotNull List<ProjectTimeEntry> getAll() {
        @Language("SQL")
        String sql = "SELECT * FROM Project_Time_Entries";
        return dbManager.executeQuery(sql, this::mapRow);
    }

    /**
     * Retrieves an {@code ProjectTimeEntry} by its unique identifier represented as a String.
     *
     * @param uuid the unique identifier of the {@code ProjectTimeEntry}; must not be {@code null}
     * @return the {@code ProjectTimeEntry} matching the given UUID, or {@code null} if none found
     * @throws NullPointerException if {@code uuid} is {@code null}
     */
    @Override
    public ProjectTimeEntry getById(@NotNull String uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");

        String sql = "SELECT * FROM Project_Time_Entries WHERE id='" + uuid + "'";
        List<ProjectTimeEntry> projectTimeEntries = dbManager.executeQuery(sql, this::mapRow);

        ProjectTimeEntry res = null;
        if (!projectTimeEntries.isEmpty()) res = projectTimeEntries.getFirst();
        return res;
    }

    /**
     * Retrieves an {@code ProjectTimeEntry} by its unique identifier represented as a {@link UUID}.
     *
     * @param uuid the unique identifier of the {@code ProjectTimeEntry}; must not be {@code null}
     * @return the {@code ProjectTimeEntry} matching the given UUID, or {@code null} if none found
     * @throws NullPointerException if {@code uuid} is {@code null}
     */
    @Override
    public ProjectTimeEntry getById(@NotNull UUID uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        return this.getById(uuid.toString());
    }

    /**
     * Inserts a new {@code ProjectTimeEntry} into the data source.
     *
     * @param entity the {@code ProjectTimeEntry} to insert; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void insert(@NotNull ProjectTimeEntry entity) {
        Objects.requireNonNull(entity, " must not be null");

        this.insertProject(entity);

        @Language("SQL")
        String sql = "INSERT INTO Project_Time_Entries (id, project_id, duration, created_at, isBillable) " +
                "VALUES (?, ?, ?, ?, ?)";
        dbManager.executeUpdate(
                sql,
                entity.getUuid(),
                entity.getProject().getUuid(),
                entity.getDuration().getSeconds(),
                entity.getCreatedAt().getTime(),
                entity.isBillable() ? 1 : 0
        );
    }

    /**
     * Deletes an existing {@code ProjectTimeEntry} from the data source.
     *
     * @param entity the {@code ProjectTimeEntry} to delete; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void delete(@NotNull ProjectTimeEntry entity) {
        Objects.requireNonNull(entity, " must not be null");

        @Language("SQL")
        String sql = "DELETE FROM Project_Time_Entries WHERE id=?";
        dbManager.executeUpdate(sql, entity.getUuid());
    }

    /**
     * Updates an existing {@code ProjectTimeEntry} in the data source.
     *
     * @param entity the {@code ProjectTimeEntry} to update; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void update(@NotNull ProjectTimeEntry entity) {
        Objects.requireNonNull(entity, " must not be null");

        this.insertProject(entity);

        @Language("SQL")
        String sql = """
            UPDATE Project_Time_Entries
            SET project_id = ?,
                duration = ?,
                created_at = ?,
                isBillable = ?
            WHERE id = ?
            """;
        dbManager.executeUpdate(
                sql,
                entity.getProject(),
                entity.getDuration().getSeconds(),
                entity.getCreatedAt().getTime(),
                entity.isBillable() ? 1 : 0,
                entity.getUuid()
        );
    }

    /**
     * Maps the current row of the given {@link ResultSet} to a {@link ProjectTimeEntry} object.
     *
     * <p>This method reads the relevant columns from the {@code ResultSet} and constructs
     * a new {@code ProjectTimeEntry} instance with the retrieved data.</p>
     *
     * @param rs the {@code ResultSet} positioned at the current row; must not be {@code null}
     * @return a {@code ProjectTimeEntry} object populated with data from the current row of the result set
     * @throws RuntimeException if a {@link SQLException} occurs while accessing the result set
     */
    private ProjectTimeEntry mapRow(ResultSet rs) {
        try {
            ProjectDao projectDao = new ProjectDao();
            return new ProjectTimeEntry(
                    UUID.fromString(rs.getString("id")),
                    projectDao.getById(rs.getString("task_id")),
                    Duration.ofSeconds(rs.getLong("duration")),
                    new Date(rs.getLong("created_at")),
                    rs.getBoolean("is_billable")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserts ProjectTimeEntry's project into the database
     *
     * @param projectTimeEntry ProjectTimeEntry whose project we want to insert into the database,
     *                         must not be null
     */
    private void insertProject(@NotNull ProjectTimeEntry projectTimeEntry) {
        if (projectTimeEntry == null) throw new IllegalArgumentException("Can't insert null projectTimeEntry's project into the database");

        ProjectDao projectDao = new ProjectDao();
        // ProjectDao.getById returns null if the project is not in the database
        if (projectDao.getById(projectTimeEntry.getProject().getUuid()) == null) {
            projectDao.insert(projectTimeEntry.getProject());
        }
    }
}
