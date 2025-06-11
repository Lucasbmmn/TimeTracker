package com.lucasbmmn.timetracker.console;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.data.database.DatabaseSetup;

import java.util.List;

public class DbCommands {
    public static void createDatabase(String[] args) {
        DatabaseSetup dbSetup = new DatabaseSetup();
        if (dbSetup.InitializeDatabase()) System.out.println("Database created.\n");
        else System.out.println("Database already exist.\n");
    }

    public static void listTables(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        List<String> tables = dbManager.listTables();
        if (tables.isEmpty()) System.out.println("No table in the database.");
        else {
            System.out.println("Tables in the database: ");
            for (String table : tables) System.out.println('\t' + table);
        }
        System.out.println();
    }

    public static void resetDb(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.resetDatabase();
        System.out.println("Database reset.\n");
    }
}
