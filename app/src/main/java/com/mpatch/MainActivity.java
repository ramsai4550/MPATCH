package com.mpatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button usButton;
    private Button adButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usButton = findViewById(R.id.activity_main_userButton);
        adButton=findViewById(R.id.activity_main_adminButton);



        usButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { user_login();
            }
        });

        adButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin_login();
            }
        });
    }

    public void user_login(){
        Intent intent = new Intent(this, log.class);
        startActivity(intent);
    }



    public  void admin_login(){
            Intent intent1 = new Intent(this, log_admin.class);
            startActivity(intent1);
    }
}