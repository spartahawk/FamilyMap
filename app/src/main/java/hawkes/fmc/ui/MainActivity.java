package hawkes.fmc.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import hawkes.fmc.R;
import hawkes.fmc.model.Model;

public class MainActivity extends AppCompatActivity {
    private LoginFragment loginFragment;
    private MapsFragment mapsFragment;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // iconify library
        Iconify.with(new FontAwesomeModule());

        Model model = Model.getModel();

        // if the user hasn't logged in yet load the login
        if (model.getAuthtoken() == null) {
            loginFragment = LoginFragment.newInstance();
            //LoginFragment loginFragment = fragmentManager.findFragmentById(R.id.loginFragmentLayout);

            fragmentManager.beginTransaction()
                    //.add(R.id.mainActivityLayout, loginFragment)
                    .replace(R.id.mainActivityLayout, loginFragment)
                    .addToBackStack("login")
                    .commit();

            // What does begin transaction do? where do I call .onCreateView? here or in the fragment?

            // once these views are loaded, and someone clicks signup, does the onclicklistener call a serverproxy method?

            // Then based on the return, does the onclicklistener
        }
        else {
            switchToMapFragment();
        }

    }

    public void switchToMapFragment() {
        mapsFragment = new MapsFragment();

//        Bundle bundle = new Bundle();
//        bundle.putString("Activity", "MainActivity");
//        mapsFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.mainActivityLayout, mapsFragment)
                .addToBackStack("mapFragment")
                .commit();

        // when coming from the map activity this will need to be false
        mapsFragment.setmIsMainActivity(true);
    }

}
