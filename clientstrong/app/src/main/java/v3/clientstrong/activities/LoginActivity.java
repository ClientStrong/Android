package v3.clientstrong.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import v3.clientstrong.R;
import v3.clientstrong.SessionManager;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    Button btnLogin;

    EditText txtUsername, txtPassword;

    // User Session Manager Class
    SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // User Session Manager
        session = new SessionManager(getApplicationContext());

        // get Email, Password input text
        txtUsername = (EditText) findViewById(R.id.email);
        txtPassword = (EditText) findViewById(R.id.password);

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();


        // User Login button
        btnLogin = (Button) findViewById(R.id.email_sign_in_button);


        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Get username, password from EditText
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                // Validate if username, password is filled
                if(username.trim().length() > 0 && password.trim().length() > 0){

                    // For testing puspose username, password is checked with static data
                    // username = admin
                    // password = admin

                    if(username.equals("admin") && password.equals("admin")){

                        // Creating user login session
                        // Statically storing name="Android Example"
                        // and email="androidexample84@gmail.com"
                        session.createUserLoginSession("Android Example",
                                "androidexample84@gmail.com");

                        // Starting MainActivity
                        Intent i = new Intent(getApplicationContext(), Dashboard.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                        finish();

                    }else{

                        // username / password doesn't match&
                        Toast.makeText(getApplicationContext(),
                                "Username/Password is incorrect",
                                Toast.LENGTH_LONG).show();

                    }
                }else{

                    // user didn't entered username or password
                    Toast.makeText(getApplicationContext(),
                            "Please enter username and password",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
    }




//    // UI references.
//    private AutoCompleteTextView mEmailView;
//    private EditText mPasswordView;
//    private View mProgressView;
//    private View mLoginFormView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        this.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        setContentView(R.layout.activity_login);
//        // Set up the login form.
//        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
//
//        mPasswordView = (EditText) findViewById(R.id.password);
//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
//        mEmailSignInButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                attemptLogin();
//            }
//        });
//
//        mLoginFormView = findViewById(R.id.login_form);
//        mProgressView = findViewById(R.id.login_progress);
//    }
//
//
//    /**
//     * Attempts to sign in or register the account specified by the login form.
//     * If there are form errors (invalid email, missing fields, etc.), the
//     * errors are presented and no actual login attempt is made.
//     */
//    private void attemptLogin() {
//
//        // Reset errors.
//        mEmailView.setError(null);
//        mPasswordView.setError(null);
//
//        // Store values at the time of the login attempt.
//        String email = mEmailView.getText().toString();
//        String password = mPasswordView.getText().toString();
//
//        boolean cancel = false;
//        View focusView = null;
//
////        // Check for a valid password, if the user entered one.
////        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
////            mPasswordView.setError("error password");
////            focusView = mPasswordView;
////            cancel = true;
////        }
//
////        // Check for a valid email address.
////        if (TextUtils.isEmpty(email)) {
////            mEmailView.setError("error field required");
////            focusView = mEmailView;
////            cancel = true;
////        } else if (!isEmailValid(email)) {
////            mEmailView.setError("invalid email");
////            focusView = mEmailView;
////            cancel = true;
////        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
//            showProgress(true);
//
//            try {
//                requestLogin("doc.dwarf@clientstrong.com", "password");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void requestLogin(String email, String password) throws JSONException {
//        String url = RequestManager.getBaseUrl() + "login";
//        Map<String, Object> params = new HashMap<>();
//        params.put("email", email);
//        params.put("password", password);
//        JSONObject jsonBody = new JSONObject(params);
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest(url, jsonBody, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                SessionManager currentSession = new SessionManager(LoginActivity.this);
//                try {
//                    currentSession.createLoginSession(response.getString("email"), response.getString("password"), response.getString("api_key"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                showProgress(false);
//                openDashboard();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // TODO Auto-generated method stub
//                error.getMessage();
//            }
//        }) {
//            // Overrides header; necessary to set response type;
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> header = new HashMap<>();
//                header.put("Accept", "application/json");
//                return header;
//            }
//        };
//        RequestManager.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);
//    }
//
//    private void openDashboard() {
//        Intent intent = new Intent(this, Dashboard.class);
//        startActivity(intent);
//    };
//
//    private boolean isEmailValid(String email) {
//        //TODO: Replace this with your own logic
//        return email.contains("@");
//    }
//
//    private boolean isPasswordValid(String password) {
//        //TODO: Replace this with your own logic
//        return password.length() > 4;
//    }
//
//    /**
//     * Shows the progress UI and hides the login form.
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }
}