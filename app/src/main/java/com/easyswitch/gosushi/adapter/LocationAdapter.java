package com.easyswitch.gosushi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.easyswitch.gosushi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationHolder> {

    private Context context;
    private List<String> locationList;
    private OnLocationClick onLocationClick;

    public LocationAdapter(Context context, List<String> locationList) {
        this.context = context;
        this.locationList = locationList;
    }

    public interface OnLocationClick {
        void onClick(View view, int position, String location);
    }

    public void setOnLocationClick(OnLocationClick onLocationClick) {
        this.onLocationClick = onLocationClick;
    }

    @NonNull
    @Override
    public LocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationHolder holder, int position) {
        String location = locationList.get(position);

        holder.tvLocation.setText(location);
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    class LocationHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvLocation)
        TextView tvLocation;

        public LocationHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tvLocation)
        public void maxStay() {
            if (onLocationClick != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                onLocationClick.onClick(itemView, getAdapterPosition(), locationList.get(getAdapterPosition()));
            }
        }
    }
}
