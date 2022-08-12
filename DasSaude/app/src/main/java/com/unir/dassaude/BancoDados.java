package com.unir.dassaude;

/*
        *** Fundação Universidade Federal de Rondônia - UNIR ***
        *** Bacharelado em Ciência da Computação ***

        Disciplina: Programação para Dispositivos Móveis
        Professor: Lucas Marques da Cunha SIAPE: 3269899

        Avaliação repositiva 08/08/2022 a 11/08/2022
        Ultima alteração 11/08/2022

        Este projeto foi desenvolvido por Jonathan Oliveira Pinheiro da Costa
        * contato: +55 (69) 9 9383-9679
        * email: jonathan-boy1999@hotmail.com

        Componentes do aplicativo:
        persistência de dados (SQL e/ou Shared Preferences), RecyclerView, SmartTabLayout, Fragments,
        Múltiplas Telas, validação de campos, Toast e/ou SnackBar, Intents explícitos e implícitos.

            *** Das informações do compilador ***

        Android Studio Bumblebee | 2021.1.1 Patch 3
        Build #AI-211.7628.21.2111.8309675, built on March 16, 2022
        Runtime version: 11.0.11+9-b60-7590822 amd64
        VM: OpenJDK 64-Bit Server VM by Oracle Corporation
        Windows 10 10.0
        GC: G1 Young Generation, G1 Old Generation
        Memory: 1280M
        Cores: 4
        Registry: external.system.auto.import.disabled=true
        Non-Bundled Plugins: com.intellij.marketplace (211.7628.36)
*/

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BancoDados extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "nutrilife";
    private static final int DATABASE_VERSION = 1;
    private final String CREATE_TABLE_CATALOGO =
            "CREATE TABLE historico ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "resultado TEXT);";

    public BancoDados(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CATALOGO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS catalogo");
        onCreate(sqLiteDatabase);
    }

    public long inserir(ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert("historico", null, cv);
        return id;
    }

//    public List<ContentValues> pesquiarPorTitulo(String titulo){
//        String sql = "SELECT * FROM catalogo WHERE titulo LIKE ?";
//        String where[] = new String[]{"%"+titulo+"%"};
//        return pesquisar(sql, where);
//    }
//
//    public List<ContentValues> pesquiarPorAno(int ano){
//        String sql = "SELECT * FROM catalogo WHERE ano LIKE ?";
//        String where[] = new String[]{String.valueOf(ano)};
//        return pesquisar(sql, where);
//    }

    public List<ContentValues> pesquiarPorTodos() {
        String sql = "SELECT * FROM historico ORDER BY id";
        String where[] = null;
        return pesquisar(sql, where);
    }

    @SuppressLint("Range")
    private List<ContentValues> pesquisar(String sql, String where[]) {
        List<ContentValues> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(sql, where);

        if (c.moveToFirst()) {
            do {
                ContentValues cv = new ContentValues();
                cv.put("id", c.getInt(c.getColumnIndex("id")));
                cv.put("resultado", c.getString(c.getColumnIndex("resultado")));
                lista.add(cv);
            } while (c.moveToNext());
        }

        return lista;
    }

//    public void alterarRegistro(int id, String titulo, String autor, int ano){
//        ContentValues valores = new ContentValues();
//        String where;
//        SQLiteDatabase db = this.getWritableDatabase();
//        where = "id=" + id;
//        valores.put("titulo", titulo);
//        valores.put("autor", autor);
//        valores.put("ano", ano);
//        db.update("historico", valores, where, null);
//        db.close();
//    }

    public void deletarRegistro(int id) {
        String where = "id=" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("historico", where, null);
        db.close();
    }
}
