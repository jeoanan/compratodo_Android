package com.cesde.compratodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.cesde.compratodo.Entities.Product;
import com.cesde.compratodo.databinding.ActivityCarBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CarActivity extends AppCompatActivity {

    private ActivityCarBinding carBinding;
    private FirebaseFirestore db;
    ArrayList<Product> productArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carBinding = ActivityCarBinding.inflate(getLayoutInflater());
        View view = carBinding.getRoot();
        setContentView(view);
        db = FirebaseFirestore.getInstance();
    }
}