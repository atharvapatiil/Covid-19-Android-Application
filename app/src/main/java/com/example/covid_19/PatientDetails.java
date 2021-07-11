package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PatientDetails extends AppCompatActivity {

    ArrayList<String> a1;
    ArrayAdapter arrayAdapter;
    ListView listView;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        pd = new ProgressDialog(this);

        pd.setMessage("Please Wait!");
        pd.show();

        listView = findViewById(R.id.myListView);

        a1 = new ArrayList<>();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, a1);

        listView.setAdapter(arrayAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Patient Details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                a1.clear();

                for(DataSnapshot snapshot1:snapshot.getChildren()){

                    Information info = snapshot1.getValue(Information.class);

                    String txt = "Name: "+info.getName()+"\n"+
                                    "Age: "+info.getAge()+"\n"+
                                    "Gender: "+info.getGender()+"\n"+
                                    "Covid: "+info.getCovid()+"\n"+
                                    "Address: "+info.getAddress()+"\n"+
                                    "Blood Grp: "+info.getBloodGroup()+"\n"+
                                    "UID: "+info.getUid();
                    a1.add(txt);
                }

                pd.dismiss();
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}