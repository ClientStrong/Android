package v3.clientstrong.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.util.HashMap;

import v3.clientstrong.R;
import v3.clientstrong.SessionManager;
import v3.clientstrong.adapters.DashboardViewPagerAdapter;
import v3.clientstrong.mainFragments.ExerciseListFragment;
import v3.clientstrong.mainFragments.MembersListFragment;
import v3.clientstrong.mainFragments.WorkoutsListFragment;

public class Dashboard extends AppCompatActivity implements MembersListFragment.OnFragmentInteractionListener, WorkoutsListFragment.OnFragmentInteractionListener, ExerciseListFragment.OnFragmentInteractionListener {

    private FloatingActionButton mFloatingActionButton;
    SessionManager mSessionManager;

    public void onFragmentInteraction(Uri uri) { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String activityTitle = getTitle().toString();
        setTitle(activityTitle);

        mSessionManager = new SessionManager(getApplicationContext());
        mSessionManager = new SessionManager(this);
        Log.i("isUserLoggedIn ", String.valueOf(mSessionManager.isUserLoggedIn()));
        if(mSessionManager.checkLogin())
            finish();

        // get user data from mSessionManager
        HashMap<String, String> user = mSessionManager.getUserDetails();
        String name = user.get(SessionManager.KEY_FIRST_NAME);
        String email = user.get(SessionManager.KEY_EMAIL);

        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.htab_toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(mPageChangeListener);
        mFloatingActionButton.setOnClickListener(mFloatingActionButtonClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    private void setupViewPager(ViewPager viewPager) {
        DashboardViewPagerAdapter adapter = new DashboardViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MembersListFragment(), "Members");
        adapter.addFragment(new WorkoutsListFragment(), "Workouts");
        adapter.addFragment(new ExerciseListFragment(), "Exercises");
        viewPager.setAdapter(adapter);
    }

    /*
    *  LISTENERS
    * */

    private View.OnClickListener mFloatingActionButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Snackbar.make(v, "Add new member (coming soon)", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    };

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mFloatingActionButton.show();
                    break;
                case 1:
                    mFloatingActionButton.hide();
                    break;
                default:
                    mFloatingActionButton.hide();
                    break;
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
}