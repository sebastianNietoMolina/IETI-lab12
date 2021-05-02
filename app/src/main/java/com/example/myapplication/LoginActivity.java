package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.LoginWrapper;
import com.example.myapplication.model.Token;
import com.example.myapplication.service.AuthService;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmailLogin;
    private EditText editTextPasswordLogin;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmailLogin = (EditText) findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin = (EditText) findViewById(R.id.editTextPasswordLogin);
    }

    public void validateLogin(View view) {
        String email = editTextEmailLogin.getText().toString();
        String password = editTextPasswordLogin.getText().toString();

        if (email.isEmpty()) {
            editTextEmailLogin.setError("Debes ingresar un email");
        }
        if (password.isEmpty()) {
            editTextPasswordLogin.setError("Debes ingresar una contraseña");
        } else {
            AuthService authService = initiation();
            System.out.println("email omee "+email);
            System.out.println("passs omee "+password);
            verify(authService, email, password);
        }
    }

    public void verify(AuthService authService, String email, String password){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<Token> response = authService.login(new LoginWrapper(email, password)).execute();
                    System.out.println("el response es..."+response.isSuccessful());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful()) {
                                Token token = response.body();
                                System.out.println("logramos obtener el token o no?" + token.getAccessToken());
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                editTextEmailLogin.setError("Correo o contraseña incorrecta");
                                editTextPasswordLogin.setError("Correo o contraseña incorrecta");
                            }

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AuthService initiation() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http:/10.0.2.2:8080") //localhost for emulator
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthService authService = retrofit.create(AuthService.class);
        return authService;
    }
}
