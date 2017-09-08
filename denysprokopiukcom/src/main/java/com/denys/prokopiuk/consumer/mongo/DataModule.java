/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.denys.prokopiuk.consumer.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.denys.prokopiuk.xml.data.Person;
import com.denys.prokopiuk.xml.data.Persons;

/**
 *
 * @author Denys.
 * Class designed for communication with MongoDB.
 */
public class DataModule {

    public void save(Persons persons) {
        MongoSettings settings = new MongoSettings();
        MongoClient mongoClient = getConnection(settings.getHost(), settings.getPort());
        DB db = mongoClient.getDB(settings.getDatabaseName());
        if (db.authenticate(settings.getUser(), settings.getPassword().toCharArray())) {
            DBCollection collection = getDBCollection(db, settings.getCollectionName());
            for (Person person : persons.getPersons()) {
                PersonMongoAdapter adapter = new PersonMongoAdapter(person);
                collection.save(adapter.asDBObject());
            }
        }
        mongoClient.close();
    }

    private DBCollection getDBCollection(DB db, String name) {
        if (!db.collectionExists(name)) {
            db.createCollection(name, new BasicDBObject());
        }
        return db.getCollection(name);
    }

    private MongoClient getConnection(String host, int port) {
        try {
            return new MongoClient(host, port);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
