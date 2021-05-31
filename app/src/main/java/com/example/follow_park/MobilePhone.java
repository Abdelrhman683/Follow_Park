package com.example.follow_park;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MobilePhone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_phone);
    }


    public void onClickEmail(View view) {
        openEmail();
    }
    public void openEmail(){
        Intent intent = new Intent(this,Email.class);
        startActivity(intent);
    }
    public void openPassword(View view) { openPassword();
    }
    public void openPassword() {
        Intent intent = new Intent(this, Password.class);
        startActivity(intent);
    }


}
