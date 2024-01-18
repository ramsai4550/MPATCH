package com.mpatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Vector;

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

public class log_admin extends AppCompatActivity {
    private Button regs;
    private Button lg;
    private EditText pass1;
    private EditText name;
    private EditText pass2;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    public int flag;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView tx;
    private Button sear;
    private EditText st;
    private int i=0;
    //FirebaseDatabase db = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users"); // "users" is the name of your database node
        ref = FirebaseDatabase.getInstance().getReference("users");
        regs=findViewById(R.id.activity_main_register_Button);
        passwordEditText=findViewById(R.id.activity_main_usernameEditText);
        usernameEditText=findViewById(R.id.activity_main_passwordEditText);
        lg=findViewById(R.id.activity_main_loginButton);

        lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 final String name = usernameEditText.getText().toString();
                final  String p = passwordEditText.getText().toString();
                System.out.println(p+" "+name);
                if(name.isEmpty() || p.isEmpty()){
                    Toast.makeText(log_admin.this,"Enter username, password", Toast.LENGTH_SHORT).show();
                }
                else {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Iterate through each child node of the "users" node
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                flag = 0;
                                // Retrieve the username and password values from each child node
                                final String username = userSnapshot.child("U_name").getValue(String.class);
                                final String password = userSnapshot.child("password").getValue(String.class);
                                // Do something with the username and password values
                                if (password.equals(name) && username.equals(p)) {
                                    Toast.makeText(log_admin.this, "Login successful", Toast.LENGTH_SHORT).show();


                                    //Toast.makeText(MainActivity.this,token[0], Toast.LENGTH_SHORT).show();
                                   /* HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("U_name", name);
                                    hashMap.put("password", p);
                                    hashMap.put("Token",token[0]);
                                    ref.child(name).setValue(hashMap);*/
                                    // Intent intent1 = new Intent(log.this, send.class);
                                    //startActivity(intent1);
                                    //intent1.putExtra("myVariable", name);
                                    //Toast.makeText(MainActivity.this,"hello", Toast.LENGTH_SHORT).show();
                                   // if(flag==1){
                                        setContentView(R.layout.content_display);
                                        sear = findViewById(R.id.search);
                                        tx = findViewById(R.id.textView3);
                                        st = findViewById(R.id.searchtext);
                                        //String text=st.getText().toString();
                                        Vector<String> vector = new Vector<>();

                                        sear.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                final String text = st.getText().toString();
                                                //vector.add(" ");
                                                //vector.add(" ");
                                                //vector.add(" ");
                                                //i=0;
                                                vector.clear();
                                                reference.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        // Iterate through each child node of the "users" node
                                                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                                            // Retrieve the username and password values from each child node
                                                            String username1 = userSnapshot.child("U_name").getValue(String.class);
                                                            String phone1 = userSnapshot.child("phone_num").getValue(String.class);
                                                            String add1 = userSnapshot.child("address").getValue(String.class);
                                                            // Do something with the username and password values
                                                            if (tx.getText().toString().isEmpty()) {
                                                                tx.setText("no results found");
                                                            }
                                                            if (text.equals(add1)) {
                                            /*if(tx.getText().toString().isEmpty()){
                                                tx.setText("no results found");
                                            }*/
                                                                //vector.add(" ");
                                                                String da = "Name:- " + username1 + "\n" + "Phone:- " + phone1 + "\n" + "Address:- " + add1 + "\n";
                                                                vector.add(da);
                                                                // i=i+1;
                                                            }

                                                        }
                                                        //i=0;
                                                        StringBuilder stringBuilder = new StringBuilder();

                                                        for (String element : vector) {
                                                            stringBuilder.append(element).append("\n");
                                                        }

                                                        tx.setText(stringBuilder.toString());

                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        // Handle any errors that occur
                                                    }
                                                });

                                            }
                                        });
                                   // }
                                    break;
                                } else {
                                    flag = 1;

                                }
                                if (flag == 1) {
                                    Toast.makeText(log_admin.this, "Invalid Data", Toast.LENGTH_SHORT).show();
break;
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle any errors that occur
                        }
                    });
                    System.out.println("flag="+flag);
                    //Showdat();
                  /*  if(flag==1){
                    setContentView(R.layout.content_display);
                    sear = findViewById(R.id.search);
                    tx = findViewById(R.id.textView3);
                    st = findViewById(R.id.searchtext);
                    //String text=st.getText().toString();
                    Vector<String> vector = new Vector<>();

                    sear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String text = st.getText().toString();
                            //vector.add(" ");
                            //vector.add(" ");
                            //vector.add(" ");
                            //i=0;
                            vector.clear();
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // Iterate through each child node of the "users" node
                                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                        // Retrieve the username and password values from each child node
                                        String username1 = userSnapshot.child("U_name").getValue(String.class);
                                        String phone1 = userSnapshot.child("phone_num").getValue(String.class);
                                        String add1 = userSnapshot.child("address").getValue(String.class);
                                        // Do something with the username and password values
                                        if (tx.getText().toString().isEmpty()) {
                                            tx.setText("no results found");
                                        }
                                        if (text.equals(add1)) {
                                            /*if(tx.getText().toString().isEmpty()){
                                                tx.setText("no results found");
                                            }*/
                                            //vector.add(" ");
                                           /* String da = "Name:- " + username1 + "\n" + "Phone:- " + phone1 + "\n" + "Address:- " + add1 + "\n";
                                            vector.add(da);
                                            // i=i+1;
                                        }

                                    }
                                    //i=0;
                                    StringBuilder stringBuilder = new StringBuilder();

                                    for (String element : vector) {
                                        stringBuilder.append(element).append("\n");
                                    }

                                    tx.setText(stringBuilder.toString());

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // Handle any errors that occur
                                }
                            });

                        }
                    });
                }*/
                }//else end
            }
        });
        regs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                registration_user();
            }
        });

    }
    public  void registration_user(){
        Intent intent2 = new Intent(this, reg_admin.class);
        startActivity(intent2);
    }

}
