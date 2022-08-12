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
import android.widget.TextView;
import android.widget.Toast;

import com.unir.dassaude.BancoDados;
import com.unir.dassaude.MainActivity;
import com.unir.dassaude.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pi extends Fragment {

    private Button btn_calcular, btn_limpar;
    private EditText altura;
    SharedPreferences preferences;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Pi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Pi.
     */
    // TODO: Rename and change types and number of parameters
    public static Pi newInstance(String param1, String param2) {
        Pi fragment = new Pi();
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
        View view = inflater.inflate(R.layout.activity_pi, container, false);

        btn_calcular = view.findViewById(R.id.btn_calcular_rcq);
        btn_limpar = view.findViewById(R.id.btn_limpar_rcq);
        altura = view.findViewById(R.id.edt_altura_rcp);

        BancoDados b = new BancoDados(getContext());
        ContentValues cv = new ContentValues();

        String nome, sexo;
        int idade;

        preferences = requireContext().getSharedPreferences("Arquivo_de_Dados", MODE_PRIVATE);
        //preferences = getContext().getSharedPreferences("Arquivo_de_Dados",MODE_PRIVATE);
        nome = preferences.getString("nome", "");
        idade = preferences.getInt("idade", 0);
        sexo = preferences.getString("sexo", "");

        btn_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (altura.getText().toString().isEmpty())
                    Toast.makeText(getContext(), "Por favor, preencha todos os dados!", Toast.LENGTH_SHORT).show();
                else {
                    try {
                        double alt = Double.parseDouble(altura.getText().toString());
                        double IMC_desejado_Homens = 22;
                        double IMC_desejado_Mulheres = 21;
                        double percentil50 = 25;         //valor padrão
                        double resultado = 0;            //valor padrao

                        String s = "";
                        AlertDialog.Builder seu_resultado = new AlertDialog.Builder(getContext(), R.style.fundo_dialogo);
                        seu_resultado.setIcon(R.drawable.logocoracao);
                        seu_resultado.setNeutralButton("Ok", null);

                        //      MULHER
                        if (alt >= 0.5 && alt <= 3) {     //pesquisei e esses são os extremos dos pesos , imagino que se inserir um valor fora do intervalor, possívelmente será um engano!

                            if (sexo.equals("Mulher")) {
                                if (idade > 0 && idade < 65) {
                                    resultado = calculaPiAdulto(alt, IMC_desejado_Mulheres);
                                    s = ("O peso ideal para " + nome + " é " + resultado + " kg.");
                                } else if (idade >= 65 && idade < 69) {
                                    percentil50 = 26.5;
                                    resultado = calculaPiIdoso(alt, percentil50);
                                    s = ("O peso ideal para " + nome + " é " + resultado + " kg.");

                                } else if (idade >= 70 && idade < 74) {
                                    percentil50 = 26.3;
                                    resultado = calculaPiIdoso(alt, percentil50);
                                    s = ("O peso ideal para " + nome + " é " + resultado + " kg.");

                                } else if (idade >= 75 && idade < 79) {
                                    percentil50 = 26.1;
                                    resultado = calculaPiIdoso(alt, percentil50);
                                    s = ("O peso ideal para " + nome + " é " + resultado + " kg.");

                                } else if (idade >= 80 && idade < 84) {
                                    percentil50 = 25.5;
                                    resultado = calculaPiIdoso(alt, percentil50);
                                    s = ("O peso ideal para " + nome + " é " + resultado + " kg.");

                                } else if (idade > 85) {   //idade vai até 130, uma idade maior, será barrada no MainActivity
                                    percentil50 = 23.6;
                                    resultado = calculaPiIdoso(alt, percentil50);
                                    s = ("O peso ideal para " + nome + " é " + resultado + " kg.");
                                }

                                seu_resultado.setMessage(s);
                                cv.put("resultado", s.toString());
                                long auxiliar = b.inserir(cv);
                                seu_resultado.show();
                            }
                            //      HOMEM

                            if (sexo.equals("Homem")) {
                                if (idade > 0 && idade < 65) {  //COLOQUEI DE 0 a 65 por que, imagino q serva para crianças tbm.
                                    resultado = calculaPiAdulto(alt, IMC_desejado_Homens);
                                    s = ("O peso ideal para " + nome + " é " + resultado + " kg.");
                                } else if (idade >= 65 && idade < 69) {
                                    percentil50 = 24.3;
                                    resultado = calculaPiIdoso(alt, percentil50);
                                    s = ("O peso ideal para " + nome + " é " + resultado + " kg.");

                                } else if (idade >= 70 && idade < 74) {
                                    percentil50 = 25.1;
                                    resultado = calculaPiIdoso(alt, percentil50);
                                    s = ("O peso ideal para " + nome + " é " + resultado + " kg.");

                                } else if (idade >= 75 && idade < 79) {
                                    percentil50 = 23.9;
                                    resultado = calculaPiIdoso(alt, percentil50);
                                    s = ("O peso ideal para " + nome + " é " + resultado + " kg.");

                                } else if (idade >= 80 && idade < 84) {
                                    percentil50 = 23.7;
                                    resultado = calculaPiIdoso(alt, percentil50);
                                    s = ("O peso ideal para " + nome + " é " + resultado + " kg.");

                                } else if (idade > 85) {
                                    percentil50 = 23.1;
                                    resultado = calculaPiIdoso(alt, percentil50);
                                    s = ("O peso ideal para " + nome + " é " + resultado + " kg.");
                                }
                                seu_resultado.setMessage(s);
                                cv.put("resultado", s.toString());
                                long auxiliar = b.inserir(cv);
                                seu_resultado.show();
                            }
                        } else
                            Toast.makeText(getContext(), "Por favor, coloque uma altura válida!", Toast.LENGTH_SHORT).show();

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
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    //  COMO é um programa pequeno, optei por deixar os metodos diferentes.
    private double calculaPiAdulto(double alt, double imc_desejado) {
        double resultado = imc_desejado * (alt * alt);
        return resultado;
    }

    private double calculaPiIdoso(double alt, double percentil50) {
        double resultado = percentil50 * (alt * alt);
        return resultado;
    }
}