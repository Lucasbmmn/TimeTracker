package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
class TaskStatusDaoTest {
    private final DatabaseManager dbManager = new DatabaseManager();
    private final TaskStatusDao dao = new TaskStatusDao();

    private final TaskStatus STATUS1 = new TaskStatus(
            UUID.fromString("695120ec-3575-4a2b-8b92-965484c360e5"),
            "In Progress"
    );

    private final TaskStatus STATUS2 = new TaskStatus(
            UUID.fromString("c5778756-357b-4f9b-89e0-4de4a225da89"),
            "Completed"
    );

    @BeforeEach
    void setUp() {
        dbManager.resetDatabase();
    }

    @Test
    void getAll() {
        // Database with 4 default statuses
        assertNotNull(dao.getAll());
        assertEquals(4, dao.getAll().size());

        // Database with two more statuses
        dao.insert(STATUS1);
        dao.insert(STATUS2);
        List<TaskStatus> statuses = dao.getAll();
        assertNotNull(statuses);
        assertEquals(6, statuses.size());
        assertTrue(statuses.contains(STATUS1));
        assertTrue(statuses.contains(STATUS2));
    }

    @Test
    void getById() {
        // Database with 4 default statuses
        assertNull(dao.getById("695120ec-3575-4a2b-8b92-965484c360e5"));
        assertNull(dao.getById("c5778756-357b-4f9b-89e0-4de4a225da89"));

        // Database with two more statuses
        dao.insert(STATUS1);
        dao.insert(STATUS2);

        // Wrong id
        assertNull(dao.getById("8c7badbe-b10d-46bc-bd74-213da74e7cd5"));

        // Right statuses
        assertEquals(STATUS1, dao.getById("695120ec-3575-4a2b-8b92-965484c360e5"));
        assertEquals(STATUS2, dao.getById("c5778756-357b-4f9b-89e0-4de4a225da89"));
    }

    @Test
    void testGetById() {
        // Database with 4 default statuses
        assertNull(dao.getById(STATUS1.getUuid()));
        assertNull(dao.getById(STATUS2.getUuid()));

        // Database with two more statuses
        dao.insert(STATUS1);
        dao.insert(STATUS2);

        // Wrong id
        assertNull(dao.getById(UUID.fromString("8c7badbe-b10d-46bc-bd74-213da74e7cd5")));

        // Right statuses
        assertEquals(STATUS1, dao.getById(STATUS1.getUuid()));
        assertEquals(STATUS2, dao.getById(STATUS2.getUuid()));
    }

    @Test
    void insert() {
        assertNull(dao.getById(STATUS1.getUuid()));
        assertNull(dao.getById(STATUS2.getUuid()));

        // The database has 4 default statuses
        dao.insert(STATUS1);
        assertEquals(5, dao.getAll().size());
        assertEquals(STATUS1, dao.getById(STATUS1.getUuid()));

        dao.insert(STATUS2);
        assertEquals(6, dao.getAll().size());
        assertEquals(STATUS2, dao.getById(STATUS2.getUuid()));
    }

    @Test
    void delete() {
        dao.insert(STATUS1);
        dao.insert(STATUS2);

        // The database has 4 default statuses
        dao.delete(STATUS1);
        assertEquals(5, dao.getAll().size());

        dao.delete(STATUS2);
        assertEquals(4, dao.getAll().size());
    }

    @Test
    void update() {
        // Adds two statuses
        dao.insert(STATUS1);
        dao.insert(STATUS2);

        // Create modified statuses
        TaskStatus updatedStatus1 = new TaskStatus(STATUS1.getUuid(), "Updated Status 1");
        TaskStatus updatedStatus2 = new TaskStatus(STATUS2.getUuid(), "Updated Status 2");

        dao.update(updatedStatus1);
        dao.update(updatedStatus2);

        // Tests
        assertEquals(updatedStatus1, dao.getById(STATUS1.getUuid()));
        assertEquals(updatedStatus2, dao.getById(STATUS2.getUuid()));
    }
}