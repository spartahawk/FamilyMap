package hawkes.fmc.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import hawkes.fmc.R;
import hawkes.fmc.model.Model;
import hawkes.fmc.net.ServerProxy;
import hawkes.model.request.LoginRequest;
import hawkes.model.result.LoginResult;

import static java.security.AccessController.getContext;

public class SettingsActivity extends AppCompatActivity {
    private LinearLayout mReSyncButton;
    private String mReSyncMesssage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        mReSyncButton = (LinearLayout) findViewById(R.id.reSyncDataLayout);

        mReSyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSyncButtonClicked();
                Toast.makeText(getBaseContext(), mReSyncMesssage, Toast.LENGTH_SHORT).show();
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
    public class GetFamilyDataTask extends AsyncTask<Void, String, Void> {

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







}
