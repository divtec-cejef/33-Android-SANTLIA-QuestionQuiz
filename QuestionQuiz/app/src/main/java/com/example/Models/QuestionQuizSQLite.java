package com.example.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class QuestionQuizSQLite extends SQLiteOpenHelper {
    static  String DB_NAME = "QuestionQuiz.db";
    static int DB_VERSION = 1;

    /***
     * Constructeur qui prend en paramètre un contexte
     * @param context Contexte de l'application, soit "this"
     */
    public QuestionQuizSQLite(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Va contenir tout ce qu'on veut qui soit executer,
     * LA PREMIERE FOIS QUE L'APP est lancé
     * @param db Base de données SQLite
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateDatabase = "CREATE TABLE quiz (idQuiz INTEGER PRIMARY KEY, question TEXTE, reponse INTEGER);";
        db.execSQL(sqlCreateDatabase);
        db.execSQL("INSERT INTO quiz VALUES(1, \"Est-ce que un serveur en atelier fait pas de bruit ?\",0)");
        db.execSQL("INSERT INTO quiz VALUES(2, \"Est-ce que Leo Messi a gagné tous les trophés possible ?\",1)");
        db.execSQL("INSERT INTO quiz VALUES(3, \"Est-ce la France est plus grande que la suisse ?\",1)");
        db.execSQL("INSERT INTO quiz VALUES(4, \"Est-ce Bitwarden n'est pas un gestionnaire de mot de passe\",0)");
        db.execSQL("INSERT INTO quiz VALUES(5, \"Peut on voir l'afrique depuis la suisse ?\",0)");
        db.execSQL("INSERT INTO quiz VALUES(6, \"La capitale l'Australie est Sydney ?\",1)");
        db.execSQL("INSERT INTO quiz VALUES(7, \"Est ce que l'EMT se situe vers le collège Stockmar ?\",0)");
        db.execSQL("INSERT INTO quiz VALUES(8, \"Est ce que tobias a fait 5,5 au module de m. Frein ?\",1)");
    }

    /**
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
