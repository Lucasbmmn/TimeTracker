package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.TaskStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class TaskStatusDao {
    private final DatabaseManager dbManager;

    public TaskStatusDao() {
        this.dbManager = new DatabaseManager();
    }

    public TaskStatus getTaskStatusByID(String uuid) {
        String sql = "SELECT * FROM Task_Statuses WHERE id='" + uuid + "'";
        List<TaskStatus> taskStatuses = dbManager.executeQuery(sql, this::mapRowToTaskStatus);

        TaskStatus res = null;
        if (!taskStatuses.isEmpty()) res = taskStatuses.getFirst();
        return res;
    }

    public TaskStatus getTaskStatusByID(UUID uuid) {
        return this.getTaskStatusByID(uuid.toString());
    }

    private TaskStatus mapRowToTaskStatus(ResultSet rs) {
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
