package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText emailEditText, senhaEditText;
    Button loginButton;

    TextView irCadastroEditText;
    DHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.editTextEmailLogin);
        senhaEditText = findViewById(R.id.editTextSenhaLogin);
        loginButton = findViewById(R.id.buttonLogin);
        irCadastroEditText = findViewById(R.id.textIrCadastro);

        db = new DHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String senha = senhaEditText.getText().toString().trim();


                if (!email.isEmpty() && !senha.isEmpty()) {
                    Usuario usuario = db.getUsuarioByEmailSenha(email, senha);

                    SharedPreferences sharedPreferences = getSharedPreferences("UsuarioLogado", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userEmail", email);
                    editor.apply();

                    if (usuario != null) {
                        Toast.makeText(LoginActivity.this, "Login feito com sucesso!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, PerfilUsuarioActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        irCadastroEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirecionar para a tela de login
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }
}
