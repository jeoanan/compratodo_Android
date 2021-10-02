package com.cesde.compratodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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
                } else if (!email.equals("admin") || !password.equals("12345")) {
                    Toast.makeText(this, "El email y la contraseña no coinciden intentalo de nuevo",
                            Toast.LENGTH_SHORT).show();
                } else {
                    CollectionReference usersRef = db.collection("users");
                    Query emailQuery = usersRef.whereEqualTo("email", email);
                    Query passwordQuery = usersRef.whereEqualTo("password", password);

                    if(email.equals(emailQuery) && password.equals(passwordQuery)){
                        Intent openLogin = new Intent(getApplicationContext(), ShopMainActivity.class);
                        startActivity(openLogin);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "el usuario y contraseña son incorrectos intentalo de nuevo", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case R.id.btnRegister:
                Intent openRegister = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(openRegister);
                break;
        }

    }
}