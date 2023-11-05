package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {
    EditText nomeEditText, emailEditText, senhaEditText;
    TextView irLogin;
    Button cadastrarButton;
    DHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nomeEditText = findViewById(R.id.editTextNome);
        emailEditText = findViewById(R.id.editTextEmail);
        senhaEditText = findViewById(R.id.editTextSenha);
        cadastrarButton = findViewById(R.id.buttonCadastrar);
        irLogin = findViewById(R.id.textIrParaLogin);

        db = new DHelper(this);

        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = nomeEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String senha = senhaEditText.getText().toString().trim();

                if (!nome.isEmpty() && !email.isEmpty() && !senha.isEmpty()) {
                    Usuario usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);

                    db.incluirUsuario(usuario);
                    Toast.makeText(CadastroActivity.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    // Limpar campos após o cadastro
                    nomeEditText.setText("");
                    emailEditText.setText("");
                    senhaEditText.setText("");

                    Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(CadastroActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        irLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirecionar para a tela de login
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
