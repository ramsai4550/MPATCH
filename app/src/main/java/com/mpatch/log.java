package com.mpatch;

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
import java.util.Vector;

public class log extends AppCompatActivity {
    private Button regs;
    private Button lg;
    private EditText pass1;
    private EditText name;
    private EditText pass2;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private int flag;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView tx1;
    private Button sear1;
    private EditText st1;
    private int j=0;
    private Button ot = findViewById(R.id.activity_main_otp_button);
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        //firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users"); // "users" is the name of your database node
        ref = FirebaseDatabase.getInstance().getReference("users");
        regs=findViewById(R.id.activity_main_register_Button);
        passwordEditText=findViewById(R.id.activity_main_usernameEditText);
        usernameEditText=findViewById(R.id.activity_main_passwordEditText);
        lg=findViewById(R.id.activity_main_loginButton);
        String name = usernameEditText.getText().toString();
        String p = passwordEditText.getText().toString();

        ot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = usernameEditText.getText().toString();
                 final String p = passwordEditText.getText().toString();
                System.out.println(p+" "+name);
                if(name.isEmpty() || p.isEmpty()){
                    Toast.makeText(log.this,"Enter username, password", Toast.LENGTH_SHORT).show();
                }
                else{

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Iterate through each child node of the "users" node
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                //flag=0;
                                // Retrieve the username and password values from each child node
                                String username = userSnapshot.child("U_name").getValue(String.class);
                                String password = userSnapshot.child("password").getValue(String.class);
                                System.out.println(username+" "+password);
                                // Do something with the username and password values
                                if(password.equals(name) && username.equals(p)){
                                    Toast.makeText(log.this,"Login successful", Toast.LENGTH_SHORT).show();
                                    setContentView(R.layout.content_display);
                                    sear1=findViewById(R.id.search);
                                    tx1=findViewById(R.id.textView3);
                                    st1=findViewById(R.id.searchtext);
                                    //String text=st.getText().toString();
                                    Vector<String> vector1 = new Vector<>();

                                    sear1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            vector1.clear();
                                            final String text1 = st1.getText().toString();
                                            //vector1.add(" ");
                                            //vector1.add(" ");
                                            //vector1.add(" ");
                                           // j=0;
                                            reference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    // Iterate through each child node of the "users" node
                                                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                                        // Retrieve the username and password values from each child node
                                                        String username2 = userSnapshot.child("U_name").getValue(String.class);
                                                        String password2 = userSnapshot.child("password").getValue(String.class);
                                                        String phone2=userSnapshot.child("phone_num").getValue(String.class);
                                                        String add2=userSnapshot.child("address").getValue(String.class);
                                                        // Do something with the username and password values
                                                        if(tx1.getText().toString().isEmpty()){
                                                            tx1.setText("no results found");
                                                        }
                                                        if(text1.equals(add2)) {
                                                            if(tx1.getText().toString().isEmpty()){
                                                                tx1.setText("no results found");
                                                            }
                                                            //vector1.add(" ");
                                                            String  da ="Name:- "+username2+"\n"+"Phone:- "+phone2+"\n"+"Address:- "+add2+"\n";
                                                            vector1.add(da);
                                                            //j=j+1;
                                                        }

                                                    }
                                                    //j=0;
                                                    StringBuilder stringBuilder = new StringBuilder();

                                                    for (String element : vector1) {
                                                        stringBuilder.append(element).append("\n");
                                                    }

                                                    tx1.setText(stringBuilder.toString());

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    // Handle any errors that occur
                                                }
                                            });

                                        }
                                    });                                    //Toast.makeText(MainActivity.this,token[0], Toast.LENGTH_SHORT).show();
                                   /* HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("U_name", name);
                                    hashMap.put("password", p);
                                    hashMap.put("Token",token[0]);
                                    ref.child(name).setValue(hashMap);*/
                                   // Intent intent1 = new Intent(log.this, send.class);
                                    //startActivity(intent1);
                                    //intent1.putExtra("myVariable", name);
                                    //Toast.makeText(MainActivity.this,"hello", Toast.LENGTH_SHORT).show();
                                    flag=0;
                                    break;
                                }
                                else{
                                    flag=1;

                                }

                            }
                            if(flag==1){
                                Toast.makeText(log.this,"Invalid Data", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle any errors that occur
                        }
                    });


                }
            }
        });
        regs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                registration_user();
            }
        });

    }
    public  void registration_user(){
        Intent intent1 = new Intent(this, reg_user.class);
        startActivity(intent1);
    }
}
