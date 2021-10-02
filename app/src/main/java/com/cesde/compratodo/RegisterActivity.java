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
        etPasswordR = findViewById(R.id.etPasswordR);
        etPasswordConR = findViewById(R.id.etPasswordConR);
        btnRegisterR = findViewById(R.id.btnRegisterR);
        btnLoginR = findViewById(R.id.btnLoginR);
    }

    @Override
    public void onClick(View view) {
        String id = etIdR.getText().toString();
        String name = etNameR.getText().toString();
        String lastname = etLastnameR.getText().toString();
        String email = etEmailR.getText().toString();
        String password = etPasswordR.getText().toString();
        String confpassword = etPasswordConR.getText().toString();

        switch (view.getId()){

            case R.id.btnLoginR:
                Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                Intent openLogin = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(openLogin);
                break;
            case R.id.btnRegisterR:
                if (id.isEmpty()){
                    Toast.makeText(getApplicationContext(), "La identificación no puede estar vacía", Toast.LENGTH_SHORT).show();
                } else if(name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
                } else if(lastname.equals("")){
                    Toast.makeText(getApplicationContext(), "El apellido no puede estar vacío", Toast.LENGTH_SHORT).show();
                } else if(email.equals("")){
                    Toast.makeText(getApplicationContext(), "El correo no puede estar vacío", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")){
                    Toast.makeText(getApplicationContext(), "La contraseña no puede estar vacia", Toast.LENGTH_SHORT).show();
                }else {
                    if(email.length() < 13){
                        Toast.makeText(getApplicationContext(), "el email es demasiado corto", Toast.LENGTH_SHORT).show();
                    } else {
                        int contador = 0;
                        for (int i=0;i<email.length();i++){
                            char position = email.charAt(i);
                            String letra = String.valueOf(position);
                            if (letra.contains("@") || letra.contains(".")){
                                contador++;
                            }
                        }

                        if (contador < 2){
                            Toast.makeText(getApplicationContext(), "El correo no tiene @ o .com", Toast.LENGTH_SHORT).show();
                        } else {
                            if(password.length() < 9){
                                Toast.makeText(getApplicationContext(), "La contraseña es demasiado corta", Toast.LENGTH_SHORT).show();
                            } else if(!password.equals(confpassword)){
                                Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                            }else {
                                Map<String, Object> user = new HashMap<>();
                                user.put("idUser", id);
                                user.put("name", name);
                                user.put("lastname", lastname);
                                user.put("email", email);
                                user.put("password", password);

                                        db.collection("users")
                                                .add(user)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(getApplicationContext(), "Registro Enviado", Toast.LENGTH_SHORT).show();

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }


                    }
                }
                break;
        }

    }
}