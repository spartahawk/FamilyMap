package hawkes.fmc.ui;

import android.support.v4.app.Fragment;

/**
 * Created by yo on 6/15/17.
 */

public class SettingsActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new SettingsFragment();
    }
}
