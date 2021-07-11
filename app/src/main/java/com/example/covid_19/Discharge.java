package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Discharge extends AppCompatActivity {

    ArrayList<String> a1;
    ArrayAdapter arrayAdapter;
    ListView listView;
    Map<Integer, String> map= new HashMap<Integer, String>();
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discharge);

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
                map.clear();

                for(DataSnapshot snapshot1:snapshot.getChildren()){

                    Information info = snapshot1.getValue(Information.class);

                    String name = info.getName();
                    String covid = info.getCovid();
                    String uid = info.getUid();

                    map.put(a1.size(),uid);

                    String txt = "Name: "+name+"\n"+"Covid: "+covid;
                    a1.add(txt);
                }

                pd.dismiss();
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int pos = position;

                new AlertDialog.Builder(Discharge.this)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle("Change status:")
                        .setMessage("Do you want to discharge this patient?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                a1.remove(pos);

                                Toast.makeText(Discharge.this, "Discharged Successfully", Toast.LENGTH_SHORT).show();
                                FirebaseDatabase.getInstance().getReference().child("Patient Details").child(map.get(pos)).removeValue();

                                arrayAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

                return true;
            }
        });
    }
}