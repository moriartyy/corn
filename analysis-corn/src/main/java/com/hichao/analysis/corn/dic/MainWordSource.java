package com.hichao.analysis.corn.dic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MainWordSource extends SqlWordSource {
	
	public MainWordSource(Properties configs) {
		super(configs.getProperty("word_source.main.url"), configs
				.getProperty("word_source.main.user_name"), configs
				.getProperty("word_source.main.password"));
	}

	@Override
	protected Word toWord(ResultSet rs) throws SQLException {
		return Word.create(rs.getString(1));
	}

	@Override
	protected String sqlGetWords() {
		return "select word from dic_main";
	}
}
