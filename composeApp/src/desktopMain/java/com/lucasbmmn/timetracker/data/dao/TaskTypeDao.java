package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.TaskType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class TaskTypeDao {
    private final DatabaseManager dbManager;

    public TaskTypeDao() {
        this.dbManager = new DatabaseManager();
    }

    public TaskType getTaskTypeByID(String uuid) {
        String sql = "SELECT * FROM Task_Types WHERE id='" + uuid + "'";
        List<TaskType> taskTypes = this.dbManager.executeQuery(sql, this::mapRowToTaskType);

        TaskType res = null;
        if (!taskTypes.isEmpty()) res = taskTypes.getFirst();
        return res;
    }

    public TaskType getTaskTypeByID(UUID uuid) {
        return this.getTaskTypeByID(uuid.toString());
    }

    private TaskType mapRowToTaskType(ResultSet rs) {
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
