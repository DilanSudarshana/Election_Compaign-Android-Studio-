package com.example.e2140139;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    Button btnreg;
    EditText edtuname, edtpwd;
    userDbHandler userdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userdb = new userDbHandler(this);

        btnreg = (Button) findViewById(R.id.btnreg);
        edtuname = (EditText) findViewById(R.id.edtunamereg);
        edtpwd = (EditText) findViewById(R.id.edtpasswordreg);
        registerLogin();
    }

    public void registerLogin() {
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(edtuname.getText().toString().equals("")||edtpwd.getText().toString().equals("")){
                        Toast.makeText(RegisterActivity.this, "Enter User Name and Password", Toast.LENGTH_LONG).show();
                    }else{
                        boolean isInserted = userdb.RegisterUser(edtuname.getText().toString(),
                                edtpwd.getText().toString());
                        if (isInserted == true) {
                            Toast.makeText(RegisterActivity.this, "Data inserted successsfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
                        }
                        edtuname.setText("");
                        edtpwd.setText("");
                    }
                } catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}