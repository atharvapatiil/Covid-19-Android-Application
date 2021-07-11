package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NewPatient extends AppCompatActivity {

    String gender[] = {"Select Gender","Male", "Female"};
    String sex = "";

    String covidStatus[] = {"Covid Status", "Positive", "Negative"};
    String covid = "";

    EditText editTextName;
    EditText editTextAge;
    EditText editTextAddress;
    EditText editTextBloodGroup;

    FirebaseAuth auth;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        pd = new ProgressDialog(this);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextBloodGroup = findViewById(R.id.editBloodGroup);


        ArrayAdapter adapterGender = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerG = findViewById(R.id.spinnerGender);
        spinnerG.setPrompt("Gender");

        spinnerG.setAdapter(adapterGender);

        spinnerG.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex = gender[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter adapterCovid = new ArrayAdapter(this, android.R.layout.simple_spinner_item, covidStatus);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerC = findViewById(R.id.spinnerCovid);
        spinnerC.setPrompt("Gender");

        spinnerC.setAdapter(adapterCovid);

        spinnerC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                covid = covidStatus[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onClickAddDetails(View view){

        String name = editTextName.getText().toString();
        String age = editTextAge.getText().toString();
        String address = editTextAddress.getText().toString();
        String bloodGroup = editTextBloodGroup.getText().toString();

        if(name.isEmpty() || age.isEmpty() || sex.equals("Select Gender") || address.isEmpty() || covid.equals("Covid Status") || bloodGroup.isEmpty()){
            Toast.makeText(this, "Please provide all the details", Toast.LENGTH_SHORT).show();
        }else{
            pd.setMessage("Please Wait!");
            pd.show();

            Map<String, Object> m1 = new HashMap<>();
            m1.put("name", name);
            m1.put("age", age);
            m1.put("gender", sex);
            m1.put("address", address);
            m1.put("covid", covid);
            m1.put("bloodGroup", bloodGroup);

            String key = FirebaseDatabase.getInstance().getReference().child("Patient Details").push().getKey();

            m1.put("uid", key);

            FirebaseDatabase.getInstance().getReference().child("Patient Details").child(key).updateChildren(m1).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(NewPatient.this, "Details Added Successfully", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull  Exception e) {
                    Toast.makeText(NewPatient.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });

        }

    }
}