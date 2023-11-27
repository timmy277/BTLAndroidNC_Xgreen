package com.example.xgreen.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.xgreen.Database.DBOrder;
import com.example.xgreen.GUI.HistoryDetailActivity;
import com.example.xgreen.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private List<DBOrder> orderList;
    private List<String> orderKeys;
    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference orderDB;

    public HistoryAdapter(List<DBOrder> orderList) {
        this.orderList = orderList;
        this.orderKeys = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        orderDB = FirebaseDatabase.getInstance().getReference().child("dathang");
        orderDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String orderKey = snapshot.getKey();
                orderKeys.add(orderKey);
                notifyDataSetChanged(); // Thay đổi ở đây
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview_history, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (orderList == null || orderList.size() <= position) {
            return; // Bỏ qua nếu danh sách rỗng hoặc không có đúng vị trí
        }
        DBOrder order = orderList.get(position);
        holder.txtDateOrder.setText(order.getThoigiandh());
        double updatedPrice = order.getTongtiendh();
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        String formattedPrice = decimalFormat.format(updatedPrice);
        /*holder.txtTotalPriceOrder.setText(order.getMadh());*/
        holder.txtTotalPriceOrder.setText(formattedPrice);
        holder.txtStatus.setText(order.getTinhtrang());

        holder.btnReOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HistoryDetailActivity.class);
                intent.putExtra("orderKey", order.getMadh());
                v.getContext().startActivity(intent);
            }
        });
        holder.cardViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HistoryDetailActivity.class);
                intent.putExtra("orderKey", order.getMadh());
                v.getContext().startActivity(intent);
            }
        });

        switch (order.getTinhtrang()){
            default:
                holder.cardViewHistory.setCardBackgroundColor(Color.parseColor("#FF9191"));
                break;
            case "Đang giao":
                holder.cardViewHistory.setCardBackgroundColor(Color.parseColor("#FFF48D"));
                break;
            case "Đã hủy":
                holder.cardViewHistory.setCardBackgroundColor(Color.parseColor("#FF9191"));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtDateOrder, txtTotalPriceOrder, txtStatus;
        Button btnReOrder;
        RecyclerView recyclerView;
        public CardView cardViewHistory;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDateOrder = itemView.findViewById(R.id.txtDateOrder);
            txtTotalPriceOrder = itemView.findViewById(R.id.txtTotalPriceOrder);
            txtStatus = itemView.findViewById(R.id.txtStatusRecyclerView);
            btnReOrder = itemView.findViewById(R.id.btnReOrder);
            recyclerView = itemView.findViewById(R.id.reyclerViewDetailHistory);
            cardViewHistory = itemView.findViewById(R.id.cardViewPending);
        }
    }
}

