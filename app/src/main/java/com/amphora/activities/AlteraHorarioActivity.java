package com.amphora.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.amphora.banco.DAO_Banco;
import com.amphora.drawer.MainDrawerAg;
import com.amphora.drawer.R;
import com.amphora.tools.VerificaString;

public class AlteraHorarioActivity extends AppCompatActivity {

    private static final String[] DIAS = new String[]{"Segunda-feira",
            "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira",
            "Sábado"};
    private static final String[] HORTARDE2 = new String[]{"", "13h", "14h",
            "15h", "16h", "17h", "18h", "19h"};
    private static final String[] HORTARDE1 = new String[]{"", "12h", "13h",
            "14h", "15h", "16h", "17h", "18h"};
    private static final String[] HORMANHA2 = new String[]{"", "8h", "9h",
            "10h", "11h", "12h"};
    private static final String[] HORMANHA1 = new String[]{"", "7h", "8h",
            "9h", "10h", "11h"};
    DAO_Banco banco = new DAO_Banco(this);
    private Cursor cursor;
    private String nome, crm, pfcrm, ufcrm, horaNovo, horManha1, horManha2,
            horTarde1, horTarde2, manha, tarde, obsNova, diaNovo, diaAntigo,
            horaAntiga, observAntiga, alterado = "", observacao;
    private int verifHorManha1, verifHorManha2, verifHorTarde1, verifHorTarde2;
    private TextView nomeMed, horAntigo, obsAntiga;
    private EditText obsNovo;
    private Button alterar, excluir;
    private Spinner combo, manha1, manha2, tarde1, tarde2;
    private CheckBox checkboxManha, checkboxTarde;
    private boolean verifica;
    private ArrayAdapter<String> adp, adpM1, adpM2, adpT1, adpT2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera_horario);
        setTitle("Horário Visita");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nome = bundle.getString("nome");
        crm = bundle.getString("crm");
        pfcrm = bundle.getString("pfcrm");
        ufcrm = bundle.getString("ufcrm");
        diaAntigo = bundle.getString("dia");
        horaAntiga = bundle.getString("hora");
        observAntiga = bundle.getString("obs");

        nomeMed = (TextView) findViewById(R.id.textoNomeHor);
        horAntigo = (TextView) findViewById(R.id.textoHor);
        obsAntiga = (TextView) findViewById(R.id.TextViewObs);
        alterar = (Button) findViewById(R.id.buttonAlteraHor);
        excluir = (Button) findViewById(R.id.ButtonExcluirHor);
        obsNovo = (EditText) findViewById(R.id.obsNovo);
        combo = (Spinner) findViewById(R.id.diaEscolhido);
        manha1 = (Spinner) findViewById(R.id.manha1);
        manha2 = (Spinner) findViewById(R.id.manha2);
        tarde1 = (Spinner) findViewById(R.id.tarde1);
        tarde2 = (Spinner) findViewById(R.id.tarde2);
        checkboxManha = (CheckBox) findViewById(R.id.checkBox1);
        checkboxTarde = (CheckBox) findViewById(R.id.checkBox2);

        nomeMed.setText(nome);
        horAntigo.setText(diaAntigo + ": " + horaAntiga);
        obsAntiga.setText("Obs.: " + observAntiga);

        adp = new ArrayAdapter<String>(this, R.layout.spinner_layout, DIAS);
        adp.setDropDownViewResource(R.layout.spinner_dropdown);
        combo.setAdapter(adp);

        horaExtenso();

        // aplicando as informações nos spinners
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

        checkboxManha.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
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

        checkboxTarde.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
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

        alterar.setOnClickListener(new Button.OnClickListener() {

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

        excluir.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Boolean verifica = banco.excluirHorario(pfcrm, ufcrm, crm,
                        diaAntigo, "S");

                if (verifica == true) {
                    Toast.makeText(AlteraHorarioActivity.this,
                            "Horário de visita excluído com sucesso!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AlteraHorarioActivity.this,
                            "Erro, informe o setor de TI!", Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }

            }

        });
    }

    private void horaExtenso() {
        // passando o valor do dia de extenso para números
        switch (diaAntigo) {
            case "segunda-feira":
                combo.setSelection(0);
                diaAntigo = "2";
                break;
            case "terça-feira":
                combo.setSelection(1);
                diaAntigo = "3";
                break;
            case "quarta-feira":
                combo.setSelection(2);
                diaAntigo = "4";
                break;
            case "quinta-feira":
                combo.setSelection(3);
                diaAntigo = "5";
                break;
            case "sexta-feira":
                combo.setSelection(4);
                diaAntigo = "6";
                break;
            case "sábado":
                combo.setSelection(5);
                diaAntigo = "7";
                break;
            default:
                break;
        }
    }

    public void horaSpinner() {
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

        verificaDados();

    }

    private void verificaDados() {
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
                    AlteraHorarioActivity.this,
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

            alteraHoraio(manha, tarde);
        }
    }

    public void alteraHoraio(String m, String t) {

        if ((m == null) && (t != null)) {
            horaNovo = t;
        } else if ((m != null) && (t == null)) {
            horaNovo = m;
        } else if ((m != null) && (t != null)) {
            horaNovo = m + "/" + t;
        } else {
            horaNovo = horaAntiga;
        }

        if (obsNovo.getText().toString().equals("")) {

            verifica = banco.updateHorarios(horaNovo, observAntiga, pfcrm,
                    ufcrm, crm, diaAntigo, diaNovo, "S");

            if (verifica == true) {
                Toast.makeText(AlteraHorarioActivity.this,
                        "Horário de visita alterado com sucesso!",
                        Toast.LENGTH_SHORT).show();
                Intent exibeDados = new Intent(getApplicationContext(),
                        MainDrawerAg.class);
                exibeDados.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(exibeDados);
                finish();
            } else {
                Toast.makeText(AlteraHorarioActivity.this,
                        "Erro, informe o setor de TI!", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {

            observacao = VerificaString.semCaracterEspecial(obsNovo.getText()
                    .toString());

            verifica = banco.updateHorarios(horaNovo, observacao.toUpperCase(), pfcrm, ufcrm, crm, diaAntigo, diaNovo, "S");
            if (verifica == true) {
                Toast.makeText(AlteraHorarioActivity.this,
                        "Horário de visita alterado com sucesso!",
                        Toast.LENGTH_SHORT).show();
                Intent exibeDados = new Intent(getApplicationContext(),
                        MainDrawerAg.class);
                exibeDados.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(exibeDados);
                finish();
            } else {
                Toast.makeText(AlteraHorarioActivity.this,
                        "Erro, informe o setor de TI!", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public void onBackPressed() {

        this.finish();
        return;
    }
}
