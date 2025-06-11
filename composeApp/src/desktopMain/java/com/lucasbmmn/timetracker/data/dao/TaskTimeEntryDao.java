package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.TaskTimeEntry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskTimeEntryDao {
    private final DatabaseManager dbManager;

    public TaskTimeEntryDao() {
        this.dbManager = new DatabaseManager();
    }

    public TaskTimeEntry getTaskTimeEntryByID(String uuid) {
        String sql = "SELECT * FROM Task_Time_Entries WHERE id='" + uuid + "'";
        List<TaskTimeEntry> taskTimeEntries = dbManager.executeQuery(sql, this::mapRowToTaskTimeEntry);

        TaskTimeEntry res = null;
        if (!taskTimeEntries.isEmpty()) res = taskTimeEntries.getFirst();
        return res;
    }

    public TaskTimeEntry getTaskTimeEntryByID(UUID uuid) {
        return this.getTaskTimeEntryByID(uuid.toString());
    }

    private TaskTimeEntry mapRowToTaskTimeEntry(ResultSet rs) {
        TaskDao taskDao = new TaskDao();
        try {
            return new TaskTimeEntry(
                    UUID.fromString(rs.getString("id")),
                    taskDao.getTaskByID(rs.getString("task_id")),
                    Duration.ofSeconds(rs.getLong("duration")),
                    new Date(rs.getLong("created_at"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
