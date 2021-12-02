package com.example.simpleandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simpleandroidapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPageActivity extends AppCompatActivity {

    private EditText edtLoginID;
    private EditText edtLoginPassword;
    private Button btnLogin;
    private Button btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        initView();
    }

    /*
    * Init view
    * */
    private void initView(){
        edtLoginID = findViewById(R.id.edt_id);
        edtLoginPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnJoin = findViewById(R.id.btn_join);

        // Set onclick event for user clicking Login Button
        // Check field blank and call function onClickLogin
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtLoginID.getText().toString();
                String password = edtLoginPassword.getText().toString();
                if (!id.equals("") & !password.equals("")) {
                    onClickLogin(id, password);
                } else {
                    Toast.makeText(LoginPageActivity.this, "Please fill up your ID and Password.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set onclick event for user click Join Button
        // Move to Join Page
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPageActivity.this, JoinPageActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
    * Read data from Realtime Database
    * */
    private void onClickLogin(String ID, String password){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("User");

        myRef.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user == null) { // Check if user is exists from database
                    Toast.makeText(LoginPageActivity.this, "Please check your ID.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Move to MainPage if password is match
                if (password.equals(user.getPassword())){
                    Toast.makeText(LoginPageActivity.this, "Login Success!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginPageActivity.this, MainPageActivity.class);
                    startActivity(intent);
                } else { // Toast show if password is not match
                    Toast.makeText(LoginPageActivity.this, "Please check your Password.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Toast show if login fails by error
                Toast.makeText(LoginPageActivity.this, "Error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}