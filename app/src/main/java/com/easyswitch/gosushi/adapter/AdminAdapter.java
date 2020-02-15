package com.easyswitch.gosushi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.easyswitch.gosushi.R;
import com.easyswitch.gosushi.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminHolder> {

    private Context context;
    private List<Product> productList;

    public AdminAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public AdminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdminHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdminHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvMass.setText(" " + product.getMass());
        holder.tvExpDate.setText(" " + product.getEndDate());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class AdminHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvProductName)
        TextView tvProductName;
        @BindView(R.id.tvMass)
        TextView tvMass;
        @BindView(R.id.tvExpDate)
        TextView tvExpDate;

        public AdminHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
