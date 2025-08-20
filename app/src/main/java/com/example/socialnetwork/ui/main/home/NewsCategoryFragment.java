package com.example.socialnetwork.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.socialnetwork.databinding.FragmentNewsCategoryBinding;
import com.example.socialnetwork.ui.main.home.adapter.ArticlesPagerAdapter;

public class NewsCategoryFragment extends Fragment {

    private FragmentNewsCategoryBinding binding;
    private NewsCategoryViewModel viewModel;
    private ArticlesPagerAdapter adapter;
    private static final String ARG_TOPIC_ID = "topic_id";
    private static final long ALL_TOPICS_ID = -1L; // Giá trị đặc biệt cho tab "All"

    // Hàm tạo fragment cho tab "All"
    public static NewsCategoryFragment newInstanceForAll() {
        NewsCategoryFragment fragment = new NewsCategoryFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TOPIC_ID, ALL_TOPICS_ID);
        fragment.setArguments(args);
        return fragment;
    }

    // Hàm tạo fragment cho một topic cụ thể
    public static NewsCategoryFragment newInstanceForTopic(long topicId) {
        NewsCategoryFragment fragment = new NewsCategoryFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TOPIC_ID, topicId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewsCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo ViewModel
        viewModel = new ViewModelProvider(this).get(NewsCategoryViewModel.class);

        setupRecyclerView();
        observeState();

        // Lấy topicId từ arguments và gọi API
        if (getArguments() != null) {
            long topicId = getArguments().getLong(ARG_TOPIC_ID);
            viewModel.loadArticles(topicId);
        }
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ArticlesPagerAdapter(getParentFragment());
        binding.recyclerView.setAdapter(adapter);
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            // binding.progressBar.setVisibility(state.isLoading ? View.VISIBLE : View.GONE);
            if (!state.isLoading && state.articles != null) {
                adapter.setArticles(state.articles);
            }
            // Có thể hiển thị lỗi nếu state.error != null
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}