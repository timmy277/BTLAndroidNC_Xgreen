package com.example.xgreen.Adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.xgreen.Database.DBCart;
import com.example.xgreen.R;

import java.text.DecimalFormat;
import java.util.List;

public class HistoryDetailAdapter extends RecyclerView.Adapter<HistoryDetailAdapter.ViewHolder> {
    private List<DBCart> cartList;

    public HistoryDetailAdapter(List<DBCart> cartList) {
        this.cartList = cartList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProductCartCount, txtProductCartName, txtProductCartPrice, txtPriceBefore;

        public ViewHolder(View itemView) {
            super(itemView);
            txtProductCartCount = itemView.findViewById(R.id.txtProductCartCount);
            txtProductCartName = itemView.findViewById(R.id.txtProductCartName);
            txtProductCartPrice = itemView.findViewById(R.id.txtProductCartPrice);
//            txtPriceBefore = itemView.findViewById(R.id.txtPriceBefore);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview_cart, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DBCart cartItem = cartList.get(position);
        holder.txtProductCartCount.setText(String.valueOf(cartItem.getSoluong()) + "x");
        holder.txtProductCartName.setText(cartItem.getTensp());

        double price = cartItem.getGiasp() * cartItem.getSoluong();
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        String formattedPrice = decimalFormat.format(price);
        holder.txtProductCartPrice.setText(formattedPrice);


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}

