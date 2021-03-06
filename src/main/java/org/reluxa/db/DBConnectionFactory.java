package org.reluxa.db;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.reluxa.model.IDObject;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.ConfigScope;
import com.db4o.events.Event4;
import com.db4o.events.EventListener4;
import com.db4o.events.EventRegistryFactory;
import com.db4o.events.ObjectInfoEventArgs;
import com.db4o.internal.Config4Impl;
import com.db4o.internal.config.EmbeddedConfigurationImpl;

@Singleton
public class DBConnectionFactory {

	private ObjectContainer db;
	
	public DBConnectionFactory(String dbFile) {
		Config4Impl config = new Config4Impl();
		config.generateUUIDs(ConfigScope.GLOBALLY);
		EmbeddedConfigurationImpl embeddedConfig = new EmbeddedConfigurationImpl(config);
		db = Db4oEmbedded.openFile(embeddedConfig, dbFile);
		
		EventRegistryFactory.forObjectContainer(db).created().addListener(new EventListener4<ObjectInfoEventArgs>() {
			@Override
			public void onEvent(Event4<ObjectInfoEventArgs> arg0, ObjectInfoEventArgs arg1) {
				if (arg1.object() instanceof IDObject) {
					((IDObject)arg1.object()).setId(arg1.info().getInternalID());
				}
				//System.out.println("Created: "+arg1.object());

			}
		});
		
		EventRegistryFactory.forObjectContainer(db).activated().addListener(new EventListener4<ObjectInfoEventArgs>() {
			@Override
			public void onEvent(Event4<ObjectInfoEventArgs> arg0, ObjectInfoEventArgs arg1) {
				if (arg1.object() instanceof IDObject) {
					((IDObject)arg1.object()).setId(arg1.info().getInternalID());
				}
			}
		});
	}
		

	public DBConnectionFactory() {
		this(getDBFileName());	
	}
	
	private static String getDBFileName() {
		if (StringUtils.isNotEmpty(System.getenv("SQUASH_DB"))) {
			return System.getenv("SQUASH_DB");
		} else {
			return "D:\\database.data";
		}
	}

	public ObjectContainer getConnection() {
		return db;
	}

}
