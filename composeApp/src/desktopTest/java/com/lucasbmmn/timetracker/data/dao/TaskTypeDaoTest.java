package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.TaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskTypeDaoTest {
    private final DatabaseManager dbManager = new DatabaseManager();
    private final TaskTypeDao dao = new TaskTypeDao();

    private final TaskType TYPE1 = new TaskType(
            UUID.fromString("db6d61cf-b9e1-4559-b84c-e04862ebda37"),
            "Design"
    );

    private final TaskType TYPE2 = new TaskType(
            UUID.fromString("f6135761-d37b-4352-9642-54ef5f923e30"),
            "Development"
    );

    @BeforeEach
    void setUp() {
        dbManager.resetDatabase();
    }

    @Test
    void getAll() {
        // Database with 4 default types
        assertNotNull(dao.getAll());
        assertEquals(4, dao.getAll().size());

        // Database with two more types
        dao.insert(TYPE1);
        dao.insert(TYPE2);
        List<TaskType> types = dao.getAll();
        assertNotNull(types);
        assertEquals(6, types.size());
        assertTrue(types.contains(TYPE1));
        assertTrue(types.contains(TYPE2));
    }

    @Test
    void getById() {
        // Database with 4 default types
        assertNull(dao.getById("db6d61cf-b9e1-4559-b84c-e04862ebda37"));
        assertNull(dao.getById("f6135761-d37b-4352-9642-54ef5f923e30"));

        // Database with two more types
        dao.insert(TYPE1);
        dao.insert(TYPE2);

        // Wrong id
        assertNull(dao.getById("8c7badbe-b10d-46bc-bd74-213da74e7cd5"));

        // Right types
        assertEquals(TYPE1, dao.getById("db6d61cf-b9e1-4559-b84c-e04862ebda37"));
        assertEquals(TYPE2, dao.getById("f6135761-d37b-4352-9642-54ef5f923e30"));
    }

    @Test
    void testGetById() {
        // Database with 4 default type
        assertNull(dao.getById(TYPE1.getUuid()));
        assertNull(dao.getById(TYPE2.getUuid()));

        // Database with two more types
        dao.insert(TYPE1);
        dao.insert(TYPE2);

        // Wrong id
        assertNull(dao.getById(UUID.fromString("8c7badbe-b10d-46bc-bd74-213da74e7cd5")));

        // Right types
        assertEquals(TYPE1, dao.getById(TYPE1.getUuid()));
        assertEquals(TYPE2, dao.getById(TYPE2.getUuid()));
    }

    @Test
    void insert() {
        assertNull(dao.getById(TYPE1.getUuid()));
        assertNull(dao.getById(TYPE2.getUuid()));

        // The database has 4 default types
        dao.insert(TYPE1);
        assertEquals(5, dao.getAll().size());
        assertEquals(TYPE1, dao.getById(TYPE1.getUuid()));

        dao.insert(TYPE2);
        assertEquals(6, dao.getAll().size());
        assertEquals(TYPE2, dao.getById(TYPE2.getUuid()));
    }

    @Test
    void delete() {
        dao.insert(TYPE1);
        dao.insert(TYPE2);

        // The database has 4 default types
        dao.delete(TYPE1);
        assertEquals(5, dao.getAll().size());

        dao.delete(TYPE2);
        assertEquals(4, dao.getAll().size());
    }

    @Test
    void update() {
        // Adds two types
        dao.insert(TYPE1);
        dao.insert(TYPE2);

        // Create modified types
        TaskType updatedType1 = new TaskType(TYPE1.getUuid(), "Updated Type 1");
        TaskType updatedType2 = new TaskType(TYPE2.getUuid(), "Updated Type 2");

        dao.update(updatedType1);
        dao.update(updatedType2);

        // Tests
        assertEquals(updatedType1, dao.getById(TYPE1.getUuid()));
        assertEquals(updatedType2, dao.getById(TYPE2.getUuid()));
    }
}