package com.fb.config;
//MongoClientURI connectionStr = new MongoClientURI("mongodb://manish2:welcome@2019@localhost:27017/caper");


import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

@Configuration
@ComponentScan(basePackages = "com.fb")
@EnableAsync
@EnableMongoRepositories(basePackages = "com.fb.repository")
public class MongoDbConfig extends AbstractMongoConfiguration {


	    @Bean
	    public MongoDbFactory mongoDbFactory() {
	        return new SimpleMongoDbFactory(new MongoClientURI("mongodb://manish2:welcome2019@localhost:27017/caper"));
	    }

		@Bean
		public MongoTemplate mongoTemplate() {
			MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

			return mongoTemplate;

	    }

		@Override
		public MongoClient mongoClient() {

//			DB_SERVER = new String(Base64.getUrlDecoder().decode(DB_SERVER.getBytes()));
//			DB_SERVER_PORT = new String(Base64.getUrlDecoder().decode(DB_SERVER_PORT.getBytes()));
//			DB_USER = new String(Base64.getUrlDecoder().decode(DB_USER.getBytes()));
//			DB_NAME = new String(Base64.getUrlDecoder().decode(DB_NAME.getBytes()));
//			DB_USER_PWD = new String(Base64.getUrlDecoder().decode(DB_USER_PWD.getBytes()));
//
//			DB_SERVER_TWO = new String(Base64.getUrlDecoder().decode(DB_SERVER_TWO.getBytes()));
//			DB_SERVER_THREE = new String(Base64.getUrlDecoder().decode(DB_SERVER_THREE.getBytes()));
//			DB_PREFERENCE = new String(Base64.getUrlDecoder().decode(DB_PREFERENCE.getBytes()));

			ServerAddress serverAddress = new ServerAddress("localhost", 27017);
//			ServerAddress serverAddress2 = new ServerAddress(DB_SERVER_TWO, Integer.parseInt(DB_SERVER_PORT));
//			ServerAddress serverAddress3 = new ServerAddress(DB_SERVER_THREE, Integer.parseInt(DB_SERVER_PORT));

			List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();
			serverAddresses.add(serverAddress);
			//serverAddresses.add(serverAddress2);
			//serverAddresses.add(serverAddress3);

			MongoClient mongoClient = new MongoClient(serverAddresses);
//			if (DB_PREFERENCE.equalsIgnoreCase("SECONDARYPREFERRED"))
//				mongoClient.setReadPreference(ReadPreference.secondaryPreferred());
//			else if (DB_PREFERENCE.equalsIgnoreCase("PRIMARYPREFERRED"))
//				mongoClient.setReadPreference(ReadPreference.primaryPreferred());
//			else if (DB_PREFERENCE.equalsIgnoreCase("NEAREST"))
//				mongoClient.setReadPreference(ReadPreference.nearest());
//			else if (DB_PREFERENCE.equalsIgnoreCase("PRIMARY"))
//				mongoClient.setReadPreference(ReadPreference.primary());
//			else if (DB_PREFERENCE.equalsIgnoreCase("SECONDARY"))
//				mongoClient.setReadPreference(ReadPreference.secondary());
//			else
				mongoClient.setReadPreference(ReadPreference.primary());

//			String mongoURI = null;
//			if (environment.equalsIgnoreCase("live") || environment.equalsIgnoreCase("preProd"))
//				mongoURI = "mongodb://" + DB_USER + ":" + DB_USER_PWD + "@" + DB_SERVER + ":" + DB_SERVER_PORT + ","
//						+ DB_SERVER_TWO + ":" + DB_SERVER_PORT + "," + DB_SERVER_THREE + ":" + DB_SERVER_PORT + "/"
//						+ DB_NAME + "?replicaSet=rs8";
//			else
//				mongoURI = "mongodb://" + DB_USER + ":" + DB_USER_PWD + "@" + DB_SERVER + ":" + DB_SERVER_PORT + ","
//						+ DB_SERVER_TWO + ":" + DB_SERVER_PORT + "," + DB_SERVER_THREE + ":" + DB_SERVER_PORT + "/"
//						+ DB_NAME;

			//logger.debug("mongo uri before connection : " + mongoURI);
			MongoClientURI connectionStr = new MongoClientURI("mongodb://manish2:welcome2019@localhost:27017/caper");
			mongoClient = new MongoClient(connectionStr);

			return mongoClient;
		}

		@Override
		protected String getDatabaseName() {
			// TODO Auto-generated method stub
			return "caper";
		}
}