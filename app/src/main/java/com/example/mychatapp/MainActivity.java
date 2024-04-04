package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    UsersAdapter usersAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //setsupportActionbar is used to keep a toolbar always on top
        setSupportActionBar(toolbar);
        String userName = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(userName);

        usersAdapter = new UsersAdapter(this);
        recyclerView = findViewById(R.id.recycler);

        recyclerView.setAdapter(usersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersAdapter.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String uId = dataSnapshot.getKey();
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    //this condition is used to check if the userid going to be changed is same or not if not same then ->
                    if(userModel != null && userModel.getUserID()!=null && !userModel.getUserID().equals(FirebaseAuth.getInstance().getUid())){
                        usersAdapter.add(userModel);
                    }
                }
                List<UserModel> userModelList = usersAdapter.getUserModelList();
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //instantiate menu xml to menu objects
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, SigninActivity.class));
            finish();
            return  true;
        }

        return false;
    }
}