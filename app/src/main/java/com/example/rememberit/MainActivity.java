package com.example.rememberit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button mSaveBtn,mLoadBtn;
    private TextView readDB;
    private EditText mMainText;
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainText = (EditText)findViewById(R.id.mainText);
        mLoadBtn = (Button)findViewById(R.id.loadBtn);
        mSaveBtn = (Button)findViewById(R.id.saveBtn);
        readDB = (TextView)findViewById(R.id.readDB);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notes = mMainText.getText().toString();
                Map<String, String> userMap = new HashMap<>();
                userMap.put("Note",notes);
//                mFirestore.collection("Users").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(MainActivity.this, "Note Added", Toast.LENGTH_SHORT).show();
//                    }
//                });

                mFirestore.collection("Users").document("one").set(userMap).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        mLoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirestore.collection("Users").document("one").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists() && documentSnapshot!=null) {
                                String notes = documentSnapshot.getString("Note");
                                readDB.setText("Welcome " + notes);
                            }
                        }else {
                            readDB.setText("Wesdsdlcome " );
                        }
                    }
                });
            }
        });
    }
}
