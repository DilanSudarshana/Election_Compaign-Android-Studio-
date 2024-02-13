package com.example.e2140139;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DataRecordActivity extends AppCompatActivity {

    Button btnSaveData;
    Button btnClearData;
    Button btnGo;
    EditText edtlat, edtlon, edtdate, edtNoOfVotes, edtexpVote, edtAddDetails;
    Spinner sptype, spftype, spmsparty;
    userDbHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_record);
        db = new userDbHandler(this);

        edtlat = findViewById(R.id.edtlat);
        edtlon = findViewById(R.id.edtlon);
        edtdate = findViewById(R.id.edtdate);
        edtNoOfVotes = findViewById(R.id.edtnovotes);
        edtexpVote = findViewById(R.id.edtexvotes);
        edtAddDetails = findViewById(R.id.edtadditionl);
        btnSaveData = findViewById(R.id.btnsavedata);
        btnClearData = findViewById(R.id.btncleardata);
        btnGo = findViewById(R.id.btngodashboad);

        Intent i = getIntent();
        String lat = i.getStringExtra("lat");
        String lon = i.getStringExtra("lon");

        edtlat.setText(String.valueOf(lat));
        edtlon.setText(String.valueOf(lon));

        sptype = findViewById(R.id.spParty);
        spftype = findViewById(R.id.spftype);
        spmsparty = findViewById(R.id.spmsparty);

        // Create arrays for Spinner items
        String[] party = {"UNP", "SLFP", "JVP", "CPSL","SETM"};
        String[] type = {"Very Negative", "Negative", "Unavailable", "Neutral", "Positive", "Excellent"};
        String[] mostsParty = {"UNP", "SLFP", "JVP", "CPSL","SETM"};

        // Create ArrayAdapter for Spinners
        ArrayAdapter<String> adapterParty = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, party);
        ArrayAdapter<String> adapterftype = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, type);
        ArrayAdapter<String> adaptermsParty = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mostsParty);

        // Set dropdown layout style
        adapterParty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterftype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptermsParty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set adapters for the Spinners
        sptype.setAdapter(adapterParty);
        spftype.setAdapter(adapterftype);
        spmsparty.setAdapter(adaptermsParty);

        // Set a click listener for the Save button
        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (edtlat.getText().toString().equals("") ||
                            edtlon.getText().toString().equals("") ||
                            edtdate.getText().toString().equals("") ||
                            edtNoOfVotes.getText().toString().equals("") ||
                            edtexpVote.getText().toString().equals("") ||
                            edtAddDetails.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Empty Inputs", Toast.LENGTH_LONG).show();
                    } else {
                        String selectedParty = sptype.getSelectedItem().toString();
                        String selectedType = spftype.getSelectedItem().toString();
                        String selectedFeedback = spmsparty.getSelectedItem().toString();
                        int noOfVotes = Integer.valueOf(edtNoOfVotes.getText().toString());
                        int noOfExVotes = Integer.valueOf(edtexpVote.getText().toString());
                        saveData(i.getStringExtra("lat"), i.getStringExtra("lon"), selectedParty, edtdate.getText().toString(),
                                noOfVotes, noOfExVotes, selectedType, selectedFeedback,
                                edtAddDetails.getText().toString());
                        edtlat.setText("");
                        edtlon.setText("");
                        edtdate.setText("");
                        edtNoOfVotes.setText("");
                        edtexpVote.setText("");
                        edtAddDetails.setText("");
                        setResult(RESULT_OK);
                        finish();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
        clearData();
        goDashboard();
    }

    public void saveData(String lat, String lon, String party, String date, int nov, int noev, String feedbacktype, String msparty, String ad) {
        try {
            boolean isInserted = db.insertData(lat, lon, party, date, nov, noev, feedbacktype, msparty, ad);
            if (isInserted == true) {
                Toast.makeText(DataRecordActivity.this, "Data Uploaded Successsfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(DataRecordActivity.this, "Data Not Uploaded", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(DataRecordActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void clearData(){
        btnClearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtlat.setText("");
                edtlon.setText("");
                edtdate.setText("");
                edtNoOfVotes.setText("");
                edtexpVote.setText("");
                edtAddDetails.setText("");
            }
        });
    }
    public void goDashboard(){
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataRecordActivity.this, Dashboad.class));
                Toast.makeText(DataRecordActivity.this, "Close session", Toast.LENGTH_LONG).show();
            }
        });
    }

}
