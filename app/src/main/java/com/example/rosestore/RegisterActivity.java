package com.example.rosestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    CircleImageView mCircleImageView;

    TextInputEditText mTextInputEditTextRegisterEmail;
    TextInputEditText mTextInputEditTextRegisterPassword;
    TextInputEditText mTextInputEditTextConfirmRegisterPassword;
    Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mTextInputEditTextRegisterEmail = findViewById(R.id.textInputEdit_register_email);
        mTextInputEditTextRegisterPassword = findViewById(R.id.textInputEdit_register_password);
        mTextInputEditTextConfirmRegisterPassword = findViewById(R.id.textInputEdit_confirm_password);
        mButtonRegister = findViewById(R.id.button_register);

        mCircleImageView = findViewById(R.id.CircleReturn);
        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTextInputEditTextRegisterEmail.getText() != null &&
                        mTextInputEditTextRegisterPassword.getText() != null &&
                        mTextInputEditTextConfirmRegisterPassword.getText() != null
                ) {
                    if (validatePassword(
                            mTextInputEditTextRegisterPassword.getText().toString(),
                            mTextInputEditTextConfirmRegisterPassword.getText().toString()
                    )) {
                        registerUser(
                                mTextInputEditTextRegisterEmail.getText().toString(),
                                mTextInputEditTextRegisterPassword.getText().toString()
                        );
                    } else {
                        Toast.makeText(RegisterActivity.this, "Verifique los datos ingresados",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegisterActivity.this, "Registro Exitoso",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "El Registro ha fallado",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private Boolean validatePassword(String password, String confirmPassword) {
        if (password == null ||
                confirmPassword == null ||
                password.equals("") ||
                confirmPassword.equals("")
        ) return false;

        return password.equals(confirmPassword);
    }
}