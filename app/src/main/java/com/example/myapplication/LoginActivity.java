package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmailLogin;
    private EditText editTextPasswordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmailLogin =  (EditText) findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin =  (EditText) findViewById(R.id.editTextPasswordLogin);
    }

    public void validateLogin(View view){
        String email = editTextEmailLogin.getText().toString();
        String password = editTextPasswordLogin.getText().toString();

        if(email.isEmpty()){
            editTextEmailLogin.setError("Debes ingresar un email");
        }if(password.isEmpty()) {
            editTextPasswordLogin.setError("Debes ingresar una contraseña");
        }
    }
}
