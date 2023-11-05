package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarPerfilActivity extends AppCompatActivity {
    EditText editNome, editEmail, editSenha;
    Button botaoAtualizar;
    private DHelper db;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        db = new DHelper(this);

        editNome = findViewById(R.id.editTextNomeEdit);
        editEmail = findViewById(R.id.editTextEmailEdit);
        editSenha = findViewById(R.id.editTextSenhaEdit);
        botaoAtualizar = findViewById(R.id.botaoAtualizarEdit);

        Usuario usuarioLogado = db.obterUsuarioLogado(this);
        if (usuarioLogado != null) {
            editNome.setText(usuarioLogado.getNome());
            editEmail.setText(usuarioLogado.getEmail());
            editSenha.setText(usuarioLogado.getSenha());
        }

        botaoAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obter os novos valores dos campos de edição
                String novoNome = editNome.getText().toString();
                String novoEmail = editEmail.getText().toString();
                String novaSenha = editSenha.getText().toString();

                // Atualizar as informações do usuário no banco de dados
                Usuario usuarioAtualizado = new Usuario();
                usuarioAtualizado.setId(usuarioLogado.getId());
                usuarioAtualizado.setNome(novoNome);
                usuarioAtualizado.setEmail(novoEmail);
                usuarioAtualizado.setSenha(novaSenha);

                // Atualizar o usuário no banco de dados
                boolean atualizacaoBemSucedida = db.atualizarUsuario(usuarioAtualizado);

                if (atualizacaoBemSucedida) {
                    Toast.makeText(EditarPerfilActivity.this, "Perfil atualizado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent resultadoIntent = new Intent();
                    resultadoIntent.putExtra("nome", novoNome);
                    resultadoIntent.putExtra("email", novoEmail);
                    setResult(Activity.RESULT_OK, resultadoIntent);
                    finish();
                } else {
                    Toast.makeText(EditarPerfilActivity.this, "Falha ao atualizar o perfil", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}