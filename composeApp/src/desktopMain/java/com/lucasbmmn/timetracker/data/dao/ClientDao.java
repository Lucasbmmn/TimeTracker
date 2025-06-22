package com.lucasbmmn.timetracker.data.dao;

import com.lucasbmmn.timetracker.data.database.DatabaseManager;
import com.lucasbmmn.timetracker.model.Client;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Data Access Object that provides CRUD (Create, Read, Update, Delete) operations for
 * {@link Client}.
 */
public class ClientDao implements Dao<Client> {
    private final DatabaseManager dbManager;

    /**
     * Constructs a new {@code ClientDao} object.
     */
    public ClientDao() {
        this.dbManager = new DatabaseManager();
    }

    /**
     * Retrieves all {@code Client} from the data source.
     *
     * @return a list of all {@code Client}; never {@code null}, may be empty
     */
    @Override
    public @NotNull List<Client> getAll() {
        @Language("SQL")
        String sql = "SELECT * FROM Clients";
        return dbManager.executeQuery(sql, this::mapRow);
    }

    /**
     * Retrieves an {@code Client} by its unique identifier represented as a String.
     *
     * @param uuid the unique identifier of the {@code Client}; must not be {@code null}
     * @return the {@code Client} matching the given UUID, or {@code null} if none found
     * @throws NullPointerException if {@code uuid} is {@code null}
     */
    @Override
    public Client getById(@NotNull String uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");

        @Language("SQL")
        String sql = "SELECT * FROM Clients WHERE id=?";
        List<Client> clients = dbManager.executeQuery(sql, this::mapRow, uuid);

        Client res = null;
        if (!clients.isEmpty()) res = clients.getFirst();
        return res;
    }

    /**
     * Retrieves an {@code Client} by its unique identifier represented as a {@link UUID}.
     *
     * @param uuid the unique identifier of the {@code Client}; must not be {@code null}
     * @return the {@code Client} matching the given UUID, or {@code null} if none found
     * @throws NullPointerException if {@code uuid} is {@code null}
     */
    @Override
    public Client getById(@NotNull UUID uuid) {
        Objects.requireNonNull(uuid, " must not be null");
        return this.getById(uuid.toString());
    }

    /**
     * Inserts a new {@code Client} into the data source.
     *
     * @param entity the {@code Client} to insert; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void insert(@NotNull Client entity) {
        Objects.requireNonNull(entity, " must not be null");

        @Language("SQL")
        String sql = "INSERT INTO Clients (id, company, name, email, phone_number, timezone) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        dbManager.executeUpdate(
                sql,
                entity.getUuid(),
                entity.getCompany(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getTimezone()
        );
    }

    /**
     * Deletes an existing {@code Client} from the data source.
     *
     * @param entity the {@code Client} to delete; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void delete(@NotNull Client entity) {
        Objects.requireNonNull(entity, " must not be null");

        @Language("SQL")
        String sql = "DELETE FROM Clients WHERE id=?";
        dbManager.executeUpdate(sql, entity.getUuid());
    }

    /**
     * Updates an existing {@code Client} in the data source.
     *
     * @param entity the {@code Client} to update; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    @Override
    public void update(@NotNull Client entity) {
        Objects.requireNonNull(entity, " must not be null");

        @Language("SQL")
        String sql = """
            UPDATE Clients
            SET company = ?,
                name = ?,
                email = ?,
                phone_number = ?,
                timezone = ?
            WHERE id = ?
            """;
        dbManager.executeUpdate(
                sql,
                entity.getCompany(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getTimezone(),
                entity.getUuid()
        );
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
