package com.example.questionquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextDirectionHeuristic;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Models.QuestionQuizSQLite;
import com.google.android.material.slider.Slider;

import java.time.DayOfWeek;

public class ParametreActivity extends AppCompatActivity {

    // initialise tous les objets
    private EditText param_saisie_intitule;
    private RadioButton param_reponse_vrai;
    private RadioButton param_reponse_faux;
    private RadioGroup param_reponse;
    private Button param_btn_ajouter;
    private Button param_btn_quitter;
    private Slider param_slider;
    private QuestionManager questionManager;
    private int reponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);

        //récupère l'id des objets
        param_saisie_intitule = findViewById(R.id.param_saisie_intitule);
        param_reponse_vrai = findViewById(R.id.param_reponse_vrai);
        param_reponse_faux = findViewById(R.id.param_reponse_faux);
        param_btn_ajouter = findViewById(R.id.param_btn_ajouter);
        param_reponse = findViewById(R.id.param_reponse);
        param_slider = findViewById(R.id.param_slider);
        param_btn_quitter = findViewById(R.id.param_btn_quitter);

        int reponse = 0;

        questionManager = new QuestionManager(this);

        param_btn_ajouter.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();

            param_saisie_intitule.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    ajouteQuestionEstValide();
                    System.out.println(String.valueOf(param_reponse.isActivated()));
                }
            });

            param_reponse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    ajouteQuestionEstValide();
                }
            });

            param_btn_ajouter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String intitule = param_saisie_intitule.getText().toString();
                    Question newQuestion = new Question(intitule, reponse);

                    ajouterQuestion(newQuestion);
                    questionManager.ajouterQuestionListe(newQuestion);

                    param_saisie_intitule.getText().clear();
                    param_reponse.clearCheck();
                }
            });

            param_slider.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    jeu jeu = new jeu();
                    float sliderValue = param_slider.getValue() * 1000;
                    jeu.TEMP_QUESTION = (long) sliderValue;
                }
            });

            param_btn_quitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mainActivity = new Intent(ParametreActivity.this, MainActivity.class);
                    startActivity(mainActivity);
                }
            });

    }

    public void ajouteQuestionEstValide(){
        if (param_saisie_intitule.getText().toString().isEmpty() ||
                (param_reponse_vrai.isChecked() == false && param_reponse_faux.isChecked() == false)){
            param_btn_ajouter.setEnabled(false);
        } else {
            param_btn_ajouter.setEnabled(true);
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.param_reponse_vrai:
                if (param_reponse_vrai.isChecked())
                    reponse = 1;
            case R.id.param_reponse_faux:
                if (param_reponse_faux.isChecked())
                    reponse = 0;
        }
    }

    public void ajouterQuestion(Question question){
        // delcare notre classe de la db quon a fait
        QuestionQuizSQLite helper = new QuestionQuizSQLite(this);
        // declare la bibliothuque de trvail et la met en LECTURE SEULE
        SQLiteDatabase db = helper.getWritableDatabase();
        int tailleListe = questionManager.getQuestionList().size() + 1;
        int reponse = question.getReponse();
        db.execSQL("INSERT INTO quiz VALUES(\"" + tailleListe + "\", \"" + question.getIntitule() + "\", \"" + reponse + "\")");
        Toast toast = Toast.makeText(getApplicationContext(), "La question est ajoutée", Toast.LENGTH_SHORT);
        toast.show();
        db.close();
    }

}