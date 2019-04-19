package kr.ac.jejunu.userdao;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private final JdbcContext jdbcContext;

    public UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public User get(Long id) throws SQLException {
        StatementStrategy statementStrategy = new GetStatementStrategy(id);
        return jdbcContext.jdbcContextforGet(statementStrategy);
    }

    public Long add(User user) throws SQLException {
        StatementStrategy statementStrategy = new AddStatementStrategy(user);
        return jdbcContext.jdbcContextforAdd(statementStrategy);
    }

    public void update(User user) throws SQLException {
        StatementStrategy statementStrategy = new UpdateStatementStrategy(user);
        jdbcContext.jdbcContextforUpdate(statementStrategy);
    }

    public void delete(User user) throws SQLException {
        StatementStrategy statementStrategy = new DeleteStatementStrategy(user);
        jdbcContext.jdbcContextforUpdate(statementStrategy);
    }

}