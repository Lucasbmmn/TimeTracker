package com.lucasbmmn.timetracker.console;

import com.lucasbmmn.timetracker.data.dao.ClientDao;
import com.lucasbmmn.timetracker.data.dao.ProjectDao;
import com.lucasbmmn.timetracker.model.Client;
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
                case "update" -> update(otherArgs);
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
            System.out.println("project add error: wrong number of parameters");
        }
        System.out.println();
    }

    private static void delete(String[] args) {
        if (args != null && args.length == 1) {
            ProjectDao dao = new ProjectDao();
            Project project = dao.getProjectByID(args[0]);
            if (project != null) {
                dao.deleteProject(project);
                System.out.println("Project successfully deleted.");
            }
            else System.out.println("project delete error: project does not exit");
        }
        else {
            System.out.println("project delete error: wrong number of parameters");
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

    private static void update(String[] args) {
        // Update command always takes 3 parameters
        if (args != null && args.length == 3) {
            ProjectDao dao = new ProjectDao();
            Project project = dao.getProjectByID(args[0]);

            // Updates project only if it already exists
            if (project != null) {

                boolean isProjectModified = true;
                // TODO: 13/06/2025 Test everything
                switch (args[1]) {
                    case "client", "-c" -> {
                        Client client = (new ClientDao()).getClientByID(args[2]);
                        if (client != null) project.setClient(client);
                        else {
                            isProjectModified = false;
                            System.out.println("project update error: client does not exist");
                        }
                    }
                    case "created-at", "-ca", "-cd" ->
                            project.setCreatedAt(new Date(Long.parseLong(args[2])));
                    case "description", "desc", "-d" -> project.setDescription(args[2]);
                    case "deadline", "-dl" ->
                            project.setDeadline(new Date(Long.parseLong(args[2])));
                    case "estimated-time", "-e" ->
                            project.setEstimatedTime(Duration.ofSeconds(Long.parseLong(args[2])));
                    case "fixed-price", "-f" ->
                            project.setFixedPrice(Double.parseDouble(args[2]));
                    case "hourly-rate", "-hr" ->
                            project.setHourlyRate(Double.parseDouble(args[2]));
                    case "name", "-n" -> project.setName(args[2]);
                    default -> isProjectModified = false;
                }

                if (isProjectModified) {
                    dao.updateProject(project);
                    System.out.println("Project successfully updated");
                } else System.out.printf("project update error: attribute '%s' does not exist%n" +
                        "'project update -h for more informations'%n", args[1]);
            } else System.out.println("project update error: project does not exist");
        } else if (args != null && (args[0].equals("-h") || args[0].equals("help"))) {
            updateHelp();
        } else System.out.println("project update error: wrong number of parameters");

        System.out.println();
    }

    private static void help() {
        System.out.println("""
                project add <name>                                      Create a new project
                project delete <id>                                     Delete a projet
                project list [client-id]                                Show all projects (optionally filter by client)
                project update <id> <attribute> <value>                 Update a project ("project update -h" for more information)
                """);
    }

    private static void updateHelp() {
        System.out.println("""
                project update <id> <attribute> <value>
                project update -h: shows this page
                Attributes:
                    client, -c: project's client
                    created-at, -ca, -cd: project's creation date
                    description, desc, -d: project's description
                    deadline, -dl: project's deadline
                    estimated-time, -e: project's time estimation
                    fixed-price, -f: project's fixed price
                    hourly-rate, -hr: project's hourly rate
                    name, -n: project's name
                """);
    }
}
