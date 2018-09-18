package com.amphora.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amphora.banco.DAO_Banco;
import com.amphora.drawer.R;
import com.amphora.tools.VerificaString;

public class NovosHorariosActivity extends AppCompatActivity {

    private static final String[] HORTARDE2 = new String[]{"", "13h", "14h",
            "15h", "16h", "17h", "18h", "19h"};
    private static final String[] HORTARDE1 = new String[]{"", "12h", "13h",
            "14h", "15h", "16h", "17h", "18h"};
    private static final String[] HORMANHA2 = new String[]{"", "8h", "9h",
            "10h", "11h", "12h"};
    private static final String[] HORMANHA1 = new String[]{"", "7h", "8h",
            "9h", "10h", "11h"};
    private static final String[] DIAS = new String[]{"Segunda-feira",
            "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira",
            "Sábado"};
    DAO_Banco banco = new DAO_Banco(this);
    private String nome, crm, pf, uf, horaNovo, horManha1, horManha2,
            horTarde1, horTarde2, manha, tarde, obsNova, diaNovo,
            alterado = "";
    private TextView medico;
    private int verifHorManha1, verifHorManha2, verifHorTarde1, verifHorTarde2;
    private Button incluir;
    private EditText obsNovo;
    private Spinner combo, manha1, manha2, tarde1, tarde2;
    private CheckBox checkboxManha, checkboxTarde;
    private boolean verifica;
    private ArrayAdapter<String> adp, adpM1, adpM2, adpT1, adpT2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novos_horarios);
        setTitle("Horário Visita");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        nome = bundle.getString("nome");
        crm = bundle.getString("crm");
        pf = bundle.getString("pf");
        uf = bundle.getString("uf");

        medico = findViewById(R.id.textoNomeHorN);
        incluir = findViewById(R.id.buttonIncluiHorN);
        obsNovo = findViewById(R.id.obsNovoN);
        combo = findViewById(R.id.diaEscolhidoN);
        manha1 = findViewById(R.id.manha1N);
        manha2 = findViewById(R.id.manha2N);
        tarde1 = findViewById(R.id.tarde1N);
        tarde2 = findViewById(R.id.tarde2N);
        checkboxManha = findViewById(R.id.checkBox1N);
        checkboxTarde = findViewById(R.id.checkBox2N);

        medico.setText(nome);

        adp = new ArrayAdapter<String>(this, R.layout.spinner_layout, DIAS);
        adp.setDropDownViewResource(R.layout.spinner_dropdown);
        combo.setAdapter(adp);
        adpM1 = new ArrayAdapter<String>(this, R.layout.spinner_layout_hora,
                HORMANHA1);
        adpM1.setDropDownViewResource(R.layout.spinner_dropdown_hora);
        manha1.setAdapter(adpM1);
        adpM2 = new ArrayAdapter<String>(this, R.layout.spinner_layout_hora,
                HORMANHA2);
        adpM2.setDropDownViewResource(R.layout.spinner_dropdown_hora);
        manha2.setAdapter(adpM2);
        adpT1 = new ArrayAdapter<String>(this, R.layout.spinner_layout_hora,
                HORTARDE1);
        adpT1.setDropDownViewResource(R.layout.spinner_dropdown_hora);
        tarde1.setAdapter(adpT1);
        adpT2 = new ArrayAdapter<String>(this, R.layout.spinner_layout_hora,
                HORTARDE2);
        adpT2.setDropDownViewResource(R.layout.spinner_dropdown_hora);
        tarde2.setAdapter(adpT2);

        checkboxManha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {

                    manha1.setEnabled(false);
                    manha1.setSelection(0);
                    manha2.setEnabled(false);
                    manha2.setSelection(0);

                } else {

                    manha1.setEnabled(true);
                    manha2.setEnabled(true);

                }
            }

        });

        checkboxTarde.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {

                    tarde1.setEnabled(false);
                    tarde1.setSelection(0);
                    tarde2.setEnabled(false);
                    tarde2.setSelection(0);

                } else {

                    tarde1.setEnabled(true);
                    tarde2.setEnabled(true);

                }

            }
        });

        incluir.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch (combo.getSelectedItemPosition()) {
                    case 0:
                        diaNovo = "2";
                        break;

                    case 1:
                        diaNovo = "3";
                        break;

                    case 2:
                        diaNovo = "4";
                        break;

                    case 3:
                        diaNovo = "5";
                        break;

                    case 4:
                        diaNovo = "6";
                        break;

                    case 5:
                        diaNovo = "7";
                        break;

                }

                horaSpinner();

            }
        });
    }
    protected void horaSpinner() {

        switch (manha1.getSelectedItemPosition()) {
            case 0:
                horManha1 = "";
                break;
            case 1:
                horManha1 = "7";
                break;
            case 2:
                horManha1 = "8";
                break;
            case 3:
                horManha1 = "9";
                break;
            case 4:
                horManha1 = "10";
                break;
            case 5:
                horManha1 = "11";
                break;

        }

        switch (manha2.getSelectedItemPosition()) {
            case 0:
                horManha2 = "";
                break;
            case 1:
                horManha2 = "8";
                break;
            case 2:
                horManha2 = "9";
                break;
            case 3:
                horManha2 = "10";
                break;
            case 4:
                horManha2 = "11";
                break;
            case 5:
                horManha2 = "12";
                break;

        }

        switch (tarde1.getSelectedItemPosition()) {
            case 0:
                horTarde1 = "";
                break;
            case 1:
                horTarde1 = "12";
                break;
            case 2:
                horTarde1 = "13";
                break;
            case 3:
                horTarde1 = "14";
                break;
            case 4:
                horTarde1 = "15";
                break;
            case 5:
                horTarde1 = "16";
                break;
            case 6:
                horTarde1 = "17";
                break;
            case 7:
                horTarde1 = "18";
                break;

        }

        switch (tarde2.getSelectedItemPosition()) {
            case 0:
                horTarde2 = "";
                break;
            case 1:
                horTarde2 = "13";
                break;
            case 2:
                horTarde2 = "14";
                break;
            case 3:
                horTarde2 = "15";
                break;
            case 4:
                horTarde2 = "16";
                break;
            case 5:
                horTarde2 = "17";
                break;
            case 6:
                horTarde2 = "18";
                break;
            case 7:
                horTarde2 = "19";
                break;

        }

        if (horManha1 == "") {
            verifHorManha1 = 0;
        } else {
            verifHorManha1 = Integer.parseInt(horManha1);
        }

        if (horManha2 == "") {
            verifHorManha2 = 0;
        } else {
            verifHorManha2 = Integer.parseInt(horManha2);
        }

        if (horTarde1 == "") {
            verifHorTarde1 = 0;
        } else {
            verifHorTarde1 = Integer.parseInt(horTarde1);
        }

        if (horTarde2 == "") {
            verifHorTarde2 = 0;
        } else {
            verifHorTarde2 = Integer.parseInt(horTarde2);
        }

        if ((verifHorManha1 > verifHorManha2)
                || (verifHorTarde1 > verifHorTarde2)) {
            Toast.makeText(
                    NovosHorariosActivity.this,
                    "Horário inicial não pode ser maior que o horário final, verifique!",
                    Toast.LENGTH_LONG).show();
        } else {
            if ((!horManha1.equals("")) && (!horManha2.equals(""))) {
                manha = horManha1 + "-" + horManha2;
            } else if ((!horManha1.equals("")) && (horManha2 == "")) {
                manha = horManha1;
            } else if ((horManha1 == "") && (!horManha2.equals(""))) {
                manha = horManha2;
            } else {
                manha = null;
            }

            if ((!horTarde1.equals("")) && (!horTarde2.equals(""))) {
                tarde = horTarde1 + "-" + horTarde2;
            } else if ((!horTarde1.equals("")) && (horTarde2 == "")) {
                tarde = horTarde1;
            } else if ((horTarde1 == "") && (!horTarde2.equals(""))) {
                tarde = horTarde2;
            } else {
                tarde = null;
            }

            if (checkboxManha.isChecked()) {
                manha = "M";
            }
            if (checkboxTarde.isChecked()) {
                tarde = "T";
            }

            incluiHoraio(manha, tarde);
        }

    }

    private void incluiHoraio(String m, String t) {

        if ((m == null) && (t != null)) {
            horaNovo = t;
        } else if ((m != null) && (t == null)) {
            horaNovo = m;
        } else if ((m != null) && (t != null)) {
            horaNovo = m + "/" + t;
        }

        if (obsNovo.getText().toString().equals("")) {

            verifica = banco.inserirHorarios(horaNovo, "", pf, uf, crm,
                    diaNovo, "S");
            if (verifica == true) {
                Toast.makeText(NovosHorariosActivity.this,
                        "Horário de visita alterado com sucesso!",
                        Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(NovosHorariosActivity.this,
                        "Erro: Existe agenda para esse dia, verifique!",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            obsNova = VerificaString.semCaracterEspecial(obsNovo.getText().toString().toUpperCase());
            verifica = banco.inserirHorarios(horaNovo, obsNova
                    , pf, uf, crm, diaNovo, "S");
            if (verifica == true) {
                Toast.makeText(NovosHorariosActivity.this,
                        "Horário de visita alterado com sucesso!",
                        Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(NovosHorariosActivity.this,
                        "Erro: Existe agenda para esse dia, verifique!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onBackPressed() {
        this.finish();
        return;

    }
}
