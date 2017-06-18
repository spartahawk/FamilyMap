package hawkes.fmc.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import hawkes.fmc.R;
import hawkes.fmc.model.Model;
import hawkes.fmc.net.ServerProxy;
import hawkes.model.request.LoginRequest;
import hawkes.model.result.LoginResult;

import static java.security.AccessController.getContext;

public class SettingsActivity extends AppCompatActivity {
    private LinearLayout mReSyncButton;
    private LinearLayout mLogoutButton;
    private Spinner mLifeStoryLinesSpinner;
    private Spinner mFamilyTreeLinesSpinner;
    private Spinner mSpouseLinesSpinner;
    private Spinner mMapTypeSpinner;
    private Switch mLifeStoryLinesSwitch;
    private Switch mFamilyTreeLinesSwitch;
    private Switch mSpouseLinesSwitch;

    private String mReSyncMesssage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Model model = Model.getModel();

        mReSyncButton = (LinearLayout) findViewById(R.id.reSyncDataLayout);
        mLogoutButton = (LinearLayout) findViewById(R.id.logoutLayout);

        mLifeStoryLinesSpinner = (Spinner) findViewById(R.id.lifeStoryLinesSpinner);
        mFamilyTreeLinesSpinner = (Spinner) findViewById(R.id.familyTreeLinesSpinner);
        mSpouseLinesSpinner = (Spinner) findViewById(R.id.spouseLinesSpinner);
        mMapTypeSpinner = (Spinner) findViewById(R.id.mapTypeSpinner);

        mLifeStoryLinesSwitch = (Switch) findViewById(R.id.lifeStoryLinesSwitch);
        mFamilyTreeLinesSwitch = (Switch) findViewById(R.id.familyTreeLinesSwitch);
        mSpouseLinesSwitch = (Switch) findViewById(R.id.spouseLinesSwitch);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> lineColorsAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinnerColorsArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        lineColorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the line color spinners and set their default positions
        mLifeStoryLinesSpinner.setAdapter(lineColorsAdapter);
        mLifeStoryLinesSpinner.setSelection(0);
        mFamilyTreeLinesSpinner.setAdapter(lineColorsAdapter);
        mFamilyTreeLinesSpinner.setSelection(1);
        mSpouseLinesSpinner.setAdapter(lineColorsAdapter);
        mSpouseLinesSpinner.setSelection(2);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> mapTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.mapTypeArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        mapTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the map type spinner
        mMapTypeSpinner.setAdapter(mapTypeAdapter);

        mLifeStoryLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lifeStoryLinesColor = (String) parent.getItemAtPosition(position);
                model.getSettings().setLifeStoryLinesColor(lifeStoryLinesColor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {  }
        });

        mFamilyTreeLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String familyTreeLinesColor = (String) parent.getItemAtPosition(position);
                model.getSettings().setFamilyTreeLinesColor(familyTreeLinesColor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {  }
        });

        mSpouseLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spouseLinesColor = (String) parent.getItemAtPosition(position);
                model.getSettings().setSpouseLinesColor(spouseLinesColor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {  }
        });

        mMapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mapType = (String) parent.getItemAtPosition(position);
                model.getSettings().setMapType(mapType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {  }
        });

        mReSyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSyncButtonClicked();
                //Toast.makeText(getBaseContext(), mReSyncMesssage, Toast.LENGTH_SHORT).show();
            }
        });

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutButtonClicked();
                //Toast.makeText(getBaseContext(), mReSyncMesssage, Toast.LENGTH_SHORT).show();
            }
        });

        mLifeStoryLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                model.getSettings().setShowLifeStoryLines(isChecked);
            }
        });

        mFamilyTreeLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                model.getSettings().setShowFamilyTreeLines(isChecked);
            }
        });

        mSpouseLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                model.getSettings().setShowSpouseLines(isChecked);
            }
        });


    }

    private void reSyncButtonClicked() {

        // get the family data from the server and go to the main activity map with the fresh data
        // this should set the local family data to the family data already in the database
        GetFamilyDataTask getFamilyDataTask = new GetFamilyDataTask();
        getFamilyDataTask.execute();

    }

    // GET FAMILY DATA
    // rather than returning anything, update the Model's persons and events directly.
    private class GetFamilyDataTask extends AsyncTask<Void, String, Void> {

        //protected Long doInBackground(URL... urls) {
        protected Void doInBackground(Void... voids) {
//            loginSuccess = false;
//            make the login info into a LoginRequest
//            LoginRequest loginRequest = new LoginRequest();
//            loginRequest.setUserName(userName);
//            loginRequest.setPassword(password);

            //get the server info
            Model model = Model.getModel();
            String serverHost = model.getServerHost();
            String serverPort = model.getServerPort();

            //call the serverproxy getFamilyData method (it will get the user info itself)
            // make it a singleton in the future...
            ServerProxy serverProxy = new ServerProxy();
            // Update the Model instance with the family data from the serverproxy


            try {
                serverProxy.getPersons(serverHost, serverPort);
                serverProxy.getEvents(serverHost, serverPort);
                //Log.v("doinbackground", "AUTHTOKEN: " + Model.getModel().get);
            } catch (Exception e) {
                e.printStackTrace();
                publishProgress("Family data could not be retrieved.");

            }

            return null;
        }

        protected void onProgressUpdate(String... updateMessage) {
            Toast.makeText(getBaseContext(), updateMessage[0], Toast.LENGTH_SHORT).show();
        }

        protected void onPostExecute(Void voids) {
            //do the UI stuff

            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);


//            Person p = Model.getModel().getPersons().get(0);
//
//            System.out.println("person's ID from model: " + p.getPersonID());
//
//            Toast.makeText(getContext(),
//                    "First name: " + p.getFirstName() +
//                            "\nLast name: " + p.getLastName(),
//                    Toast.LENGTH_SHORT).show();
//
//            mActivity = (MainActivity) getContext();
//            mActivity.switchToMapFragment();
            //System.out.println("TEST TEST TEST");

        }
    }

    private void logoutButtonClicked() {
        Model model = Model.getModel();
        model.setAuthtoken(null);

        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }




}
