package com.naseyun.computer.ahchacha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ImageView imageView;
    private int Co2=1500;
    Chronometer chronometer;
    //ToggleButton toggleButton;
    ImageButton btn1;
    ImageButton btn2;

    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et=(EditText)findViewById(R.id.et);

        imageView=(ImageView)findViewById(R.id.imageView);
        //toggleButton=(ToggleButton)findViewById(R.id.toggleButton);
        chronometer=(Chronometer)findViewById(R.id.chronometer);
        btn1=(ImageButton)findViewById(R.id.btn1);
        btn2=(ImageButton)findViewById(R.id.btn2);


        /*
        toggleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (toggleButton.isChecked()) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    toggleButton.setBackgroundDrawable(
                            getResources().
                                    getDrawable(R.drawable.stop)
                    );

                }else{
                    chronometer.stop();

                    toggleButton.setBackgroundDrawable(
                            getResources().
                                    getDrawable(R.drawable.start)
                    );
                }
            }
        });
         */
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlertActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                startActivity(intent);
            }
        });

    }

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 5*1000; //5초간 delay
    @Override
    protected void onResume() {
        //액티비티보이면 핸들러 start
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                setImage(Co2);
                handler.postDelayed(runnable, delay);
            }
        }, delay);
        chronometer.start();//chronometer start
        super.onResume();
    }


    public void mOnClick(View v){//co2값에 따라 이미지 바뀌는지 확인
        try {
            String co = et.getText().toString().trim();
            Co2 = Integer.parseInt(co);
            Toast.makeText(this, "Co2 : "+Co2, Toast.LENGTH_SHORT).show();
        } catch(NumberFormatException e){
            Toast.makeText(this, "숫자만 입력하세요", Toast.LENGTH_SHORT).show();
        }
    }

    public void setImage(int Co2){//co2값에 따라 이미지 변경
        Log.d(TAG,"setImage실행");
        if(Co2<=1000){
            imageView.setImageResource(R.drawable.stat_good);
        }
        else if(Co2>1000&&Co2<=2000){
            imageView.setImageResource(R.drawable.stat_normal);
        }
        else{
            imageView.setImageResource(R.drawable.stat_bad);
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG,"핸들러 일시 정지");
        handler.removeCallbacks(runnable); //액티비티가 보이지 않으면 핸들러 stop
        super.onPause();
    }
}
