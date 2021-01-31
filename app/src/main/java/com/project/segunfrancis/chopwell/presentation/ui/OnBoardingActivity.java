package com.project.segunfrancis.chopwell.presentation.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.project.segunfrancis.chopwell.R;
import com.project.segunfrancis.chopwell.adapter.OnBoardingPagerAdapter;
import com.project.segunfrancis.chopwell.entity.OnBoardingEntity;
import com.project.segunfrancis.chopwell.viewmodel.OnBoardingViewModel;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {

    private final static String TAG = OnBoardingActivity.class.getSimpleName();
    private ImageView mIndicator1;
    private ImageView mIndicator2;
    private ImageView mIndicator3;
    private OnBoardingViewModel mViewModel;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        // Instantiate ViewModel
        mPreferences = getSharedPreferences("onBoarding_screen_preference", Context.MODE_PRIVATE);
        mViewModel = ViewModelProviders.of(OnBoardingActivity.this).get(OnBoardingViewModel.class);
        mViewModel.seenOnBoardingScreen(mPreferences.getBoolean("onBoarding_preference_boolean_key", false));
        mViewModel.mUserStatesMutableLiveData.observe(this, userStates -> {
            switch (userStates) {
                case SEEN_ON_BOARDING_SCREEN:
                    navigateToStartActivity();
                    break;
                case NOT_SEEN_ON_BOARDING_SCREEN:
                    // Show onBoarding Screen
                    setUpViewPager();
                    break;
            }
        });
    }

    private void setUpViewPager() {
        ViewPager2 onBoardingViewPager = findViewById(R.id.onBoarding_viewPager);
        AppCompatButton prevButton = findViewById(R.id.button_prev);
        AppCompatButton nextButton = findViewById(R.id.button_next);
        mIndicator1 = findViewById(R.id.indicator1);
        mIndicator2 = findViewById(R.id.indicator2);
        mIndicator3 = findViewById(R.id.indicator3);

        // Populate OnBoarding screen data
        List<OnBoardingEntity> modelList = new ArrayList<>();
        OnBoardingEntity onBoardingEntity1 = new OnBoardingEntity(R.drawable.undraw_healthy, getResources().getString(R.string.first_onboarding_text));
        modelList.add(onBoardingEntity1);
        OnBoardingEntity onBoardingEntity2 = new OnBoardingEntity(R.drawable.undraw_diet, getResources().getString(R.string.second_onboarding_text));
        modelList.add(onBoardingEntity2);
        OnBoardingEntity onBoardingEntity3 = new OnBoardingEntity(R.drawable.undraw_eating, getResources().getString(R.string.third_onboarding_text));
        modelList.add(onBoardingEntity3);

        OnBoardingPagerAdapter adapter = new OnBoardingPagerAdapter(modelList);
        onBoardingViewPager.setAdapter(adapter);

        prevButton.setOnClickListener(view -> onBoardingViewPager.setCurrentItem(onBoardingViewPager.getCurrentItem() - 1, true));

        nextButton.setOnClickListener(view -> {
            if (onBoardingViewPager.getCurrentItem() < 2)
                onBoardingViewPager.setCurrentItem(onBoardingViewPager.getCurrentItem() + 1, true);
            else {
                mViewModel.seenOnBoardingScreen(true);
                mEditor = mPreferences.edit();
                mEditor.putBoolean("onBoarding_preference_boolean_key", true);
                mEditor.apply();
                navigateToStartActivity();
                finish();
            }
        });

        onBoardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            /**
             * This method will be invoked when a new page becomes selected. Animation is not
             * necessarily complete.
             *
             * @param position Position index of the new selected page.
             */
            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "Displayed item position: " + position);
                switch (position) {
                    case 0:
                        hideButton(prevButton);
                        nextButton.setText(R.string.next_onboarding_text);
                        indicatorSelector(true, false, false);
                        break;
                    case 1:
                        showButton(prevButton);
                        showButton(nextButton);
                        nextButton.setText(R.string.next_onboarding_text);
                        indicatorSelector(false, true, false);
                        break;
                    case 2:
                        showButton(prevButton);
                        nextButton.setText(R.string.start_onboarding_text);
                        indicatorSelector(false, false, true);
                        break;
                }
            }
        });
    }

    private void showButton(AppCompatButton button) {
        button.setVisibility(View.VISIBLE);
    }

    private void hideButton(AppCompatButton button) {
        button.setVisibility(View.GONE);
    }

    private void indicatorSelector(boolean first, boolean second, boolean third) {
        mIndicator1.setSelected(first);
        mIndicator2.setSelected(second);
        mIndicator3.setSelected(third);
    }

    private void navigateToStartActivity() {
        startActivity(new Intent(OnBoardingActivity.this, StartActivity.class));
        finish();
    }
}
