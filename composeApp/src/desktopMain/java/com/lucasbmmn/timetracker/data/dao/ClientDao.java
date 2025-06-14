package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.Client;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ClientDao implements Dao<Client> {
    private final DatabaseManager dbManager;

    /**
     * Constructs a new {@code ClientDao} object
     */
    public ClientDao() {
        this.dbManager = new DatabaseManager();
    }

    @Override
    public @NotNull List<Client> getAll() {
        return List.of();
    }

    @Override
    public Client getById(@NotNull String uuid) {
        String sql = "SELECT * FROM Clients WHERE id='" + uuid + "'";
        List<Client> clients = dbManager.executeQuery(sql, this::mapRow);

        Client res = null;
        if (!clients.isEmpty()) res = clients.getFirst();
        return res;
    }

    @Override
    public Client getById(@NotNull UUID uuid) {
        return this.getById(uuid.toString());
    }

    @Override
    public void insert(@NotNull Client entity) {
        // TODO: 14/06/2025 Implement method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void update(@NotNull Client entity) {
        // TODO: 14/06/2025 Implement method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(@NotNull Client entity) {
        // TODO: 14/06/2025 Implement method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Maps the current row of the given {@link ResultSet} to a {@link Client} object.
     *
     * <p>This method reads the relevant columns from the {@code ResultSet} and constructs
     * a new {@code Client} instance with the retrieved data.</p>
     *
     * @param rs the {@code ResultSet} positioned at the current row; must not be {@code null}
     * @return a {@code Client} object populated with data from the current row of the result set
     * @throws RuntimeException if a {@link SQLException} occurs while accessing the result set
     */
    private Client mapRow(ResultSet rs) {
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
