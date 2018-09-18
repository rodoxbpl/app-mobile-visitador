package com.amphora.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rodolfo on 11/09/2018.
 */

public class DAO_Banco {

    public static int codvisitador = 1;// Codigo visitador 1 = Luciana / 2 = Renata
    private String visitador = null;
    private static Banco data = null;
    SQLiteDatabase banco = null;
    private Context context;

    private Cursor cursorAgenda, cursorMaterial, cursorMedico, cursorExporta,
            cursorExporta2, cursorHora, cursorRegistro;
    private boolean registroExiste;

    public DAO_Banco(Context context) {
        if (data == null) {
            data = new Banco(context);
        }
    }

    public String getVisitador(){
        if (codvisitador == 1){
            return visitador = "LUCIANA";
        }
        else
            return visitador = "RENATA";
    }

    public Cursor getCursorAgenda() {
        return cursorAgenda;
    }

    public void setCursorAgenda(Cursor cursorAgenda) {
        this.cursorAgenda = cursorAgenda;
    }

    public Cursor getCursorMaterial() {
        return cursorMaterial;
    }

    public void setCursorMaterial(Cursor cursorMaterial) {
        this.cursorMaterial = cursorMaterial;
    }

    public Cursor getCursorMedico() {
        return cursorMedico;
    }

    public void setCursorMedico(Cursor cursorMedico) {
        this.cursorMedico = cursorMedico;
    }

    public Cursor getCursorExporta() {
        return cursorExporta;
    }

    public void setCursorExporta(Cursor cursorExporta) {
        this.cursorExporta = cursorExporta;
    }

    public boolean getRegistroExiste() {
        return registroExiste;
    }

    public void setRegistroExiste(boolean registroExiste) {
        this.registroExiste = registroExiste;
    }

    public Cursor getCursorRegistro() {
        return cursorRegistro;
    }

    public void setCursorRegistro(Cursor cursorRegistro) {
        this.cursorRegistro = cursorRegistro;
    }

    public Cursor getCursorExporta2() {
        return cursorExporta2;
    }

    public void setCursorExporta2(Cursor cursorExporta2) {
        this.cursorExporta2 = cursorExporta2;
    }

    public Cursor getCursorHora() {
        return cursorHora;
    }

    public void setCursorHora(Cursor cursorHora) {
        this.cursorHora = cursorHora;
    }

    public void inserirEspecialidades(String cdesp, String esp) {
        SQLiteDatabase db = data.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("cdesp", cdesp);
        valores.put("especialidade", esp);

        db.insert("especialidades", null, valores);
        db.close();

    }

    public void inserirMedico(String pfcrm, String ufcrm, String nrcrm,
                              String nome, String sexo, String dtnas, String cel,
                              String visInicio, String visitador, String alterado) {
        SQLiteDatabase db = data.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("pfcrm", pfcrm);
        valores.put("ufcrm", ufcrm);
        valores.put("tpsex", sexo);
        valores.put("nomemed", nome);
        valores.put("cel", cel);
        valores.put("nrcrm", nrcrm);
        valores.put("dtnas", dtnas);
        valores.put("visdtinicio", visInicio);
        valores.put("codvisitador", visitador);
        valores.put("alterado", alterado);

        db.insert("medico", null, valores);
        db.close();

    }

    public void inserirMedicoAgVisitas(String pfcrm, String ufcrm,
                                       String nrcrm, String dias, String horarios, String obs) {
        SQLiteDatabase db = data.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("pfcrm", pfcrm);
        valores.put("ufcrm", ufcrm);
        valores.put("dias", dias);
        valores.put("horarios", horarios);
        valores.put("obs", obs);
        valores.put("nrcrm", nrcrm);

        db.insert("medico_agvisitas", null, valores);
        db.close();

    }

    public void inserirMedicoEndereco(String pfcrm, String ufcrm, String nrcrm,
                                      String tipo, String cep, String endereco, String fone,
                                      String email1, String email2, String pvisitar) {
        SQLiteDatabase db = data.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("pfcrm", pfcrm);
        valores.put("ufcrm", ufcrm);
        valores.put("tipo", tipo);
        valores.put("cep", cep);
        valores.put("endereco", endereco);
        valores.put("nrcrm", nrcrm);
        valores.put("fone", fone);
        valores.put("email1", email1);
        valores.put("email2", email2);
        valores.put("pvisitar", pvisitar);

        db.insert("medico_enderecos", null, valores);
        db.close();

    }

    public void inserirMedicoEspecialidades(String pfcrm, String ufcrm,
                                            String nrcrm, String cdesp, String itemid, String pvisitar) {
        SQLiteDatabase db = data.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("pfcrm", pfcrm);
        valores.put("ufcrm", ufcrm);
        valores.put("cdesp", cdesp);
        valores.put("pvisitar", pvisitar);
        valores.put("nrcrm", nrcrm);
        valores.put("itemid", itemid);

        db.insert("medico_especialidades", null, valores);
        db.close();

    }

    public void inserirMedicoHisdivulgacao(String pfcrm, String ufcrm,
                                           String nrcrm, String dthr, String divulgacao, String codVisitador) {
        SQLiteDatabase db = data.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("pfcrm", pfcrm);
        valores.put("ufcrm", ufcrm);
        valores.put("divulgacao", divulgacao);
        valores.put("dthr", dthr);
        valores.put("nrcrm", nrcrm);
        valores.put("codvishist", codVisitador);

        db.insert("medico_hisdivulgacao", null, valores);
        db.close();

    }

    public Boolean inserirMaterial(String material, String crm, String pf,
                                   String uf, String dt, String codVisitador) {
        Boolean verifica;

        try {
            SQLiteDatabase db = data.getWritableDatabase();
            ContentValues valores = new ContentValues();

            valores.put("pfcrm", pf);
            valores.put("ufcrm", uf);
            valores.put("nrcrm", crm);
            valores.put("dthr", dt);
            valores.put("divulgacao", material);
            valores.put("codvishist", codVisitador);

            db.insert("medico_hisdivulgacao", null, valores);
            verifica = true;
            db.close();

        } catch (Exception e) {
            verifica = false;
        }

        return verifica;

    }

    public boolean inserirHorarios(String hora, String obs, String pf,
                                   String uf, String crm, String dia, String altera) {
        SQLiteDatabase db = data.getWritableDatabase();
        String query = "select * from medico_agvisitas where pfcrm = '" + pf
                + "' and ufcrm = '" + uf + "' and nrcrm = " + crm
                + " and dias = '" + dia + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            return false;
        } else {
            ContentValues valores = new ContentValues();
            ContentValues valores2 = new ContentValues();

            valores.put("pfcrm", pf);
            valores.put("ufcrm", uf);
            valores.put("nrcrm", crm);
            valores.put("dias", dia);
            valores.put("horarios", hora);
            valores.put("obs", obs);
            valores2.put("alterado", altera);

            db.insert("medico_agvisitas", null, valores);
            db.update("medico", valores2,
                    "pfcrm = ? AND ufcrm = ? AND nrcrm = ?", new String[]{pf,
                            uf, crm});
            db.close();

            return true;
        }

    }

    public boolean updateHorarios(String hora, String obs, String pf,
                                  String uf, String crm, String diaAntigo, String diaNovo,
                                  String altera) {

        try {
            SQLiteDatabase db = data.getWritableDatabase();

            ContentValues valores = new ContentValues();
            ContentValues valores2 = new ContentValues();

            valores.put("dias", diaNovo);
            valores.put("horarios", hora);
            valores.put("obs", obs);
            valores2.put("alterado", altera);

            db.update("medico_agvisitas", valores,
                    String.format("pfcrm = ? AND ufcrm = ? AND nrcrm = ? AND dias = ?"),
                    new String[]{pf, uf, crm, diaAntigo});
            db.update("medico", valores2,
                    "pfcrm = ? AND ufcrm = ? AND nrcrm = ?", new String[]{pf,
                            uf, crm});

            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean excluirHorario(String pf, String uf, String crm, String dia,
                                  String altera) {
        SQLiteDatabase db = data.getWritableDatabase();

        try {
            ContentValues valores = new ContentValues();

            valores.put("alterado", altera);
            db.delete("medico_agvisitas",
                    "pfcrm = ? AND ufcrm = ? AND nrcrm = ? AND dias = ?",
                    new String[]{pf, uf, crm, dia});
            db.update("medico", valores,
                    "pfcrm = ? AND ufcrm = ? AND nrcrm = ?", new String[]{pf,
                            uf, crm});

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public void buscarAgenda(String dia) {
        SQLiteDatabase db = data.getReadableDatabase();
        String query = "select null _id, * from medico as m inner join medico_agvisitas as mg "
                + "on m.nrcrm = mg.nrcrm and m.pfcrm = mg.pfcrm and m.ufcrm = mg.ufcrm "
                + "inner join medico_enderecos as me on m.nrcrm = me.nrcrm and m.pfcrm = me.pfcrm and m.ufcrm = me.ufcrm "
                + "inner join medico_especialidades as mesp on m.nrcrm = mesp.nrcrm and m.pfcrm = mesp.pfcrm and m.ufcrm = mesp.ufcrm "
                + "inner join especialidades as esp on esp.cdesp = mesp.cdesp where me.pvisitar = 'S' and mesp.pvisitar = 'S' "
                + "and m.codvisitador = "
                + codvisitador
                + " and dias = '"
                + dia + "' order by nomemed";

        cursorAgenda = db.rawQuery(query, null);

    }

    public void buscarAgendaFiltros(String dia, String med, String esp,
                                    String end, String hora) {
        SQLiteDatabase db = data.getReadableDatabase();
        String query = "select null _id, * from medico as m inner join medico_agvisitas as mg "
                + "on m.nrcrm = mg.nrcrm and m.pfcrm = mg.pfcrm and m.ufcrm = mg.ufcrm "
                + "inner join medico_enderecos as me on m.nrcrm = me.nrcrm and m.pfcrm = me.pfcrm and m.ufcrm = me.ufcrm "
                + "inner join medico_especialidades as mesp on m.nrcrm = mesp.nrcrm and m.pfcrm = mesp.pfcrm and m.ufcrm = mesp.ufcrm "
                + "inner join especialidades as esp on esp.cdesp = mesp.cdesp where me.pvisitar = 'S' and mesp.pvisitar = 'S' "
                + "and m.codvisitador = "
                + codvisitador
                + " and nomemed like '%"
                + med
                + "%' and endereco like '%"
                + end
                + "%' "
                + "and especialidade like '%"
                + esp
                + "%' and dias = '"
                + dia
                + "' and horarios like '%"
                + hora
                + "%' order by nomemed";

        cursorAgenda = db.rawQuery(query, null);

    }

    public void buscarAgendaMedico(String pf, String uf, String crm) {
        SQLiteDatabase db = data.getReadableDatabase();
        String query = "select null _id, dias, horarios, obs from medico_agvisitas "
                + "where pfcrm = '"
                + pf
                + "' and ufcrm = '"
                + uf
                + "' and nrcrm = '" + crm + "'";

        cursorAgenda = db.rawQuery(query, null);

    }

    public void buscarHorarios(String crm, String uf, String pf, String dia) {
        SQLiteDatabase db = data.getReadableDatabase();
        String query = "select * from medico_agvisitas " + "where pfcrm = '"
                + pf + "' and ufcrm = '" + uf + "' and nrcrm = '" + crm
                + "' and dias = '" + dia + "'";

        cursorHora = db.rawQuery(query, null);
    }

    public void buscarMaterial(String crm, String uf, String pf) {
        SQLiteDatabase db = data.getReadableDatabase();
        String query = "select null _id, strftime ('%d/%m/%Y %H:%m', datetime([dthr])),"
                + "divulgacao from medico_hisdivulgacao "
                + "where nrcrm = '" + crm + "' and ufcrm = '" + uf
                + "' and pfcrm = '" + pf + "' order by dthr desc";

        cursorMaterial = db.rawQuery(query, null);

    }

    public void buscarMedico(String nome) {
        SQLiteDatabase db = data.getReadableDatabase();
        String query = "select null _id, * from medico as m "
                + "inner join medico_enderecos as me on m.nrcrm = me.nrcrm and m.pfcrm = me.pfcrm and m.ufcrm = me.ufcrm "
                + "inner join medico_especialidades as mesp on m.nrcrm = mesp.nrcrm and m.pfcrm = mesp.pfcrm and m.ufcrm = mesp.ufcrm "
                + "inner join especialidades as esp on esp.cdesp = mesp.cdesp "
                + "where me.pvisitar = 'S' and mesp.pvisitar = 'S' and m.codvisitador = "
                + codvisitador + "" + " and (nomemed like '%" + nome
                + "%' or m.nrcrm = '" + nome + "') order by nomemed";

        cursorMedico = db.rawQuery(query, null);

    }

    public void exportaDivulgacao() {
        SQLiteDatabase db = data.getReadableDatabase();
        String query = "select * from medico_hisdivulgacao as mg inner join medico as m "
                + "on mg.pfcrm = m.pfcrm and mg.ufcrm = m.ufcrm and mg.nrcrm = m.nrcrm "
                + "where m.codvisitador = " + codvisitador;

        cursorExporta = db.rawQuery(query, null);
    }

    public void exportaHorarioVisita() {
        SQLiteDatabase db = data.getReadableDatabase();
        String query = "select * from medico_agvisitas as mg inner join medico as m "
                + "on mg.pfcrm = m.pfcrm and mg.ufcrm = m.ufcrm and mg.nrcrm = m.nrcrm "
                + "where m.codvisitador = "
                + codvisitador
                + " and m.alterado = 'S'";

        cursorExporta2 = db.rawQuery(query, null);
    }
}
