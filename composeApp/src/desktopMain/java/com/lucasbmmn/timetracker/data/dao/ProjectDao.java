package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ProjectDao {
    private final DatabaseManager dbManager;

    public ProjectDao() {
        this.dbManager = new DatabaseManager();
    }

    public Project getProjectByID(String uuid) {
        String sql = "SELECT * FROM Projects WHERE id='" + uuid + "'";
        List<Project> projects = dbManager.executeQuery(sql, this::mapRowToProject);

        Project res = null;
        if (!projects.isEmpty()) res = projects.getFirst();
        return res;
    }

    public Project getProjectByID(UUID uuid) {
        return this.getProjectByID(uuid.toString());
    }

    private Project mapRowToProject(ResultSet rs) {
        try {
            ClientDao clientDao = new ClientDao();
            return new Project(
                    UUID.fromString(rs.getString("id")),
                    clientDao.getClientByID(rs.getString("client_id")),
                    rs.getString("name"),
                    rs.getString("description"),
                    Duration.ofSeconds(rs.getLong("estimated_time")),
                    rs.getLong("hourly_rate"),
                    rs.getLong("fixed_price"),
                    new Date(rs.getLong("create_at")),
                    new Date(rs.getLong("deadline"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
