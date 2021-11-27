package com.cesde.compratodo.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cesde.compratodo.BuyProductActivity;
import com.cesde.compratodo.EditProductActivity;
import com.cesde.compratodo.Entities.Product;
import com.cesde.compratodo.databinding.ProductItemBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    private Context context;
    private ProductItemBinding productItemBinding;
    private ArrayList<Product> productArrayList;
    private FirebaseFirestore db;

    public ProductAdapter(
            Context context,
            ArrayList<Product> productArrayList,
            FirebaseFirestore db){
        this.context = context;
        this.productArrayList = productArrayList;
        this.db = db;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        productItemBinding = productItemBinding.inflate(LayoutInflater.from(context));
        return new ProductViewHolder(productItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = productArrayList.get(position);
        holder.itemBinding.tvName.setText(product.getName());
        holder.itemBinding.tvDescription.setText(product.getDescription());
        holder.itemBinding.tvStock.setText(String.valueOf(product.getStock()));
        holder.itemBinding.tvPrice.setText(String.valueOf(product.getPrice()));
        holder.itemBinding.tvCategory.setText(product.getCategory());
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.collection("products").document(product.getId()).delete()
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(context, "Archivo eliminado",
                                    Toast.LENGTH_SHORT).show();
                            productArrayList.remove(holder.getAdapterPosition());
                            notifyDataSetChanged();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(context, "Falla al eliminar",
                                        Toast.LENGTH_SHORT).show());

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        holder.itemBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("¿Está seguro de eliminar el elemento?");
                builder.create().show();

            }
        });
        holder.itemBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditProductActivity.class);
                intent.putExtra("product", product);
                context.startActivity(intent);
            }
        });
        holder.itemBinding.btnBuyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buyIntent = new Intent(context, BuyProductActivity.class);
                buyIntent.putExtra("product",product);
                context.startActivity(buyIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ProductItemBinding itemBinding;
        public ProductViewHolder(@NonNull ProductItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
