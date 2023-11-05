package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PerfilUsuarioActivity extends AppCompatActivity {

    TextView nomeTextView, emailTextView;
    Button botaoEditarPerfil;
    DHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nomeTextView = findViewById(R.id.textViewNome);
        emailTextView = findViewById(R.id.textViewEmail);
        botaoEditarPerfil = findViewById(R.id.botaoEditarPerfil);
        db = new DHelper(this);

        nomeTextView = findViewById(R.id.textViewNome);
        emailTextView = findViewById(R.id.textViewEmail);

        Usuario usuarioLogado = db.obterUsuarioLogado(this);

        if (usuarioLogado != null) {
            // Exibe as informações do usuário logado
            nomeTextView.setText("Nome: " + usuarioLogado.getNome());
            emailTextView.setText("Email: " + usuarioLogado.getEmail());
        } else {
            // Lidar com o caso em que o usuário não está logado
            // Por exemplo, redirecionar para a tela de login
        }

        botaoEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilUsuarioActivity.this, EditarPerfilActivity.class);
                intent.putExtra("nome", usuarioLogado.getNome());
                intent.putExtra("email", usuarioLogado.getEmail());
                startActivity(intent);
            }
        });

        Button botaoExcluir = findViewById(R.id.botaoExcluir);
        botaoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deletarUsuario(usuarioLogado);
                Toast.makeText(PerfilUsuarioActivity.this, "Usuário excluido com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PerfilUsuarioActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });


    }
}