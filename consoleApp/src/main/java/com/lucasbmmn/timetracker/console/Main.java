package com.lucasbmmn.timetracker.console;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("TimeTracker> ");
            String input = scanner.nextLine().toLowerCase();
            String[] parts = input.split("\\s+");

            if (parts.length != 0) {
                String command = parts[0];
                String[] commandArgs = Arrays.copyOfRange(parts, 1, parts.length);

                switch (command) {
                    case "help" -> printHelp(commandArgs);
                    case "project" -> ProjectCommands.executeCommand(commandArgs);
                    case "create-db" -> DbCommands.createDatabase(commandArgs);
                    case "list-tables" -> DbCommands.listTables(commandArgs);
                    case "reset-db" -> DbCommands.resetDb(commandArgs);
                    case "exit", "quit" -> running = false;
                    default -> System.out.println("Unknown command. Type 'help' for a list of commands.");
                }
            }
        }

        System.out.println("Exiting...");
    }

    private static void printHelp(String[] args) {
        System.out.println("""
            Available commands:
              help            Show this help message
              create-db       Create and init database
              list-tables     Lists of tables in the database
              reset-db        Reset database
              add-project     Add a new project
              list-projects   List all projects
              add-task        Add a new task
              exit/quit       Quit the application
            """);
    }
}
