package com.example.follow_park;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
    }

    public void mobilephone(View view) {openMobilePhone();
    }
    public void openMobilePhone() {
        Intent intent = new Intent(this, MobilePhone.class);
        startActivity(intent);
    }

    public void openRegister() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void openRegisterprak(View view) {openRegister();
    }
}
