package com.unir.dassaude.fragmentos;

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

import static android.content.Context.MODE_PRIVATE;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unir.dassaude.BancoDados;
import com.unir.dassaude.MainActivity;
import com.unir.dassaude.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Imc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Imc extends Fragment {

    private Button btn_calcular, btn_limpar;
    private EditText altura, peso;
    SharedPreferences preferences;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Imc() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Imc.
     */
    // TODO: Rename and change types and number of parameters
    public static Imc newInstance(String param1, String param2) {
        Imc fragment = new Imc();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_imc, container, false);

        btn_calcular = view.findViewById(R.id.btn_calcular_imc);
        btn_limpar = view.findViewById(R.id.btn_limpar_imc);
        altura = view.findViewById(R.id.edt_altura_imc);
        peso = view.findViewById(R.id.edt_peso_imc);

        BancoDados b = new BancoDados(getContext());
        ContentValues cv = new ContentValues();

        String nome;
        int idade;
        preferences = requireContext().getSharedPreferences("Arquivo_de_Dados", MODE_PRIVATE);
        nome = preferences.getString("nome", "");
        idade = preferences.getInt("idade", 0);

        btn_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder seu_resultado = new AlertDialog.Builder(getContext(), R.style.fundo_dialogo);
                seu_resultado.setIcon(R.drawable.logocoracao);
                seu_resultado.setNeutralButton("Ok", null);

                if (altura.getText().toString().isEmpty() || peso.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        double altura1 = Double.parseDouble(altura.getText().toString());
                        double peso1 = Double.parseDouble(peso.getText().toString());

                        double resultado = calculoIMC(altura1, peso1);

                        String s = "";

                        if (altura1 >= 0.5 && altura1 <= 3 && peso1 >= 1.5 && peso1 <= 600) {      //pesquisei e esses são os extremos dos tamanhos e pesos , imagino que se inserir um valor fora do intervalor, possívelmente será um engano!

                            if (idade >= 18 && idade < 65) {
                                if (resultado < 16) {
                                    s = ("Olá " + nome + ", seu indice IMC é: " + resultado + "\n\nSua classificação de obesidade: Magreza Grau III");
                                } else if (resultado >= 16 && resultado <= 16.9) {
                                    s = ("Olá " + nome + ", seu indice IMC é: " + resultado + "\n\nSua classificação de obesidade: Magreza Grau II");
                                } else if (resultado >= 17 && resultado <= 18.4) {
                                    s = ("Olá " + nome + ", seu indice IMC é: " + resultado + "\n\nSua classificação de obesidade: Magreza Grau I");
                                } else if (resultado >= 18.5 && resultado <= 24.9) {
                                    s = ("Olá " + nome + ", seu indice IMC é: " + resultado + "\n\nSua classificação de obesidade: Eutrofia (normal) ");
                                } else if (resultado >= 25 && resultado <= 29.9) {
                                    s = ("Olá " + nome + ", seu indice IMC é: " + resultado + "\n\nSua classificação de obesidade: Sobrepeso" + "\nRisco aumentado");
                                } else if (resultado >= 30 && resultado <= 34.9) {
                                    s = ("Olá " + nome + ", seu indice IMC é: " + resultado + "\n\nSua classificação de obesidade: Obesidade Grau I" + "\nRisco moderado");
                                } else if (resultado >= 35 && resultado <= 40) {
                                    s = ("Olá " + nome + ", seu indice IMC é: " + resultado + "\n\nSua classificação de obesidade: Obesidade Grau II" + "\nRisco grave");
                                } else
                                    s = ("Olá " + nome + ", seu indice IMC é: " + resultado + "\n\nSua classificação de obesidade: Obesidade Grau III" + "\nRisco muito grave");

                                seu_resultado.setMessage(s);
                                cv.put("resultado", s.toString());
                                long auxiliar = b.inserir(cv);
                                seu_resultado.show();

                            } else if (idade >= 65) {
                                if (resultado < 23) {
                                    s = ("Olá " + nome + ", seu indice IMC é: " + resultado + "\n\nSua classificação de obesidade: Magreza" + "\nReferência Projeto SABE (OPAS/OMS)");
                                } else if (resultado >= 23 && resultado <= 28) {
                                    s = ("Olá " + nome + ", seu indice IMC é: " + resultado + "\n\nSua classificação de obesidade: Eutrófico (normal) " + "\nReferência Projeto SABE (OPAS/OMS)");
                                } else if (resultado >= 28 && resultado < 30) {
                                    s = ("Olá " + nome + ", seu indice IMC é: " + resultado + "\n\nSua classificação de obesidade: Sobrepeso" + "\nReferência Projeto SABE (OPAS/OMS)");
                                } else
                                    s = ("Olá " + nome + ", seu indice IMC é: " + resultado + "\n\nSua classificação de obesidade: Obesidade" + "\nReferência Projeto SABE (OPAS/OMS)");

                                seu_resultado.setMessage(s);
                                cv.put("resultado", s.toString());
                                long auxiliar = b.inserir(cv);
                                seu_resultado.show();

                            } else if (idade < 18) {
                                seu_resultado.setMessage("Olá " + nome + ". Infelizmente não temos dados para calcular seu indice RCQ na sua faixa etária.");
                                seu_resultado.show();
                            }
                        } else
                            Toast.makeText(getContext(), "Por favor, preencha os dados corretamente!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                altura.setText("");
                peso.setText("");
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private double calculoIMC(double altura, double peso) {
        double resultado = peso / (altura * altura);
        return resultado;
    }
}