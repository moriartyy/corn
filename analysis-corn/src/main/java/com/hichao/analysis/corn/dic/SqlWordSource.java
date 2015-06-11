package com.hichao.analysis.corn.dic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SqlWordSource implements WordSource {

    protected ResultSet resultSet;
    protected Connection connection;
	private String userName;
	private String url;
	private String password;
	
	public SqlWordSource(String host, int port, String userName, String password, String dbName) {
		this(String.format("jdbc:mysql://%s:%d/%s", host, port, dbName), userName, password);
	}
	
	public SqlWordSource(String url, String userName, String password) {
		this.url = url;
		this.userName = userName;
		this.password = password;
	}
	
	@Override
	public void close() {
        if (this.resultSet != null) {
            try {
                this.resultSet.close();
            } catch (SQLException e) {
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }
	}

	@Override
	public Word next() {
        try {
            if (this.resultSet == null) {
                this.resultSet = createResultSet();
            }
            Word word = null;
            while (resultSet.next()) {
                word = toWord(resultSet);
                if (word == Word.Empty) {
                	continue;
                }
                return word;
            } 
            return null;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ee) {
                }
            }
            throw new RuntimeException(e);
        }
	}
	
	protected abstract Word toWord(ResultSet rs) throws SQLException;
	protected abstract String sqlGetWords();

	protected ResultSet createResultSet() throws SQLException {
        this.connection = DriverManager.getConnection(url, userName, password);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetWords());
        return preparedStatement.executeQuery();
    }

	
}
