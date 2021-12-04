package com.cesde.compratodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cesde.compratodo.Adapters.CheckAdapter;
import com.cesde.compratodo.Entities.Check;
import com.cesde.compratodo.Entities.Product;
import com.cesde.compratodo.databinding.ActivityCheckBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CheckActivity extends AppCompatActivity {

    private ActivityCheckBinding checkBinding;
    private FirebaseFirestore db;
    private String emailUser;
    ArrayList<Check> checkArrayList;
    CheckAdapter checkAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkBinding = ActivityCheckBinding.inflate(getLayoutInflater());
        View view = checkBinding.getRoot();
        setContentView(view);
        db = FirebaseFirestore.getInstance();

        checkArrayList = new ArrayList<>();
        checkAdapter = new CheckAdapter(this, checkArrayList);
        checkBinding.rvCheck.setHasFixedSize(true);
        checkBinding.rvCheck.setLayoutManager(new LinearLayoutManager(this));
        checkBinding.rvCheck.setAdapter(checkAdapter);
        emailUser = getIntent().getStringExtra("email");

        getChecks(emailUser);
    }

    public void getChecks(String emailU){
        db.collection("check").whereEqualTo("email", emailU)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error !=null){
                            Toast.makeText(getApplicationContext(), "Error al recibir información",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                checkArrayList.add(dc.getDocument().toObject(Check.class));
                            }
                        }
                        checkAdapter.notifyDataSetChanged();
                    }
                });
    }
}