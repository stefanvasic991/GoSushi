package com.easyswitch.gosushi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.easyswitch.gosushi.R;
import com.easyswitch.gosushi.model.Fish;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FishAdapter extends RecyclerView.Adapter<FishAdapter.FishHolder> {

    Context context;
    private List<Fish> fishList;
    OnFishClick onFishClick;

    public FishAdapter(Context context, List<Fish> fishList) {
        this.context = context;
        this.fishList = fishList;
    }

    public interface OnFishClick {
        void onClick(View view, int position, Fish fish);
    }

    public void setOnFishClick(OnFishClick onFishClick) {
        this.onFishClick = onFishClick;
    }

    @NonNull
    @Override
    public FishHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FishHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_fish, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FishHolder holder, int position) {
        Fish fish = fishList.get(position);
    }

    @Override
    public int getItemCount() {
        return fishList.size();
    }

    class FishHolder extends RecyclerView.ViewHolder {

        public FishHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tvFish)
        public void maxStay() {
            if (onFishClick != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                onFishClick.onClick(itemView, getAdapterPosition(), fishList.get(getAdapterPosition()));
            }
        }
    }
}
