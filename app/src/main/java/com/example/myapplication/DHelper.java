package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "usuarios_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USUARIOS = "usuarios";
    private static final String KEY_ID = "id";
    private static final String KEY_NOME = "nome";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SENHA = "senha";

    public DHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table " + TABLE_USUARIOS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NOME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_SENHA + " TEXT" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(db);
    }

    public void incluirUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOME, usuario.getNome());
        values.put(KEY_EMAIL, usuario.getEmail());
        values.put(KEY_SENHA, usuario.getSenha());
        db.insert(TABLE_USUARIOS, null, values);
        db.close();
    }

    public Usuario getUsuario(int usuarioId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USUARIOS, new String[]{KEY_ID, KEY_NOME, KEY_EMAIL, KEY_SENHA},
                KEY_ID + "=?", new String[]{String.valueOf(usuarioId)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Usuario usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            usuario.setNome(cursor.getString(cursor.getColumnIndex(KEY_NOME)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex(KEY_SENHA)));
            cursor.close();
            return usuario;
        }
        return null;
    }

    public Usuario getUsuarioByEmailSenha(String email, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USUARIOS, new String[]{KEY_ID, KEY_NOME, KEY_EMAIL, KEY_SENHA},
                KEY_EMAIL + "=? AND " + KEY_SENHA + "=?", new String[]{email, senha}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                Usuario usuario = new Usuario();
                usuario.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                usuario.setNome(cursor.getString(cursor.getColumnIndex(KEY_NOME)));
                usuario.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
                usuario.setSenha(cursor.getString(cursor.getColumnIndex(KEY_SENHA)));
                cursor.close();
                return usuario;
            }
        }
        return null;
    }
        public Usuario obterUsuarioLogado(Context context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("UsuarioLogado", Context.MODE_PRIVATE);
            String userEmail = sharedPreferences.getString("userEmail", null);

            if (userEmail != null) {
                // Recuperar o usuário pelo email
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.query(TABLE_USUARIOS, new String[]{KEY_ID, KEY_NOME, KEY_EMAIL, KEY_SENHA},
                        KEY_EMAIL + "=?", new String[]{userEmail}, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    Usuario usuario = new Usuario();
                    usuario.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    usuario.setNome(cursor.getString(cursor.getColumnIndex(KEY_NOME)));
                    usuario.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
                    usuario.setSenha(cursor.getString(cursor.getColumnIndex(KEY_SENHA)));
                    cursor.close();
                    return usuario;
                }
            }
            return null; // Se o usuário não estiver logado ou não encontrado
        }


    public boolean atualizarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NOME, usuario.getNome());
        values.put(KEY_EMAIL, usuario.getEmail());
        values.put(KEY_SENHA, usuario.getSenha());

        int linhasAfetadas = db.update(TABLE_USUARIOS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(usuario.getId())});

        return linhasAfetadas > 0;
    }

    public boolean deletarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        int linhasAfetadas = db.delete(TABLE_USUARIOS, KEY_ID + "=?", new String[]{String.valueOf(usuario.getId())});
        db.close();
        return linhasAfetadas > 0; // Retorna true se linhas foram afetadas (usuário excluído)
    }

}
