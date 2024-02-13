package com.example.e2140139;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboad extends AppCompatActivity {

    Button btnstart;
    Button btnlogout;
    TextView txtunp, txtslep, txtjvp, txtcpsl, txtsetm, txttnovotes, txtleparty;
    userDbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboad);
        btnstart = (Button) findViewById(R.id.btnstartsession);
        btnlogout = (Button) findViewById(R.id.btnlogout);
        db = new userDbHandler(this);

        txtunp = (TextView) findViewById(R.id.txtunp);
        txtslep = (TextView) findViewById(R.id.txtslfp);
        txtjvp = (TextView) findViewById(R.id.txtjvp);
        txtcpsl = (TextView) findViewById(R.id.txtcpsl);
        txtsetm = (TextView) findViewById(R.id.txttxtsepm);
        txttnovotes = (TextView) findViewById(R.id.txttnovotes);
        txtleparty = (TextView) findViewById(R.id.txtexparty);
        startSession();
        countVotes();
        leadingParty();
        logOut();
    }

    public void startSession() {
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboad.this, MapsActivity.class));
            }
        });
    }
    public void logOut() {
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboad.this, MainActivity.class));
            }
        });
    }

    public void countVotes() {
        try {
            int totalVotes = db.countTotalVotes();
            txttnovotes.setText("Total Votes: " + String.valueOf(totalVotes));

            int totalUnpVotes = db.countUnpVotes();
            int totalSLEFVotes = db.countSLPFVotes();
            int totalJVPVotes = db.countJVPFVotes();
            int totalCPSLVotes = db.countCPSLVotes();
            int totalSETMVotes = db.countSETMVotes();

            float punp = ((float) totalUnpVotes / totalVotes) * 100.0f;
            float pslef = ((float) totalSLEFVotes / totalVotes) * 100.0f;
            float pjvp = ((float) totalJVPVotes / totalVotes) * 100.0f;
            float pcpsl = ((float) totalCPSLVotes / totalVotes) * 100.0f;
            float pset = ((float) totalSETMVotes / totalVotes) * 100.0f;

            txtunp.setText(String.format("%.2f %%", punp));
            txtslep.setText(String.format("%.2f %%", pslef));
            txtjvp.setText(String.format("%.2f %%", pjvp));
            txtcpsl.setText(String.format("%.2f %%", pcpsl));
            txtsetm.setText(String.format("%.2f %%", pset));
        } catch (Exception e) {
            Toast.makeText(Dashboad.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void leadingParty() {
        try {
            String leParty = db.leadingParty().toString();
            txtleparty.setText("The Leading Party is: " + leParty);
        } catch (Exception e) {
            Toast.makeText(Dashboad.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}