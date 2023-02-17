package com.example.questionquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.transform.Result;

public class jeu extends AppCompatActivity {
    public static long TEMP_QUESTION = 1000;

    // initialise tous les objets
    private TextView nomJoueur1;
    private TextView nomJoueur2;
    private TextView textJoueur1;
    private TextView textJoueur2;
    private Button btnJoueur1;
    private Button btnJoueur2;
    private TextView scoreJoueur1;
    private TextView scoreJoueur2;
    private Button BT_Menu;
    private Button BT_Rejouer;

    QuestionManager questionManager;
    Runnable questionRunnable = null;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        // récupère l'id des objets
        nomJoueur1 = findViewById(R.id.jeu_nomjoueur1);
        nomJoueur2 = findViewById(R.id.jeu_nomjoueur2);
        textJoueur1 = findViewById(R.id.jeu_text_joueur1);
        textJoueur2 = findViewById(R.id.jeu_text_joueur2);
        btnJoueur1 = findViewById(R.id.jeu_btn_joueur1);
        btnJoueur2 = findViewById(R.id.jeu_btn_joueur2);
        scoreJoueur1 = findViewById(R.id.scoreJoueur1);
        scoreJoueur2 = findViewById(R.id.scoreJoueur2);
        BT_Menu = findViewById(R.id.jeu_btn_menu);
        BT_Rejouer = findViewById(R.id.jeu_btn_rejouer);

        // permet de récupérer le nom des deux joueurs saisie
        // dans l'accueil
        Intent jeuActivity = getIntent();
        String joueur1 = String.valueOf(jeuActivity.getStringExtra("joueur1"));
        String joueur2 = String.valueOf(jeuActivity.getStringExtra("joueur2"));

        nomJoueur1.setText(joueur1);
        nomJoueur2.setText(joueur2);
        questionManager = new QuestionManager(this);

        setVisibilityBoutonFinDeJeu(View.INVISIBLE);
        setEnabledBoutonJoueur(false);

        // lance le compte à rebours
        startCoutDownTimer();
    }

    @Override
    protected void onStart() {
        super.onStart();

        BT_Rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // relance le jeu de question
                startCoutDownTimer();
                questionManager.setIndex(0);
                resetScoreJoueur(0);
                setVisibilityBoutonFinDeJeu(View.INVISIBLE);
                setVisibilityTextJoueur(View.VISIBLE);
            }
        });

        BT_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // revient à l'activité "Accueil"
                Intent mainActivity = new Intent(jeu.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });

        btnJoueur1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifierScore(scoreJoueur1);
            }
        });

        btnJoueur2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifierScore(scoreJoueur2);
            }
        });

    }

    /***
     * Modifie le score (TextView !) d'un joueur passé en paramètre
     * Si la réponse est vrai alors on rajoute un point sinon on enlève
     * un point
     * @param scoreJoueur TextView, score du joueur
     */
    private void modifierScore(TextView scoreJoueur){
        int score = Integer.parseInt(scoreJoueur.getText().toString());
        if(questionManager.getQuestion().getReponse() == 1){
            score += 1;
        } else {
            score -= 1;
        }
        String scoreAsString = "" + score;
        scoreJoueur.setText(scoreAsString);

        setEnabledBoutonJoueur(false);
    }


    /***
     * Enchaine les question selon le TIMER choisi par le slider
     */
    private void startQuestionIterative(){
        handler = new Handler();
        questionRunnable = new Runnable() {
            @Override
            public void run() {
                // si la liste est vide alors c'est la fin de partie
                // sinon on exécute le jeu de question
                if(questionManager.getQuestionList().isEmpty()){
                    //Executer le code de fin de partie
                    handler.removeCallbacks(this);
                } else {
                    // si on se trouve pas à la dernière question on incrémente
                    // l'index sinon on affiche le message de fin
                    if (questionManager.getIndex() != questionManager.getQuestionList().size() - 1) {
                        questionManager.nextQuestion();
                    } else {
                        afficherMessageFin();
                        setVisibilityBoutonFinDeJeu(View.VISIBLE);
                        setEnabledBoutonJoueur(false);
                        return;
                    }
                    // récupère une question de la liste pour l'afficher aux joueurs
                    String question = String.valueOf(questionManager.getQuestion().getIntitule());
                    setTextJoueur(question);

                    handler.postDelayed(this, TEMP_QUESTION);

                }
                setEnabledBoutonJoueur(true);
            }
        };
        handler.postDelayed(questionRunnable, 1000);

    }

    /***
     * Démarre le compte à rebour pour ensuite, à la fin, lancer
     * l'itération de question
     */
    private void startCoutDownTimer() {
        new CountDownTimer(5000, 1000) {
            public void onTick(long milliUntilFinished) {
                String temps = String.valueOf(milliUntilFinished / 1000 + 1);
                setTextJoueur(temps);
            }

            public void onFinish() {
                //Afficher le départ à l'utilisateur
                setTextJoueur("C'est parti");
                startQuestionIterative();

            }
        }.start();

    }

    /***
     * Affiche le message de fin aux joueurs
     * Si gagné : Vous avez gagné
     * Si perdu : Vous avez perdu
     * Si égalité : égalité parfaite
     */
    public void afficherMessageFin(){
        int score1 = Integer.parseInt(scoreJoueur1.getText().toString());
        int score2 = Integer.parseInt(scoreJoueur2.getText().toString());
        if (score1 > score2){
            textJoueur1.setText("Vous avez gagné !");
            textJoueur2.setText("Vous avez perdu !");
        } else if (score1 < score2){
            textJoueur2.setText("Vous avez gagné");
            textJoueur1.setText("Vous avez perdu");
        } else {
            setTextJoueur("Egalité parfaite !");
        }

    }


    /***
     * Défini l'activition des longs boutons pour les 2 joueurs
     * @param etat Etat des boutons, True ou False
     */
    public void setEnabledBoutonJoueur(boolean etat){
        btnJoueur1.setEnabled(etat);
        btnJoueur2.setEnabled(etat);
    }

    /***
     * Défini la visibilité des TextView (pour les question et pour le compteur)
     * des 2 joueurs
     * @param etat Etat des TextView, True ou False
     */
    public void setVisibilityTextJoueur(int etat){
        textJoueur1.setVisibility(etat);
        textJoueur2.setVisibility(etat);
    }

    /***
     * Défini le texte des TextView (pour les question et pour le compteur) des 2 joueurs
     * @param text Texte à définir
     */
    public void setTextJoueur(String text){
        textJoueur1.setText(text);
        textJoueur2.setText(text);
    }

    /***
     * Défini la visibility des 2 boutons de fin de jeu, Menu et Rejoueur
     * @param etat Etat des boutons (int car View.VISIBLE est sous forme de int par exemple)
     */
    public void setVisibilityBoutonFinDeJeu(int etat){
        BT_Menu.setVisibility(etat);
        BT_Rejouer.setVisibility(etat);
    }

    /***
     * Reset le score des 2 joueurs
     * @param score Score à reset
     */
    public void resetScoreJoueur(int score){
        scoreJoueur1.setText(String.valueOf(score));
        scoreJoueur2.setText(String.valueOf(score));
    }
}