package com.naseyun.computer.ahchacha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    private ListView listview;
    private ImageButton addBtn;
    private QuizAdapter adapter;
    private Context context = this;
    private CustomDialog customDialog;
    private String quiz;
    private String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        adapter = new QuizAdapter(); //Adapter생성
        listview = findViewById(R.id.listview_quiz);
        addBtn = findViewById(R.id.addBtn);
        listview.setAdapter(adapter);

        adapter.addItem("3+2는 무엇입니까?", "5");
        adapter.addItem("내 이름은  무엇입니까?", "나세윤");

        addBtn.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                //dialog
                customDialog = new CustomDialog(QuizActivity.this);
                customDialog.show();
                customDialog.setCanceledOnTouchOutside(true); //다이얼로그 밖 눌렀을 때 사라지게
                customDialog.setOnDismissListener((DialogInterface.OnDismissListener)context);
            }
        });

        //변경 알림
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        quiz = customDialog.getWriteQuiz();
        answer = customDialog.getWriteAnswer();

        if(quiz.equals("") != true &&  answer.equals("") != true) {
            adapter.addItem(quiz, answer);
        }
    }
}

