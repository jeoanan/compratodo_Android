package com.cesde.compratodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cesde.compratodo.Adapters.ProductAdapter;
import com.cesde.compratodo.Entities.Product;
import com.cesde.compratodo.databinding.ActivityShopMainBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShopMainActivity extends AppCompatActivity {

    private ActivityShopMainBinding shopMainBinding;
    private FirebaseFirestore db;
    private String emailUser;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shopMainBinding = ActivityShopMainBinding.inflate(getLayoutInflater());
        View view = shopMainBinding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        emailUser = (String) intent.getSerializableExtra("email");
        db = FirebaseFirestore.getInstance();
        productArrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(this,productArrayList, db);
        shopMainBinding.rvProducts.setHasFixedSize(true);
        shopMainBinding.rvProducts.setLayoutManager(new LinearLayoutManager(this));
        shopMainBinding.rvProducts.setAdapter(productAdapter);
        getProducts();
    }

    public void getProducts(){
        db.collection("products")
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
                                productArrayList.add(dc.getDocument().toObject(Product.class));
                            }
                        }
                        productAdapter.notifyDataSetChanged();
                    }
                });

    }

    public void addProducts(View view){
        Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
        startActivity(intent);
    }
}