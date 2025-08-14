package com.example.socialnetwork;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.socialnetwork.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavigationView, (v, insets) -> {
            int bottomInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            params.bottomMargin = bottomInset;
            v.setLayoutParams(params);
            return insets;
        });

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        NavController navController = navHostFragment.getNavController();

        // 2. Kết nối BottomNavigationView với NavController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);


        // 3. Thêm Listener để lắng nghe sự kiện chuyển màn hình
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // 4. Kiểm tra xem màn hình hiện tại có phải là màn hình xác thực không
            if (destination.getId() == R.id.loginFragment ||
                    destination.getId() == R.id.signUpFragment ||
                    destination.getId() == R.id.forgotPasswordFragment ||
                    destination.getId() == R.id.OTPFragment ||
                    destination.getId() == R.id.resetPasswordFragment ||
                    destination.getId() == R.id.resetPasswordSuccessFragment)
            {
                // Nếu là màn hình xác thực, ẩn BottomNavigationView đi
                binding.bottomNavigationView.setVisibility(View.GONE);
            } else {
                // Nếu là các màn hình khác (Home, Notification,...), hiện nó lên
                binding.bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.homeFragment) {
                navController.navigate(R.id.homeFragment);
            } else if (id == R.id.exploreFragment) {
                navController.navigate(R.id.exploreFragment);
                return true;
            } else if (id == R.id.bookmarkFragment) {
                navController.navigate(R.id.bookmarkFragment);
                return true;
            } else if (id == R.id.profileFragment) {
                navController.navigate(R.id.profileFragment);
                return true;
            }
            return false;
        });







    }
}