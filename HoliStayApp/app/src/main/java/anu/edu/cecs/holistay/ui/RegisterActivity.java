package anu.edu.cecs.holistay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import anu.edu.cecs.holistay.Logger;
import anu.edu.cecs.holistay.R;
import anu.edu.cecs.holistay.User;

/**
 * User registration activity (using firebase).
 *
 * Functionalities:
 * 1) Get new user details such as name, email and password
 * 2) Save account on firebase, if successful
 * 3) Have optional login if user registered in the past
 *
 * @Authors: Srinivasa Sai Damarla (u7370240), Nakul Nambiar (u7433687)
 */
public class RegisterActivity extends AppCompatActivity {

    EditText userName, userEmail, userPassword;
    Button register;
    TextView loginLink;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set views to variables
        setViewIDs();

        // Firebase support
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        // Skip register/login activities once user is logged in on a device
        if(firebaseUser != null) startActivity(new Intent(getApplicationContext(), HotelListActivity.class));

        // Set listeners
        setClickListeners();
    }

    public void setClickListeners(){
        // register listener: save user details on firebase and if successful, go to HotelListActivity
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString();
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                User user = new User(name, email);
                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Logger.getInstance().info(RegisterActivity.class, "User " + email + " Created");
                                    firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Logger.getInstance().info(RegisterActivity.class, "Successfully added user to database!");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Logger.getInstance().error(RegisterActivity.class, e);
                                        }
                                    });
                                    Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), HotelListActivity.class));
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Error while creating user", Toast.LENGTH_SHORT).show();
                                    Logger.getInstance().error(this.getClass(), task.getException());
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
            }
        });

        // loginLink listener: link to LoginActivity
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    public void setViewIDs(){
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        register = findViewById(R.id.userRegister);
        loginLink = findViewById(R.id.loginLink);
        progressBar = findViewById(R.id.progressBar);
    }
}