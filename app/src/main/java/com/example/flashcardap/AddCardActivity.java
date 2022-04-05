package com.example.flashcardap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddCardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        ImageView cancel_button = findViewById(R.id.cancel);

        ImageView save_button= findViewById(R.id.save_button);



        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText answer= findViewById(R.id.edit_answer);
                String ansTest= answer.getText().toString();
                String quesTest= ((EditText)findViewById(R.id.edit_question)).getText().toString();
                if (ansTest.length()>0 && quesTest.length()>0)
                {
                    Intent data= new Intent();
                    data.putExtra("Question",quesTest);
                    data.putExtra("Answer",ansTest);
                    setResult(RESULT_OK,data);

                }



                finish();

            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToMain();

            }
        });
    }
    public void GoToMain()
    {
       finish();
    }

}