package com.hichao.analysis.corn.dic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.elasticsearch.common.collect.Tuple;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;

import com.hichao.analysis.corn.dic.Dictionary;
import com.hichao.analysis.corn.dic.DictionaryFactory;
import com.hichao.analysis.corn.dic.WordSource;

public class DictionaryFactoryImpl implements DictionaryFactory {
	
	public synchronized static Dictionary create(Settings settings, Environment environment) {
		Tuple<Settings, Environment> key = Tuple.tuple(settings, environment);
		Dictionary dic = Dictionary.get(key);
		if (dic == null) {
			dic = createDic(environment);
			Dictionary.put(key, dic);
		}
		return dic;
	}

	private static Dictionary createDic(Environment environment) {
		return new DictionaryFactoryImpl(environment).create();
	}

	private File configFile;
	private Properties configs;
	
	private DictionaryFactoryImpl(Environment environment) {
		configFile = new File(environment.configFile(), "corn/corn.conf");
		loadConfg(configFile);
		this.configs = loadConfg(configFile);
	}

	private Properties loadConfg(File file) {
		
		Reader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			Properties configs = new Properties();
			configs.load(reader);
			return configs;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
	}

	@Override
	public Dictionary create() {
		return Dictionary.from(new WordSourceFactory() {
			
			@Override
			public WordSource[] createSources() {
				WordSource[] sources = new WordSource[2];
				sources[0] = new MainWordSource(configs);
				sources[1] = new EntityWordSource(configs);
				return sources;
			}
		});
	}
}
