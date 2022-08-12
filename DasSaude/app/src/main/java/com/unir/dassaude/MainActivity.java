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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edt_nome, edt_idade;
    private Button btn_inciar, btn_limpar;
    private RadioGroup radioGroup;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("Arquivo_de_Dados", MODE_PRIVATE);

        edt_nome = findViewById(R.id.edt_nome);
        edt_idade = findViewById(R.id.edt_idade);

        radioGroup = findViewById(R.id.radioGroup);

        btn_inciar = findViewById(R.id.btn_iniciar);
        btn_limpar = findViewById(R.id.btn_limpar);

        btn_inciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_nome.getText().toString().isEmpty() || edt_idade.getText().toString().isEmpty() || radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(MainActivity.this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else {

                    try {
                        String nome = String.format(edt_nome.getText().toString());
                        int idade = Integer.parseInt(edt_idade.getText().toString());

                        if (idade > 0 && idade <= 130) {  //pesquisei, acho q n tem uma pessoa passou dos 122 anos e 164 dias
                            SharedPreferences.Editor editor = preferences.edit();

                            editor.putString("nome", nome);
                            editor.putInt("idade", idade);

                            Intent fundo_Abas = new Intent(getApplicationContext(), Fundo_Abas.class);
                            int op = radioGroup.getCheckedRadioButtonId();

                            if (op == R.id.rb_masc) {
                                String sexo = "Homem";
                                editor.putString("sexo", sexo);
                            } else if (op == R.id.rb_fem) {
                                String sexo = "Mulher";
                                editor.putString("sexo", sexo);
                            }

                            editor.commit();
                            startActivity(fundo_Abas);
                        } else
                            Toast.makeText(MainActivity.this, "Por favor, informe uma idade válida!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btn_limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_nome.setText("");
                edt_idade.setText("");
                radioGroup.clearCheck();
            }
        });
    }
}