package com.example.socialnetwork.ui.main.search;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.socialnetwork.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SearchFragment extends Fragment {

    private EditText etSearch;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    public SearchFragment() {
        // Constructor rỗng bắt buộc
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etSearch = view.findViewById(R.id.et_search);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        // Thiết lập ViewPager Adapter. Lưu ý: sử dụng getChildFragmentManager()
        SearchPagerAdapter adapter = new SearchPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Kết nối TabLayout và ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("News");
                            break;
                        case 1:
                            tab.setText("Topics");
                            break;
                        case 2:
                            tab.setText("Author");
                            break;
                    }
                }).attach();

        // Xử lý sự kiện tìm kiếm
        // Tương tác với EditText và gửi từ khóa đến các Fragment con
    }
}