package com.example.socialnetwork.ui.main.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.socialnetwork.databinding.FragmentNotificationBinding;

public class NotificationFragment extends Fragment {

    private FragmentNotificationBinding binding;
    private NotificationViewModel viewModel;
    private NotificationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observeViewModel();
    }

    private void setupToolbar() {
        // Xử lý sự kiện khi nhấn nút back trên toolbar
        binding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );
        // Bạn có thể thêm xử lý cho menu 3 chấm ở đây
    }

    private void setupRecyclerView() {
        adapter = new NotificationAdapter();
        binding.notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.notificationsRecyclerView.setAdapter(adapter);
    }

    private void observeViewModel() {
        // Lắng nghe dữ liệu từ ViewModel và cập nhật cho Adapter
        viewModel.getNotifications().observe(getViewLifecycleOwner(), notifications -> {
            adapter.submitList(notifications);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}