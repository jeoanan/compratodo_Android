package com.cesde.compratodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{


    EditText etIdR, etNameR, etLastnameR, etEmailR, etPasswordR, etPasswordConR;
    Button btnRegisterR, btnLoginR;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();


        etIdR = findViewById(R.id.etIdR);
        etNameR = findViewById(R.id.etNameR);
        etLastnameR = findViewById(R.id.etLastnameR);
        etEmailR = findViewById(R.id.etEmailR);
        etNameR = findViewById(R.id.etNameR);
        etPasswordR = findViewById(R.id.etPasswordR);
        etPasswordConR = findViewById(R.id.etPasswordConR);
        btnRegisterR = findViewById(R.id.btnRegisterR);
        btnLoginR = findViewById(R.id.btnLoginR);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnLoginR:
                Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                Intent openLogin = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(openLogin);
                break;
            case R.id.btnRegisterR:
                Map<String, Object> user = new HashMap<>();
                user.put("first", "Ada");
                user.put("last", "Lovelace");
                user.put("born", 1815);

// Add a new document with a generated ID
                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(this, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(this, "Error adding document", e);
                            }
                        });
                Toast.makeText(getApplicationContext(), "Enviar registro", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}