package com.naseyun.computer.ahchacha;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> arrayList_quiz = new ArrayList<ListViewItem>();
    private Context context;
    private TextView textView_quiz;
    private TextView textView_answer;
    private ImageButton removeBtn;

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

    public void addItem(String quiz, String answer) { //quiz추가
        ListViewItem item = new ListViewItem();

        item.setQuiz(quiz);
        item.setAnswer(answer);

        arrayList_quiz.add(item);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        //ViewHolder
        context = viewGroup.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_quiz, viewGroup, false);
        }
        textView_quiz = convertView.findViewById(R.id.quiz);
        textView_answer = convertView.findViewById(R.id.answer);
        removeBtn = convertView.findViewById(R.id.removeBtn);

        ListViewItem listViewItem = arrayList_quiz.get(position);
        textView_quiz.setText(listViewItem.getQuiz());
        textView_answer.setText(listViewItem.getAnswer());

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = getCount();

                if(count > 0) {
                    if(position > -1 && position < count) {
                        arrayList_quiz.remove(position);
                        Log.v("seyuuuun", String.valueOf(position));
                        notifyDataSetChanged();
                    }
                }
            }
        });


        return convertView;
    }
}
