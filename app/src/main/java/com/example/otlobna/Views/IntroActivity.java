package com.example.otlobna.Views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.otlobna.Adapter.IntroPageAdapter;
import com.example.otlobna.control.PreferenceManagerIntroActivity;
import com.example.otlobna.R;

public class IntroActivity extends AppCompatActivity {
    private ViewPager ViewPagerIntroActivity ;
    private int [] Layouts = {R.layout.intro_first, R.layout.intro_second , R.layout.intro_third };
    private IntroPageAdapter introPageAdapter;
    private LinearLayout DotsLayout ;
    private ImageView[] Dots;
    private TextView Next ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (new PreferenceManagerIntroActivity(this).CheckPreference())
        {
            LoadHomeActivity();
        }
        if (Build.VERSION.SDK_INT >=19)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.intro_screen);
        ViewPagerIntroActivity = findViewById(R.id.viewPagerIntroActivity);
        introPageAdapter = new IntroPageAdapter(Layouts , IntroActivity.this) ;
        ViewPagerIntroActivity.setAdapter(introPageAdapter);
        DotsLayout = findViewById(R.id.LayoutDotsIntro);
        Next = findViewById(R.id.Next) ;
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadNextSlide() ;
            }
        });
        Next.setVisibility(View.INVISIBLE);
        CreateDots(0);

        ViewPagerIntroActivity.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                CreateDots(position);
                if (position == Layouts.length - 1)
                {
                    Next.setVisibility(View.VISIBLE);
                }
                else
                    {
                        Next.setVisibility(View.INVISIBLE);
                    }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

     private void CreateDots (int CurrentPosition)
    {
        if (DotsLayout != null)
        {
            DotsLayout.removeAllViews();
        }
        Dots = new ImageView[Layouts.length] ;
        for (int i = 0 ; i <Layouts.length ; i++)
        {
            Dots[i] = new ImageView(this) ;
            if (i == CurrentPosition)
            {
                Dots[i].setImageDrawable(ContextCompat.getDrawable(this , R.drawable.active_dots_intro_activity));
            }
            else
            {
                Dots[i].setImageDrawable(ContextCompat.getDrawable(this , R.drawable.notactive_dots_intro_activity));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT) ;
            params.setMargins(4,0,4,0);
            DotsLayout.addView(Dots[i] , params);
        }
    }
    private void LoadHomeActivity()
    {
            Intent intent = new Intent(IntroActivity.this , Login.class);
            startActivity(intent);
            finish();
    }
    private void LoadNextSlide()
    {
        int NextSlide = ViewPagerIntroActivity.getCurrentItem()+1 ;
        if (NextSlide < Layouts.length)
        {
            ViewPagerIntroActivity.setCurrentItem(NextSlide);
        }
        else
        {
            LoadHomeActivity();
            new PreferenceManagerIntroActivity(this).WritePreference();
        }

    }

}
