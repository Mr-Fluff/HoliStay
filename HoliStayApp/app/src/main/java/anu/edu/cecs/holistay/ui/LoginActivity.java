package anu.edu.cecs.holistay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import anu.edu.cecs.holistay.Logger;
import anu.edu.cecs.holistay.R;

/**
 * User login activity (using firebase).
 *
 * Functionalities:
 * 1) Sign in a previously registered user
 *
 * @Authors: Srinivasa Sai Damarla (u7370240), Nakul Nambiar (u7433687)
 */
public class LoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    Button loginButton;
    EditText eUserName, ePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set views to variables
        setViewIDs();

        // Firebase support
        firebaseAuth = FirebaseAuth.getInstance();

        // Skip register/login activities once user is logged in on a device
        if(firebaseAuth.getCurrentUser() != null) startActivity(new Intent(getApplicationContext(), HotelListActivity.class));

        // Set listeners
        setClickListeners();
    }

    public void setClickListeners(){
        // loginButton listener: sign in with user details and if successful, go to HotelListActivity
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eUserName.getText().toString();
                String password = ePassword.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(LoginActivity.this,"Logging you in!",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), HotelListActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Authentication Failed!", Toast.LENGTH_LONG).show();
                        Logger.getInstance().error(LoginActivity.class, e);
                    }
                });
            }
        });
    }

    public void setViewIDs(){
        loginButton = (Button) findViewById(R.id.userLogin);
        eUserName = findViewById(R.id.userEmail);
        ePassword = findViewById(R.id.userPassword);
    }
}