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

import static com.SampleProject.Company.Const.Consts.*;

@Service
public class UserService {
//    Client client = Client.getClient("localhost", 3000);
    @Autowired
    AerospikeClient client;
    public User addUser(User user) {
        Key userKey = new Key(NAME_SPACE, USER_SET, user.getId());
        Bin userId = new Bin(USER_ID, user.getId());
        Bin userName = new Bin(USER_NAME, user.getName());
        client.put(null, userKey, userId, userName);
        return user;
    }

    public User getUser(Long id) {
        Key userKey = new Key(NAME_SPACE, USER_SET, id);
        if (client.exists(null, userKey)) {
            Record userRecord = client.get(null, userKey, USER_ID, USER_NAME);
            User user = new User(userRecord.getLong(USER_ID), userRecord.getString(USER_NAME));
            return user;
        }
        return null;
    }

    /**
     * method used by controller to delete user by id
     *
     * @param id id of the user
     * @return boolean whether the user deleted
     */
    public boolean deleteUser(Long id) {
        Key userKey = new Key(NAME_SPACE, USER_SET, id);
        if (client.exists(null, userKey)) {
            client.delete(null, userKey);
            return true;
        }
        return false;
    }

    /**
     * method to get all users
     *
     * @return list of users
     */
    public List<User> getAllUsers() {
        Statement statement = new Statement();
        statement.setNamespace(NAME_SPACE);
        statement.setSetName(USER_SET);
        RecordSet recordSet = client.query(null, statement);
        List<User> users = new ArrayList<>();

        while (recordSet.next()) {
            Record currentRecord = recordSet.getRecord();
            User user = new User();
            user.setId(currentRecord.getLong(USER_ID));
            user.setName(currentRecord.getString(USER_NAME));
            users.add(user);
        }
        return users;
    }


}
