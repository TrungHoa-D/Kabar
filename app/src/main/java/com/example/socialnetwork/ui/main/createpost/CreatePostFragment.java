// filepath: com/example/socialnetwork/ui/main/createpost/CreatePostFragment.java
package com.example.socialnetwork.ui.main.createpost;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.data.model.dto.TopicDto;
import com.example.socialnetwork.databinding.FragmentCreatePostBinding;
import com.example.socialnetwork.utils.RealPathUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreatePostFragment extends Fragment {

    private FragmentCreatePostBinding binding;
    private CreatePostViewModel viewModel;
    private ActivityResultLauncher<String> imagePickerLauncher;
    private Uri selectedImageUri;
    private long selectedTopicId = -1;
    private List<TopicDto> topicList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        selectedImageUri = uri;
                        Glide.with(this).load(uri).into(binding.ivCoverImage);
                    }
                });
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CreatePostViewModel.class);

        setupToolbar();
        setupImagePicker();
        setupTopicDropdown();
        observeViewModel();
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());
        binding.btnPost.setOnClickListener(v -> createAndUploadPost());
    }

    private void setupImagePicker() {
        binding.ivCoverImage.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
    }

    private void setupTopicDropdown() {
        binding.actvTopics.setOnItemClickListener((parent, view, position, id) -> {
            selectedTopicId = topicList.get(position).getId();
        });
    }

    private void observeViewModel() {
        viewModel.topics.observe(getViewLifecycleOwner(), topics -> {
            this.topicList = topics;
            List<String> topicNames = new ArrayList<>();
            for (TopicDto topic : topics) {
                topicNames.add(topic.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, topicNames);
            binding.actvTopics.setAdapter(adapter);
        });

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            binding.progressBar.setVisibility(state.isLoading ? View.VISIBLE : View.GONE);
            binding.btnPost.setEnabled(!state.isLoading);

            if (state.error != null) {
                Toast.makeText(getContext(), state.error, Toast.LENGTH_SHORT).show();
            }
            if (state.isSuccess) {
                Toast.makeText(getContext(), "Post created successfully!", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(this)
                        .getPreviousBackStackEntry()
                        .getSavedStateHandle()
                        .set("new_post_created", true);
                NavHostFragment.findNavController(this).navigateUp();
            }
        });
    }

    private void createAndUploadPost() {
        String title = binding.etTitle.getText().toString().trim();
        String content = binding.etContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty() || selectedTopicId == -1 || selectedImageUri == null) {
            Toast.makeText(getContext(), "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        String realPath = RealPathUtil.getRealPath(getContext(), selectedImageUri);
        if (realPath == null) {
            Toast.makeText(getContext(), "Failed to get file path", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(realPath);
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContext().getContentResolver().getType(selectedImageUri)), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("coverImage", file.getName(), requestFile);

        viewModel.createPost(title, content, selectedTopicId, body);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}