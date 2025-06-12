package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.Project;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

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

    public List<Project> getAllProjects() {
        String sql = "SELECT * FROM Projects";
        return dbManager.executeQuery(sql, this::mapRowToProject);
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

    public void insertProject(@NotNull Project project) {
        if (project == null) throw new IllegalArgumentException("Can't insert null project into the database");

        // TODO: 12/06/2025 Insert project's client to the db if it's not already

        @Language("SQL")
        String sql = "INSERT INTO Projects (id, client_id, name, description, estimated_time, " +
                "hourly_rate, fixed_price, create_at, deadline) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        dbManager.executeUpdate(
                sql,
                project.getUuid(),
                project.getClient() == null ? null : project.getClient().getUuid(),
                project.getName(),
                project.getDescription(),
                project.getEstimatedTime() == null ? null : project.getEstimatedTime().toSeconds(),
                project.getHourlyRate(),
                project.getFixedPrice(),
                project.getCreatedAt().getTime(),
                project.getDeadline() == null ? null : project.getDeadline().getTime()
        );
    }

    public void deleteProject(@NotNull Project project) {
        if (project == null) throw new IllegalArgumentException("Can't delete null project from the database");

        @Language("SQL")
        String sql = "DELETE FROM Projects WHERE id=?";
        dbManager.executeUpdate(sql, project.getUuid());
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
