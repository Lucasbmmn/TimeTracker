package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.Client;
import com.lucasbmmn.timetracker.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProjectDaoTest {
    private final DatabaseManager dbManager = new DatabaseManager();
    private final ProjectDao dao = new ProjectDao();

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

    @BeforeEach
    void setUp() {
        dbManager.resetDatabase();
    }

    @Test
    void getAll() {
        // Empty database
        assertNotNull(dao.getAll());
        assertEquals(0, dao.getAll().size());

        // Database with two projects
        dao.insert(PROJECT1);
        dao.insert(PROJECT2);
        List<Project> projects = dao.getAll();
        assertNotNull(projects);
        assertEquals(2, projects.size());
        assertTrue(projects.contains(PROJECT1));
        assertTrue(projects.contains(PROJECT2));
    }

    @Test
    void getByIdString() {
        // Empty database
        assertNull(dao.getById("b25179cf-32a1-4e0d-8f16-32d23eec0166"));
        assertNull(dao.getById("cd6e621b-b81d-4752-96f7-061f3ae5db24"));


        // Database with two projects
        dao.insert(PROJECT1);
        dao.insert(PROJECT2);

        // Wrong id
        assertNull(dao.getById("8c7badbe-b10d-46bc-bd74-213da74e7cd5"));

        // Right projects
        assertEquals(PROJECT1, dao.getById("b25179cf-32a1-4e0d-8f16-32d23eec0166"));
        assertEquals(PROJECT2, dao.getById("cd6e621b-b81d-4752-96f7-061f3ae5db24"));
    }

    @Test
    void getByIdUUID() {
        // Empty database
        assertNull(dao.getById(PROJECT1.getUuid()));
        assertNull(dao.getById(PROJECT2.getUuid()));


        // Database with two projects
        dao.insert(PROJECT1);
        dao.insert(PROJECT2);

        // Wrong id
        assertNull(dao.getById(UUID.fromString("8c7badbe-b10d-46bc-bd74-213da74e7cd5")));

        assertEquals(PROJECT1, dao.getById(PROJECT1.getUuid()));
        assertEquals(PROJECT2, dao.getById(PROJECT2.getUuid()));
    }

    @Test
    void insert() {
        assertNull(dao.getById(PROJECT1.getUuid()));
        assertNull(dao.getById(PROJECT2.getUuid()));

        dao.insert(PROJECT1);
        assertEquals(1, dao.getAll().size());
        assertEquals(PROJECT1, dao.getById(PROJECT1.getUuid()));

        dao.insert(PROJECT2);
        assertEquals(2, dao.getAll().size());
        assertEquals(PROJECT2, dao.getById(PROJECT2.getUuid()));
    }

    @Test
    void delete() {
        dao.insert(PROJECT1);
        dao.insert(PROJECT2);

        dao.delete(PROJECT1);
        assertEquals(1, dao.getAll().size());

        dao.delete(PROJECT2);
        assertEquals(0, dao.getAll().size());
    }

    @Test
    void update() {
        // Adds default projects
        dao.insert(PROJECT1);
        dao.insert(PROJECT2);

        // Create modified projects
        Project updatedProject1 = new Project(PROJECT1.getUuid(), null, "Other name", "Other description", null, 0, 1, new Date(), null);
        Project updatedProject2 = new Project(PROJECT2.getUuid(), CLIENT1, "Different name", "Different description", Duration.ofSeconds(10), 0, 1, new Date(), new Date());

        dao.update(updatedProject1);
        dao.update(updatedProject2);

        // Tests
        assertEquals(updatedProject1, dao.getById(PROJECT1.getUuid()));
        assertEquals(updatedProject2, dao.getById(PROJECT2.getUuid()));
    }
}