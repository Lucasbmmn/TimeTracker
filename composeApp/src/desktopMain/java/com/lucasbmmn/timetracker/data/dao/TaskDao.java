package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.Project;
import com.lucasbmmn.timetracker.model.Task;
import com.lucasbmmn.timetracker.model.TaskStatus;
import com.lucasbmmn.timetracker.model.TaskType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskDao {
    private final DatabaseManager dbManager;

    public TaskDao() {
        this.dbManager = new DatabaseManager();
    }

    public Task getTaskByID(String uuid) {
        String sql = "SELECT * FROM Tasks WHERE id='" + uuid + "'";
        List<Task> tasks = dbManager.executeQuery(sql, this::mapRowToTask);

        Task res = null;
        if (!tasks.isEmpty()) res = tasks.getFirst();
        return res;
    }

    public Task getTaskByID(UUID uuid) {
        return this.getTaskByID(uuid.toString());
    }

    private Task mapRowToTask(ResultSet rs) {
        ProjectDao projectDao = new ProjectDao();
        TaskStatusDao taskStatusDao = new TaskStatusDao();
        TaskTypeDao taskTypeDao = new TaskTypeDao();
        try {
            return new Task(
                    UUID.fromString(rs.getString("id")),
                    projectDao.getProjectByID(rs.getString("project_id")),
                    rs.getString("name"),
                    rs.getString("description"),
                    Duration.ofSeconds(rs.getLong("estimated_time")),
                    taskStatusDao.getTaskStatusByID(rs.getString("task_status_id")),
                    taskTypeDao.getTaskTypeByID(rs.getString("task_type_id")),
                    new Date(rs.getLong("created_at"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
