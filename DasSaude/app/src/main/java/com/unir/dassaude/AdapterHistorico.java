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

import android.content.ContentValues;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdapterHistorico extends RecyclerView.Adapter<AdapterHistorico.MyViewHolder> {
    List<ContentValues> list = new ArrayList<>();

    public AdapterHistorico(List<ContentValues> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterHistorico.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_historico, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistorico.MyViewHolder holder, int position) {
        ContentValues cv = new ContentValues();
        cv = list.get(position);
        holder.id.setText(cv.getAsString("id"));
        holder.resultado.setText(cv.getAsString("resultado"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView id, resultado;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id22);
            resultado = itemView.findViewById(R.id.resultado);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    BancoDados b = new BancoDados(itemView.getContext());
                    b.deletarRegistro(Integer.parseInt(id.getText().toString()));
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    return false;
                }
            });
        }
    }

}