package com.cesde.compratodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cesde.compratodo.Adapters.ProductAdapter;
import com.cesde.compratodo.Adapters.ProductUserAdapter;
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
    private String emailUser, roleUSer, shopUser;
    ArrayList<Product> productArrayList;
    ArrayList<Product> productusertArrayList;
    ProductAdapter productAdapter;
    ProductUserAdapter productUserAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shopMainBinding = ActivityShopMainBinding.inflate(getLayoutInflater());
        View view = shopMainBinding.getRoot();
        setContentView(view);
        db = FirebaseFirestore.getInstance();
       roleUSer = getIntent().getStringExtra("role");
        if(roleUSer.equals("Vendedor")){
            shopMainBinding.btnCar.setVisibility(View.GONE);
            shopMainBinding.btnCheck.setVisibility(View.GONE);
            productArrayList = new ArrayList<>();
            productAdapter = new ProductAdapter(this,productArrayList, db);
            shopMainBinding.rvProducts.setHasFixedSize(true);
            shopMainBinding.rvProducts.setLayoutManager(new LinearLayoutManager(this));
            shopMainBinding.rvProducts.setAdapter(productAdapter);

            shopUser = getIntent().getStringExtra("shop");
            getProducts(shopUser);
        }else if(roleUSer.equals("Usuario")){
            shopMainBinding.btnCreateProduct.setVisibility(View.GONE);
            shopMainBinding.btncheckVendor.setVisibility(View.GONE);
            productArrayList = new ArrayList<>();
            productusertArrayList = new ArrayList<>();
            productUserAdapter = new ProductUserAdapter(this,productusertArrayList);
            shopMainBinding.rvProducts.setHasFixedSize(true);
            shopMainBinding.rvProducts.setLayoutManager(new LinearLayoutManager(this));
            shopMainBinding.rvProducts.setAdapter(productUserAdapter);
            emailUser = getIntent().getStringExtra("email");
            getProducts();
        }

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
                                productusertArrayList.add(dc.getDocument().toObject(Product.class));
                            }
                        }
                        productUserAdapter.notifyDataSetChanged();
                    }
                });

    }

    public void getProducts(String shop){
        db.collection("products").whereEqualTo("shopuser", shop)
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
        intent.putExtra("shop", shopUser);
        startActivity(intent);
    }


    public void checkUsers(View view){
        Intent intent = new Intent(getApplicationContext(), CheckActivity.class);
        intent.putExtra("email", emailUser);
        startActivity(intent);
    }
}