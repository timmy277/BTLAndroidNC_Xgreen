package com.example.xgreen.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class OtherFragment extends Fragment {
    private RecyclerView recyclerViewOther;
    private ProductAdapter adapterOther;
    private List<DBProduct> productOther;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String other = "-NXPMMB0Gglb68aK9Svr"; // Mã danh mục Khác
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("sanpham");
        Query query_other = databaseRef.orderByChild("madm").equalTo(other);
        query_other.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productOther.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String id = data.getKey();
                    String name = data.child("tensp").getValue(String.class);
                    String desc = data.child("motasp").getValue(String.class);
                    String price = String.valueOf(data.child("giasp").getValue(Long.class)); // đối với dạng số "50000"
                    /*String price = data.child("giasp").getValue(String.class); // đối với dạng số "50000" // dạng string*/
                    String imageUrl = data.child("hinhsp").getValue(String.class);
                    DBProduct product = new DBProduct(id,name, desc, price, imageUrl);
                    productOther.add(product);
                }
                adapterOther.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        recyclerViewOther = view.findViewById(R.id.recyclerViewOther);
        productOther = new ArrayList<>();
        adapterOther = new ProductAdapter(productOther);
        recyclerViewOther.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewOther.setAdapter(adapterOther);
        return view;
    }
}