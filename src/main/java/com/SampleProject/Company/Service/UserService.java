package com.SampleProject.Company.Service;

import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.AerospikeClient;

import com.SampleProject.Company.Model.User;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
//    Client client = Client.getClient("localhost", 3000);
    @Autowired
    AerospikeClient client;
    public User addUser(User user) {
        Key userKey = new Key("test", "user", user.getId());
        Bin userId = new Bin("userId", user.getId());
        Bin userName = new Bin("userName", user.getName());
        client.put(null, userKey, userId, userName);
        return user;
    }

    public User getUser(Long id) {
        Key userKey = new Key("test", "user", id);
        if (client.exists(null, userKey)) {
            Record userRecord = client.get(null, userKey, "userName", "userId");
            User user = new User(userRecord.getLong("userId"), userRecord.getString("userName"));
            return user;
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        Key userKey = new Key("test", "user", id);
        if (client.exists(null, userKey)) {
            client.delete(null, userKey);
            return true;
        }
        return false;
    }

    public List<User> getAllUsers() {
        Statement statement = new Statement();
        statement.setNamespace("test");
        statement.setSetName("user");
        RecordSet recordSet = client.query(null, statement);
        List<User> users = new ArrayList<>();

        while (recordSet.next()) {
            Record currentRecord = recordSet.getRecord();
            User user = new User();
            user.setId(currentRecord.getLong("userId"));
            user.setName(currentRecord.getString("userName"));
            users.add(user);
        }
        return users;
    }


}
