package com.nikita.mozhaev.socialrank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText email, pass;
    Button btn_login;
    TextView txt_new;

    FirebaseAuth auth;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        email = findViewById(R.id.email3);
        pass = findViewById(R.id.password2);
        btn_login = findViewById(R.id.btn_log2);
        txt_new = findViewById(R.id.btn_to_reg2);

        if (firebaseUser != null) {
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish();
        }

        auth = FirebaseAuth.getInstance();
        txt_new.setOnClickListener(v -> {
            Intent start_reg = new Intent(MainActivity.this, MainActivity.class);
            startActivity(start_reg);
        });

        btn_login.setOnClickListener(v -> {
            String txt_email = email.getText().toString();
            String txt_pass = pass.getText().toString();

            if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pass)) {
                Toast.makeText(MainActivity.this, "Поля пусты!", Toast.LENGTH_SHORT).show();
            } else {


                auth.signInWithEmailAndPassword(txt_email, txt_pass).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Проблема с сервером авторизации", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}