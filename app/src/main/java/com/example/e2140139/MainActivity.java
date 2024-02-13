package com.example.e2140139;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnlog;
    Button btnreg;
    EditText edtuname, edtpwd;
    userDbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new userDbHandler(this);

        btnlog = findViewById(R.id.btnlogin);
        btnreg = findViewById(R.id.btncreate);
        edtuname = findViewById(R.id.edtuname);
        edtpwd = findViewById(R.id.edtpassword);

        onClickLog();
        onClickRegister();
    }

    public void onClickLog() {
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (edtuname.getText().toString().equals("") || edtpwd.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Enter a username and password", Toast.LENGTH_LONG).show();
                    } else {
                        boolean result = db.loginUser(edtuname.getText().toString(), edtpwd.getText().toString());
                        if (result) {
                            String uname=edtuname.getText().toString();
                            String password=edtpwd.getText().toString();
                            startActivity(new Intent(MainActivity.this, Dashboad.class));
                            edtuname.setText("");
                            edtpwd.setText("");
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error Login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onClickRegister() {
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
}
