package com.example.tute5_testapp_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText Sid,Sname,Sadr,Scon;

    DatabaseReference dbRef;
    Student std;

    private void clearControls(){
        Sid.setText("");
        Sname.setText("");
        Sadr.setText("");
        Scon.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Sid=findViewById(R.id.id);
        Sname=findViewById(R.id.name);
        Sadr=findViewById(R.id.address);
        Scon=findViewById(R.id.conNo);



        std = new Student();

        final Button saveBtn=findViewById(R.id.button);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              dbRef = FirebaseDatabase.getInstance().getReference().child("Student");

              try {
                  if(TextUtils.isEmpty(Sid.getText().toString()))
                      Toast.makeText(getApplicationContext(),"Please enter an ID",Toast.LENGTH_SHORT).show();
                  else if(TextUtils.isEmpty((Sname.getText().toString())))
                      Toast.makeText(getApplicationContext(),"Please enter a Name",Toast.LENGTH_SHORT).show();
                  else if(TextUtils.isEmpty(Sadr.getText().toString()))
                      Toast.makeText(getApplicationContext(),"Please enter an address",Toast.LENGTH_SHORT).show();
                  else
                        std.setId(Sid.getText().toString().trim());
                        std.setName(Sname.getText().toString().trim());
                        std.setAddress(Sadr.getText().toString().trim());
                        std.setConNo(Integer.parseInt(Scon.getText().toString().trim()));

                        dbRef.push().setValue(std);

                        Toast.makeText(getApplicationContext(),"Data saved Successfully",Toast.LENGTH_SHORT).show();
                        clearControls();

              }catch(NumberFormatException e){
                  Toast.makeText(getApplicationContext(),"Invalid Contact No",Toast.LENGTH_SHORT).show();
              }
            }
        });

        final Button showbtn=findViewById(R.id.button2);
        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference readRef=FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            Sid.setText(dataSnapshot.child("id").getValue().toString());
                            Sname.setText(dataSnapshot.child("name").getValue().toString());
                            Sadr.setText(dataSnapshot.child("address").getValue().toString());
                            Scon.setText(dataSnapshot.child("conNo").getValue().toString());
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No Source to Display",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        final  Button btnupdate=findViewById(R.id.button3);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference upRef=FirebaseDatabase.getInstance().getReference().child("Student");
                upRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Std1")){
                            try {
                                std.setId(Sid.getText().toString().trim());
                                std.setName(Sname.getText().toString().trim());
                                std.setAddress(Sadr.getText().toString().trim());
                                std.setConNo(Integer.parseInt(Scon.getText().toString().trim()));

                                dbRef = FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                                dbRef.setValue(std);
                                clearControls();

                                Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();

                            }catch (NumberFormatException e){
                                Toast.makeText(getApplicationContext(),"Invalid Contact Number",Toast.LENGTH_SHORT).show();
                            }

                        }else
                            Toast.makeText(getApplicationContext(),"No Source to Update",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        final Button btDelete=findViewById(R.id.button4);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Student");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Std1")){
                            dbRef=FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                            dbRef.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(),"Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No Source to Delete",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }


}
