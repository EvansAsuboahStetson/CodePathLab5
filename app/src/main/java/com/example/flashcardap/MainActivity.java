package com.example.flashcardap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView flashCardQuestion;
    private TextView flashcardAnswer;
    private ImageView arrowNext;
    int currentCardDisplayedIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView toggle_choices_visibility=findViewById(R.id.toggle_choices_visibility);
        ImageView showIcon=findViewById(R.id.showIcon);
        final Boolean[] toggle = {false};


        flashCardQuestion= findViewById(R.id.flashcard_question);
        flashcardAnswer= findViewById(R.id.flashcard_answer);
        TextView firstOption=findViewById(R.id.First_Option);
        TextView secondOption=findViewById(R.id.Second_Option);
        TextView thirdOption=findViewById(R.id.Third_Option);



        ImageView addbutton= findViewById(R.id.addbutton);

        arrowNext= findViewById(R.id.arrow);




        flashCardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashCardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);
            }
        });
        flashcardAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashCardQuestion.setVisibility(View.VISIBLE);
                flashcardAnswer.setVisibility(View.INVISIBLE);

            }
        });
        firstOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstOption.setBackgroundColor(getResources().getColor(R.color.red));
                thirdOption.setBackgroundColor(getResources().getColor(R.color.green));
            }
        });
        thirdOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thirdOption.setBackgroundColor(getResources().getColor(R.color.green));
            }
        });
        secondOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondOption.setBackgroundColor(getResources().getColor(R.color.red));
                thirdOption.setBackgroundColor(getResources().getColor(R.color.green));


            }
        });
        toggle_choices_visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle[0] =false;
                Log.d("success", String.valueOf(toggle[0]));
                showIcon.setVisibility(View.VISIBLE);
                toggle_choices_visibility.setVisibility(View.INVISIBLE);
                firstOption.setVisibility(View.INVISIBLE);
                secondOption.setVisibility(View.INVISIBLE);
                thirdOption.setVisibility(View.INVISIBLE);



            }
        });
        showIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle[0] =true;
                Log.d("success", String.valueOf(toggle[0]));
                showIcon.setVisibility(View.INVISIBLE);
                toggle_choices_visibility.setVisibility(View.VISIBLE);
                firstOption.setVisibility(View.VISIBLE);
                secondOption.setVisibility(View.VISIBLE);
                thirdOption.setVisibility(View.VISIBLE);
            }
        });
        arrowNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (allFlashcards.size() == 0)
                    return;
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if(currentCardDisplayedIndex >= allFlashcards.size()) {
                    Snackbar.make(flashCardQuestion,
                            "You've reached the end of the cards, going back to start.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                allFlashcards = flashcardDatabase.getAllCards();
                Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                ((TextView) findViewById(R.id.flashcard_question)).setText(flashcard.getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(flashcard.getAnswer());

            }
        });
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_activity();
            }
        });
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();


        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

    }

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;




    public void add_activity()
    {
        Intent intent= new Intent(MainActivity.this,AddCardActivity.class);
        MainActivity.this.startActivityForResult(intent,100);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && resultCode==RESULT_OK)
        {
            String question_data= data.getExtras().getString("Question");
            String answer_data= data.getExtras().getString("Answer");
            flashCardQuestion.setText(question_data);
            flashcardAnswer.setText(answer_data);

            flashcardDatabase.insertCard(new Flashcard(question_data, answer_data));
            allFlashcards = flashcardDatabase.getAllCards();


            System.out.println(question_data);
            System.out.println(answer_data);
        }
    }
}