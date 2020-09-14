package com.naseyun.computer.ahchacha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final String TAG = "MainActivity";

    Chronometer chronometer;
    ImageButton alertBtn;
    ImageButton quizBtn;
    SpeechRecognizer mRecognizer;
    TextToSpeech tts;
    Intent sttIntent;

    static Context context;
    final int PERMISSION=1;
    TextView ttsbtn;
    private QuizAdapter adapter;
    ArrayList<ListViewItem> quizDatabaseList = new ArrayList<ListViewItem>();
    ArrayList<String> sttList = new ArrayList<String>();

    DatabaseReference rootDatabaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childDatabaseReference;
    int randomNum = 0;
    String answer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        chronometer=(Chronometer)findViewById(R.id.chronometer);
        alertBtn=(ImageButton)findViewById(R.id.alertBtn);
        quizBtn=(ImageButton)findViewById(R.id.quizBtn);
        ttsbtn = (TextView)findViewById(R.id.text2);

        alertBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlertActivity.class);
                startActivity(intent);
            }
        });

        quizBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                startActivity(intent);
            }
        });
        chronometer.start();

        //권한설정
        if(Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }
        speechInit();

        //"opencv결과 받아와서 일정치 이상이면"이라는 조건에 넣어야함!
        ttsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuizTest();
                /*Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speechStart();
                    }
                }, 2000);*/
            }
        });

    }

    public void QuizTest() {
        childDatabaseReference = rootDatabaseReference.child("quizList");
        childDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quizDatabaseList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ListViewItem item = snapshot.getValue(ListViewItem.class);
                    quizDatabaseList.add(item);
                }
                randomNum = (int)(Math.random() * quizDatabaseList.size());
                Log.v("seyuuuun", "난수 for문 : " + randomNum);
                funcVoiceOut(quizDatabaseList.get(randomNum).getQuiz());

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speechStart();
                    }
                }, 2000);

                Log.v("seyuuuun", "답 : " + quizDatabaseList.get(randomNum).getAnswer());
                Log.v("seyuuuun", "음성 인식 : " + answer);

                if(answer.equals(quizDatabaseList.get(randomNum).getAnswer())) {

                    Log.v("seyuuuun", "정답!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void speechInit() {
        sttIntent  = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        //tts 객체 생성, 초기화
        tts = new TextToSpeech(MainActivity.this, this);
    }

    public void speechStart() { //stt
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        mRecognizer.setRecognitionListener(listener);
        mRecognizer.startListening(sttIntent);
    }

    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) { //음성인식 준비완료
            Toast.makeText(getApplicationContext(), "음성인식을 시작합니다.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int error) {
            String message;
            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트워크 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을수없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버 에러";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간 초과";
                    break;
                default:
                    message = "알 수 없는 오류";
                    break;
            }
            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다 : " + message, Toast.LENGTH_SHORT).show();
            funcVoiceOut("에러가 발생하였습니다."); //tts
        }

        @Override
        public void onResults(Bundle result) { //음성받아오는
            ArrayList<String> matches = result.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            answer = matches.get(0);
            Toast.makeText(getApplicationContext(), answer, Toast.LENGTH_SHORT).show();

            //퀴즈답과 비교 필요!
        }

        @Override
        public void onPartialResults(Bundle bundle) {

        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    };

    public void funcVoiceOut(String OutMsg) { //tts
        if(OutMsg.length()<1)
            return;
        if(!tts.isSpeaking()) {
            tts.setSpeechRate(1f); //읽는 속도 기본 빠르기로 설정
            tts.speak(OutMsg, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.KOREA);
            tts.setPitch(1);
        } else {
            Log.e("seyun", "초기화실패");
        }
    }

    @Override
    protected void onDestroy() {
        if(tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
        if(mRecognizer != null) {
            mRecognizer.destroy();
            mRecognizer.cancel();
            mRecognizer = null;
        }
        super.onDestroy();
    }
}
