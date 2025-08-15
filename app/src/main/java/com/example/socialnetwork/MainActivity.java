package com.example.socialnetwork;

import android.os.Bundle;
import android.util.Log;
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

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            Log.d("BottomNavVisibility", "Destination changed to: " + destination.getLabel() + " (ID: " + destination.getId() + ")");

            boolean shouldHideBottomNav = (destination.getId() == R.id.loginFragment ||
                    destination.getId() == R.id.signUpFragment ||
                    destination.getId() == R.id.forgotPasswordFragment ||
                    destination.getId() == R.id.OTPFragment ||
                    destination.getId() == R.id.resetPasswordFragment ||
                    destination.getId() == R.id.resetPasswordSuccessFragment ||
                    destination.getId() == R.id.searchFragment);

            Log.d("BottomNavVisibility", "Should hide BottomNav for current destination: " + shouldHideBottomNav);

            if (shouldHideBottomNav) {
                if (binding.bottomNavigationView.getVisibility() == View.VISIBLE) {
                    binding.bottomNavigationView.setVisibility(View.GONE);
                    Log.d("BottomNavVisibility", "BottomNav set to GONE for " + destination.getLabel());
                } else {
                    Log.d("BottomNavVisibility", "BottomNav already GONE for " + destination.getLabel());
                }
            } else {
                if (binding.bottomNavigationView.getVisibility() == View.GONE) {
                    binding.bottomNavigationView.setVisibility(View.VISIBLE);
                    Log.d("BottomNavVisibility", "BottomNav set to VISIBLE for " + destination.getLabel());
                } else {
                    Log.d("BottomNavVisibility", "BottomNav already VISIBLE for " + destination.getLabel());
                }
            }
        });

    }
}