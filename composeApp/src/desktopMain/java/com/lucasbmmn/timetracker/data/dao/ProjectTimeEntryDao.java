package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.Client;
import com.lucasbmmn.timetracker.model.ProjectTimeEntry;
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
        return List.of();
    }

    @Override
    public ProjectTimeEntry getById(@NotNull String uuid) {
        String sql = "SELECT * FROM Project_Time_Entries WHERE id='" + uuid + "'";
        List<ProjectTimeEntry> projectTimeEntries = dbManager.executeQuery(sql, this::mapRowToProjectTimeEntry);

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
        // TODO: 14/06/2025 Implement method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void update(@NotNull ProjectTimeEntry entity) {
        // TODO: 14/06/2025 Implement method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(@NotNull ProjectTimeEntry entity) {
        // TODO: 14/06/2025 Implement method
        throw new UnsupportedOperationException("Not implemented yet");
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
    private ProjectTimeEntry mapRowToProjectTimeEntry(ResultSet rs) {
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
