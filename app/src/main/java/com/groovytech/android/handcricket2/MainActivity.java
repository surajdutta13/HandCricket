package com.groovytech.android.handcricket2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView hostTextView,tossSelectionTextView;
    Button button1,button2,button3,headButton,tailsButton,battingButton,ballingButton,playButton;
    Boolean toss;
    int noOfWickets;
    private AdView mAdView;

    public void bat(View view){
        Button button = (Button)view;
        if(Integer.parseInt(button.getTag().toString())==1) {
            Intent intent = new Intent(MainActivity.this, MatchActivity.class);
            intent.putExtra("Bat", true);
            intent.putExtra("wickets",noOfWickets);
            startActivity(intent);
        }else{
            ball(playButton);
        }
    }
    public void ball(View view){
        Button button = (Button)view;
        if(Integer.parseInt(button.getTag().toString())==0) {
            Intent intent = new Intent(MainActivity.this, MatchActivity.class);
            intent.putExtra("Bat", false);
            intent.putExtra("wickets",noOfWickets);
            startActivity(intent);
        }else
            bat(playButton);
    }

    public void wickets(View view)
    {
        Button wickets = (Button) view;
        Log.i("wickets ", wickets.getTag().toString());
        noOfWickets = Integer.parseInt((String) wickets.getTag());
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);

        tailsButton.setVisibility(View.VISIBLE);
        headButton.setVisibility(View.VISIBLE);
        hostTextView.setText("Toss Time !! ");

    }

    public void toss(View view)
    {
        Button button = (Button) view;
        String tossTag =  headButton.getTag().toString();
        Log.i("users choice",tossTag);


        Random rand = new Random();
        toss = rand.nextBoolean();
        Log.i("Toss ", toss.toString());
        if(toss==true && tossTag.equals("heads")) {
            hostTextView.setText("You Won The Toss");
            headButton.setVisibility(View.INVISIBLE);
            tailsButton.setVisibility(View.INVISIBLE);
            ballingButton.setVisibility(View.VISIBLE);
            battingButton.setVisibility(View.VISIBLE);
            tossSelectionTextView.setVisibility(View.VISIBLE);

        }
        else{
            hostTextView.setText("You Lost The Toss");
            headButton.setVisibility(View.INVISIBLE);
            tailsButton.setVisibility(View.INVISIBLE);
            ballingButton.setVisibility(View.INVISIBLE);
            tossSelectionTextView.setVisibility(View.VISIBLE);
            Boolean r = rand.nextBoolean();
            if (r==true) {
                tossSelectionTextView.setText("Computer is going to bat first");
                playButton.setVisibility(View.VISIBLE);
                //ball(playButton);
                playButton.setTag(0);
            }

            else{
                tossSelectionTextView.setText("Computer is going to ball first");
                playButton.setVisibility(View.VISIBLE);
                //bat(playButton);
                playButton.setTag(1);
            }

        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);

        hostTextView = (TextView) findViewById(R.id.hostTextView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        tailsButton = findViewById(R.id.tailsButton);
        headButton = findViewById(R.id.headsButton);
        battingButton = findViewById(R.id.battingButton);
        ballingButton = findViewById(R.id.ballingButton);
        tossSelectionTextView = findViewById(R.id.tossSelection);
        playButton = (Button) findViewById(R.id.playButton);
        playButton.setVisibility(View.INVISIBLE);
    }
}
