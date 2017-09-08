/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.denys.prokopiuk.consumer.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.denys.prokopiuk.xml.data.Person;

/**
 *
 * @author Denys
 */
public class PersonMongoAdapter {

    private Person person;

    public PersonMongoAdapter(Person person) {
        this.person = person;
    }

    public DBObject asDBObject() {
        return new BasicDBObject("name", person.getName()).append("age", person.getAge()).append("department", person.getDepartment());
    }

}
