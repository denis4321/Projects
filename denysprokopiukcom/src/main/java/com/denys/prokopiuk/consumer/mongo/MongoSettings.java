/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.denys.prokopiuk.consumer.mongo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Denys
 */
public class MongoSettings {

    private Properties props;

    public MongoSettings() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("/configs/database/mongo_config.properties");) {
            props = new Properties();
            props.load(in);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getHost() {
        return props.getProperty("host", System.getenv("OPENSHIFT_MONGODB_DB_HOST"));
    }

    public int getPort() {
        return Integer.parseInt(props.getProperty("port", System.getenv("OPENSHIFT_MONGODB_DB_PORT")));
    }

    public String getDatabaseName() {
        return props.getProperty("app.name", System.getenv("OPENSHIFT_APP_NAME"));
    }

    public String getUser() {
        return props.getProperty("user", System.getenv("OPENSHIFT_MONGODB_DB_USERNAME"));
    }

    public String getPassword() {
        return props.getProperty("password", System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD"));
    }

    public String getCollectionName() {
        return props.getProperty("collection.name", "persons");
    }
    
}
