package com.cesde.compratodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cesde.compratodo.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{


    EditText etNameR, etEmailR, etRoleR, etPasswordR, etPasswordConR, etShopR;
    Button btnRegisterR, btnLoginR;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        etNameR = findViewById(R.id.etNameR);
        etEmailR = findViewById(R.id.etEmail);
        etRoleR = findViewById(R.id.etRoleR);
        etPasswordR = findViewById(R.id.etPasswordR);
        etPasswordConR = findViewById(R.id.etPasswordConR);
        etShopR = findViewById(R.id.etShopR);
        btnRegisterR = findViewById(R.id.btnRegisterR);
        btnLoginR = findViewById(R.id.btnLoginR);
    }

    @Override
    public void onClick(View view) {
        String name = binding.etNameR.getText().toString();
        String email = binding.etEmailR.getText().toString().toLowerCase();
        String role = binding.etRoleR.getText().toString();
        String password = binding.etPasswordR.getText().toString();
        String confpassword = binding.etPasswordConR.getText().toString();
        String shop = binding.etShopR.getText().toString();

        switch (view.getId()){

            case R.id.btnLoginR:
                Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                Intent openLogin = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(openLogin);
                break;
            case R.id.btnRegisterR:
                if (name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "El correo no puede estar vacío", Toast.LENGTH_SHORT).show();
                } else if(role.equals("")){
                    Toast.makeText(getApplicationContext(), "El rol no puede estar vacío", Toast.LENGTH_SHORT).show();
                } else if(password.equals("")){
                    Toast.makeText(getApplicationContext(), "El correo no puede estar vacío", Toast.LENGTH_SHORT).show();
                } else if (confpassword.equals("")){
                    Toast.makeText(getApplicationContext(), "La contraseña no puede estar vacia", Toast.LENGTH_SHORT).show();
                } else {
                    if(email.length() < 13){
                        Toast.makeText(getApplicationContext(), "el email es demasiado corto", Toast.LENGTH_SHORT).show();
                    } else {
                        Pattern pat = Pattern.compile("[A-z0-9_+\\-*/]+@[A-z0-9]+\\.[A-z.]+");
                        Matcher mat = pat.matcher(email);

                        if(mat.find()){
                            Toast.makeText(getApplicationContext(), "Correo Valido", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Correo Invalido", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }

    }
}