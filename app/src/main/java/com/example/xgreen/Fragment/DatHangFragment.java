package com.example.xgreen.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.xgreen.GUI.CartActivity;
import com.example.xgreen.R;

public class DatHangFragment extends Fragment {
    private TabLayout tab_category;
    private ViewPager2 view_category;
    private FloatingActionButton btnfCart;
    private FirebaseAuth auth;
    private DatabaseReference reference;

    public DatHangFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dat_hang, container, false);
        tab_category = view.findViewById(R.id.tab_category);
        view_category = view.findViewById(R.id.view_category);
        btnfCart = view.findViewById(R.id.btnfCart);


        btnfCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), CartActivity.class));
            }
        });

        ViewCategoryAdapter viewCategoryAdapter = new ViewCategoryAdapter(getActivity());
        view_category.setAdapter(viewCategoryAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tab_category, view_category, (tab, position) -> {
            String title = viewCategoryAdapter.getPageTitle(position).toString();
            tab.setText(title);
            tab.setTag(position); // Lưu trữ vị trí của tab
        });
        tabLayoutMediator.attach();

        /* Check co nguoi dung hien thi btnCart */
        /* Authentication */
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            /* Hiển thị tên người dùng */
            reference.child("taikhoan").child(userId).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userLogin = snapshot.getValue(String.class);
                    if (userLogin != null){
                        btnfCart.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            btnfCart.setVisibility(View.GONE);
        }

        return view;
    }

    public static class ViewCategoryAdapter extends FragmentStateAdapter {

        public ViewCategoryAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new CoffeeFragment();
                case 1:
                    return new MilkTeaFragment();
                case 2:
                    return new TeaFragment();
                case 3:
                    return new SmoothiesFragment();
                case 4:
                    return new FoodSnackFragment();
                case 5:
                    return new PackageFragment();
                case 6:
                    return new OtherFragment();
                default:
                    return new CoffeeFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 7;
        }

        @Nullable
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title = "Giấy báo";
                    break;
                case 1:
                    title = "Sắt đặc";
                    break;
                case 2:
                    title = "Giấy thùng";
                    break;
                case 3:
                    title = "Sắt vụn";
                    break;
                case 4:
                    title = "Giấy hồ sơ";
                    break;
                case 5:
                    title = "Sắt tôn";
                    break;
                case 6:
                    title = "Lon nhôm";
                    break;
            }
            return title;
        }
    }
}