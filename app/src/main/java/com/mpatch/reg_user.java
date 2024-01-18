package com.mpatch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.mpatch.databinding.ActivityMainBinding;

public class reg_user extends AppCompatActivity {
    public static final String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
    String amount;
    String name = "Ram sai";
    String upiId = "9542389557@paytm";
    String transactionNote = "pay test";
    String status;
    Uri uri;
    EditText ruppes;
    private ActivityMainBinding binding;
    private Button reg;
    private Button lg;
    private EditText pass1_us;
    private EditText name_us;
    private EditText pass2_us;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private int flag = 0;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    private Activity activity;

    private static boolean isAppInstalled(Context context, String packageName)
    {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }
    private static Uri getUpiPaymentUri(String name, String upiId, String transactionNote, String amount) {
        return new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", transactionNote)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.user_registration);
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users"); // "users" is the name of your database node
        ref = FirebaseDatabase.getInstance().getReference("users");
        pass1_us=findViewById(R.id.reg_passwordEditText);
        pass2_us=findViewById(R.id.reg_confirm_passwordEditText);
        name_us=findViewById(R.id.reg_usernameEditText);
        reg=findViewById(R.id.activity_register_Button);

        reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (pass1_us.getText().toString().equals(pass2_us.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "password match", Toast.LENGTH_SHORT).show();
                    String uname = name_us.getText().toString();
                    String pas = pass1_us.getText().toString();
                    String cpas = pass2_us.getText().toString();
                    ref.child(uname).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    Toast.makeText(reg_user.this, "user exists", Toast.LENGTH_SHORT).show();
                                    //setContentView(R.layout.activity_main);
                                }
                                /*else {
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("U_name", uname);
                                    hashMap.put("password", pas);
                                    ref.child(uname).setValue(hashMap);
                                    Toast.makeText(reg_user.this, "Registeration Successful", Toast.LENGTH_SHORT).show();
                                    //setContentView(R.layout.activity_main);
                                }*/
                            }
                        }
                    });
                    amount = Integer.toString(300);
                    uri = getUpiPaymentUri(name, upiId, transactionNote, amount);
                    payWithGPay();
                } else {
                    Toast.makeText(reg_user.this, "password didn't matched", Toast.LENGTH_SHORT).show();
                    /*HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("U_name", uname);
                    hashMap.put("password", pas);
                    ref.child("users").child(uname).setValue(hashMap)*/
                }
            }
        });

    }
    private void payWithGPay() {
        if (isAppInstalled(this, GOOGLE_PAY_PACKAGE_NAME)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
            activity.startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
        } else {
            Toast.makeText(reg_user.this, "Please Install GPay", Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            status = data.getStringExtra("Status").toLowerCase();
        }

        if ((RESULT_OK == resultCode) && status.equals("success")) {
            Toast.makeText(reg_user.this, "Transaction Successful", Toast.LENGTH_SHORT).show();
            String uname = name_us.getText().toString();
            String pas = pass1_us.getText().toString();
            String cpas = pass2_us.getText().toString();
            ref.child(uname).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            Toast.makeText(reg_user.this, "user exists", Toast.LENGTH_SHORT).show();
                            //setContentView(R.layout.activity_main);
                        } else {
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("U_name", uname);
                            hashMap.put("password", pas);
                            ref.child(uname).setValue(hashMap);
                            Toast.makeText(reg_user.this, "Registeration Successful", Toast.LENGTH_SHORT).show();
                            //setContentView(R.layout.activity_main);
                        }
                    }
                }
            });
        } else {
            Toast.makeText(reg_user.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
