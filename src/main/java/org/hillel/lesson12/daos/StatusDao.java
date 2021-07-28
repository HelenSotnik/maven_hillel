package org.hillel.lesson12.daos;

import org.hillel.lesson12.database.Database;
import org.hillel.lesson12.entities.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusDao {
    private static final String INSERT = "INSERT INTO statuses (alias, description) VALUES (?,?)";
    private static final String SELECT_ALL = "SELECT * FROM statuses";
    private static final String DELETE = "DELETE FROM statuses WHERE id=?";
    private static final String UPDATE = "UPDATE statuses SET alias=?, description=? WHERE id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM statuses WHERE id=?";

    private Status setStatusParameters(ResultSet rs) throws SQLException {
        Status status = new Status();
        status.setId(rs.getInt("id"));
        status.setAlias(rs.getString("alias"));
        status.setDescription(rs.getString("description"));
        return status;
    }

    public List<Status> findAllStatuses() {
        List<Status> resultList = new ArrayList<>();
        try ( Connection connection = Database.getConnection();
              Statement statement = connection.createStatement();
              ResultSet resultSet = statement.executeQuery(SELECT_ALL) ) {
            while (resultSet.next()) {
                resultList.add(setStatusParameters(resultSet));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return resultList;
    }

    public Status findById(int id) {
        try ( Connection connection = Database.getConnection();
              PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID) ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return setStatusParameters(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void saveStatus(Status status) {
        try ( Connection connection = Database.getConnection();
              PreparedStatement statement = connection.prepareStatement(INSERT) ) {
            statement.setString(1, status.getAlias());
            statement.setString(2, status.getDescription());
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteStatus(int id) {
        try ( Connection connection = Database.getConnection();
              PreparedStatement statement = connection.prepareStatement(DELETE) ) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateStatus(Status status) {
        try ( Connection connection = Database.getConnection();
              PreparedStatement statement = connection.prepareStatement(UPDATE) ) {
            statement.setString(1, status.getAlias());
            statement.setString(2, status.getDescription());
            statement.setInt(3, status.getId());
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
