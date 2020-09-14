package com.naseyun.computer.ahchacha;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QuizAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> arrayList_quiz = new ArrayList<ListViewItem>();

    private Context context;
    private TextView textView_quiz;
    private TextView textView_answer;
    private ImageButton removeBtn;

    private int getPosition = 0;

    DatabaseReference rootDatabaseReference = FirebaseDatabase.getInstance().getReference();

    public QuizAdapter(ArrayList<ListViewItem> quizList) {
        arrayList_quiz = quizList;
    }

    @Override
    public int getCount() {
        return arrayList_quiz.size();
    }

    @Override
    public ListViewItem getItem(int position) {
        return arrayList_quiz.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        //ViewHolder
        context = viewGroup.getContext();

        getPosition = position;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_quiz, viewGroup, false);
        }
        textView_quiz = convertView.findViewById(R.id.quiz);
        textView_answer = convertView.findViewById(R.id.answer);
        removeBtn = convertView.findViewById(R.id.removeBtn);

        ListViewItem listViewItem = arrayList_quiz.get(position);

        final String quizId = listViewItem.getQuizId();
        textView_quiz.setText(listViewItem.getQuiz());
        textView_answer.setText(listViewItem.getAnswer());

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = getCount();

                if(count > 0) {
                    if(position > -1 && position < count) {
                        rootDatabaseReference.child("quizList").child("quiz" + quizId).removeValue();
                        arrayList_quiz.remove(position);
                        Log.v("seyuuuun", String.valueOf(position));
                        Log.v("seyuuuun", "remove position: " + quizId);
                        notifyDataSetChanged();
                    }
                }
            }
        });

        return convertView;
    }

    public int getPosition() {
        return getPosition;
    }

}
