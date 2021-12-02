package com.example.simpleandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpleandroidapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JoinPageActivity extends AppCompatActivity {

    private TextView txtCheckID;
    private EditText edtJoinID;
    private EditText edtJoinPassword;
    private EditText edtCheckPassword;
    private EditText edtJoinEmail;
    private EditText edtJoinBirthday;
    private Button btnCheckID;
    private Button btnJoin;
    private Button btnBack;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private Calendar todayCalendar;
    private Calendar myCalendar;

    private String emailPattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_page);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("User");
        todayCalendar = Calendar.getInstance();
        myCalendar = Calendar.getInstance();

        initview();
    }

    /*
     * Init view
     * */
    private void initview(){
        txtCheckID = findViewById(R.id.txt_check_id);
        edtJoinID = findViewById(R.id.edt_join_id);
        edtJoinPassword = findViewById(R.id.edt_join_password);
        edtCheckPassword = findViewById(R.id.edt_check_password);
        edtJoinEmail = findViewById(R.id.edt_join_email);
        edtJoinBirthday = findViewById(R.id.edt_join_birthday);
        btnCheckID = findViewById(R.id.btn_check_ID);
        btnJoin = findViewById(R.id.btn_join_join);
        btnBack = findViewById(R.id.btn_back);
        chooseDate();

        // Button for checking ID
        btnCheckID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String joinID = edtJoinID.getText().toString();
                if (joinID.equals("")){
                    txtCheckID.setText("This form cannot be blank.");
                } else {
                    onClickCheckID(joinID); // Check if ID exists in Database
                }
            }
        });

        // Button for getting input and validation input
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtJoinID.getText().toString();
                String password = edtJoinPassword.getText().toString();
                String retypePassword = edtCheckPassword.getText().toString();
                String email = edtJoinEmail.getText().toString();
                String birthday = edtJoinBirthday.getText().toString();

                validation(id, password, retypePassword, email, birthday);// Validate input
            }
        });

        // Button for back press
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /*
    * Set onclick events for edtJoinBirthday
    * Open DatePicker Dialog select Birthday
    * */
    private void chooseDate() {
        // Create DatePickerDialog
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        // edtJoinBirthday onclick event to show DatePickerDialog
        edtJoinBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(JoinPageActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    /*
    * Format birthday
    * Set text for textview to show the date
    * */
    private void updateLabel(){
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        edtJoinBirthday.setText(sdf.format(myCalendar.getTime()));
    }

    /*
    * Function for checking ID
    * Check if ID exists and update status in textview
    * */
    private void onClickCheckID(String ID){
        myRef.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user != null) {
                    txtCheckID.setText("Please choose different ID. This ID has been chosen.");
                } else {
                    txtCheckID.setText("Your ID is valid.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JoinPageActivity.this, "Error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    * Function for validation
    * Check if all fields are fill up
    * Check if password and retype password are match
    * Check if email is valid
    * Check if birth day is valid
    * */
    private void validation(String id, String password, String retypePassword,
                            String email, String birthday) {
        if (id.equals("")) {
            Toast.makeText(JoinPageActivity.this, "Id is required.",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (password.equals("")){
            Toast.makeText(JoinPageActivity.this, "Password is required.",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (email.equals("")){
            Toast.makeText(JoinPageActivity.this, "Email is required.",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (birthday.equals("")){
            Toast.makeText(JoinPageActivity.this, "Birthday is required.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(retypePassword)) {
            Toast.makeText(JoinPageActivity.this, "Password and Retype password must match!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.matches(emailPattern)) {
            Toast.makeText(JoinPageActivity.this, "Your email is not valid!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (todayCalendar.get(Calendar.YEAR) <= myCalendar.get(Calendar.YEAR)) {
            Toast.makeText(JoinPageActivity.this, "Your birthday is not valid!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Create new user
        User user = new User();
        user.setID(id);
        user.setPassword(password);
        user.setEmail(email);
        user.setBirthday(birthday);

        // Join user to database
        onClickJoin(user);
    }

    /*
    * Update Database
    * */
    private void onClickJoin(User user){
        onClickCheckID(user.getID());

        myRef.child(user.getID()).setValue(user);
        Toast.makeText(JoinPageActivity.this, "Register success!",
                Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}