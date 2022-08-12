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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class Historico extends AppCompatActivity {

    private RecyclerView recycler;
    private Button btn_voltar;

    List<ContentValues> lista = new ArrayList<ContentValues>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        recycler = findViewById(R.id.recycler);
        btn_voltar = findViewById(R.id.btn_voltar);

        BancoDados b = new BancoDados(getApplicationContext());
        lista = b.pesquiarPorTodos();

        AdapterHistorico adapter = new AdapterHistorico(lista);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        recycler.addItemDecoration(new DividerItemDecoration(Historico.this, LinearLayout.VERTICAL));
        recycler.setAdapter(adapter);

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

/*
        preferences = getSharedPreferences("Arquivo_de_Dados", MODE_PRIVATE);
        String nome = preferences.getString("nome", "");
        double resultado=preferences.getFloat("resultadoRCQ",0);

        String resul=String.format(String.valueOf(resultado));
        StringBuilder builder=new StringBuilder();
        builder.append("Olá, ").append(nome).append("\n");

        lista.add(resul);


        x.append("Seu IMC é: ").append(im).append("\n\n");
        x.append("A sua classificação de risco é: ").append(c);

        //String resultado = builder.toString();

*/
    }
}