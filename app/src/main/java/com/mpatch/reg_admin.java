package com.mpatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class reg_admin extends AppCompatActivity {
    private Button reg_ad;
    private Button lg;
    private EditText pass1_ad;
    private EditText name_ad;
    private EditText pass2_ad;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private int flag = 0;
    private TextView tx;
    private EditText ph;
    private TextView add;

    FirebaseDatabase db = FirebaseDatabase.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_registration);
        ref = FirebaseDatabase.getInstance().getReference("users");
        reg_ad=findViewById(R.id.admin_register_Button);
        pass1_ad=findViewById(R.id.ad_reg_passwordEditText);
        pass2_ad=findViewById(R.id.ad_reg_confirm_passwordEditText);
        name_ad=findViewById(R.id.ad_reg_usernameEditText);
        ph=findViewById(R.id.editTextPhone2);
        add=findViewById(R.id.Address_ad);
        final String[] token =new String[1];
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        token[0] = task.getResult();
                    }
                });
        reg_ad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int l=ph.getText().toString().length();
                System.out.println("num len="+l);
                if (pass1_ad.getText().toString().equals(pass2_ad.getText().toString())    ) {
                    Toast.makeText(getApplicationContext(), "password match", Toast.LENGTH_SHORT).show();
                    String uname = name_ad.getText().toString();
                    String pas = pass1_ad.getText().toString();
                    String pho= ph.getText().toString();
                    String ad = add.getText().toString();
                    //String cpas = pass2_ad.getText().toString();
                    ref.child(uname).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    Toast.makeText(reg_admin.this, "user exists", Toast.LENGTH_SHORT).show();
                                    //setContentView(R.layout.activity_main);
                                } else {
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("U_name", uname);
                                    hashMap.put("password", pas);
                                    hashMap.put("phone_num",pho);
                                    hashMap.put("address",ad);
                                    hashMap.put("Token",token[0]);
                                    ref.child(uname).setValue(hashMap);
                                    Toast.makeText(reg_admin.this, "Registeration Successful", Toast.LENGTH_SHORT).show();
                                    setContentView(R.layout.admin_login);

                                }
                            }
                        }
                    });


                } else {
                    Toast.makeText(reg_admin.this, "please enter your details properly", Toast.LENGTH_SHORT).show();
                    /*HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("U_name", uname);
                    hashMap.put("password", pas);
                    ref.child("users").child(uname).setValue(hashMap)*/
                }
            }
        });

    }
}
