package com.joshirwin.myjokedisplay;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class JokeDisplayActivity extends AppCompatActivity {
    public static String JOKE = "joke";
    private String myJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(JOKE))
            myJoke = intent.getStringExtra(JOKE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (myJoke != null) {
            JokeDisplayActivityFragment fragment = (JokeDisplayActivityFragment) getFragmentManager().findFragmentById(R.id.fragment_for_displaying_joke);
            fragment.setJoke_display(myJoke);
        }
    }
}
