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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.unir.dassaude.fragmentos.Imc;
import com.unir.dassaude.fragmentos.Pi;
import com.unir.dassaude.fragmentos.Rcq;

public class Fundo_Abas extends AppCompatActivity {

    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    private ImageView duvida, historico, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundo_abas);

        smartTabLayout = findViewById(R.id.viewpagertab);
        viewPager = findViewById(R.id.viewpager);
        historico = findViewById(R.id.historico);
        duvida = findViewById(R.id.duvida);
        home = findViewById(R.id.home);

        historico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Historico.class);
                startActivity(intent);
            }
        });

        duvida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Fundo_Abas.this, R.style.fundo_dialogo);
                builder.setTitle(R.string.app_name);

                builder.setItems(R.array.dialog_items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                sobreRCQ();
                                break;
                            case 1:
                                sobreIMC();
                                break;
                            case 2:
                                SobrePI();
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();

            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //getSupportActionBar().setElevation(0);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("Calculo RCQ", Rcq.class)
                        .add("Calculo IMC", Imc.class)
                        .add("Calculo PI", Pi.class)
                        .create()
        );

        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }

    private void SobrePI() {
        //Toast.makeText(this, "PI", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.drnutricao.com.br/Antropometria/calcular-peso-ideal"));
        startActivity(intent);
    }

    private void sobreIMC() {
        //Toast.makeText(this, "IMC", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.drnutricao.com.br/antropometria/imc"));
        startActivity(intent);
    }

    private void sobreRCQ() {
        //Toast.makeText(this, "RCQ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tuasaude.com/relacao-cintura-quadril/"));
        startActivity(intent);
    }
}