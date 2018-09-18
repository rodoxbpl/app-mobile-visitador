package com.amphora.drawer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amphora.activities.ImportExportActivity;
import com.amphora.activities.ItemAgendaActivity;
import com.amphora.activities.MenuActivity;
import com.amphora.banco.DAO_Banco;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainDrawerAg extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static TextView filtro, visitadora;
    private static Button buttonData;
    private static ListView listaAgenda;
    private static int ano;
    private static int mes;
    private static int dia;
    private static Calendar CAL;
    static SimpleDateFormat stringDate = new SimpleDateFormat("dd/MM/yyyy");
    static SimpleDateFormat dateString = new SimpleDateFormat("EEEE");
    public static String dt;
    private static DAO_Banco bd;
    private static SimpleCursorAdapter adp;
    public static String txtMed = null, txtEsp = null, txtEnd = null,
            txtHora = null, diaFiltro = null, nomeMed, crm, hora, obs, fone,
            email, endereco, esp, data, ufcrm, pfcrm;
    private static Context contexto;
    public static final String TAG = "LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer_ag);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Agenda");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        if (ContextCompat.checkSelfPermission(this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)) {
                // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
            } else {
                // Solicita a permissão
                ActivityCompat.requestPermissions(this ,new String[]{WRITE_EXTERNAL_STORAGE},0);
            }
        } else {
            // Tudo OK, podemos prosseguir.
        }

        // Cria pasta dos arquivos de importa褯 e exporta褯

        File dir = new File("/mnt/sdcard/visitadores/");
        if (!dir.exists()) {
            dir.mkdir();
        }

        filtro = (TextView) findViewById(R.id.textFiltro);
        buttonData = (Button) findViewById(R.id.buttonData);
        listaAgenda = (ListView) findViewById(R.id.listaCadastro);
        visitadora = headerView.findViewById(R.id.txtVisitadora);

        bd = new DAO_Banco(this);
        String visTexto = bd.getVisitador();
        visitadora.setText(visTexto);
        contexto = getApplicationContext();

        CAL = Calendar.getInstance();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            txtMed = bundle.getString("txt3");
            txtEnd = bundle.getString("txt1");
            txtEsp = bundle.getString("txt2");
            txtHora = bundle.getString("txt4");
            data = bundle.getString("data");
        }
        buttonData.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                MostrarData(v);

            }
        });

        verificaFiltro();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Log.v(TAG, "Permission: " + permissions[0] + "was "
                    + grantResults[0]);

        }
    }

    private void alertAndFinish() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name)
                .setMessage(
                        "Para utilizar este aplicativo, vocʠprecisa aceitar as permiss��");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void MostrarData(View v) {

        FragmentManager fm = getSupportFragmentManager();
        DialogFragment ClasseData = new DatePickerFragment();
        ClasseData.show(fm, "date picker");

    }

    private void verificaFiltro(){
        /*
		 * Verifica se as variⷥis do filtro est䯠vazias Se n䯠estiverem
		 * nulas, seta para as variⷥis locais o valor das variⷥis do filtro
		 */
        if ((txtMed != null) || (txtEsp != null)
                || (txtEnd != null)
                || (txtHora != null)) {

            filtro.setText("Filtros: Médico=" + txtMed + "  especialidade="
                    + txtEsp + "  Endereço" + txtEnd + " Horário=" + txtHora);
            filtro.setVisibility(View.VISIBLE);
            diaFiltro = data;

            preencheDiaFiltro();
            criarListagem();

        }

		/*
		 * Se as variⷥis do filtro estiverem vazias, serᡲealizado o processo
		 * de busca no banco sem os resultados especcos, apenas levando em
		 * conta o dia atual definido no smartphone
		 */
        else {

            // Para armazenar dia, m볠e ano da data selecionada

            ano = CAL.get(Calendar.YEAR);
            mes = CAL.get(Calendar.MONTH);
            dia = CAL.get(Calendar.DAY_OF_MONTH);
            String calendario = new StringBuilder().append(dia).append("/")
                    .append(mes + 1).append("/").append(ano).toString();


            preencheDia(calendario);
            criarListagem();
        }

    }


    private void preencheDiaFiltro() {
        try {

            switch (diaFiltro) {
                case "segunda-feira":
                    buttonData.setText("Segunda-Feira");
                    bd.buscarAgendaFiltros("2", txtMed, txtEsp, txtEnd, txtHora);
                    break;
                case "terça-feira":
                    buttonData.setText("Terça-Feira");
                    bd.buscarAgendaFiltros("3", txtMed, txtEsp, txtEnd, txtHora);
                    break;
                case "quarta-feira":
                    buttonData.setText("Quarta-Feira");
                    bd.buscarAgendaFiltros("4", txtMed, txtEsp, txtEnd, txtHora);
                    break;
                case "quinta-feira":
                    buttonData.setText("Quinta-Feira");
                    bd.buscarAgendaFiltros("5", txtMed, txtEsp, txtEnd, txtHora);
                    break;
                case "sexta-feira":
                    buttonData.setText("Sexta-Feira");
                    bd.buscarAgendaFiltros("6", txtMed, txtEsp, txtEnd, txtHora);
                    break;
                case "sábado":
                    buttonData.setText("Sábado");
                    bd.buscarAgendaFiltros("7", txtMed, txtEsp, txtEnd, txtHora);

            }

        } catch (Exception e) {
            Toast.makeText(this, "Erro, informe o setor de TI!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private static void preencheDia(String calendario){
        try {
            Date cal1 = stringDate.parse(calendario);
            dt = dateString.format(cal1);
            switch (dt) {
                case "segunda-feira":
                    buttonData.setText("Segunda-Feira");
                    bd.buscarAgenda("2");
                    break;
                case "terça-feira":
                    buttonData.setText("Terça-Feira");
                    bd.buscarAgenda("3");
                    break;
                case "quarta-feira":
                    buttonData.setText("Quarta-Feira");
                    bd.buscarAgenda("4");
                    break;
                case "quinta-feira":
                    buttonData.setText("Quinta-Feira");
                    bd.buscarAgenda("5");
                    break;
                case "sexta-feira":
                    buttonData.setText("Sexta-Feira");
                    bd.buscarAgenda("6");
                    break;
                case "sábado":
                    buttonData.setText("Sábado");
                    bd.buscarAgenda("7");
            }
            criarListagem();
        } catch (ParseException e1) {

            Toast.makeText(contexto, "Erro", Toast.LENGTH_SHORT).show();
        }
    }

    private static void criarListagem(){

		/*
		 * Quais colunas iremos pegar os dados
		 */
        String[] from = { "NOMEMED", "NRCRM", "ESPECIALIDADE", "HORARIOS",
                "OBS", "ENDERECO", "FONE", "EMAIL1" };
		/*
		 * Serᡳetado os valores do banco para os campos do xml que alimentarፊ		 * a lista
		 */
        int[] to = { R.id.itemNome, R.id.itemCrm2, R.id.itemEsp, R.id.itemHora,
                R.id.itemObs, R.id.itemEnd, R.id.itemFone, R.id.itemEmail };

        adp = new SimpleCursorAdapter(contexto, R.layout.lista_agenda,
                bd.getCursorAgenda(), from, to);

        listaAgenda.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

				/*
				 * Pegando os valores das colunas e guardando nas variⷥis que
				 * ser䯠utilizadas na tela de detalhes
				 */

                SQLiteCursor sqlCursor = (SQLiteCursor) adp.getItem(position);
                nomeMed = sqlCursor.getString(sqlCursor
                        .getColumnIndex("NOMEMED"));
                crm = sqlCursor.getString(sqlCursor.getColumnIndex("NRCRM"));
                hora = sqlCursor.getString(sqlCursor.getColumnIndex("HORARIOS"));
                obs = sqlCursor.getString(sqlCursor.getColumnIndex("OBS"));
                fone = sqlCursor.getString(sqlCursor.getColumnIndex("FONE"));
                email = sqlCursor.getString(sqlCursor.getColumnIndex("EMAIL1"));
                endereco = sqlCursor.getString(sqlCursor
                        .getColumnIndex("ENDERECO"));
                esp = sqlCursor.getString(sqlCursor
                        .getColumnIndex("ESPECIALIDADE"));
                ufcrm = sqlCursor.getString(sqlCursor.getColumnIndex("UFCRM"));
                pfcrm = sqlCursor.getString(sqlCursor.getColumnIndex("PFCRM"));

                Bundle bundle = new Bundle();
                bundle.putString("nomeMed", nomeMed);
                bundle.putString("crm", crm);
                bundle.putString("hora", hora);
                bundle.putString("obs", obs);
                bundle.putString("fone", fone);
                bundle.putString("endereco", endereco);
                bundle.putString("esp", esp);
                bundle.putString("ufcrm", ufcrm);
                bundle.putString("pfcrm", pfcrm);
                bundle.putString("email", email);
                bundle.putString("dia", dt);

                Intent intent = new Intent(contexto,
                        ItemAgendaActivity.class);
                intent.putExtras(bundle);
                contexto.startActivity(intent);

            }

        });

        listaAgenda.setAdapter(adp);
    }


    // Classe para gerenciar as datas selecionadas
    @SuppressLint("ValidFragment")
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            ano = CAL.get(Calendar.YEAR);
            mes = CAL.get(Calendar.MONTH);
            dia = CAL.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, ano, mes, dia);

        }

        // M굯do para alterar nas variⷥis a data selecionada
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;
            filtro.setVisibility(View.INVISIBLE);

            String calendario = new StringBuilder().append(dia).append("/").append(mes + 1)
                    .append("/").append(ano).toString();

            preencheDia(calendario);
        }

        @Override
        public int show(FragmentTransaction transaction, String tag) {
            return super.show(transaction, tag);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_drawer_ag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainDrawerAg.this, MenuActivity.class);
            startActivity(intent);
            finish();            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_agenda) {

        } else if (id == R.id.nav_medico) {
            startActivity(new Intent(MainDrawerAg.this, DrawerMedico.class));
            finish();

        } else if (id == R.id.nav_material) {
            startActivity(new Intent(MainDrawerAg.this, DrawerMaterial.class));
            finish();

        } else if (id == R.id.nav_import) {
            startActivity(new Intent(MainDrawerAg.this, ImportExportActivity.class));
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
