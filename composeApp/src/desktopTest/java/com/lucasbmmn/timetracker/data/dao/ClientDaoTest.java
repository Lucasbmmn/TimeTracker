package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientDaoTest {
    private final DatabaseManager dbManager = new DatabaseManager();
    private final ClientDao dao = new ClientDao();

    private final Client CLIENT1 = new Client(
            UUID.fromString("1f71ee92-f47b-4dc4-8e4e-b47abafa978f"),
            "XYZ Ltd",
            "Jane Smith",
            "jane.smith@xyzltd.com",
            "0612345678",
            "+02:00"
    );

    private final Client CLIENT2 = new Client(
            UUID.fromString("f075758e-f63d-466b-89c2-919658ccc4d9"),
            "ABC Corp",
            "John Doe",
            "john.doe@abccorp.com",
            "+1234567890",
            "+01:00"
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

        // Database with two clients
        dao.insert(CLIENT1);
        dao.insert(CLIENT2);
        List<Client> clients = dao.getAll();
        assertNotNull(clients);
        assertEquals(2, clients.size());
        assertTrue(clients.contains(CLIENT1));
        assertTrue(clients.contains(CLIENT2));
    }

    @Test
    void getByIdString() {
        // Empty database
        assertNull(dao.getById("1f71ee92-f47b-4dc4-8e4e-b47abafa978f"));
        assertNull(dao.getById("f075758e-f63d-466b-89c2-919658ccc4d9"));

        // Database with two clients
        dao.insert(CLIENT1);
        dao.insert(CLIENT2);

        // Wrong id
        assertNull(dao.getById("8c7badbe-b10d-46bc-bd74-213da74e7cd5"));

        // Right clients
        assertEquals(CLIENT1, dao.getById("1f71ee92-f47b-4dc4-8e4e-b47abafa978f"));
        assertEquals(CLIENT2, dao.getById("f075758e-f63d-466b-89c2-919658ccc4d9"));
    }

    @Test
    void getByIdUUID() {
        // Empty database
        assertNull(dao.getById(CLIENT1.getUuid()));
        assertNull(dao.getById(CLIENT2.getUuid()));

        // Database with two clients
        dao.insert(CLIENT1);
        dao.insert(CLIENT2);

        // Wrong id
        assertNull(dao.getById(UUID.fromString("8c7badbe-b10d-46bc-bd74-213da74e7cd5")));

        // Right clients
        assertEquals(CLIENT1, dao.getById(CLIENT1.getUuid()));
        assertEquals(CLIENT2, dao.getById(CLIENT2.getUuid()));
    }

    @Test
    void insert() {
        assertNull(dao.getById(CLIENT1.getUuid()));
        assertNull(dao.getById(CLIENT2.getUuid()));

        dao.insert(CLIENT1);
        assertEquals(1, dao.getAll().size());
        assertEquals(CLIENT1, dao.getById(CLIENT1.getUuid()));

        dao.insert(CLIENT2);
        assertEquals(2, dao.getAll().size());
        assertEquals(CLIENT2, dao.getById(CLIENT2.getUuid()));
    }

    @Test
    void delete() {
        dao.insert(CLIENT1);
        dao.insert(CLIENT2);

        dao.delete(CLIENT1);
        assertEquals(1, dao.getAll().size());

        dao.delete(CLIENT2);
        assertEquals(0, dao.getAll().size());
    }

    @Test
    void update() {
        // Adds default clients
        dao.insert(CLIENT1);
        dao.insert(CLIENT2);

        // Create modified clients
        Client updatedClient1 = new Client(CLIENT1.getUuid(), "Updated Company", "Updated Name", "updated.email@example.com", "0987654321", "+03:00");
        Client updatedClient2 = new Client(CLIENT2.getUuid(), "Another Company", "Another Name", "another.email@example.com", "1987654321", "+04:00");

        dao.update(updatedClient1);
        dao.update(updatedClient2);

        // Tests
        assertEquals(updatedClient1, dao.getById(CLIENT1.getUuid()));
        assertEquals(updatedClient2, dao.getById(CLIENT2.getUuid()));
    }
}