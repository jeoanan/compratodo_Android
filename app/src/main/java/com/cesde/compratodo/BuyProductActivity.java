package com.cesde.compratodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cesde.compratodo.Entities.Product;
import com.cesde.compratodo.databinding.ActivityBuyProductBinding;
import com.cesde.compratodo.databinding.ActivityEditProductBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BuyProductActivity extends AppCompatActivity {

    private ActivityBuyProductBinding buyProductBinding;
    private Product product;
    private FirebaseFirestore db;
    private int newStock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buyProductBinding = ActivityBuyProductBinding.inflate(getLayoutInflater());
        View view = buyProductBinding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");
        db = FirebaseFirestore.getInstance();
        buyProductBinding.tvBuyProductName.setText(product.getName());
        buyProductBinding.tvBuyProductDescription.setText(product.getDescription());
        buyProductBinding.tvBuyProductStock.setText(String.valueOf(product.getStock()));
        buyProductBinding.tvBuyProductPrice.setText(String.valueOf(product.getPrice()));
        buyProductBinding.tvBuyProductCategory.setText(product.getCategory());
        //buyProductBinding.tvBuyProductCategory.setText(product.geturl());  add imageURL

    }

    public void addCar(View view){
        String productName = buyProductBinding.tvBuyProductName.getText().toString();
        String description =  buyProductBinding.tvBuyProductDescription.getText().toString();
        String stock =  buyProductBinding.tvBuyProductStock.getText().toString();
        String price =  buyProductBinding.tvBuyProductPrice.getText().toString();
        String buyProduct =  buyProductBinding.etBuyProductBuyStock.getText().toString();
        String category = buyProductBinding.tvBuyProductCategory.getText().toString();
        Map<String, Object> carData = new HashMap<>();
        carData.put("name",productName);
        carData.put("description",description);
        carData.put("stock", Integer.parseInt(stock));
        carData.put("buyProduct", Integer.parseInt(buyProduct));
        carData.put("price", Double.parseDouble(price));
        carData.put("category", category);
        db.collection("carshop").add(carData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Elemento Guardado en el carrito",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),ShopMainActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public void buyProduct(View view){
        int buyUser = Integer.parseInt(buyProductBinding.etBuyProductBuyStock.getText().toString());
        int stockData = Integer.parseInt(buyProductBinding.tvBuyProductStock.getText().toString());

        newStock = stockData - buyUser;

        if(buyUser < 1){
            Toast.makeText(getApplicationContext(),
                    "Seleccione las unidades a comprar", Toast.LENGTH_SHORT).show();
        }else if(buyUser > stockData){
            Toast.makeText(getApplicationContext(),
                    "Las unidades a comprar superan las unidades disponibles",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            String productName = buyProductBinding.tvBuyProductName.getText().toString();
            String description = buyProductBinding.tvBuyProductDescription.getText().toString();
            String stock = buyProductBinding.tvBuyProductStock.getText().toString();
            String price = buyProductBinding.tvBuyProductPrice.getText().toString();
            String buyProduct = buyProductBinding.etBuyProductBuyStock.getText().toString();
            String category = buyProductBinding.tvBuyProductCategory.getText().toString();
            Map<String, Object> buyData = new HashMap<>();
            buyData.put("name", productName);
            buyData.put("description", description);
            buyData.put("buyProduct", Integer.parseInt(buyProduct));
            buyData.put("price", Double.parseDouble(price));
            buyData.put("category", category);
            buyData.put("email", "usuario1@usuario.com");
            db.collection("check").add(buyData)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Map<String, Object> updateProduct = new HashMap<>();
                            updateProduct.put("stock", newStock);
                            db.collection("products").document(product.getId()).update(updateProduct)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Gracias por su compra",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(),
                                                    ShopMainActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Error al actualizar el producto",
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }
}