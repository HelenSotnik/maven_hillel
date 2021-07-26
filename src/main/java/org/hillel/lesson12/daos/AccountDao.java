package org.hillel.lesson12.daos;

import org.hillel.lesson12.database.Database;
import org.hillel.lesson12.entities.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private static final String INSERT = "INSERT INTO accounts (client_id, number,value) " +
            "VALUES (?,?,?)";
    private static final String SELECT_ALL = "SELECT * FROM accounts";
    private static final String DELETE = "DELETE FROM accounts WHERE id=?";
    private static final String UPDATE = "UPDATE accounts SET client_id=?, number=?, value=? WHERE id=?";
    private static final String SELECT_NUMBER_BY_VALUE = "SELECT number FROM accounts WHERE value>?";
    private static final String SELECT_BY_ID = "SELECT * FROM accounts WHERE id=?";

    private Account setAccountParameters(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt("id"));
        account.setClient_id(rs.getInt("client_id"));
        account.setNumber(rs.getString("number"));
        account.setValue(rs.getDouble("value"));
        return account;
    }

    public List<Account> findAllAccounts() {
        List<Account> resultList = new ArrayList<>();
        try ( Connection connection = Database.getConnection();
              Statement statement = connection.createStatement();
              ResultSet resultSet = statement.executeQuery(SELECT_ALL) ) {
            while (resultSet.next()) {
                resultList.add(setAccountParameters(resultSet));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return resultList;
    }

    public Account findById(int id) {
        try ( Connection connection = Database.getConnection();
              PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID) ) {
            statement.setInt(1, id);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return setAccountParameters(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void saveAccount(Account account) {
        try ( Connection connection = Database.getConnection();
              PreparedStatement statement = connection.prepareStatement(INSERT) ) {
            statement.setInt(1, account.getClient_id());
            statement.setString(2, account.getNumber());
            statement.setDouble(3, account.getValue());
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteAccount(int id) {
        try ( Connection connection = Database.getConnection();
              PreparedStatement statement = connection.prepareStatement(DELETE) ) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateAccount(Account account) {
        try ( Connection connection = Database.getConnection();
              PreparedStatement statement = connection.prepareStatement(UPDATE) ) {
            statement.setInt(1, account.getClient_id());
            statement.setString(2, account.getNumber());
            statement.setDouble(3, account.getValue());
            statement.setInt(4, account.getId());
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<String> findAccountNumberBiggerThanGivenValue(Double value) {
        List<String> resultList = new ArrayList<>();
        try ( Connection connection = Database.getConnection();
              PreparedStatement statement = connection.prepareStatement(SELECT_NUMBER_BY_VALUE) ) {
            statement.setDouble(1, value);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                resultList.add(resultSet.getString("number"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return resultList;
    }
}
