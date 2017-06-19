package hawkes.fmc.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import hawkes.fmc.R;
import hawkes.fmc.model.Model;

public class MapActivity extends AppCompatActivity {

    private MapsFragment mapsFragment;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // iconify library
        Iconify.with(new FontAwesomeModule());

        Model model = Model.getModel();
        switchToMapFragment();

    }

    public void switchToMapFragment() {
        mapsFragment = new MapsFragment();

//        Bundle bundle = new Bundle();
//        bundle.putString("Activity", "MainActivity");
//        mapsFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.mapActivityLayout, mapsFragment)
                .addToBackStack("mapFragment")
                .commit();

        // when coming from the map activity this will need to be false
        mapsFragment.setmIsMainActivity(false);
    }
}
