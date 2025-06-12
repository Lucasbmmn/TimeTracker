package com.lucasbmmn.timetracker.console;

import com.lucasbmmn.timetracker.data.dao.ProjectDao;
import com.lucasbmmn.timetracker.model.Project;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;

public class ProjectCommands {
    public static void executeCommand(String[] args) {
        if (args.length != 0) {
            String[] otherArgs = args.length >= 2 ? Arrays.copyOfRange(args, 1, args.length) : null;
            switch (args[0]) {
                case "add" -> add(otherArgs);
                case "delete" -> delete(otherArgs);
                case "list" -> list(otherArgs);
                default -> help();
            }
        } else {
            help();
        }
    }

    private static void add(String[] args) {
        if (args != null &&  args.length == 1) {
            Project project = new Project(null, args[0], "", Duration.ofSeconds(0), 0, 0,
                    new Date(), null);
            ProjectDao dao = new ProjectDao();
            dao.insertProject(project);
            System.out.println("Project successfully created.");
        }
        else {
            System.out.println("project list error: wrong number of parameters");
        }
        System.out.println();
    }

    private static void delete(String[] args) {
        if (args != null && args.length == 1) {
            ProjectDao dao = new ProjectDao();
            dao.deleteProject(dao.getProjectByID(args[0]));
            System.out.println("Project successfully deleted.");
        }
        else {
            System.out.println("project list error: wrong number of parameters");
        }
        System.out.println();
    }

    private static void list(String[] args) {
        ProjectDao dao = new ProjectDao();
        if (args == null) {
            for (Project project : dao.getAllProjects()) System.out.println(project);
        } else if (args.length == 1) {
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
                project add <name>                                      Create a new project
                project delete <id>                                     Delete a projet
                project list [client-id]                                Show all projects (optionally filter by client)
                """);
    }
}
