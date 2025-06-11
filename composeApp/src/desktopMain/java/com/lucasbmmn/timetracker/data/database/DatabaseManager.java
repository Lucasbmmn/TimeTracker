package com.lucasbmmn.timetracker.data.database;

import com.lucasbmmn.timetracker.util.AppDataManager;

import javax.swing.tree.RowMapper;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseManager {
    public final String URL = "jdbc:sqlite:" + AppDataManager.getAppDataPath() + File.separator +
            "TimeTracker.db";

    public boolean databaseExists() {
        boolean doesExist = false;

        File databaseFile = new File(AppDataManager.getAppDataPath() + File.separator +
                "TimeTracker.db");
        if (databaseFile.exists()) doesExist = true;
        if (this.listTables().isEmpty()) doesExist = false;

        return doesExist;
    }

    public List<String> listTables() {
        List<String> tables = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(this.URL);
                Statement statement = connection.createStatement()
                ) {
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table';");

            while (rs.next()) {
                tables.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Collections.unmodifiableList(tables);
    }

    public void resetDatabase() {
        for (String table : this.listTables()) this.executeUpdate("DROP TABLE " + table);
        DatabaseSetup dbSetup = new DatabaseSetup();
        dbSetup.InitializeDatabase();
    }

    public <T> List<T> executeQuery(String sql, RowMapper<T> mapper) {
        List<T> results = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(URL);
                Statement statement = connection.createStatement()
                ) {
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) results.add(mapper.mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return results;
    }

    public int executeUpdate(String sql) {
        try (
                Connection connection = DriverManager.getConnection(URL);
                Statement statement = connection.createStatement()
                ) {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public interface RowMapper<T> {
        T mapRow(ResultSet rs) throws SQLException;
    }
}
