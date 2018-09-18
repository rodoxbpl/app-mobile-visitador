package com.amphora.activities;

import android.content.Intent;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.amphora.drawer.MainDrawerAg;
import com.amphora.drawer.R;

public class MenuActivity extends AppCompatActivity {

    private String txtEnd = null, txtEsp = null, txtMed = null,
            txtHora = null;
    private String data;
    SimpleCursorAdapter adp;
    private RadioGroup rd1, rd2, rd3, rd4;
    private EditText txt1, txt2, txt3, txt4;
    private Button filtro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Filtro Agenda");

        filtro = findViewById(R.id.btnBuscarMenu);
        rd1 = findViewById(R.id.groupRadio1);
        rd2 = findViewById(R.id.groupRadio2);
        rd3 = findViewById(R.id.groupRadio3);
        rd4 = findViewById(R.id.groupRadio4);
        txt1 = findViewById(R.id.txtOpEnd);
        txt1.setVisibility(View.INVISIBLE);
        txt2 = findViewById(R.id.txtOpEsp);
        txt2.setVisibility(View.INVISIBLE);
        txt3 = findViewById(R.id.txtOpMed);
        txt3.setVisibility(View.INVISIBLE);
        txt4 = findViewById(R.id.txtOpHora);
        txt4.setVisibility(View.INVISIBLE);

        data = MainDrawerAg.dt;

        // Setando a visibilidade dos campos de edição de texto somente se for
        // escolhido o radio button específico
        rd1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rdButtonEndEsp) {
                    txt1.setVisibility(View.VISIBLE);
                } else {
                    txt1.setVisibility(View.INVISIBLE);
                }

            }

        });
        rd2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rdButtonEsp) {
                    txt2.setVisibility(View.VISIBLE);
                } else {
                    txt2.setVisibility(View.INVISIBLE);
                }

            }

        });
        rd3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rdButtonMed) {
                    txt3.setVisibility(View.VISIBLE);
                } else {
                    txt3.setVisibility(View.INVISIBLE);
                }

            }

        });
        rd4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rdButtonHora) {
                    txt4.setVisibility(View.VISIBLE);
                } else {
                    txt4.setVisibility(View.INVISIBLE);
                }

            }

        });

        filtro.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (txt1.getText() != null) {
                    txtEnd = txt1.getText().toString();
                }
                if (txt2.getText() != null) {
                    txtEsp = txt2.getText().toString();
                }
                if (txt3.getText() != null) {
                    txtMed = txt3.getText().toString();
                }
                if (txt4.getText() != null) {
                    txtHora = txt4.getText().toString();
                }

                //enviando dados pra próxima tela e chamando-a
                Bundle bundle = new Bundle();
                bundle.putString("txt1", txtEnd);
                bundle.putString("txt2", txtEsp);
                bundle.putString("txt3", txtMed);
                bundle.putString("txt4", txtHora);
                bundle.putString("data", data);
                Intent intent = new Intent(MenuActivity.this, MainDrawerAg.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }
        });

    }

    public void onBackPressed() {
        startActivity(new Intent(MenuActivity.this, MainDrawerAg.class));
        this.finish();
        return;
    }
}
