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

public class ProjectDao implements Dao<Project> {
    private final DatabaseManager dbManager;

    /**
     * Constructs a new {@code ProjectDao} object
     */
    public ProjectDao() {
        this.dbManager = new DatabaseManager();
    }

    @Override
    public @NotNull List<Project> getAll() {
        String sql = "SELECT * FROM Projects";
        return dbManager.executeQuery(sql, this::mapRow);
    }

    @Override
    public Project getById(@NotNull String uuid) {
        String sql = "SELECT * FROM Projects WHERE id='" + uuid + "'";
        List<Project> projects = dbManager.executeQuery(sql, this::mapRow);

        Project res = null;
        if (!projects.isEmpty()) res = projects.getFirst();
        return res;
    }

    @Override
    public Project getById(@NotNull UUID uuid) {
        return this.getById(uuid.toString());
    }

    @Override
    public void insert(@NotNull Project entity) {
        if (entity == null) throw new IllegalArgumentException("Can't insert null project into the database");

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

    @Override
    public void delete(@NotNull Project entity) {
        if (entity == null) throw new IllegalArgumentException("Can't delete null project from the database");

        @Language("SQL")
        String sql = "DELETE FROM Projects WHERE id=?";
        dbManager.executeUpdate(sql, entity.getUuid());
    }

    @Override
    public void update(@NotNull Project entity) {
        if (entity == null) throw new IllegalArgumentException("Can't update null project");

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
     * Inserts project's client into the database
     *
     * @param project Project whose client we want to insert into the database, must not be null
     */
    private void insertClient(@NotNull Project project) {
        if (project == null) throw new IllegalArgumentException("Can't insert null project's client into the database");

        if (project.getClient() != null) {
            ClientDao clientDao = new ClientDao();
            // ClientDao.getById returns null if the client is not in the database
            if (clientDao.getById(project.getClient().getUuid()) == null) {
                clientDao.insert(project.getClient());
            }
        }
    }
}
