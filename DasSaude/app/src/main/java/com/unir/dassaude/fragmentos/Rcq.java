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
 * Use the {@link Rcq#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Rcq extends Fragment {

    private Button btn_calcular, btn_limpar;
    private EditText edt_circ_cintura, edt_circ_quadril;
    SharedPreferences preferences;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Rcq() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Rcq.
     */
    // TODO: Rename and change types and number of parameters
    public static Rcq newInstance(String param1, String param2) {
        Rcq fragment = new Rcq();
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
        View view = inflater.inflate(R.layout.activity_rcq, container, false);

        btn_calcular = view.findViewById(R.id.btn_calcular_rcq);
        btn_limpar = view.findViewById(R.id.btn_limpar_rcq);
        edt_circ_cintura = view.findViewById(R.id.edt_altura_rcp);
        edt_circ_quadril = view.findViewById(R.id.edt_quadril_rcq);

        BancoDados b = new BancoDados(getContext());
        ContentValues cv = new ContentValues();

        String nome, sexo;
        int idade;
        preferences = requireContext().getSharedPreferences("Arquivo_de_Dados", MODE_PRIVATE);
        nome = preferences.getString("nome", "");
        idade = preferences.getInt("idade", 0);
        sexo = preferences.getString("sexo", "");

        btn_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_circ_cintura.getText().toString().isEmpty() || edt_circ_quadril.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        double cintura = Double.parseDouble(edt_circ_cintura.getText().toString());
                        double quadril = Double.parseDouble(edt_circ_quadril.getText().toString());

                        double resultado = calculoRCQ(cintura, quadril);

                        AlertDialog.Builder seu_resultado = new AlertDialog.Builder(getContext(), R.style.fundo_dialogo);
                        seu_resultado.setIcon(R.drawable.logocoracao);
                        seu_resultado.setNeutralButton("Ok", null);

                        String s = "";

                        if (cintura >= 30 && cintura < 600 && quadril >= 50 && quadril <= 300) {      //pesquisei e esses são os extremos dos tamanhos, imagino que se inserir um valor fora do intervalor, possívelmente será um engano!

                            if (sexo.equals("Homem")) { //HOMEM

                                if (idade >= 20 & idade <= 29) {
                                    if (resultado < 0.83)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é baixa");
                                    if (resultado >= 0.83 & resultado <= 0.88)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é moderado");
                                    if (resultado >= 0.89 & resultado <= 0.94)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é alta");
                                    if (resultado > 0.94)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é muito alta");
                                } else if (idade >= 30 & idade < 39) {
                                    if (resultado < 0.84)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é baixa");
                                    if (resultado >= 0.84 & resultado <= 0.91)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é moderado");
                                    if (resultado >= 0.92 & resultado <= 0.96)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é alta");
                                    if (resultado > 0.96)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é muito alta");
                                } else if (idade >= 40 & idade <= 49) {
                                    if (resultado < 0.88)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é baixa");
                                    if (resultado >= 0.88 & resultado <= 0.95)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é moderado");
                                    if (resultado >= 0.96 & resultado <= 1)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é alta");
                                    if (resultado > 1)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é muito alta");
                                } else if (idade >= 50 & idade <= 59) {
                                    if (resultado < 0.90)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é baixa");
                                    if (resultado >= 0.90 & resultado <= 0.96)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é moderado");
                                    if (resultado >= 0.97 & resultado <= 1.02)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é alta");
                                    if (resultado > 1.02)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é muito alta");
                                } else if (idade >= 60 & idade <= 69) {
                                    if (resultado < 0.91)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é baixa");
                                    if (resultado >= 0.91 & resultado <= 0.98)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é moderada");
                                    if (resultado >= 0.99 & resultado <= 1.03)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é alta");
                                    if (resultado > 1.03)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é muito alta");
                                }
                                if (idade < 20)
                                    s = ("Olá " + nome + ". Infelizmente não temos dados para calcular seu indice RCQ na sua faixa etária.");
                                if (idade > 69)
                                    s = ("Olá " + nome + ". Infelizmente não temos dados para calcular seu indice RCQ na sua faixa etária.");

                                seu_resultado.setMessage(s);
                                cv.put("resultado", s.toString());
                                long auxiliar = b.inserir(cv);
                                seu_resultado.show();

                            } else if (sexo.equals("Mulher")) {     //MULHER
                                if (idade >= 20 & idade <= 29) {
                                    if (resultado < 0.71)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é baixa");
                                    if (resultado >= 0.71 & resultado <= 0.77)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é moderado");
                                    if (resultado >= 0.78 & resultado <= 0.82)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é alta");
                                    if (resultado > 0.82)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é muito alta");
                                } else if (idade >= 30 & idade <= 39) {
                                    if (resultado < 0.72)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é baixa");
                                    if (resultado >= 0.72 & resultado <= 0.78)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é moderado");
                                    if (resultado >= 0.79 & resultado <= 0.84)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é alta");
                                    if (resultado > 0.84)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é muito alta");
                                } else if (idade >= 40 & idade <= 49) {
                                    if (resultado < 0.73)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é baixa");
                                    if (resultado >= 0.73 & resultado <= 0.79)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é moderado");
                                    if (resultado >= 0.80 & resultado <= 0.87)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é alta");
                                    if (resultado > 0.87)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é muito alta");
                                } else if (idade >= 50 & idade <= 59) {
                                    if (resultado < 0.74)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é baixa");
                                    if (resultado >= 0.74 & resultado <= 0.81)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é moderado");
                                    if (resultado >= 0.82 & resultado <= 0.88)
                                        seu_resultado.setMessage("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é alta");
                                    if (resultado > 0.88)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é muito alta");
                                } else if (idade >= 60 & idade <= 69) {
                                    if (resultado < 0.76)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é baixa");
                                    if (resultado >= 0.76 & resultado <= 0.83)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é moderado");
                                    if (resultado >= 0.84 & resultado <= 0.90)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é alta");
                                    if (resultado > 0.90)
                                        s = ("Olá " + nome + ", seu indice RCQ é: "
                                                + resultado + "\n\nSua classificação de risco cardiovascular é muito alta");
                                }
                                if (idade < 20)
                                    s = ("Olá " + nome + ". Infelizmente não temos dados para calcular seu indice RCQ para sua faixa etária.");
                                if (idade > 69)
                                    s = ("Olá " + nome + ". Infelizmente não temos dados para calcular seu indice RCQ para sua faixa etária.");
                                seu_resultado.setMessage(s);
                                cv.put("resultado", s.toString());
                                long auxiliar = b.inserir(cv);
                                seu_resultado.show();
                            } else
                                Toast.makeText(getContext(), "Houve um erro inesperado com seu sexo." + nome + idade, Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getContext(), "Por favor, insira os dados corretamente!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btn_limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_circ_cintura.setText("");
                edt_circ_quadril.setText("");
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public Double calculoRCQ(double cintura, double quadril) {
        double resultado = (cintura / quadril);
        return resultado;
    }

}