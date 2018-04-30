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

    protected String playerName;
    public void start(View v) {
        EditText teamName = (EditText) findViewById(R.id.inputTeamName);
        String team = teamName.getText().toString();
        if (!TextUtils.isEmpty(team)) {
            playerName = team; //can be printed to console when Leaderboard button is pressed
            goToTutorial(v);
        } else {
            String temp = "Don't forget to enter a team name!";
            Toast.makeText(this,temp,Toast.LENGTH_LONG).show();

        }

    }

    @Override
    protected void onNewIntent(Intent intent){
        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
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
                    if(text.equals("testing for kevin")){
                        setContentView(R.layout.screen2);
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
    public void buttonOnClick(View v){
        System.out.println(playerName);
    }

    public void goToBroken(View v){
        setContentView(R.layout.screen2);
    }

    public void goToHome(View v){
        setContentView(R.layout.activity_main);
    }

    public void goToFirst(View v) { setContentView(R.layout.screen3);}

    public void goToTutorial(View v) {
        setContentView(R.layout.screenv);

        VideoView videoview = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.vid);
        videoview.setVideoURI(uri);
        videoview.start();
    }
    public void goToSecond(View v) { setContentView(R.layout.screen4);}

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
        sr.setRecognitionListener((RecognitionListener) listener);
        sr.startListening(intent);

        System.out.print("I'm listening!");
    }

}
