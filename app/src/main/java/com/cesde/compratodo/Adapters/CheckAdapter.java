package com.cesde.compratodo.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cesde.compratodo.Entities.Check;
import com.cesde.compratodo.Entities.Product;
import com.cesde.compratodo.databinding.CheckuserItemBinding;
import com.cesde.compratodo.databinding.ProductinvitedItemBinding;

import java.util.ArrayList;

public class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.CheckViewHolder> {
    private Context context;
    private CheckuserItemBinding checkuserItemBinding;
    private ArrayList<Check> checkArrayList;
    public CheckAdapter(Context context, ArrayList<Check> checkArrayList) {
        this.context = context;
        this.checkArrayList = checkArrayList;
    }

    @NonNull
    @Override
    public CheckAdapter.CheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        checkuserItemBinding = CheckuserItemBinding.inflate(LayoutInflater.from(context));
        return new CheckViewHolder(checkuserItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckAdapter.CheckViewHolder holder, int position) {
        Check check = checkArrayList.get(position);
        holder.itemBinding.tvCheckUserName.setText(check.getNameCheck());
        holder.itemBinding.tvCheckUserDescrip .setText(check.getDescriptionCheck());
        holder.itemBinding.tvCheckUserCat.setText(check.getCategoryCheck());
        holder.itemBinding.tvCheckUserValue.setText(String.valueOf(check.getValueCheck()));
        holder.itemBinding.tvCheckUserItems.setText(String.valueOf(check.getQuantityCheck()));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        /*Glide.with(context).load(check.getImageUrl()).into(holder.itemBinding.ivProductItem);*/

    }

    @Override
    public int getItemCount() {
        return checkArrayList.size();
    }

    public class CheckViewHolder extends RecyclerView.ViewHolder {
        CheckuserItemBinding itemBinding;
        public CheckViewHolder(@NonNull CheckuserItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
