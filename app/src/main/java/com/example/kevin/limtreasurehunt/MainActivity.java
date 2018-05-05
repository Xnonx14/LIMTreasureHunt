package com.example.kevin.limtreasurehunt;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;
import android.media.MediaRecorder;
import android.speech.SpeechRecognizer;

public class MainActivity extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    private MediaRecorder mmediaRecorder;
    private SpeechRecognizer sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter!= null && nfcAdapter.isEnabled()){
            Toast.makeText(this,"NFC Enabled!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"NFC NOT ENABLED!",Toast.LENGTH_LONG).show();
        }

    }
    // protected variables to keep track of
    protected String playerName;
    protected int prevPage = R.layout.default_screen;
    protected Integer numCorrect = 0;
    protected Integer numPuzzles = 5;
    protected Integer progress = numCorrect*100/numPuzzles;

    public void start(View v) {
        EditText teamName = findViewById(R.id.inputTeamName);
        String team = teamName.getText().toString();
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
        setContentView(R.layout.default_screen);
        prevPage = R.layout.default_screen;
    }

    public void goToPrev (View v) {
        setContentView(prevPage);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setIndeterminate(false);
        progressBar.setMax(100);
        progressBar.setProgress(progress);
    }

    public void goToBroken(View v){
        setContentView(R.layout.broken_parts);

    }

    public void goToDriving () {
        setContentView(R.layout.driving);
        prevPage = R.layout.driving;
    }

    public void goToGoingPlaces () {
        setContentView(R.layout.going_places);
        prevPage = R.layout.going_places;
    }

    public void goToMajorParts() {
        setContentView(R.layout.major_parts);
        prevPage = R.layout.major_parts;
    }

    public void goToMakingCarriages() {
        setContentView(R.layout.making_carriages);
        prevPage = R.layout.making_carriages;
    }

    public void goToTripToYest() {
        setContentView(R.layout.trip_to_yesterday);
        prevPage = R.layout.trip_to_yesterday;
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
            //get text response from the record button
            String response = "";
            if (!TextUtils.isEmpty(response)) {
                if ((response.equals("seat")) || (response.equals("seats")) || (response.equals("20")) || (response.equals("twenty"))){
                    String temp = "Great job, you fixed the seats!";
                    Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
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

        else if (prevPage == R.layout.making_carriages){
            EditText ans = findViewById(R.id.editText3);
            String response = ans.getText().toString().toLowerCase().trim();
            if (!TextUtils.isEmpty(response)) {
                if (response.equals("1902")){
                    String temp = "Great job, you fixed the engine!";
                    Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
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
                    //ADD TO THIS
                    if(text.equals("Driving for sport and pleasure")){
                        goToDriving();
                    }
                    else if(text.equals("Going places")){
                        goToGoingPlaces();
                    }
                    else if(text.equals("Major parts of a vehicle")){
                        goToMajorParts();
                    }
                    else if(text.equals("Making carriages")){
                        goToMakingCarriages();
                    }
                    else if(text.equals("A trip to yesterday")){
                        goToTripToYest();
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

    public void voiceRecognize(View v){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        RecognitionListener listener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {

            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        };

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(listener);
        sr.startListening(intent);

        Toast.makeText(this,"I'm listening!",Toast.LENGTH_LONG).show();
    }

}
