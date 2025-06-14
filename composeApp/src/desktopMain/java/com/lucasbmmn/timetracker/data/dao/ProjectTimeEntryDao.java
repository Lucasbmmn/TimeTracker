package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.Client;
import com.lucasbmmn.timetracker.model.ProjectTimeEntry;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ProjectTimeEntryDao implements Dao<ProjectTimeEntry> {
    private final DatabaseManager dbManager;

    /**
     * Constructs a new {@code ProjectTimeEntryDao} object
     */
    public ProjectTimeEntryDao() {
        this.dbManager = new DatabaseManager();
    }

    @Override
    public @NotNull List<ProjectTimeEntry> getAll() {
        @Language("SQL")
        String sql = "SELECT * FROM Project_Time_Entries";
        return dbManager.executeQuery(sql, this::mapRow);
    }

    @Override
    public ProjectTimeEntry getById(@NotNull String uuid) {
        String sql = "SELECT * FROM Project_Time_Entries WHERE id='" + uuid + "'";
        List<ProjectTimeEntry> projectTimeEntries = dbManager.executeQuery(sql, this::mapRow);

        ProjectTimeEntry res = null;
        if (!projectTimeEntries.isEmpty()) res = projectTimeEntries.getFirst();
        return res;
    }

    @Override
    public ProjectTimeEntry getById(@NotNull UUID uuid) {
        return this.getById(uuid.toString());
    }

    @Override
    public void insert(@NotNull ProjectTimeEntry entity) {
        if (entity == null) throw new IllegalArgumentException("Can't insert null ProjectTimeEntry into the database");

        // TODO: 14/06/2025 Insert project into the db if it's not already

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

    @Override
    public void delete(@NotNull ProjectTimeEntry entity) {
        if (entity == null) throw new IllegalArgumentException("Can't delete null ProjectTimeEntry from the database");

        @Language("SQL")
        String sql = "DELETE FROM Project_Time_Entries WHERE id=?";
        dbManager.executeUpdate(sql, entity.getUuid());
    }

    @Override
    public void update(@NotNull ProjectTimeEntry entity) {
        if (entity == null) throw new IllegalArgumentException("Can't update null ProjectTimeEntry");

        // TODO: 14/06/2025 Insert project into the db if it's not already

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
}
