package com.example.xgreen.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.xgreen.Adapter.HistoryAdapter;
import com.example.xgreen.Database.DBOrder;
import com.example.xgreen.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    ;
    private HistoryAdapter historyAdapter;
    private TextView txtNoAccount, txtNoData;
    private Button btnReOrder;
    private List<DBOrder> orderList;
    private List<String> orderKeys;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dathangRef = database.getReference("dathang");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewHistory);
        txtNoAccount = view.findViewById(R.id.txtNoAccountHistory);
        txtNoData = view.findViewById(R.id.txtNoDataHistory);
        orderList = new ArrayList<>();
        /*cartList = new ArrayList<>();*/

        historyAdapter = new HistoryAdapter(orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(historyAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            String tinhtrang = "Đã hủy";
            dathangRef.orderByChild("makh").equalTo(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    orderList.clear();
                    for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                        DBOrder order = orderSnapshot.getValue(DBOrder.class);

                        if (order.getTinhtrang().equals(tinhtrang)) {
                            orderList.add(order);
                        }
                    }
                    historyAdapter.notifyDataSetChanged();
                    if (orderList.isEmpty()) {
                        // Không có thông tin đơn hàng
                        txtNoData.setVisibility(View.VISIBLE);
                    } else {
                        // Có thông tin đơn hàng
                        txtNoData.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }else {
            // Không có tài khoản đăng nhập
            txtNoAccount.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
        }
        return view;
    }
}

