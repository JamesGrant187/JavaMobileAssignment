package nz.uict.a2037689.tuckbox;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static nz.uict.a2037689.tuckbox.UserManager.user;


public class MainActivity extends AppCompatActivity {

    private static boolean hasStarted = false;

    //Firebase Authentication declarations
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //Create a static string for LogCat
    private static final String TAG = "SignIn";
    //Create two EditText variables for login/password
    private EditText loginUsername, loginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Check for network connectivity
        if(!isNetworkAvailable()){
            Toast toast = Toast.makeText(getApplicationContext(), "No connection available!\nUnable to continue!\nPlease try again with network services", Toast.LENGTH_LONG);
            toast.show();
        }


        //Solving the log out error that appears if you log out from the main menu.
        //It doesn't like re-running the Persistence

        //Next time I will reserve the MainActivity for runtime stuff that only needs to be run once
        //on app startup.
        if (!hasStarted){
            hasStarted = true;
            //Set Firebase Database persistance locally to true
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }



        //Set signin and password fields on the form to two variables
        loginUsername = (EditText) findViewById(R.id.signInEditUsername);
        loginPassword = (EditText) findViewById(R.id.signInEditPassword);


        //FIREBASE AUTHENTICATION
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }

    @Override
    public void onStart() {
        //Firebase authentication
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        //Firebase authentication
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    //SIGN IN METHOD FROM FIREBASE TOOLS
    //Pass in email and password from EditText fields on form.

    public void signIn (final String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            //Get information from the database for current existing user
                            FirebaseDatabase.getInstance().getReference("users").child(task.getResult().getUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    user = dataSnapshot.getValue(User.class);

                                    Log.d(TAG, "User name: " + UserManager.user.getEmail() + ", email " + user.getEmail());

                                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    Log.w(TAG, "Failed to read value.", error.toException());
                                }

                            });
                        }
                    }
                });
    }



    //SIGN UP METHOD FROM FIREBASE TOOLS
    //Pass in email and password from EditText fields on form.
    public void createAccount (final String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            UserManager.user = new User (task.getResult().getUser().getUid());
                            UserManager.user.setEmail(email);







                            Intent intent = new Intent(MainActivity.this, MainMenu.class);
                            startActivity(intent);
                        }

                        // ...
                    }
                });
    }






    public void SignUpMainClick(View view) {

        //IF The username/password fields are empty, display an error message.
        //Or the password is 5 or less characters!
        if(loginUsername.getText().toString().isEmpty()||loginPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "You have not entered a username/password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(loginPassword.getText().toString().length() <= 5){
            Toast.makeText(this, "Password must be more  6 characters or longer", Toast.LENGTH_SHORT).show();
            return;
        }
        createAccount(loginUsername.getText().toString(), loginPassword.getText().toString());

    }

    public void SignInMainClick(View view) {

        if(loginUsername.getText().toString().isEmpty()||loginPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "You have not entered a username/password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(loginPassword.getText().toString().length() <= 5){
            Toast.makeText(this, "Password must be more  6 characters or longer", Toast.LENGTH_SHORT).show();
            return;
        }
        signIn(loginUsername.getText().toString(), loginPassword.getText().toString());
    }


    //DISABLE BACK BUTTON USE
    @Override
    public void onBackPressed() {
    }




    //getAllNetworkInfo is depricated in API 23 and above. With this we check to see if the user is using an earlier version or not.
    //If it's after 23 then use activeNetworkInfo, if not then use getAllNetworkInfo.
    public boolean isNetworkAvailable(){

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            {
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }

        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }

        return false;

    }
}