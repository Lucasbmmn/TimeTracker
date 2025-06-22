package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskDaoTest {
    private final DatabaseManager dbManager = new DatabaseManager();
    private final TaskDao dao = new TaskDao();

    private final Client CLIENT1 = new Client(
            UUID.fromString("1f71ee92-f47b-4dc4-8e4e-b47abafa978f"),
            "XYZ Ltd",
            "Jane Smith",
            "jane.smith@xyzltd.com",
            "0612345678",
            "+02:00"
    );

    private final Project PROJECT1 = new Project(
            UUID.fromString("b25179cf-32a1-4e0d-8f16-32d23eec0166"),
            CLIENT1,
            "Website Redesign",
            "Redesign the company website",
            Duration.ofSeconds(432000),
            75,
            0,
            new Date(1_633_046_400_000L),
            new Date(1_635_724_800_000L)
    );

    private final TaskStatus STATUS1 = new TaskStatus(
            UUID.fromString("d31ba5ed-82ca-4768-867a-4eeffcb3d94a"),
            "Not Started"
    );

    private final TaskType TYPE1 = new TaskType(
            UUID.fromString("db6d61cf-b9e1-4559-b84c-e04862ebda37"),
            "Design"
    );

    private final Task TASK1 = new Task(
            UUID.fromString("76ba6928-39d6-408a-821a-285a72f33b08"),
            PROJECT1,
            "Design Homepage",
            "Design the homepage for the website",
            Duration.ofSeconds(72000),
            STATUS1,
            TYPE1,
            new Date(1_633_219_200_000L)
    );

    private final Project PROJECT2 = new Project(
            UUID.fromString("cd6e621b-b81d-4752-96f7-061f3ae5db24"),
            null,
            "Mobile App Development",
            "Develop a mobile app for XYZ Ltd",
            null,
            85,
            0,
            new Date(1_633_132_800_000L),
            null
    );

    private final Task TASK2 = new Task(
            UUID.fromString("c235b9ec-b3c5-407d-be0f-8b5e5fdf3c0c"),
            PROJECT2,
            "Develop Login Screen",
            "Develop the login screen for the mobile app",
            null,
            null,
            null,
            new Date(1_633_219_200_000L)
    );

    @BeforeEach
    void setUp() {
        dbManager.resetDatabase();
    }

    @Test
    void getAll() {
        // Empty database
        assertNotNull(dao.getAll());
        assertEquals(0, dao.getAll().size());

        // Database with two tasks
        dao.insert(TASK1);
        dao.insert(TASK2);
        List<Task> tasks = dao.getAll();
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertTrue(tasks.contains(TASK1));
        assertTrue(tasks.contains(TASK2));
    }

    @Test
    void getByIdString() {
        // Empty database
        assertNull(dao.getById("76ba6928-39d6-408a-821a-285a72f33b08"));
        assertNull(dao.getById("c235b9ec-b3c5-407d-be0f-8b5e5fdf3c0c"));

        // Database with two tasks
        dao.insert(TASK1);
        dao.insert(TASK2);

        // Wrong id
        assertNull(dao.getById("8c7badbe-b10d-46bc-bd74-213da74e7cd5"));

        // Right tasks
        assertEquals(TASK1, dao.getById("76ba6928-39d6-408a-821a-285a72f33b08"));
        assertEquals(TASK2, dao.getById("c235b9ec-b3c5-407d-be0f-8b5e5fdf3c0c"));
    }

    @Test
    void getByIdUUID() {
        // Empty database
        assertNull(dao.getById(TASK1.getUuid()));
        assertNull(dao.getById(TASK2.getUuid()));

        // Database with two tasks
        dao.insert(TASK1);
        dao.insert(TASK2);

        // Wrong id
        assertNull(dao.getById(UUID.fromString("8c7badbe-b10d-46bc-bd74-213da74e7cd5")));

        // Right tasks
        assertEquals(TASK1, dao.getById(TASK1.getUuid()));
        assertEquals(TASK2, dao.getById(TASK2.getUuid()));
    }

    @Test
    void insert() {
        assertNull(dao.getById(TASK1.getUuid()));
        assertNull(dao.getById(TASK2.getUuid()));

        dao.insert(TASK1);
        assertEquals(1, dao.getAll().size());
        assertEquals(TASK1, dao.getById(TASK1.getUuid()));

        dao.insert(TASK2);
        assertEquals(2, dao.getAll().size());
        assertEquals(TASK2, dao.getById(TASK2.getUuid()));
    }

    @Test
    void delete() {
        dao.insert(TASK1);
        dao.insert(TASK2);

        dao.delete(TASK1);
        assertEquals(1, dao.getAll().size());

        dao.delete(TASK2);
        assertEquals(0, dao.getAll().size());
    }

    @Test
    void update() {
        // Adds default tasks
        dao.insert(TASK1);
        dao.insert(TASK2);

        // Create modified tasks
        Task updatedTask1 = new Task(TASK1.getUuid(), PROJECT2, "Updated Task 1", "Updated Description for Task 1", null, null, null, new Date());
        Task updatedTask2 = new Task(TASK2.getUuid(), PROJECT1, "Updated Task 2", "Updated Description for Task 2", Duration.ofSeconds(9000), STATUS1, TYPE1, new Date());

        dao.update(updatedTask1);
        dao.update(updatedTask2);

        // Tests
        assertEquals(updatedTask1, dao.getById(TASK1.getUuid()));
        assertEquals(updatedTask2, dao.getById(TASK2.getUuid()));
    }
}