package org.hillel.lesson12.daos;

import org.hillel.lesson12.database.Database;
import org.hillel.lesson12.entities.Client;
import org.hillel.lesson12.entities.ClientsStatuses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {
    private static final String INSERT = "INSERT INTO clients (name,email,phone,about) " +
            "VALUES (?,?,?,?,?)";
    private static final String SELECT_ALL = "SELECT * FROM clients";
    private static final String DELETE = "DELETE FROM clients WHERE id=?";
    private static final String UPDATE = "UPDATE clients SET name=?, email=?, phone=?, about=?, age=? WHERE id=?";
    private static final String SELECT_BY_PHONE = "SELECT * FROM clients WHERE phone =?";
    private static final String SELECT_BY_ID = "SELECT * FROM clients WHERE id=?";
    private static final String SELECT_CLIENTS_JOIN_ACCOUNTS = "SELECT c.* FROM clients AS c " +
            "INNER JOIN accounts AS a ON c.id = a.client_id";
    private static final String SELECT_NAME_EMAIL_ALIAS = "SELECT name,email,alias FROM client_status AS cs " +
            "JOIN clients AS c ON cs.client_id = c.id " +
            "JOIN statuses AS s ON cs.status_id = s.id WHERE age>18";


    private Client setClientParameters(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setName(rs.getString("name"));
        client.setEmail(rs.getString("email"));
        client.setPhone(rs.getLong("phone"));
        client.setAbout(rs.getString("about"));
        client.setAge(rs.getInt("age"));
        return client;
    }

    public List<Client> findAllClients() {
        List<Client> resultList = new ArrayList<>();
        try ( Connection connection = Database.getConnection();
              Statement statement = connection.createStatement();
              ResultSet resultSet = statement.executeQuery(SELECT_ALL) ) {
            while (resultSet.next()) {
                resultList.add(setClientParameters(resultSet));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return resultList;
    }

    public Client findById(int id) {
        try ( Connection connection = Database.getConnection();
              PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID) ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return setClientParameters(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void saveClient(Client client) {
        try ( Connection connection = Database.getConnection();
              PreparedStatement statement = connection.prepareStatement(INSERT) ) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setLong(3, client.getPhone());
            statement.setString(4, client.getAbout());
            statement.setInt(5,client.getAge());
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteClient(int id) {
        try ( Connection connection = Database.getConnection();
              PreparedStatement statement = connection.prepareStatement(DELETE) ) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateClient(Client client) {
        try ( Connection connection = Database.getConnection();
              PreparedStatement statement = connection.prepareStatement(UPDATE) ) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setLong(3, client.getPhone());
            statement.setString(4, client.getAbout());
            statement.setInt(5,client.getAge());
            statement.setInt(6, client.getId());
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Client findClientByPhone(Long phone) {
        try ( Connection connection = Database.getConnection();
              PreparedStatement statement = connection.prepareStatement(SELECT_BY_PHONE) ) {
            statement.setLong(1, phone);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return setClientParameters(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public List<Client> findClientsWhereIdEqualsAccountsClientId() {
        List<Client> list = new ArrayList<>();
        try ( Connection connection = Database.getConnection();
              Statement statement = connection.createStatement();
              ResultSet resultSet = statement.executeQuery(SELECT_CLIENTS_JOIN_ACCOUNTS) ) {
            while (resultSet.next()) {
                list.add(setClientParameters(resultSet));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return list;
    }

    public List<ClientsStatuses> findNameEmailAlias() {
        List<ClientsStatuses> list = new ArrayList<>();
        try ( Connection connection = Database.getConnection();
              Statement statement = connection.createStatement();
              ResultSet resultSet = statement.executeQuery(SELECT_NAME_EMAIL_ALIAS) ) {
            while (resultSet.next()) {
                ClientsStatuses cs = new ClientsStatuses();
                cs.setName(resultSet.getString("name"));
                cs.setEmail(resultSet.getString("email"));
                cs.setAlias(resultSet.getString("alias"));
                list.add(cs);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return list;
    }
}
