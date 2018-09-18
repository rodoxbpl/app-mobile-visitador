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
import com.amphora.drawer.DrawerMedico;
import com.amphora.drawer.R;

public class DetalhesMedicoActivity extends AppCompatActivity {

    DAO_Banco bd = new DAO_Banco(this);
    Cursor cursor;
    private String nome, crm, pf, uf, dataNas, cel, fone, endereco, email,
            dados, esp, dados2, dia;
    private TextView name, data;
    private Button incluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_medico);
        setTitle("Médico");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        nome = bundle.getString("nome");
        crm = bundle.getString("crm");
        dataNas = bundle.getString("dataNas");
        cel = bundle.getString("cel");
        fone = bundle.getString("fone");
        endereco = bundle.getString("endereco");
        email = bundle.getString("email");
        esp = bundle.getString("esp");
        pf = bundle.getString("pf");
        uf = bundle.getString("uf");

        bd.buscarAgendaMedico(pf, uf, crm);
        cursor = bd.getCursorAgenda();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                switch (cursor.getString(1)) {
                    case "2":
                        dia = "Segunda-Feira";
                        break;
                    case "3":
                        dia = "Terça-Feira";
                        break;
                    case "4":
                        dia = "Quarta-Feira";
                        break;
                    case "5":
                        dia = "Quinta-Feira";
                        break;
                    case "6":
                        dia = "Sexta-Feira";
                        break;
                    case "7":
                        dia = "Sábado";
                        break;
                }

                if (dados2 == null) {
                    dados2 = "Dia: " + dia + " - Horário: "
                            + cursor.getString(2) + " - Obs.: "
                            + cursor.getString(3) + "\n\n";
                } else {
                    dados2 += "Dia: " + dia + " - Horário: "
                            + cursor.getString(2) + " - Obs.: "
                            + cursor.getString(3) + "\n\n";
                }
            } while (cursor.moveToNext());
        }

        name = (TextView) findViewById(R.id.txtNomeMed3);
        name.setMovementMethod(new ScrollingMovementMethod());
        data = (TextView) findViewById(R.id.TextDadosVisita);
        data.setMovementMethod(new ScrollingMovementMethod());
        incluir = findViewById(R.id.buttonIncluirHor);

        dados = "CRM: " + crm + "\nEspecialidade: " + esp + "\nData de nascimento: " + dataNas
                + "\n\nTelefone: " + fone + "\n\nCelular: " + cel + "\n\nEndereço " + endereco + "\n\nEmail: " + email;

        name.setText("Médico: " + nome + "\n\n" + dados);
        data.setText(dados2);

        incluir.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent exibeDados = new Intent(getApplicationContext(), NovosHorariosActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nome", nome);
                bundle.putString("crm", crm);
                bundle.putString("pf", pf);
                bundle.putString("uf", uf);
                exibeDados.putExtras(bundle);
                startActivity(exibeDados);
            }
        });

    }

    public void onBackPressed() {

        startActivity(new Intent(DetalhesMedicoActivity.this, DrawerMedico.class));
        this.finish();
        return;
    }
}
