package com.example.kevin.limtreasurehunt;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.media.MediaRecorder;
import android.speech.SpeechRecognizer;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    private MediaRecorder mmediaRecorder;
    private SpeechRecognizer sr;
    Button speechButton;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private String recordAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter != null && nfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC Enabled!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "NFC NOT ENABLED!", Toast.LENGTH_LONG).show();
        }
    }

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Simply say your answer and wait");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //speechButton.setText(result.get(0));
                    recordAnswer = result.get(0);
                    boolean valid = checkAnswer();
                    if (valid == true) {
                        //goToDefault(View v);
                        /*setContentView(R.layout.default_screen);
                        prevPage = R.layout.default_screen;
                        ProgressBar progressBar = findViewById(R.id.progressBar);
                        progressBar.setIndeterminate(false);
                        progressBar.setMax(100);
                        progressBar.setProgress(getProgressBarProgress());*/

                        ProgressBar progressBar = findViewById(R.id.progressBar);
                        int progress = getProgressBarProgress();
                        if(progress == 100){
                            setContentView(R.layout.congrats);
                            TextView name = (TextView) findViewById(R.id.textView2);
                            progressBar.setMax(100);
                            CharSequence newString = team;
                            name.setText(newString);
                        }
                        else {
                            setContentView(R.layout.default_screen);
                            ProgressBar progressBar1 = findViewById(R.id.progressBar);
                            progressBar1.setIndeterminate(false);
                            progressBar1.setMax(100);
                            progressBar1.setProgress(progress);
                            prevPage = R.layout.default_screen;
                        }
                    }
                }
                break;
            }
        }
    }

    // protected variables to keep track of
    protected String playerName;
    protected int prevPage = R.layout.default_screen;
    protected int numCorrect = 0;
    protected int numPuzzles = 5;
    protected String team = "";
    protected boolean solvedDriving = false;
    protected boolean solvedGoing = false;
    protected boolean solvedMajor = false;
    protected boolean solvedMaking = false;
    protected boolean solvedTrip = false;
    public int getProgressBarProgress(){
        int progress = numCorrect*100/numPuzzles;
        return progress;
    }


    public void start(View v) {
        EditText teamName = findViewById(R.id.inputTeamName);
        team = teamName.getText().toString();
        if (!TextUtils.isEmpty(team)) {
            playerName = team; //can be printed to console when Leaderboard button is pressed
            Toast.makeText(this,"Welcome " + team + "! Let's begin!", Toast.LENGTH_LONG).show();
            goToTutorial(v);
        } else {
            String temp = "Don't forget to enter a team name!";
            Toast.makeText(this,temp,Toast.LENGTH_LONG).show();

        }

    }



    public void goToHome(View v){
        setContentView(R.layout.activity_main);
    }

    public void goToTutorial(View v) {
        setContentView(R.layout.intro_video);

        VideoView videoview = findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.vid);
        videoview.setVideoURI(uri);
        videoview.start();
    }

    public void goToDefault(View v)  {
        ProgressBar progressBar = findViewById(R.id.progressBar);

        int progress = getProgressBarProgress();
        if(progress == 100){
            setContentView(R.layout.congrats);
            TextView name = (TextView) findViewById(R.id.textView2);
            progressBar.setMax(100);
            CharSequence newString = team;
            name.setText(newString);
        }
        else {
            setContentView(R.layout.default_screen);
            ProgressBar progressBar1 = findViewById(R.id.progressBar);
            progressBar1.setIndeterminate(false);
            progressBar1.setMax(100);
            progressBar1.setProgress(progress);
            prevPage = R.layout.default_screen;
        }

    }

    public void goToPrev (View v) {
        setContentView(prevPage);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setIndeterminate(false);
        progressBar.setMax(100);
        progressBar.setProgress(getProgressBarProgress());
    }

    public void goToBroken(View v){
        setContentView(R.layout.broken_parts);
        ImageView view1 = findViewById(R.id.imageViewx1);   //Seat
        ImageView view2 = findViewById(R.id.imageViewx2);   //Belt
        ImageView view3 = findViewById(R.id.imageViewx3);   //engine
        ImageView view4 = findViewById(R.id.imageViewx4);   //power supply
        ImageView view5 = findViewById(R.id.imageViewx5);   //calendar
        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.VISIBLE);
        view3.setVisibility(View.VISIBLE);
        view4.setVisibility(View.VISIBLE);
        view5.setVisibility(View.VISIBLE);

        if(solvedMajor == true){
            view1.setVisibility(View.INVISIBLE);
        }else{
            view1.setVisibility(View.VISIBLE);
        }
        if(solvedDriving == true){
            view2.setVisibility(View.INVISIBLE);
        }else{
            view2.setVisibility(View.VISIBLE);
        }
        if(solvedMaking == true){
            view3.setVisibility(View.INVISIBLE);
        }else{
            view3.setVisibility(View.VISIBLE);
        }
        if(solvedGoing == true){
            view4.setVisibility(View.INVISIBLE);
        }else{
            view4.setVisibility(View.VISIBLE);
        }
        if(solvedTrip == true){
            view5.setVisibility(View.INVISIBLE);
        }else{
            view5.setVisibility(View.VISIBLE);
        }

    }

    public void goToDriving () {
        setContentView(R.layout.driving);
        prevPage = R.layout.driving;
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(getProgressBarProgress());
    }

    public void goToGoingPlaces () {
        setContentView(R.layout.going_places);
        prevPage = R.layout.going_places;
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(getProgressBarProgress());
    }

    public void goToMajorParts() {
        setContentView(R.layout.major_parts);
        prevPage = R.layout.major_parts;
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(getProgressBarProgress());

        speechButton = (Button)findViewById(R.id.button7);

        speechButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //goToHome(v);
                askSpeechInput();
            }
        });
    }

    public void goToMakingCarriages() {
        setContentView(R.layout.making_carriages);
        prevPage = R.layout.making_carriages;
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(getProgressBarProgress());
    }

    public void goToTripToYest() {
        setContentView(R.layout.trip_to_yesterday);
        prevPage = R.layout.trip_to_yesterday;
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(getProgressBarProgress());
    }

    public void newPuzzle(View v) {
        boolean valid = checkAnswer();
        if (valid == true) {
            goToDefault(v);
        }
    }

    private boolean checkAnswer(){
        if (prevPage == R.layout.driving){
            EditText ans = findViewById(R.id.editText);
            String response = ans.getText().toString().toLowerCase().trim();
            if (!TextUtils.isEmpty(response)) {
                if ((response.equals("20")) || (response.equals("twenty"))){
                    String temp = "Great job, you fixed the seatbelts!";
                    Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
                    solvedDriving = true;
                    numCorrect ++;
                    return true;
                }
                else{
                    String temp = "Your answer, ";
                    temp += response;
                    temp += " is incorrect. Try again!";
                    Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                String temp = "You must provide an answer!";
                Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
                return false;

            }
        }
        else if (prevPage == R.layout.going_places){
            EditText ans = findViewById(R.id.editText2);
            String response = ans.getText().toString().toLowerCase().trim();
            if (!TextUtils.isEmpty(response)) {
                if (response.equals("wells fargo")){
                    String temp = "Great job, you fixed the power supply!";
                    Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
                    solvedGoing = true;
                    numCorrect++;
                    return true;
                }
                else{
                    String temp = "Your answer, ";
                    temp += response;
                    temp += " is incorrect. Try again!";
                    Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                String temp = "You must provide an answer!";
                Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
                return false;

            }
        }

        else if (prevPage == R.layout.major_parts){
            if (!TextUtils.isEmpty(recordAnswer)) {
                if ((recordAnswer.equals("seat")) || (recordAnswer.equals("seats")) || (recordAnswer.equals("20")) || (recordAnswer.equals("twenty"))){
                    String temp = "Great job, you fixed the seats!";
                    Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
                    solvedMajor = true;
                    numCorrect++;
                    return true;
                }
                else{
                    String temp = "Your answer, ";
                    temp += recordAnswer;
                    temp += " is incorrect. Try again!";
                    Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                String temp = "You must provide an answer!";
                Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
                return false;

            }
        }

        else if (prevPage == R.layout.making_carriages){
            EditText ans = findViewById(R.id.editText3);
            String response = ans.getText().toString().toLowerCase().trim();
            if (!TextUtils.isEmpty(response)) {
                if (response.equals("1902")){
                    String temp = "Great job, you fixed the engine!";
                    Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
                    solvedMaking = true;
                    numCorrect++;
                    return true;
                }
                else{
                    String temp = "Your answer, ";
                    temp += response;
                    temp += " is incorrect. Try again!";
                    Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                String temp = "You must provide an answer!";
                Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
                return false;

            }
        }
        else if (prevPage == R.layout.trip_to_yesterday){
            EditText ans = findViewById(R.id.editText4);
            String response = ans.getText().toString().toLowerCase().trim();
            if (!TextUtils.isEmpty(response)) {
                if ((response.equals("12:30pm")) || (response.equals("12:30 pm")) || (response.equals("12:30")) || (response.equals("twelve-thirty")) || (response.equals("twelve thirty"))){
                    String temp = "Great job, you found the schedule!";
                    Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
                    solvedTrip = true;
                    numCorrect++;
                    return true;
                }
                else{
                    String temp = "Your answer, ";
                    temp += response;
                    temp += " is incorrect. Try again!";
                    Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                String temp = "You must provide an answer!";
                Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
                return false;

            }
        }
        else{
            return false;
        }
    }

    @Override
    protected void onNewIntent(Intent intent){
        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) && prevPage == R.layout.default_screen) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMessages != null) {
                NdefMessage[] messages = new NdefMessage[rawMessages.length];
                for (int i = 0; i < rawMessages.length; i++) {
                    messages[i] = (NdefMessage) rawMessages[i];
                }

                byte[] payload = messages[0].getRecords()[0].getPayload();


                int languageCodeLength = payload[0] & 0077;
                try {
                    String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
                    String text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, "UTF-8");
                    if(text.equals("Driving for sport and pleasure") && solvedDriving == false){
                        goToDriving();
                    }
                    else if(text.equals("Going places") && solvedGoing == false){
                        goToGoingPlaces();
                    }
                    else if(text.equals("Major parts of a vehicle") && solvedMajor == false){
                        goToMajorParts();
                    }
                    else if(text.equals("Making carriages") && solvedMaking == false){
                        goToMakingCarriages();
                    }
                    else if(text.equals("A trip to yesterday") && solvedTrip == false){
                        goToTripToYest();
                    }
                    else if(text.equals("http://www.jigsawplanet.com/?rc=play&pid=3fe556f4babe&pieces=12")){
                        Intent newIntent = new Intent();
                        Uri uri = Uri.parse(text);
                        newIntent.setAction(Intent.ACTION_VIEW);
                        newIntent.setData(uri);
                        startActivity(newIntent);
                    }
                }catch(Exception e){
                    Toast.makeText(this, "Unable to read text", Toast.LENGTH_LONG).show();
                }

            }
        }
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume(){
        if(nfcAdapter!=null){
            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
            IntentFilter[] intentfilter = new IntentFilter[]{};
            nfcAdapter.enableForegroundDispatch(this,pendingIntent,intentfilter,null);
        }
        super.onResume();
    }

    protected void onPause(){
        if(nfcAdapter!=null){
            nfcAdapter.disableForegroundDispatch(this);
        }
        super.onPause();
    }

}
