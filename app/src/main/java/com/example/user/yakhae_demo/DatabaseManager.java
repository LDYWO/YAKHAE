package com.example.user.yakhae_demo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseManager {
    public static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
}
