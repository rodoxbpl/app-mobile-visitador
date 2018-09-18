package com.amphora.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amphora.banco.DAO_Banco;
import com.amphora.drawer.R;

public class ItemAgendaActivity extends AppCompatActivity {

    private String nome, crm,
            hora, endereco,
            telefone, eMail,
            cdesp, observ,
            ufcrm, pfcrm,
            data, dadoTotal, material;
    private Cursor cursor;
    private TextView dados, mat;
    private Button incluir, alterar, incluirHor;
    private DAO_Banco bd = new DAO_Banco(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_agenda);

        setTitle("Agenda");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        nome = bundle.getString("nomeMed");
        crm = bundle.getString("crm");
        hora = bundle.getString("hora");
        endereco = bundle.getString("endereco");
        telefone = bundle.getString("fone");
        eMail = bundle.getString("email");
        cdesp = bundle.getString("esp");
        observ = bundle.getString("obs");
        ufcrm = bundle.getString("ufcrm");
        pfcrm = bundle.getString("pfcrm");
        data = bundle.getString("dia");

        incluir = findViewById(R.id.btnIncluiMat);
        alterar = findViewById(R.id.btnAlteraHor);
        incluirHor = findViewById(R.id.btnIncluiHor);

        dadoTotal = "Médico: " + nome + "\n\nCRM: " + crm
                + "\n\nEspecialidade: " + cdesp + "\n\nHorário de visita: "
                + hora + "\n\nObs.: " + observ + "\n\nEndereço " + endereco
                + "\n\nTelefone: " + telefone + "\n\nEmail: " + eMail;

        dados = findViewById(R.id.lblDados);
        dados.setMovementMethod(new ScrollingMovementMethod());
        dados.setText(dadoTotal);

        bd.buscarMaterial(crm, ufcrm, pfcrm);
        cursor = bd.getCursorMaterial();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                if (material == null) {
                    material = cursor.getString(1) + "-" + cursor.getString(2)
                            + "\n\n";
                } else {
                    material += cursor.getString(1) + "-"
                            + cursor.getString(2) + "\n\n";
                }

            } while (cursor.moveToNext());
        }

        mat = findViewById(R.id.lblMaterial);
        mat.setMovementMethod(new ScrollingMovementMethod());
        mat.setText(material);

        incluir.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle2 = new Bundle();
                Intent intent = new Intent(getApplicationContext(),
                        DetalhesMaterialActivity.class);
                
                bundle2.putString("nome", nome);
                bundle2.putString("crm", crm);
                bundle2.putString("pfcrm", pfcrm);
                bundle2.putString("ufcrm", ufcrm);
                bundle2.putString("material", material);
                intent.putExtras(bundle2);
                startActivity(intent);

            }

        });

        alterar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle bundle3 = new Bundle();
                Intent intent2 = new Intent(getApplicationContext(),
                        AlteraHorarioActivity.class);

                bundle3.putString("nome", nome);
                bundle3.putString("crm", crm);
                bundle3.putString("pfcrm", pfcrm);
                bundle3.putString("ufcrm", ufcrm);
                bundle3.putString("obs", observ);
                bundle3.putString("hora", hora);
                bundle3.putString("dia", data);
                intent2.putExtras(bundle3);

                startActivity(intent2);

            }

        });

        incluirHor.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle bundle4 = new Bundle();
                Intent intent3 = new Intent(getApplicationContext(),
                        NovosHorariosActivity.class);

                bundle4.putString("nome", nome);
                bundle4.putString("crm", crm);
                bundle4.putString("pfcrm", pfcrm);
                bundle4.putString("ufcrm", ufcrm);
                intent3.putExtras(bundle4);
                startActivity(intent3);


            }

        });

    }

    public void onBackPressed() {

        this.finish();
        return;
    }
}
