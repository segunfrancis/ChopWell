package com.example.computer.chopwell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.computer.chopwell.adapter.OnBoardingPagerAdapter;
import com.example.computer.chopwell.model.OnBoardingModel;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {

    private final static String TAG = OnBoardingActivity.class.getSimpleName();
    private ImageView mIndicator1;
    private ImageView mIndicator2;
    private ImageView mIndicator3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        ViewPager2 onBoardingViewPager = findViewById(R.id.onBoarding_viewPager);
        AppCompatButton prevButton = findViewById(R.id.button_prev);
        AppCompatButton nextButton = findViewById(R.id.button_next);
        mIndicator1 = findViewById(R.id.indicator1);
        mIndicator2 = findViewById(R.id.indicator2);
        mIndicator3 = findViewById(R.id.indicator3);

        // Populate OnBoarding screen data
        List<OnBoardingModel> modelList = new ArrayList<>();
        OnBoardingModel onBoardingModel1 = new OnBoardingModel(R.drawable.undraw_healthy, getResources().getString(R.string.first_onboarding_text));
        modelList.add(onBoardingModel1);
        OnBoardingModel onBoardingModel2 = new OnBoardingModel(R.drawable.undraw_diet, getResources().getString(R.string.second_onboarding_text));
        modelList.add(onBoardingModel2);
        OnBoardingModel onBoardingModel3 = new OnBoardingModel(R.drawable.undraw_eating, getResources().getString(R.string.third_onboarding_text));
        modelList.add(onBoardingModel3);

        OnBoardingPagerAdapter adapter = new OnBoardingPagerAdapter(modelList);
        onBoardingViewPager.setAdapter(adapter);

        prevButton.setOnClickListener(view -> onBoardingViewPager.setCurrentItem(onBoardingViewPager.getCurrentItem() - 1, true));

        nextButton.setOnClickListener(view -> {
            if (onBoardingViewPager.getCurrentItem() < 2)
                onBoardingViewPager.setCurrentItem(onBoardingViewPager.getCurrentItem() + 1, true);
            else {
                startActivity(new Intent(OnBoardingActivity.this, StartActivity.class));
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
}
