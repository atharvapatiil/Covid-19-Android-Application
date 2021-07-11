package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNewPatient(View view){

        Intent intent = new Intent(getApplicationContext(), NewPatient.class);
        startActivity(intent);

    }

    public void onClickCovidStatus(View view){

        Intent intent = new Intent(getApplicationContext(), CovidStatus.class);
        startActivity(intent);

    }

    public void onClickDischarge(View view){

        Intent intent = new Intent(getApplicationContext(), Discharge.class);
        startActivity(intent);

    }

    public void onClickPatientDetails(View view){

        Intent intent = new Intent(getApplicationContext(), PatientDetails.class);
        startActivity(intent);

    }
}