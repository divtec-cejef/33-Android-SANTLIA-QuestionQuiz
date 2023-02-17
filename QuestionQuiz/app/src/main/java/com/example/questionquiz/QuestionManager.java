package com.example.questionquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.style.QuoteSpan;

import com.example.Models.QuestionQuizSQLite;
import com.example.questionquiz.Question;

import java.util.ArrayList;

public class QuestionManager {
    private ArrayList<Question> QuestionList;
    private int index = 0;


    /***
     * Constructeur qui prend en paramètre un contexte
     * @param context Contexte de l'application, soit "this"
     */
    public QuestionManager(Context context) {
        QuestionList = initQuestionList(context);
    }

    /***
     * Retourne la liste de question
     * @return liste de question
     */
    public ArrayList<Question> getQuestionList() {
        return QuestionList;
    }

    /***
     * Retourne l'index pour la liste de question
     * @return L'index pour la liste de question
     */
    public int getIndex() {
        return index;
    }

    /***
     * Permet de définir l'index pour la liste de question
     * @param index Index pour la liste de question
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /***
     * Permet de définir la liste de question
     * @param questionList Liste de question
     */
    public void setQuestionList(ArrayList<Question> questionList) {
        this.QuestionList = questionList;
    }

    /***
     * Permet d'incrémenter l'index de la question
     */
    public void nextQuestion(){
        index++;
    }

    /***
     * Retourne une instance de type "Question"
     * @return Instance de type Question
     */
    public Question getQuestion(){
        return QuestionList.get(index);
    }

    /***
     * Permet d'ajouter une instance de type "Question" à la liste de question
     * @param question Instance de type "Question"
     */
    public void ajouterQuestionListe(Question question){
        QuestionList.add(question);
    }

    /***
     * Initialise la liste de question en allant rechercher les questions
     * dans la base de données SQLite
     * @param context Contexte de l'application, soit "this"
     * @return La liste de question de la base de données sous forme d'ArrayList
     */
    private ArrayList<Question> initQuestionList(Context context){
        ArrayList<Question> initQuestion = new ArrayList<>();
        QuestionQuizSQLite helper = new QuestionQuizSQLite(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // paramètrage de la requête (args = "Where" par exemple)
        Cursor cursor = db.query(true,"quiz", new String[]{"idQuiz","question","reponse"},null,null, null,null,"idQuiz", null);

        // Boucle qui parcour tout le cursor (donc toute la table) et qui ajoute
        // les questions à la liste
        while(cursor.moveToNext()){
            initQuestion.add(new Question(cursor));
        }

        cursor.close();
        db.close();

        return initQuestion;
    }

}
