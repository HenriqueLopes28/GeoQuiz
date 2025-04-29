package com.example.geoquiz_v4_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class QuestaoDB {

    private Context mContext;
    private static Context mStaticContext;
    private SQLiteDatabase mDatabase;

    public QuestaoDB(Context contexto){
        mContext = contexto.getApplicationContext();
        mStaticContext = mContext;
        mDatabase = new QuestoesDBHelper(mContext).getWritableDatabase();
    }
    private static ContentValues getValoresConteudo(Questao q, boolean resposta, boolean colador){
        ContentValues valores = new ContentValues();
        boolean respondeu = resposta == q.isRespostaCorreta();

        // pares chave-valor: nomes das colunas - valores
        valores.put(QuestoesDbSchema.QuestoesTbl.Cols.UUID, q.getId().toString());
        valores.put(QuestoesDbSchema.QuestoesTbl.Cols.COLOU, colador);
        valores.put(QuestoesDbSchema.QuestoesTbl.Cols.TEXTO_QUESTAO, mStaticContext.getString(q.getTextoRespostaId()));
        valores.put(QuestoesDbSchema.QuestoesTbl.Cols.QUESTAO_CORRETA, respondeu);

        return valores;
    }
    public void addQuestao(Questao q, boolean resposta, boolean colador){

        ContentValues valores = getValoresConteudo(q, resposta, colador);
        Log.i("db", valores.toString());
        mDatabase.insert(QuestoesDbSchema.QuestoesTbl.NOME, null, valores);
    }
    public void updateQuestao(Questao q){
        String uuidString = q.getId().toString();
        ContentValues valores = getValoresConteudo(q, false, false);
        // mDatabase.update(QuestoesDbSchema.QuestoesTbl.NOME, valores, QuestoesDbSchema.QuestoesTbl.Cols.UUID +" = ?",
        //        new String[] {uuidString});
    }
    public Cursor queryQuestao(String clausulaWhere, String[] argsWhere){
        Cursor cursor = mDatabase.query(QuestoesDbSchema.QuestoesTbl.NOME,
                null,  // todas as colunas
                clausulaWhere,
                argsWhere,
                null, // sem group by
                null, // sem having
                null  // sem order by
        );
        return cursor;
    }
    void removeBanco(){
        int delete;
        delete = mDatabase.delete(
                QuestoesDbSchema.QuestoesTbl.NOME,
                null, null);
    }
}
