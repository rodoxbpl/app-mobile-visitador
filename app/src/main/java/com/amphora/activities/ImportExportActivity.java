package com.amphora.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amphora.banco.DAO_Banco;
import com.amphora.drawer.MainDrawerAg;
import com.amphora.drawer.R;
import com.amphora.tools.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ImportExportActivity extends AppCompatActivity {

    public static final String TAG = "LOG";
    DAO_Banco bd = new DAO_Banco (this);
    private Button btnImport, btnExport;
    private EditText userInput;
    private File dir;
    private ProgressDialog pbar;
    private String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_export);
        setTitle("Importar e Exportar");

        pbar = new ProgressDialog(ImportExportActivity.this);

        pbar.setCancelable(false);
        btnImport = findViewById(R.id.btnImport);
        btnExport = findViewById(R.id.btnExport);

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.layout_senha, null);

        userInput = promptsView.findViewById(R.id.userinput);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.userinput);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                result = (userInput.getText().toString());
                                if (result.equals("365785")){
                                    dialog.cancel();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Senha inválida!", Toast.LENGTH_SHORT);
                                    finish();
                                    startActivity(getIntent());

                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                startActivity(new Intent(ImportExportActivity.this, MainDrawerAg.class));
                                finish();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();




        btnImport.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {


                pbar.setMessage("Importando dados...");
                pbar.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        especialidades();
                        medico();
                        medicoAgVisitas();
                        medicoEndereco();
                        medicoEspecialidades();
                        medicoHisVisitacao();

                    }
                }).start();


            }

        });

        btnExport.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                ExportarDivulgacao();
                ExportarHorarios();
            }

        });
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        if (pbar != null && pbar.isShowing()){
            pbar.cancel();
        }
    }

    protected void especialidades() {

        try {
            String caminho = "/mnt/sdcard/visitadores/imp_especialidade.csv";
            FileReader arq = new FileReader(caminho);
            BufferedReader bufferedReader = new BufferedReader(arq);
            String recebe_string;
            recebe_string = bufferedReader.readLine();
            while (recebe_string != null) {
                String[] dados = recebe_string.split(";");//separa os dados de acordo com ;
                String cdesp = dados[0];
                String esp = dados[1];

                bd.inserirEspecialidades(cdesp, esp);
                recebe_string = bufferedReader.readLine();
            }
            arq.close();
            Toast.makeText(
                    ImportExportActivity.this,
                    "Dados inseridos com sucesso na tabela especialidades!",
                    Toast.LENGTH_LONG).show();
        }catch (Exception e){

        }



    }

    protected void medico(){

        try {
            String caminho = "/mnt/sdcard/visitadores/imp_medico.csv";
            FileReader arq = new FileReader(caminho);
            BufferedReader bufferedReader = new BufferedReader(arq);
            String recebe_string;
            recebe_string = bufferedReader.readLine();
            while (recebe_string != null) {
                String[] dados = recebe_string.split(";"); //separa os dados de acordo com ;
                String pfcrm = dados[0];
                String medico = dados[1];
                String sexo = dados[2];
                String dtnas = dados[3];
                String cel = dados[4];
                String visInicio = dados[5];
                String ufcrm = dados[6];
                String nrcrm = dados[7];
                String visitador = dados[8];

                bd.inserirMedico(pfcrm, ufcrm, nrcrm, medico, sexo,
                        dtnas, cel, visInicio, visitador, "N");

                recebe_string = bufferedReader.readLine();
            }
            arq.close();

        }catch (Exception e){

        }


    }

    protected void medicoAgVisitas(){

        try {
            String caminho = "/mnt/sdcard/visitadores/imp_medico_agvisitas.csv";
            FileReader arq = new FileReader(caminho);
            BufferedReader bufferedReader = new BufferedReader(arq);
            String recebe_string;
            recebe_string = bufferedReader.readLine();
            while (recebe_string != null) {
                String[] dados = recebe_string.split(";"); //separa os dados de acordo com ;
                String pfcrm = dados[0];
                String nrcrm = dados[1];
                String dias = dados[2];
                String horarios = dados[3];
                String obs = dados[4];
                String ufcrm = dados[5];

                bd.inserirMedicoAgVisitas(pfcrm, ufcrm, nrcrm, dias,
                        horarios, obs);
                recebe_string = bufferedReader.readLine();
            }
            arq.close();

        }catch (Exception e){

        }

    }

    protected void medicoEndereco(){

        try{
            String caminho = "/mnt/sdcard/visitadores/imp_medico_endereco.csv";
            FileReader arq = new FileReader(caminho);
            BufferedReader bufferedReader = new BufferedReader(arq);
            String recebe_string;
            recebe_string = bufferedReader.readLine();
            while (recebe_string != null) {
                String[] dados = recebe_string.split(";"); //separa os dados de acordo com ;
                String pfcrm = dados[0];
                String nrcrm = dados[1];
                String tipo = dados[2];
                String cep = dados[3];

                String end1 = dados[4];
                String end2 = dados[5];
                String end3 = dados[6];
                String end4 = dados[7];
                String end5 = dados[8];
                String end6 = dados[9];

                String endereco = end1 + ", " + end2 + ", " + end3
                        + ", " + end4 + "-" + end5 + "/" + end6;

                String fon1 = dados[10];
                String fon2 = dados[11];
                String fon3 = dados[12];

                String fone = fon1 + " / " + fon2 + " / " + fon3;
                String email1 = dados[13];
                String email2 = dados[14];
                String pvisitar = dados[15];
                String ufcrm = dados[16];

                bd.inserirMedicoEndereco(pfcrm, ufcrm, nrcrm, tipo,
                        cep, endereco, fone, email1, email2, pvisitar);
                recebe_string = bufferedReader.readLine();
            }
            arq.close();

        }catch (Exception e) {
        }



    }

    protected void medicoEspecialidades(){

        try{
            String caminho = "/mnt/sdcard/visitadores/imp_medico_especialidades.csv";
            FileReader arq = new FileReader(caminho);
            BufferedReader bufferedReader = new BufferedReader(arq);
            String recebe_string;
            recebe_string = bufferedReader.readLine();
            while (recebe_string != null) {
                String[] dados = recebe_string.split(";"); //separa os dados de acordo com ;

                String pfcrm = dados[0];
                String nrcrm = dados[1];
                String cdesp = dados[2];
                String itemid = dados[3];
                String pvisitar = dados[4];
                String ufcrm = dados[5];
                bd.inserirMedicoEspecialidades(pfcrm, ufcrm, nrcrm,
                        cdesp, itemid, pvisitar);
                recebe_string = bufferedReader.readLine(); // lï¿½ da

            }
            arq.close();

        }catch (Exception e){
        }

    }

    protected void medicoHisVisitacao(){

        try{
            String caminho = "/mnt/sdcard/visitadores/imp_medico_hisvisitacao.csv";
            FileReader arq = new FileReader(caminho);
            BufferedReader bufferedReader = new BufferedReader(arq);
            String recebe_string;
            recebe_string = bufferedReader.readLine();
            while (recebe_string != null) {
                String[] dados = recebe_string.split(";"); //separa os dados de acordo com ;

                String pfcrm = dados[0];
                String nrcrm = dados[1];
                String dthr = dados[2];
                String divulg = dados[3];
                String ufcrm = dados[4];
                String codVistador = dados[5];

                bd.inserirMedicoHisdivulgacao(pfcrm, ufcrm, nrcrm,
                        dthr, divulg, codVistador);
                recebe_string = bufferedReader.readLine(); // lï¿½ da

            }

            arq.close();
            pbar.dismiss();

        }catch (Exception e){
        }


    }

    protected void ExportarDivulgacao() {
        File file = new File("/mnt/sdcard/visitadores/exp_hisdivulgacao.csv");
        try {
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            bd.exportaDivulgacao();
            while (bd.getCursorExporta().moveToNext()) {
                String dados[] = {bd.getCursorExporta().getString(0),
                        bd.getCursorExporta().getString(1),
                        bd.getCursorExporta().getString(2),
                        bd.getCursorExporta().getString(3),
                        bd.getCursorExporta().getString(5),
                        bd.getCursorExporta().getString(4)};
                csvWrite.writeNext(dados);
            }
            csvWrite.close();
            Toast.makeText(ImportExportActivity.this,
                    "Dados do histórico de divulgação exportado com sucesso!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(ImportExportActivity.this,
                    "Erro ao exportar arquivo!", Toast.LENGTH_LONG).show();
        }

    }

    protected void ExportarHorarios() {
        File file = new File("/mnt/sdcard/visitadores/exp_medico_agvisitas.csv");
        try {
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            bd.exportaHorarioVisita();
            while (bd.getCursorExporta2().moveToNext()) {
                String dados[] = {bd.getCursorExporta2().getString(0),
                        bd.getCursorExporta2().getString(1),
                        bd.getCursorExporta2().getString(2),
                        bd.getCursorExporta2().getString(3),
                        bd.getCursorExporta2().getString(4),
                        bd.getCursorExporta2().getString(5)};
                csvWrite.writeNext(dados);
            }
            csvWrite.close();
            Toast.makeText(ImportExportActivity.this,
                    "Dados alteração de horários exportado com sucesso!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(ImportExportActivity.this,
                    "Erro ao exportar arquivo!", Toast.LENGTH_LONG).show();
        }

    }

    public void onBackPressed() {
        startActivity(new Intent(ImportExportActivity.this, MainDrawerAg.class));
        this.finish();
        return;
    }
}
