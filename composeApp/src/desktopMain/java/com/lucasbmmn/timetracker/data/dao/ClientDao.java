package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ClientDao {
    private final DatabaseManager dbManager;

    public ClientDao() {
        this.dbManager = new DatabaseManager();
    }

    public Client getClientByID(String uuid) {
        String sql = "SELECT * FROM Clients WHERE id='" + uuid + "'";
        List<Client> clients = dbManager.executeQuery(sql, this::mapRowToClient);

        Client res = null;
        if (!clients.isEmpty()) res = clients.getFirst();
        return res;
    }

    public Client getClientByID(UUID uuid) {
        return this.getClientByID(uuid.toString());
    }

    private Client mapRowToClient(ResultSet rs) {
        try {
            return new Client(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("company"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone_number"),
                    rs.getString("timezone")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
