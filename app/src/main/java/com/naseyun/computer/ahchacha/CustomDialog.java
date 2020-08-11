package com.naseyun.computer.ahchacha;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;

public class CustomDialog extends Dialog {

    private Button OkBtn;
    private Button CancelBtn;
    private EditText Quiz;
    private EditText Answer;
    private Context context;
    private QuizAdapter adapter;
    private String writeQuiz;
    private String writeAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customdialog_quiz);

        //다이얼로그 밖의 화면 흐리게
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.2f;
        getWindow().setAttributes(layoutParams);

        adapter = new QuizAdapter();

        //setting
        OkBtn = findViewById(R.id.okBtn);
        CancelBtn = findViewById(R.id.cancelBtn);
        Quiz = findViewById(R.id.editText_quiz);
        Answer = findViewById(R.id.editText_answer);

        Quiz.setText("");
        Answer.setText("");

        OkBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addItem(getWriteQuiz(), getWriteAnswer());
                Log.v("seyuuuun", getWriteQuiz());
                Log.v("seyuuuun", getWriteAnswer());
                dismiss();
            }
        });

        CancelBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public String getWriteQuiz() {
        writeQuiz = Quiz.getText().toString();
        return writeQuiz;
    }

    public String getWriteAnswer() {
        writeAnswer = Answer.getText().toString();
        return writeAnswer;
    }
}
