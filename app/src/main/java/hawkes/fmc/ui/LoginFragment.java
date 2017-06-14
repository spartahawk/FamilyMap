package hawkes.fmc.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import hawkes.fmc.R;
import hawkes.fmc.model.Model;
import hawkes.fmc.net.ServerProxy;
import hawkes.model.Person;
import hawkes.model.request.LoginRequest;
import hawkes.model.request.RegisterRequest;
import hawkes.model.result.LoginResult;
import hawkes.model.result.RegisterResult;

public class LoginFragment extends Fragment {

    private static final String TAG = "LOGIN FRAGMENT";
    private boolean loginSuccess;
    LoginResult successfulLoginResult;
    private boolean registerSuccess;
    RegisterResult successfulRegisterResult;

    // Those fields for login
    private EditText mServerHostField;
    private EditText mServerPortField;
    private EditText mUserNameField;
    private EditText mPasswordField;
    private Button mSignInButton;

    // Additional fields for register
    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mEmailField;
    //private RadioButton mMaleButton;
    //private RadioButton mFemaleButton;
    private RadioGroup mGenderRadioGroup;
    private Button mRegisterButton;

    private String serverHost;
    private String serverPort;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginSuccess = false;
        registerSuccess = false;

        //fields for login
        mServerHostField = (EditText) view.findViewById(R.id.serverHostValue);
        mServerPortField = (EditText) view.findViewById(R.id.serverPortValue);
        mUserNameField = (EditText) view.findViewById(R.id.userNameValue);
        mPasswordField = (EditText) view.findViewById((R.id.passwordValue));

        // additional fields for register
        mFirstNameField = (EditText) view.findViewById(R.id.firstNameValue);
        mLastNameField = (EditText) view.findViewById(R.id.lastNameValue);
        mEmailField = (EditText) view.findViewById(R.id.emailValue);
        //mMaleButton = (RadioButton) view.findViewById(R.id.radio_male);
        //mFemaleButton = (RadioButton) view.findViewById(R.id.radio_female);
        mGenderRadioGroup = (RadioGroup) view.findViewById(R.id.genderRadioGroup);

        mSignInButton = (Button) view.findViewById(R.id.button_sign_in);
        mRegisterButton = (Button) view.findViewById(R.id.button_register);

        mServerHostField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                serverHost = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        mServerPortField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                serverPort = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        mUserNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userName = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        mPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        mFirstNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firstName = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        mLastNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lastName = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        mEmailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        mGenderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int genderButtonID = group.getCheckedRadioButtonId();
                switch (genderButtonID) {
                    case R.id.radio_male:
                        gender = "m";
                        break;
                    case R.id.radio_female:
                        gender = "f";
                        break;
                    case -1:
                        //todo: throw an exception and reprompt with a toast or something
                        break;
                }
            }
        });

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInButtonClicked();
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call a serverProxy method with the register info
                registerButtonClicked();
                //Toast.makeText(getContext(), R.string.toastTestRegister, Toast.LENGTH_SHORT).show();

            }
        });



        return view;
    }








    private void signInButtonClicked() {

        LoginTask loginTask = new LoginTask();
        loginTask.execute();

        GetFamilyDataTask getFamilyDataTask = new GetFamilyDataTask();
        getFamilyDataTask.execute();

    }

    private void registerButtonClicked() {

        RegisterTask registerTask = new RegisterTask();
        registerTask.execute();
    }


    public class LoginTask extends AsyncTask<Void, String, LoginResult> {

        //protected Long doInBackground(URL... urls) {
        protected LoginResult doInBackground(Void... voids) {
            loginSuccess = false;
            //make the login info into a LoginRequest
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUserName(userName);
            loginRequest.setPassword(password);

            publishProgress("Attempting to login");

            //call the serverproxy login method and give it the loginRequest
            // make it a singleton in the future...
            ServerProxy serverProxy = new ServerProxy();
            LoginResult loginResult = serverProxy.login(serverHost, serverPort, loginRequest);
            Log.v("doinbackground", "AUTHTOKEN: " + loginResult.getAuthToken());
            return loginResult;
        }

        protected void onProgressUpdate(String... updateMessage) {
            Toast.makeText(getContext(), updateMessage[0], Toast.LENGTH_SHORT).show();
        }

        protected void onPostExecute(LoginResult loginResult) {
            //do the UI stuff
            if (loginResult.getAuthToken() == null) {
                Log.v("LOGIN FRAGMENT", "AUTHTOKEN NULL");
                loginSuccess = false;
                Toast.makeText(getContext(),
                        loginResult.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
            else {
                loginSuccess = true;
                Log.v("LOGINFRAGMENT", "LOGIN SUCESSFUL.");

                //todo: I might be able to get rid of this
                successfulLoginResult = loginResult;

                Model.getModel().setLoginResult(loginResult);

//                Toast.makeText(getContext(),
//                        "First name: " + firstName +
//                                "\nLast name: " + lastName,
//                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    // GET FAMILY DATA AFTER SUCCESSFUL LOGIN
    // rather than returning anything, update the Model's persons and events directly.
    public class GetFamilyDataTask extends AsyncTask<Void, String, Void> {

        //protected Long doInBackground(URL... urls) {
        protected Void doInBackground(Void... voids) {
//            loginSuccess = false;
//            make the login info into a LoginRequest
//            LoginRequest loginRequest = new LoginRequest();
//            loginRequest.setUserName(userName);
//            loginRequest.setPassword(password);

            publishProgress("Getting Famiy Data");

            //call the serverproxy getFamilyData method (it will get the user info itself)
            // make it a singleton in the future...
            ServerProxy serverProxy = new ServerProxy();
            // Update the Model instance with the family data from the serverproxy
            serverProxy.getPersons(serverHost, serverPort);
            serverProxy.getEvents(serverHost, serverPort);
            //Log.v("doinbackground", "AUTHTOKEN: " + Model.getModel().get);

            return null;
        }

        protected void onProgressUpdate(String... updateMessage) {
            Toast.makeText(getContext(), updateMessage[0], Toast.LENGTH_SHORT).show();
        }

        protected void onPostExecute(Void voids) {
            //do the UI stuff

            Person p = Model.getModel().getPersons().get(0);

            System.out.println("person's ID from model: " + p.getPersonID());

            Toast.makeText(getContext(),
                    "First name: " + p.getFirstName() +
                    "\nLast name: " + p.getLastName(),
                    Toast.LENGTH_SHORT).show();

        }
    }

    public class RegisterTask extends AsyncTask<Void, String, RegisterResult> {

        //protected Long doInBackground(URL... urls) {
        protected RegisterResult doInBackground(Void... voids) {

            registerSuccess = false;

            //make the login info into a LoginRequest
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setUserName(userName);
            registerRequest.setPassword(password);
            registerRequest.setEmail(email);
            registerRequest.setFirstName(firstName);
            registerRequest.setLastName(lastName);
            registerRequest.setGender(gender);

            publishProgress("Attempting to Register");

            //call the serverproxy register method and give it the registerRequest
            // make it a singleton in the future...
            ServerProxy serverProxy = new ServerProxy();
            RegisterResult registerResult = serverProxy.register(serverHost, serverPort, registerRequest);
            //Log.v("doinbackground", "AUTHTOKEN: " + loginResult.getAuthToken());
            return registerResult;
        }

        protected void onProgressUpdate(String... updateMessage) {
            Toast.makeText(getContext(), updateMessage[0], Toast.LENGTH_SHORT).show();
        }

        protected void onPostExecute(RegisterResult registerResult) {
            //do the UI stuff
            if (registerResult.getMessage() == null) {
                Log.v("LOGIN FRAGMENT", "result message NULL; REGISSTRATION SUCCESSFUL.");
                registerSuccess = true;

                Toast.makeText(getContext(),
                        "First name: " + firstName +
                                "\nLast name: " + lastName,
                        Toast.LENGTH_SHORT).show();

            }
            else {
                registerSuccess = false;
                Toast.makeText(getContext(),
                        registerResult.getMessage(),
                        Toast.LENGTH_SHORT).show();

                Log.v("LOGINFRAGMENT", "REGISTRATION FAILED.");
                successfulRegisterResult = registerResult;
            }
        }
    }











}
