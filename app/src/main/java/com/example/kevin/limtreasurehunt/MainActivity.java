package com.example.kevin.limtreasurehunt;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    NfcAdapter nfcAdapter;
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

    @Override
    protected void onNewIntent(Intent intent){
        Toast.makeText(this,"Intent received!",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(this, text, Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(this, "Unable to read text", Toast.LENGTH_LONG).show();
                }


            }
        }


        super.onNewIntent(intent);
    }
    @Override
    protected void onResume(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        IntentFilter[] intentfilter = new IntentFilter[]{};
        nfcAdapter.enableForegroundDispatch(this,pendingIntent,intentfilter,null);
        super.onResume();
    }

    protected void onPause(){
        nfcAdapter.disableForegroundDispatch(this);
        super.onPause();
    }
    public void buttonOnClick(View v){
        System.out.println("kevin is the best!!! amazing");
    }

    public void goToBroken(View v){
        setContentView(R.layout.screen2);
    }

    public void goToHome(View v){
        setContentView(R.layout.activity_main);
    }

    public void goToFirst(View v) { setContentView(R.layout.screen3);}
}
