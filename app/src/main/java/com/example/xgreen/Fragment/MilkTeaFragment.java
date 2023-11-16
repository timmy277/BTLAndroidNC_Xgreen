package com.example.xgreen.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.example.xgreen.Adapter.ProductAdapter;
import com.example.xgreen.Database.DBProduct;
import com.example.xgreen.R;

import java.util.ArrayList;
import java.util.List;

public class MilkTeaFragment extends Fragment {

    private RecyclerView recyclerViewMilkTea;
    private ProductAdapter adapterMilkTea;
    private List<DBProduct> productMilkTea;

    public MilkTeaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String milktea = "-NX9r4LR1f6twnVU28_R"; // Mã danh mục Trà sữa
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("sanpham");
        Query query_milktea = databaseRef.orderByChild("madm").equalTo(milktea);
        /* Danh  mục Trà sữa */
        query_milktea.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productMilkTea.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String id = data.getKey();
                    String name = data.child("tensp").getValue(String.class);
                    String desc = data.child("motasp").getValue(String.class);
                    String price = String.valueOf(data.child("giasp").getValue(Long.class)); // đối với dạng số "50000"
                    /*String price = data.child("giasp").getValue(String.class); // đối với dạng số "50000" // dạng string*/
                    String imageUrl = data.child("hinhsp").getValue(String.class);
                    DBProduct product = new DBProduct(id,name, desc, price, imageUrl);
                    productMilkTea.add(product);
                }
                adapterMilkTea.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_milk_tea, container, false);
        recyclerViewMilkTea = view.findViewById(R.id.recyclerViewMilkTea);
        productMilkTea = new ArrayList<>();
        adapterMilkTea = new ProductAdapter(productMilkTea);
        recyclerViewMilkTea.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMilkTea.setAdapter(adapterMilkTea);
        return view;
    }
}