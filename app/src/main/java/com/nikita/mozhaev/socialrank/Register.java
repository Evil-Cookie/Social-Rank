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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    Button reg;
    EditText email, pass;
    TextView txt_no_acc;

    Button btn_auth_google;

    FirebaseAuth auth;
    DatabaseReference reference;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reg = findViewById(R.id.btn_log2);

        email = findViewById(R.id.email3);
        pass = findViewById(R.id.password2);

        txt_no_acc = findViewById(R.id.btn_to_reg2);


        txt_no_acc.setOnClickListener(v -> {
            Intent no_acc = new Intent(Register.this, MainActivity.class);
            startActivity(no_acc);
        });


        if (firebaseUser != null) {
            Intent intent = new Intent(Register.this, Home.class);
            startActivity(intent);
            finish();
        }

        auth = FirebaseAuth.getInstance();

        reg.setOnClickListener(v -> {
            String txt_email = email.getText().toString();
            String txt_pass = pass.getText().toString();

            if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pass)) {
                Toast.makeText(Register.this, "Поля пусты!", Toast.LENGTH_LONG).show();
            } else if (txt_pass.length() < 6) {

                Toast.makeText(Register.this, "Пароль должен содержать, как минимум 6 символов", Toast.LENGTH_LONG).show();
            } else {
                register(txt_email, txt_pass);
            }
        });

    }

    private void register(final String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                String userid = firebaseUser.getUid();

                reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", userid);
                hashMap.put("email", email);
                hashMap.put("Name", "name");
                hashMap.put("score", "0");



                reference.setValue(hashMap).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        finish();
                        Intent intent = new Intent(Register.this, Home.class);
                        startActivity(intent);
                    }
                });

            } else {
                Toast.makeText(Register.this, "Кажется ты уже зарегистрирован!", Toast.LENGTH_LONG).show();
            }
        });
    }
}