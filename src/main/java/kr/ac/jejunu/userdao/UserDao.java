package kr.ac.jejunu.userdao;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User get(Long id) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {

            connection = dataSource.getConnection();
            StatementStrategy statementStrategy = new GetStatementStrategy();
            preparedStatement = statementStrategy.makePrepareStatement(id, connection);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return user;
    }

    public Long add(User user) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Long id = null;
        try {
            connection = dataSource.getConnection();
            StatementStrategy statementStrategy = new AddStatementStrategy();
            preparedStatement = statementStrategy.makePrepareStatement(user, connection);
            preparedStatement.executeUpdate();

            id = getLastInsertId(connection);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }

    public void update(User user) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            StatementStrategy statementStrategy = new UpdateStatementStrategy();
            preparedStatement = statementStrategy.makePrepareStatement(user, connection);

            preparedStatement.executeUpdate();

        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void delect(Long id) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            StatementStrategy statementStrategy = new DeleteStatementStrategy();
            preparedStatement = statementStrategy.makePrepareStatement(id, connection);

            preparedStatement.executeUpdate();

            id = getLastInsertId(connection);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Long getLastInsertId(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select last_insert_id()");
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        Long id = resultSet.getLong(1);

        resultSet.close();

        return id;
    }

}
