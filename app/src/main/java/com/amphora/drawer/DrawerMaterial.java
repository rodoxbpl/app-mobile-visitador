package com.amphora.drawer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.amphora.activities.DetalhesMaterialActivity;
import com.amphora.activities.DetalhesMedicoActivity;
import com.amphora.activities.ImportExportActivity;
import com.amphora.banco.DAO_Banco;
import com.amphora.tools.VerificaString;

/**
 * Created by rodolfo on 11/09/2018.
 */

public class DrawerMaterial extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String[] FROM = new String[]{"NOMEMED", "NRCRM", "ENDERECO"};
    private final int[] TO = new int[]{R.id.itemNome3, R.id.itemCrm3,
            R.id.itemEnd3};
    SimpleCursorAdapter adp;
    SQLiteDatabase db;
    Cursor cursor;
    DAO_Banco bd = new DAO_Banco(this);
    private EditText nome;
    private ListView lista;
    private Button buscar;
    private String nomeMed;
    private TextView visitadora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_material);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Divulgação");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        nome = (EditText) findViewById(R.id.nomeMedico2);
        buscar = (Button) findViewById(R.id.btnBuscarMedico);
        visitadora = headerView.findViewById(R.id.txtVisitadora);
        String visTexto = bd.getVisitador();
        visitadora.setText(visTexto);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeMed = VerificaString.semCaracterEspecial(nome.getText().toString());
                bd.buscarMedico(nomeMed);

                criarListagem();
            }
        });

    }

    protected void criarListagem() {

        lista = (ListView) findViewById(R.id.listMedico);

        adp = new SimpleCursorAdapter(this, R.layout.medico_lista,
                bd.getCursorMedico(), FROM, TO);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                SQLiteCursor sqlCursor = (SQLiteCursor) adp.getItem(position);
                String nomeMed = sqlCursor.getString(sqlCursor
                        .getColumnIndex("NOMEMED"));
                String crm = sqlCursor.getString(sqlCursor
                        .getColumnIndex("NRCRM"));
                String pfcrm = sqlCursor.getString(sqlCursor
                        .getColumnIndex("PFCRM"));
                String ufcrm = sqlCursor.getString(sqlCursor
                        .getColumnIndex("UFCRM"));
                String dataNascimento = sqlCursor.getString(sqlCursor
                        .getColumnIndex("DTNAS"));
                String cel = sqlCursor.getString(sqlCursor
                        .getColumnIndex("CEL"));
                String endereco = sqlCursor.getString(sqlCursor
                        .getColumnIndex("ENDERECO"));
                String fone = sqlCursor.getString(sqlCursor
                        .getColumnIndex("FONE"));
                String email = sqlCursor.getString(sqlCursor
                        .getColumnIndex("EMAIL1"));
                String esp = sqlCursor.getString(sqlCursor
                        .getColumnIndex("ESPECIALIDADE"));

                Intent intent = new Intent(getApplicationContext(),
                        DetalhesMaterialActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nome", nomeMed);
                bundle.putString("crm", crm);
                bundle.putString("pfcrm", pfcrm);
                bundle.putString("ufcrm", ufcrm);
                bundle.putString("dataNas", dataNascimento);
                bundle.putString("cel", cel);
                bundle.putString("endereco", endereco);
                bundle.putString("fone", fone);
                bundle.putString("email", email);
                bundle.putString("esp", esp);
                intent.putExtras(bundle);
                startActivity(intent);


            }

        });
        lista.setAdapter(adp);

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_agenda) {
            startActivity(new Intent(DrawerMaterial.this, MainDrawerAg.class));
            finish();

        } else if (id == R.id.nav_medico) {
            startActivity(new Intent(DrawerMaterial.this, DrawerMedico.class));
            finish();

        } else if (id == R.id.nav_material) {


        } else if (id == R.id.nav_import) {
            startActivity(new Intent(DrawerMaterial.this, ImportExportActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
