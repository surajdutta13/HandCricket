package com.groovytech.android.handcricket2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Random;

public class MatchActivity extends AppCompatActivity {
    TextView inningsTextView,myScoreTextView,computerScoretextView,ballerTextView,activityTextView,resultTextView;
    int myScore=0,computerScore=0,wickets=0,run=0,baller=0,batter=0,target,computerTarget,noOfWickets;
    Button button1,button2,button3,button4,button5,button6,playAgainButton;
    Boolean gameActive=true;
    Random rand = new Random();
    private AdView mAdView; private InterstitialAd mInterstitialAd;

    public void restart(View view){
        myScore=0;computerScore=0;wickets=0;run=0;baller=0;batter=0;target=0;computerTarget=0; gameActive=true;
        Intent intent = new Intent(MatchActivity.this,MainActivity.class);
        int n = rand.nextInt(2);
        if (n==1){
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                startActivity(intent);
            }
        }else{
            startActivity(intent);
        }



    }

    public int getRun(){
        run = 1 +  rand.nextInt(6);
        return run;
    }
    public void addRunToMyScore(View view,int n){
        Log.i("Your Target ", String.valueOf(target));
        Log.i("Computer Target ", String.valueOf(computerTarget));
        int tappedRun = Integer.parseInt(view.getTag().toString());
        if (gameActive){baller=getRun();}
        ballerTextView.setText(String.valueOf(baller));
        if(baller== tappedRun && gameActive) {
            wickets++;
            Log.i("Wickets Down ",String.valueOf(wickets));
            Log.i("No of Wickets Wickets ",String.valueOf(noOfWickets));
            myScoreTextView.setText(String.valueOf(myScore) + "/" + String.valueOf(wickets));
            if (target < computerTarget && wickets == noOfWickets) {
                resultTextView.setText("Computor Won !!");   // works
                inningsTextView.setVisibility(View.INVISIBLE);
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Match Status")
                        .setMessage("Computor Won :( Better luck Next time !!")
                        .setPositiveButton("Ok",null)
                        .show();
                gameActive=false;
                Log.i("game Active", String.valueOf(gameActive));
                playAgainButton.setVisibility(View.VISIBLE);
            }
            Toast.makeText(getApplicationContext(),"Wicket !!",Toast.LENGTH_SHORT).show();
            Log.i("Target ", String.valueOf(target));
            if (wickets == noOfWickets && gameActive == true) {
                myScore=0;
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Your turn to....")
                        .setMessage("Ball")
                        .setPositiveButton("Ok",null)
                        .show();
                balling(1);
            }
        }else if (baller!= tappedRun && gameActive == true){
            myScore += tappedRun;
            target= myScore;
            myScoreTextView.setText(String.valueOf(myScore) + "/" + String.valueOf(wickets));
            if (target > computerTarget && n != 0) {
                inningsTextView.setText("You Won");
                Log.i("Status: ", "You Won ");      //works
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Match Status")
                        .setMessage("Hurray !! You Won")
                        .setPositiveButton("Ok",null)
                        .show();
                gameActive=false;
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }
    }
    public void addRunToComputerScore(View view,int m)
    {   Log.i("Computer Target ", String.valueOf(computerTarget));
        Log.i("Your Target ", String.valueOf(target));
        baller = Integer.parseInt(view.getTag().toString());
        if(gameActive){batter=getRun();}
        ballerTextView.setText(String.valueOf(batter));
        if(baller== batter && gameActive== true) {
            wickets++;
            computerScoretextView.setText(String.valueOf(myScore) + "/" + String.valueOf(wickets));
            Log.i("Wickets Down ",String.valueOf(wickets));
            Log.i("No of Wickets Wickets ",String.valueOf(noOfWickets));
            if (computerTarget < target && wickets == noOfWickets) {
                resultTextView.setText("You Won");
                inningsTextView.setVisibility(View.INVISIBLE);  // works
                Log.i("Status: ", "You Won ");
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Match Status")
                        .setMessage("Hurray !! You Won")
                        .setPositiveButton("Ok",null)
                        .show();
                gameActive=false;Log.i("game Active", String.valueOf(gameActive));
                playAgainButton.setVisibility(View.VISIBLE);
            }
            Toast.makeText(getApplicationContext(),"Wicket !!",Toast.LENGTH_SHORT).show();
            Log.i("Computer Target ", String.valueOf(computerTarget));
            if (wickets == noOfWickets && gameActive == true) {
                myScore=0;
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Your turn to....")
                        .setMessage("Bat")
                        .setPositiveButton("Ok",null)
                        .show();
                batting(1);
            }

        }else if (baller!= batter && gameActive == true){
            myScore += batter;
            computerTarget=myScore;
            computerScoretextView.setText(String.valueOf(computerTarget) + "/" + String.valueOf(wickets));
            if (computerTarget>target && m != 0 && wickets < noOfWickets) {              //Works
                inningsTextView.setText("Computor Won!!");
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Match Status")
                        .setMessage("Computor Won :( Better luck Next time !!")
                        .setPositiveButton("Ok",null)
                        .show();
                gameActive=false;
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }
    }

    public void batting(final int i)
    {   wickets=0;

        Log.i("batting", String.valueOf(i));
        inningsTextView.setText("You are BATTING");
        activityTextView.setText("Baller");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addRunToMyScore(v,i);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addRunToMyScore(v,i);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addRunToMyScore(v,i);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRunToMyScore(v,i);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRunToMyScore(v,i);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRunToMyScore(v,i);
            }
        });
    }

    public void balling(final int i)
    {   wickets=0;
        inningsTextView.setText("You are BALLING");
        activityTextView.setText("Batter");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRunToComputerScore(v,i);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRunToComputerScore(v,i);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRunToComputerScore(v,i);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRunToComputerScore(v,i);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRunToComputerScore(v,i);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRunToComputerScore(v,i);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
       // mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8417516868191988/5927167495");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        inningsTextView = (TextView)findViewById(R.id.inningsTextView);
        myScoreTextView = (TextView) findViewById(R.id.score1);
        ballerTextView = (TextView) findViewById(R.id.ballerTextView);
        activityTextView = (TextView) findViewById(R.id.activityTextView);
        computerScoretextView = (TextView) findViewById(R.id.computerScoreTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        playAgainButton.setVisibility(View.INVISIBLE);
        Intent intent= getIntent();
        boolean batting = intent.getBooleanExtra("Bat",true);
        noOfWickets = intent.getIntExtra("wickets",1);
        Log.i("wickets", String.valueOf(wickets));
        if(batting==true){
            Toast.makeText(getApplicationContext(),"Batting",Toast.LENGTH_SHORT).show();
            batting(0);
        }else {
            Toast.makeText(getApplicationContext(), "Balling", Toast.LENGTH_SHORT).show();
            balling(0);}


    }
}
