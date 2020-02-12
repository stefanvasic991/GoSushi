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
import butterknife.OnClick;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.FishHolder> {

    Context context;
    private List<Product> productList;
    OnFishClick onFishClick;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public interface OnFishClick {
        void onClick(View view, int position, Product product);
    }

    public void setOnFishClick(OnFishClick onFishClick) {
        this.onFishClick = onFishClick;
    }

    @NonNull
    @Override
    public FishHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FishHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FishHolder holder, int position) {
        Product product = productList.get(position);

        holder.tvProduct.setText(product.getName());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class FishHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvProduct)
        TextView tvProduct;

        public FishHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tvProduct)
        public void fish() {
            if (onFishClick != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                onFishClick.onClick(itemView, getAdapterPosition(), productList.get(getAdapterPosition()));
            }
        }
    }
}
