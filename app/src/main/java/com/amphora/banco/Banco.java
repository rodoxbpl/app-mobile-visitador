package com.amphora.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;

/**
 * Created by rodolfo on 11/09/2018.
 */

public class Banco extends SQLiteOpenHelper {

    private static final String nomeBd = "/android/data/com.amphora-visitadores/visitadores.db";
    private static final int versaoBd = 1;
    private File dir = new File("/android/data/com.amphora-visitadores/");

    public Banco(Context context) {
        super( context, Environment.getExternalStorageDirectory()
                .getAbsoluteFile() + nomeBd, null, versaoBd);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableEspecialidades = "CREATE TABLE especialidades (CDESP CHAR(3) PRIMARY KEY NOT NULL, ESPECIALIDADE VARCHAR(40) NOT NULL)";
        String tableMedico = "CREATE TABLE medico "
                + "(PFCRM CHAR(1) NOT NULL, UFCRM CHAR(2) NOT NULL, NRCRM VARCHAR(20) NOT NULL, NOMEMED VARCHAR(50), TPSEX CHAR(1), "
                + "DTNAS DATE, CEL CHAR (14), VISDTINICIO DATE, CODVISITADOR CHAR(1), ALTERADO CHAR(1) NOT NULL, CONSTRAINT pk_ME primary key(PFCRM,UFCRM,NRCRM));";
        String tableMedicoVisitas = "CREATE TABLE medico_agvisitas "
                + "(PFCRM CHAR(1) NOT NULL, UFCRM CHAR(2) NOT NULL, NRCRM VARCHAR(20) NOT NULL, DIAS CHAR(1) NOT NULL, "
                + "HORARIOS VARCHAR(100), OBS CHAR(50), CONSTRAINT pk_ME primary key(PFCRM,UFCRM,NRCRM,DIAS));";
        String tableMedicoEndereco = "CREATE TABLE medico_enderecos "
                + "(PFCRM CHAR(1) NOT NULL, UFCRM CHAR(2) NOT NULL, NRCRM VARCHAR(20) NOT NULL, TIPO VARCHAR(20), CEP CHAR(10), "
                + "ENDERECO VARCHAR(200), FONE VARCHAR(42), EMAIL1 VARCHAR(60), EMAIL2 VARCHAR(60), PVISITAR CHAR(1), "
                + "CONSTRAINT pk_ME primary key(PFCRM,UFCRM,NRCRM));";
        String tableMedicoEspecialidade = "CREATE TABLE medico_especialidades "
                + "(PFCRM CHAR(1) NOT NULL, UFCRM CHAR(2) NOT NULL, NRCRM VARCHAR(20) NOT NULL, CDESP CHAR(3) NOT NULL, "
                + "ITEMID INTEGER, PVISITAR CHAR(1), CONSTRAINT pk_ME primary key(PFCRM,UFCRM,NRCRM,CDESP));";
        String tableMedicoDivulgacao = "CREATE TABLE medico_hisdivulgacao "
                + "(PFCRM CHAR(1) NOT NULL, UFCRM CHAR(2) NOT NULL, NRCRM VARCHAR(20) NOT NULL, DTHR TIMESTAMP NOT NULL, "
                + "DIVULGACAO VARCHAR(200), CODVISHIST INTEGER NOT NULL, CONSTRAINT pk_ME primary key(PFCRM,UFCRM,NRCRM,DTHR));";
        String tableVisitador = "CREATE TABLE visitador (CODIGO integer PRIMARY KEY AUTOINCREMENT, NOME varchar(25) NOT NULL)";
        db.execSQL(tableEspecialidades);
        db.execSQL(tableMedico);
        db.execSQL(tableMedicoVisitas);
        db.execSQL(tableMedicoEndereco);
        db.execSQL(tableMedicoEspecialidade);
        db.execSQL(tableMedicoDivulgacao);
        db.execSQL(tableVisitador);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTIS ESPECIALIDADES");
        db.execSQL("DROP TABLE IF EXISTIS MEDICO");
        db.execSQL("DROP TABLE IF EXISTIS MEDICO_AGVISITAS");
        db.execSQL("DROP TABLE IF EXISTIS MEDICO_ENDERECOS");
        db.execSQL("DROP TABLE IF EXISTIS MEDICO_ESPECIALIDADES");
        db.execSQL("DROP TABLE IF EXISTIS MEDICO_HISDIVULGACAO");
        db.execSQL("DROP TABLE IF EXISTIS VISITADOR");
        onCreate(db);
    }
}
