package com.cesde.compratodo.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cesde.compratodo.BuyProductActivity;
import com.cesde.compratodo.Entities.Product;
import com.cesde.compratodo.databinding.ProductinvitedItemBinding;

import java.util.ArrayList;

public class ProductUserAdapter extends RecyclerView.Adapter<ProductUserAdapter.ProductUserViewHolder> {
    private Context context;
    private ProductinvitedItemBinding productinvitedItemBinding;
    private ArrayList<Product> productuserArrayList;
    public ProductUserAdapter(Context context, ArrayList<Product> productuserArrayList){
        this.context = context;
        this.productuserArrayList = productuserArrayList;
    }

    @NonNull
    @Override
    public ProductUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                       int viewType) {
        productinvitedItemBinding = ProductinvitedItemBinding.inflate(LayoutInflater.from(context));
        return new ProductUserViewHolder(productinvitedItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductUserAdapter.ProductUserViewHolder holder,
                                 int position) {
        Product product = productuserArrayList.get(position);
        holder.itemBinding.tvNameInv.setText(product.getName());
        holder.itemBinding.tvDescriptionInv.setText(product.getDescription());
        holder.itemBinding.tvStockInv.setText(String.valueOf(product.getStock()));
        holder.itemBinding.tvPriceInv.setText(String.valueOf(product.getPrice()));
        holder.itemBinding.tvCategoryInv.setText(product.getCategory());
        Glide.with(context).load(product.getImageUrl()).into(holder.itemBinding.ivProductInItem);

        holder.itemBinding.btnBuyProductInv.setOnClickListener(new View.OnClickListener() {
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
        return productuserArrayList.size();
    }

    public class ProductUserViewHolder extends RecyclerView.ViewHolder {
        ProductinvitedItemBinding itemBinding;
        public ProductUserViewHolder(@NonNull ProductinvitedItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }

}
