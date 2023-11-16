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

public class PackageFragment extends Fragment {
    private RecyclerView recyclerViewPackage;
    private ProductAdapter adapterPackage;
    private List<DBProduct> productPackage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        String sppackage = "-NXOrczHNuTFoEhXKwfe"; // Mã danh mục hàng đóng gói
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("sanpham");
        Query query_sppackage = databaseRef.orderByChild("madm").equalTo(sppackage);
        query_sppackage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productPackage.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String id = data.getKey();
                    String name = data.child("tensp").getValue(String.class);
                    String desc = data.child("motasp").getValue(String.class);
                    String price = String.valueOf(data.child("giasp").getValue(Long.class)); // đối với dạng số "50000"
                    /*String price = data.child("giasp").getValue(String.class); // đối với dạng số "50000" // dạng string*/
                    String imageUrl = data.child("hinhsp").getValue(String.class);
                    DBProduct product = new DBProduct(id,name, desc, price, imageUrl);
                    productPackage.add(product);
                }
                adapterPackage.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_package, container, false);
        recyclerViewPackage = view.findViewById(R.id.recyclerViewPackage);
        productPackage = new ArrayList<>();
        adapterPackage = new ProductAdapter(productPackage);
        recyclerViewPackage.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewPackage.setAdapter(adapterPackage);
        return view;
    }
}