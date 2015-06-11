package com.hichao.analysis.corn.dic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.hichao.analysis.corn.dic.attribute.Entity;

public class EntityWordSource implements WordSource {

	private String userName;
	private String url;
	private String password;
	private Iterator<Word> wordIterator;
	
	public EntityWordSource(Properties configs) {
		this.url = configs.getProperty("wordsources.entity.url");
		this.userName = configs.getProperty("wordsources.entity.user_name");
		this.password = configs.getProperty("wordsources.entity.password");
		this.wordIterator = loadAll().iterator();
	}
	
	private List<Word> loadAll() {
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			List<Word> wordAll = new ArrayList<Word>(1000);
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.prepareStatement(sqlGetWords());
	        rs = statement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("entity_id");
				String name = rs.getString("entity_name");
				String wordList = rs.getString("entity_words");
				String type = rs.getString("entity_type");
	        	String[] words = wordList.split(",");
	        	for (String word : words) {
	        		wordAll.add(EntityWord.create(word, Entity.create(id, name, type)));
				}
			}
			return wordAll;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	protected String sqlGetWords() {
		return "select * from dic_entities where state=1";
	}

	@Override
	public void close() {
	}

	@Override
	public Word next() {
		return wordIterator.next();
	}

}
