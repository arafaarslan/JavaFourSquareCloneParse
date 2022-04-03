package com.example.foursquarecloneparse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText signup_username_et, signup_password_et;
    private Button signup_signup_btn, signup_signin_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_username_et = findViewById(R.id.signup_username_et);
        signup_password_et = findViewById(R.id.signup_password_et);
        signup_signup_btn = findViewById(R.id.signup_signup_btn);
        signup_signin_btn = findViewById(R.id.signup_signin_btn);

        signup_signup_btn.setOnClickListener(this);
        signup_signin_btn.setOnClickListener(this);

        ParseUser parseUser = ParseUser.getCurrentUser();
        if(parseUser != null){
            Intent intent = new Intent(getApplicationContext(),LocationsActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signup_signup_btn) {
            signupBtnPressed();
        } else if (view.getId() == R.id.signup_signin_btn) {
            signinBtnPressed();
        }
    }

    private void signinBtnPressed() {

        ParseUser.logInInBackground(signup_username_et.getText().toString(), signup_password_et.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Welcome " + user.getUsername(),Toast.LENGTH_SHORT).show();
                    //Intent
                    Intent intent = new Intent(getApplicationContext(),LocationsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void signupBtnPressed() {
        ParseUser user = new ParseUser();
        user.setUsername(signup_username_et.getText().toString());
        user.setPassword(signup_password_et.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"User Signed Up!!!",Toast.LENGTH_SHORT).show();
                    //Intent
                    Intent intent = new Intent(getApplicationContext(),LocationsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}