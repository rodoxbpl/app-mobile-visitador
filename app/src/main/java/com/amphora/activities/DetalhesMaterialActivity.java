package com.amphora.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amphora.banco.DAO_Banco;
import com.amphora.drawer.DrawerMaterial;
import com.amphora.drawer.R;
import com.amphora.tools.VerificaString;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetalhesMaterialActivity extends AppCompatActivity {

    DAO_Banco banco = new DAO_Banco(this);
    Calendar calendario = Calendar.getInstance();
    int ano = calendario.get(Calendar.YEAR);
    int mes = calendario.get(Calendar.MONTH);
    int dia = calendario.get(Calendar.DAY_OF_MONTH);
    private EditText material;
    private String nome, pfcrm, crm, ufcrm, data, divulga, novoMaterial,
            material2, material3, matCorreto, tela;
    private TextView name, ncrm, dt, mat;
    private Button incluir;
    private Cursor cursor;
    private boolean verifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_material);

        setTitle("Divulgação");

        material = (EditText) findViewById(R.id.matDivulgado);
        incluir = (Button) findViewById(R.id.buttonIncluiMat);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        nome = bundle.getString("nome");
        crm = bundle.getString("crm");
        pfcrm = bundle.getString("pfcrm");
        ufcrm = bundle.getString("ufcrm");
        tela = bundle.getString("act");

        banco.buscarMaterial(crm, ufcrm, pfcrm);
        cursor = banco.getCursorMaterial();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                if (material3 == null) {
                    material3 = cursor.getString(1) + "-" + cursor.getString(2)
                            + "\n\n";
                } else {
                    material3 += cursor.getString(1) + "-"
                            + cursor.getString(2) + "\n\n";
                }

            } while (cursor.moveToNext());
        }

        name = (TextView) findViewById(R.id.textoNome);
        name.setText(nome);
        ncrm = (TextView) findViewById(R.id.textoCrm);
        ncrm.setText("CRM: " + crm);
        mat = (TextView) findViewById(R.id.textoMaterial);
        mat.setMovementMethod(new ScrollingMovementMethod());
        mat.setText(material3);

        incluir.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (material.getText().toString().equals("")) {
                    Toast.makeText(DetalhesMaterialActivity.this,
                            "Verifique o campo material divulgado!",
                            Toast.LENGTH_LONG).show();
                } else {
                    if (material.getText().length() > 200) {
                        Toast.makeText(DetalhesMaterialActivity.this,
                                "Número de caracteres excedido, máximo 200!",
                                Toast.LENGTH_LONG).show();
                    } else {
                        DateFormat dd = new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss");
                        java.util.Date agora = new java.util.Date();
                        String data = dd.format(agora);

                        material2 = material.getText().toString().toUpperCase();
                        matCorreto = VerificaString
                                .semCaracterEspecial(material2);
                        String codVisitador = Integer.toString(DAO_Banco.codvisitador);

                        verifica = banco.inserirMaterial(matCorreto, crm,
                                pfcrm, ufcrm, data,
                                codVisitador);

                        material.setText("");

                        if (verifica == true) {
                            Toast.makeText(
                                    DetalhesMaterialActivity.this,
                                    "Material de divulgação incluído com sucesso!",
                                    Toast.LENGTH_SHORT).show();

                            Intent exibeDados = new Intent(
                                    getApplicationContext(),
                                    DetalhesMaterialActivity.class);
                            exibeDados.putExtra("nome", nome);
                            exibeDados.putExtra("crm", crm);
                            exibeDados.putExtra("ufcrm", ufcrm);
                            exibeDados.putExtra("pfcrm", pfcrm);

                            startActivity(exibeDados);
                            finish();
                        } else {
                            Toast.makeText(DetalhesMaterialActivity.this,
                                    "Erro, informe o setor de TI!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }

        });
    }

    public void onBackPressed() {
        this.finish();
        return;
    }
}
