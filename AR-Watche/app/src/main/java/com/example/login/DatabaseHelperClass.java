package com.example.login;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelperClass {
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private static final String userNodePath = "users";

    DatabaseHelperClass() {
        rootNode = FirebaseDatabase.getInstance();
    }
    public void addUser(UserHelperClass u) {
        reference = rootNode.getReference(userNodePath);
        reference.child(u.getUserName()).setValue(u);
    }
}
