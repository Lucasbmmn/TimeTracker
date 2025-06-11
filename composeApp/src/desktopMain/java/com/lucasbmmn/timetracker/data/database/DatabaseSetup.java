package com.lucasbmmn.timetracker.data.database;

import com.lucasbmmn.timetracker.util.AppDataManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.UUID;

public class DatabaseSetup {
    public boolean InitializeDatabase() {
        boolean isInitialized = false;

        DatabaseManager dbManager = new DatabaseManager();
        if (!dbManager.databaseExists()) {
            createDatabase();
            insertDefaultValues();
            isInitialized = true;
        }

        return isInitialized;
    }

    private void createDatabase() {
        String url = "jdbc:sqlite:" + AppDataManager.getAppDataPath() + File.separator + "TimeTracker.db";
        String sqlFilePath = "files" + File.separator + "database" + File.separator + "schema.sql";

        try (InputStream is = DatabaseSetup.class.getClassLoader().getResourceAsStream(sqlFilePath)) {
            if (is == null) throw new RuntimeException("schema.sql not found in resources");
            Scanner scanner = new Scanner(is, StandardCharsets.UTF_8);
            String sqlFile = scanner.useDelimiter("\\A").next();

            String[] sqlStatements = sqlFile.split(";");

            try (
                    Connection connection = DriverManager.getConnection(url);
                    Statement statement = connection.createStatement()
                ) {
                statement.setQueryTimeout(30);

                for (String sql : sqlStatements) {
                    String trimmedSql = sql.trim();
                    if (!trimmedSql.isEmpty() && !trimmedSql.startsWith("--")) {
                        statement.addBatch(trimmedSql);
                    }
                }

                statement.executeBatch();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertDefaultValues() {
        String url = "jdbc:sqlite:" + AppDataManager.getAppDataPath() + File.separator + "TimeTracker.db";
        String[] defaultTaskStatuses = {"Not Started", "In Progress", "Completed", "On Hold"};
        String[] defaultTaskTypes = {"Design", "Development", "Meeting", "Admin"};
        try (
                        Connection connection = DriverManager.getConnection(url);
                        Statement statement = connection.createStatement()
            ) {
            statement.setQueryTimeout(30);

            for (String taskStatus : defaultTaskStatuses) {
                String uuid = UUID.randomUUID().toString();
                statement.executeUpdate("INSERT INTO Task_Statuses (id, label) VALUES ('" + uuid + "', '" + taskStatus + "')");
            }
            for (String taskType : defaultTaskTypes) {
                String uuid = UUID.randomUUID().toString();
                statement.executeUpdate("INSERT INTO Task_Types (id, label) VALUES ('" + uuid + "', '" + taskType + "')");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
