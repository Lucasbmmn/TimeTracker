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

class TaskTimeEntryDaoTest {
    private final DatabaseManager dbManager = new DatabaseManager();
    private final TaskTimeEntryDao dao = new TaskTimeEntryDao();

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

    private final Task TASK1 = new Task(
            UUID.fromString("76ba6928-39d6-408a-821a-285a72f33b08"),
            PROJECT1,
            "Design Homepage",
            "Design the homepage for the website",
            Duration.ofSeconds(72000),
            null,
            null,
            new Date(1_633_219_200_000L)
    );

    private final TaskTimeEntry ENTRY1 = new TaskTimeEntry(
            UUID.fromString("d206549a-c7e0-4786-8ec6-e1702030cc63"),
            TASK1,
            Duration.ofSeconds(1800),
            new Date(1_633_392_000_000L)
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
            Duration.ofSeconds(7200),
            null,
            null,
            new Date(1_633_219_200_000L)
    );

    private final TaskTimeEntry ENTRY2 = new TaskTimeEntry(
            UUID.fromString("c0a9c32b-faac-4308-a671-02bf4944a335"),
            TASK2,
            Duration.ofSeconds(3600),
            new Date(1_633_478_400_000L)
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

        // Database with two entries
        dao.insert(ENTRY1);
        dao.insert(ENTRY2);
        List<TaskTimeEntry> entries = dao.getAll();
        assertNotNull(entries);
        assertEquals(2, entries.size());
        assertTrue(entries.contains(ENTRY1));
        assertTrue(entries.contains(ENTRY2));
    }

    @Test
    void getById() {
        // Empty database
        assertNull(dao.getById("d206549a-c7e0-4786-8ec6-e1702030cc63"));
        assertNull(dao.getById("c0a9c32b-faac-4308-a671-02bf4944a335"));

        // Database with two entries
        dao.insert(ENTRY1);
        dao.insert(ENTRY2);

        // Wrong id
        assertNull(dao.getById("8c7badbe-b10d-46bc-bd74-213da74e7cd5"));

        // Right entries
        assertEquals(ENTRY1, dao.getById("d206549a-c7e0-4786-8ec6-e1702030cc63"));
        assertEquals(ENTRY2, dao.getById("c0a9c32b-faac-4308-a671-02bf4944a335"));
    }

    @Test
    void testGetById() {
        // Empty database
        assertNull(dao.getById(ENTRY1.getUuid()));
        assertNull(dao.getById(ENTRY2.getUuid()));

        // Database with two entries
        dao.insert(ENTRY1);
        dao.insert(ENTRY2);

        // Wrong id
        assertNull(dao.getById(UUID.fromString("8c7badbe-b10d-46bc-bd74-213da74e7cd5")));

        // Right entries
        assertEquals(ENTRY1, dao.getById(ENTRY1.getUuid()));
        assertEquals(ENTRY2, dao.getById(ENTRY2.getUuid()));
    }

    @Test
    void insert() {
        assertNull(dao.getById(ENTRY1.getUuid()));
        assertNull(dao.getById(ENTRY2.getUuid()));

        dao.insert(ENTRY1);
        assertEquals(1, dao.getAll().size());
        assertEquals(ENTRY1, dao.getById(ENTRY1.getUuid()));

        dao.insert(ENTRY2);
        assertEquals(2, dao.getAll().size());
        assertEquals(ENTRY2, dao.getById(ENTRY2.getUuid()));
    }

    @Test
    void delete() {
        dao.insert(ENTRY1);
        dao.insert(ENTRY2);

        dao.delete(ENTRY1);
        assertEquals(1, dao.getAll().size());

        dao.delete(ENTRY2);
        assertEquals(0, dao.getAll().size());
    }

    @Test
    void update() {
        // Adds default entries
        dao.insert(ENTRY1);
        dao.insert(ENTRY2);

        // Create modified entries
        TaskTimeEntry updatedEntry1 = new TaskTimeEntry(ENTRY1.getUuid(), TASK1, Duration.ofSeconds(5400), new Date());
        TaskTimeEntry updatedEntry2 = new TaskTimeEntry(ENTRY2.getUuid(), TASK1, Duration.ofSeconds(9000), new Date());

        dao.update(updatedEntry1);
        dao.update(updatedEntry2);

        // Tests
        assertEquals(updatedEntry1, dao.getById(ENTRY1.getUuid()));
        assertEquals(updatedEntry2, dao.getById(ENTRY2.getUuid()));
    }
}