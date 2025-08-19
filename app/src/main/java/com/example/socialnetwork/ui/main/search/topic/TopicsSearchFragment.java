    package com.example.socialnetwork.ui.main.search.topic;

    import androidx.lifecycle.ViewModelProvider;

    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import com.example.socialnetwork.R;

    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.ViewModelProvider;
    import androidx.recyclerview.widget.GridLayoutManager;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.socialnetwork.R;

    public class TopicsSearchFragment extends Fragment {

        private TopicsSearchViewModel mViewModel;
        private TopicAdapter adapter;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_topics_search, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            RecyclerView rvTopics = view.findViewById(R.id.rv_topics);
            rvTopics.setLayoutManager(new LinearLayoutManager(getContext()));

            // Khởi tạo adapter rỗng
            adapter = new TopicAdapter();
            rvTopics.setAdapter(adapter);

            mViewModel = new ViewModelProvider(this).get(TopicsSearchViewModel.class);
            mViewModel.getTopics().observe(getViewLifecycleOwner(), topics -> {
                adapter.setTopics(topics); // cập nhật dữ liệu
            });
        }
    }
