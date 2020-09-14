package com.naseyun.computer.ahchacha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    private ListView listview;
    private ImageButton addBtn;
    private QuizAdapter adapter;
    private Context context = this;
    private CustomDialog customDialog;
    private String quiz;
    private String answer;
    ArrayList<ListViewItem> Quiz = new ArrayList<ListViewItem>();
    ArrayList<ListViewItem> items = new ArrayList<>();
    private int count = 0;
    private int quizId = 0;

    DatabaseReference rootDatabaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //default Quiz값
        ListViewItem quiz1 = new ListViewItem("0", "5 더하기 7은 무엇입니까?", "12");
        ListViewItem quiz2 = new ListViewItem("1","내 이름은 무엇입니까?", "나세윤");
        ListViewItem quiz3 = new ListViewItem("2","6 곱하기 9는 무엇입니까?", "54");

        rootDatabaseReference.child("quizList").child("quiz" + quizId).setValue(quiz1);
        rootDatabaseReference.child("quizList").child("quiz" + ++quizId).setValue(quiz2);
        rootDatabaseReference.child("quizList").child("quiz" + ++quizId).setValue(quiz3);
        count = quizId;

        adapter = new QuizAdapter(Quiz); //Adapter생성
        listview = findViewById(R.id.listview_quiz);
        addBtn = findViewById(R.id.addBtn);
        listview.setAdapter(adapter);

        rootDatabaseReference.child("quizList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ListViewItem item = dataSnapshot.getValue(ListViewItem.class);
                Quiz.add(item);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ListViewItem item = dataSnapshot.getValue(ListViewItem.class);
                //Quiz.remove(item.getQuizId());

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addBtn.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                //dialog
                customDialog = new CustomDialog(QuizActivity.this);
                customDialog.show();
                customDialog.setCanceledOnTouchOutside(true); //다이얼로그 밖 눌렀을 때 사라지게
                customDialog.setOnDismissListener((DialogInterface.OnDismissListener)context);
            }
        });

        /*if(getIntent().hasExtra("quizList")) { //intent의 값이 넘어오면
            Quiz = getIntent().getParcelableArrayListExtra("quizList");
            SaveQuiz(Quiz);
            Log.v("seyuuuun", "intent성공!");
        }*/

        //변경 알림
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        quiz = customDialog.getWriteQuiz();
        answer = customDialog.getWriteAnswer();
        //count = ++count;

        if(quiz.equals("") != true && answer.equals("") != true) {
            ListViewItem item = new ListViewItem(Integer.toString(++count), quiz, answer);
            rootDatabaseReference.child("quizList").child("quiz"+count).setValue(item);
            adapter.notifyDataSetChanged();
        }
    }

    /*public void setFirebaseDatabase(String quiz, String answer) {
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        ListViewItem item = new ListViewItem(quiz, answer);
        postValues = item.toMap();
        childUpdates.put("/quiz_list/" + quiz, postValues);
        rootDatabaseReference.updateChildren(childUpdates);
    }*/

    @Override
    public void onStart() {
        super.onStart();
    }

}

