package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.ProjectTimeEntry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ProjectTimeEntryDao {
    private final DatabaseManager dbManager;

    public ProjectTimeEntryDao() {
        this.dbManager = new DatabaseManager();
    }

    public ProjectTimeEntry getProjectTimeEntryById(String uuid) {
        String sql = "SELECT * FROM Project_Time_Entries WHERE id='" + uuid + "'";
        List<ProjectTimeEntry> projectTimeEntries = dbManager.executeQuery(sql, this::mapRowToProjectTimeEntry);

        ProjectTimeEntry res = null;
        if (!projectTimeEntries.isEmpty()) res = projectTimeEntries.getFirst();
        return res;
    }

    public ProjectTimeEntry getProjectTimeEntryById(UUID uuid) {
        return this.getProjectTimeEntryById(uuid.toString());
    }

    private ProjectTimeEntry mapRowToProjectTimeEntry(ResultSet rs) {
        try {
            ProjectDao projectDao = new ProjectDao();
            return new ProjectTimeEntry(
                    UUID.fromString(rs.getString("id")),
                    projectDao.getProjectByID(rs.getString("task_id")),
                    Duration.ofSeconds(rs.getLong("duration")),
                    new Date(rs.getLong("created_at")),
                    rs.getBoolean("is_billable")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
