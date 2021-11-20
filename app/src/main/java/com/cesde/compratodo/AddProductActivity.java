package com.cesde.compratodo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cesde.compratodo.databinding.ActivityAddProductBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    private ActivityAddProductBinding addProductBinding;
    private FirebaseStorage storage;
    private FirebaseFirestore db;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addProductBinding = ActivityAddProductBinding.inflate(getLayoutInflater());
        View view = addProductBinding.getRoot();
        setContentView(view);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db = FirebaseFirestore.getInstance();

    }
    public void selectImageFromGallery(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }
    private ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Uri uri = data.getData();

                        if(uri != null){
                            addProductBinding.ivProduct.setImageURI(uri);
                            imageUri = uri;
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error al subir la imagen",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
    public void createProduct(View view){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creando Producto");
        progressDialog.show();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss.SSS",
                Locale.CANADA);
        Date date = new Date();

        String filename = formatter.format(date);
        storageReference = FirebaseStorage.getInstance()
                .getReference("productsimg/"+filename);
        UploadTask uploadTask = storageReference.putFile(imageUri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot,
                Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(task.isSuccessful()){
                    return storageReference.getDownloadUrl();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Se aproducido un error al registrar la imagen",
                            Toast.LENGTH_SHORT).show();
                    throw task.getException();
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri downloadUrl = task.getResult();
                    String url = String.valueOf(downloadUrl);
                    addProduct(url);
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        });
    }

    public void addProduct(String url){
        String productName = addProductBinding.etNamec.getText().toString();
        String description = addProductBinding.etDescripcionc.getText().toString();
        String stock =  addProductBinding.etStockc.getText().toString();
        String price =  addProductBinding.etPricec.getText().toString();
        String category = addProductBinding.etCategoryc.getText().toString();
        Map<String, Object> productData = new HashMap<>();
        productData.put("name",productName);
        productData.put("description",description);
        productData.put("stock", Integer.parseInt(stock));
        productData.put("price", Double.parseDouble(price));
        productData.put("category", category);
        productData.put("imageUrl",url);
        db.collection("products").add(productData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Elemento Creado",
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
}