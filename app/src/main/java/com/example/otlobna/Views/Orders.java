package com.example.otlobna.Views;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.Toast;

import com.example.otlobna.Model.Object.Order;
import com.example.otlobna.Model.Object.Session;
import com.example.otlobna.Model.PreferanceData.Preferance;
import com.example.otlobna.Model.Response.GeneralResponse;
import com.example.otlobna.R;
import com.example.otlobna.Service.GPS_Service;
import com.example.otlobna.network.ApiClient;
import com.example.otlobna.network.ApiInterface;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Orders extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static final String SHARED_PREF_NAME = "tayarSession";
    private static Context mCtx;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private BroadcastReceiver broadcastReceiver;
    private DataLocation Location = new DataLocation();
    private ImageButton profile;
    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.d("Lat",intent.getExtras().get("Lat")+"" );
                    Log.d("Log",intent.getExtras().get("Log")+"" );
                    float lat = Float.parseFloat(intent.getExtras().get("Lat")+"");
                    float log = Float.parseFloat(intent.getExtras().get("Log")+"");
                    UpdateLocation(lat, log);
                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));

    }

    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        //In this we calling the services ....

        Intent i = new Intent(getApplicationContext(), GPS_Service.class);
        RequestPermissions();
        startService(i);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Orders.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to lthe action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_orders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks5 on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Preferance.logout(Orders.this);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *A placeholder fragment containing a simple view.
     **/

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         **/
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_orders, container, false);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment = null ;
            String Type = Preferance.getTypeUser(Orders.this);
            if (Type.equals( "طيار"))
            {
                switch (position )
                {
                    case 0 :
                        fragment = new ProcessOrdersDelivery();
                        break;
                    case 1 :
                        fragment = new NewOrderDelivery();
                        break;
                    case 2 :
                        fragment = new OldOrdersDelivery();
                        break;
                }
            }
            else if (Type.equals( "مكان"))
            {
                switch (position )
                {
                    case 0:
                        fragment = new ProcessOrdersPlace();
                        break;
                    case 1 :
                        fragment = new NewOrdersPlace();
                        break;
                    case 2 :
                        fragment = new OldOrdersPlace();
                        break;
                }
            }

            return fragment ;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    private void RequestPermissions() {
        Context context = getApplicationContext();
        ActivityCompat.requestPermissions(Orders.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    private void UpdateLocation(float lat , float log )
    {
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        int Id =Integer.parseInt(Preferance.getID(Orders.this));
        Call<ResponseBody> call = service.UpdateLocation(Id , lat , log);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String Result = response.body().string();
                    Gson gson = new Gson();
                    GeneralResponse generalResponse = gson.fromJson(Result , GeneralResponse.class);
                    if (generalResponse.getError() == 0 || generalResponse.getError() ==2)
                    {
                    }
                } catch (IOException e) {
                    Toast.makeText(Orders.this , e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Orders.this , t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });

    }
}
