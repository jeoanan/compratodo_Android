package com.cesde.compratodo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail, etPassword;
    Button btnLogin, btnRegister;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        switch (view.getId()){

            case R.id.btnLogin:
                if(email.equals("")) {
                    Toast.makeText(this, "El email no puede estar vacio", Toast.LENGTH_SHORT).show();
                } else if(password.equals("")) {
                    Toast.makeText(this, "La contraseña no puede estar vacia", Toast.LENGTH_SHORT).show();
                } else {
                    final DocumentReference docRef = db.collection("users")
                            .document(email);
                    docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value,
                                            @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Toast.makeText(getApplicationContext(), "Error al iniciar sesion",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (value != null && value.exists()) {
                                String passwordUser = value.get("password").toString();
                                if(password.equals(passwordUser)){
                                    Intent openLogin = new Intent(getApplicationContext(), ShopMainActivity.class);
                                    startActivity(openLogin);
                                }else{
                                    Toast.makeText(getApplicationContext(),
                                            "La contraseña es incorrecta, intentalo de nuevo",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Usuario no existe",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                        /*Intent openLogin = new Intent(getApplicationContext(), ShopMainActivity.class);
                        startActivity(openLogin);
                        Toast.makeText(getApplicationContext(),
                         "el usuario y contraseña son incorrectos intentalo de nuevo", Toast.LENGTH_SHORT).show();*/
                }
                break;
            case R.id.btnRegister:
                Intent openRegister = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(openRegister);
                break;
        }

    }
}