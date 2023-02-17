package com.example.questionquiz;

import android.database.Cursor;

public class Question {
    private String intitule;
    private int reponse;


    /***
     * Constructeur qui prend en paramètre un intitulé et une réponse
     * @param intitule Intitulé de la question
     * @param reponse Réponse de la question, 1 si vrai et 0 si faux
     */
    public Question(String intitule, int reponse){
        setIntitule(intitule);
        setReponse(reponse);
    }


    /***
     * Constructeur qui initialise une question avec les données
     * d'une colonne de la base de données
     * @param cursor Résultat d'une requête
     */
    public Question(Cursor cursor){
        intitule = cursor.getString(cursor.getColumnIndexOrThrow("question"));
        reponse = cursor.getInt(cursor.getColumnIndexOrThrow("reponse"));
    }

    /***
     * Retourne l'intitulé de la question
     * @return L'intitulé
     */
    public String getIntitule(){
        return intitule;
    }

    /***
     * Retourne la réponse de la question
     * @return la réponse
     */
    public int getReponse(){
        return reponse;
    }

    /***
     * Permet de définir l'intitulé de la question
     * @param newIntitule Nouvel intitulé de la question
     */
    public void setIntitule(String newIntitule){
        this.intitule = newIntitule;
    }

    /***
     * Permet de définir la réponse de la question
     * @param newReponse Nouvelle réponse de la question
     */
    public void setReponse(int newReponse){
        this.reponse = newReponse;
    }
}
