package com.example.follow_park;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void register(View view) {openNameActivity();

    }


    public void openNameActivity(){
        Intent intent = new Intent(this, NameActivity.class);
        startActivity(intent);
    }

    public void login(View view) {openhome();
    }




    public void openhome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
