package com.example.mychatapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder>{

    //using this adapter we will be able to get users their name and email on the mainActivity
    //when we click on one the chatActivity starts


    //what is an adapter and recyclerview?
    //--> https://stackoverflow.com/questions/59919366/what-is-recyclerview-adaptermyadapter-myviewholder-and-how-it-is-different-fro
    private Context context;
    private List<UserModel> userModelList;

    public UsersAdapter(Context context) {
        this.context = context;
        this.userModelList = new ArrayList<>();
    }

    public void add(UserModel userModel){
        userModelList.add(userModel);
    }
    public void clear(){
        userModelList.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UsersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater is used to create a new View (or Layout) object from one of your xml layouts.
        //this creates and view using the user_row xml
        //for reference -> https://stackoverflow.com/questions/3477422/what-does-layoutinflater-in-android-do
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent ,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.MyViewHolder holder, int position) {
        UserModel userModel = userModelList.get(position);
        holder.name.setText(userModel.getUserName());
        holder.email.setText(userModel.getUserEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("id",userModel.getUserID());
                intent.putExtra("name", userModel.getUserName());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public List<UserModel> getUserModelList(){
        return userModelList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name, email;
        public MyViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.username);
            email = itemView.findViewById(R.id.useremail);
        }
    }
}
