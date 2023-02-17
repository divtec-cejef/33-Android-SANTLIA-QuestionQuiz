package com.example.questionquiz;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText ET_saisieJoueur1;
    private EditText ET_saisieJoueur2;
    private Button BT_Start;
    QuestionManager questionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mainToolBar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolBar);

        BT_Start = findViewById(R.id.main_btn_start);
        BT_Start.setEnabled(false);
        questionManager = new QuestionManager(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        ET_saisieJoueur1 = findViewById(R.id.main_joueur1_tf);
        ET_saisieJoueur2 = findViewById(R.id.main_joueur2_tf);

        ET_saisieJoueur1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkUserName(ET_saisieJoueur1.getText().toString(), ET_saisieJoueur2.getText().toString());
            }
        });

        ET_saisieJoueur2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkUserName(ET_saisieJoueur1.getText().toString(), ET_saisieJoueur2.getText().toString());
            }
        });


        BT_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Créé une nouvelle instance de Jeu pour lancer cette activité
                // + contient les noms des 2 joueurs
                Intent jeuActivity = new Intent(MainActivity.this, jeu.class);
                jeuActivity.putExtra("joueur1",ET_saisieJoueur1.getText().toString());
                jeuActivity.putExtra("joueur2",ET_saisieJoueur2.getText().toString());
                startActivity(jeuActivity);

            }
        });

    }


    /***
     * Check si les valeur passé en paramètre ne sont pas null,
     * donc contrôle si les champs de saisie sont remplis pour
     * les DEUX joueurs sinon on désactive le bouton start
     * @param joueur1 Nom du joueur 1
     * @param joueur2 Nom du joueur 2
     */
    public void checkUserName(String joueur1, String joueur2){
        if (!joueur2.isEmpty() && !joueur1.isEmpty()){
            BT_Start.setEnabled(true);
        } else {
            BT_Start.setEnabled(false);
        }
    }

    /***
     * Reset les champs de saisie, désactive le bouton Start
     * et remet le focus sur la saisie du joueur 1
     */
    public void resetFields(){
        ET_saisieJoueur1.getText().clear();
        ET_saisieJoueur2.getText().clear();
        BT_Start.setEnabled(false);
        ET_saisieJoueur1.requestFocus();
    }

    /***
     * Créer un menu Inflater pour les options du menu
     * @param menu Menu de l'activité principal
     * @return Vrai (permet de prendre une valeur en paramètre sans
     * utilisé VOID)
     */
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    /***
     * Affiche une alert dialog qui contient la version de l'application,
     * l'auteur et la descriptions
     */
    public void aboutAlertDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("A propos de");
        alert.setMessage("Créée par Liam Santin" +
                "\n\nQuiz entre 2 personnes" +
                "\n\nVersion : 1.0");
        alert.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                closeOptionsMenu();
            }
        });
        alert.show();
    }

    /***
     * Permet d'effectuer les fonctions par rapport au menu cliqué
     * @param item Item du menu (options)
     * @return True (permet de prendre une valeur en paramètre, sans utiliser VOID)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_delete:
                resetFields();
                return true;
            case R.id.menu_settings:
                Intent paramActivtiy = new Intent(MainActivity.this, ParametreActivity.class);
                startActivity(paramActivtiy);
                return true;
            case R.id.menu_about:
                aboutAlertDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}