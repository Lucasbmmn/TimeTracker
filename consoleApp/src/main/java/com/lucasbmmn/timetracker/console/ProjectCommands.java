package com.lucasbmmn.timetracker.console;

import com.lucasbmmn.timetracker.data.dao.ProjectDao;
import com.lucasbmmn.timetracker.model.Project;

import java.util.Arrays;

public class ProjectCommands {
    public static void executeCommand(String[] args) {
        if (args.length != 0) {
            String[] otherArgs = args.length >= 2 ? Arrays.copyOfRange(args, 1, args.length) : null;
            switch (args[0]) {
                case "list" -> list(otherArgs);
                default -> help();
            }
        } else {
            help();
        }
    }

    private static void list(String[] args) {
        if (args == null) {
            // TODO: 10/06/2025 List every projects
        } else if (args.length == 1) {
            ProjectDao dao = new ProjectDao();
            Project project = dao.getProjectByID(args[0]);
            if (project != null) System.out.println(project);
            else System.out.println("project list error: no project with the id \"" + args[0] + '"');
        } else {
            System.out.println("project list error: wrong number of parameters");
        }
        System.out.println();
    }

    private static void help() {
        System.out.println("""
                project list [client-id]                                Show all projects (optionally filter by client)
                """);
    }
}
