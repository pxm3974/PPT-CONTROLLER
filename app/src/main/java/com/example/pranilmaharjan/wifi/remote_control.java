package com.example.pranilmaharjan.wifi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class remote_control extends AppCompatActivity {


    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remote_control);
        Button start=(Button)findViewById(R.id.start_slide);
        Button pause=(Button)findViewById(R.id.pause_slide);
        Button next=(Button)findViewById(R.id.next_slide);
        Button previous=(Button)findViewById(R.id.previous_slide);
        TextView pageNo=(TextView)findViewById(R.id.pageNo);

        Log.d(TAG, "We are in remote_control");
        start.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Log.d(TAG, "We are in start");
                    }
                });

        pause.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Log.d(TAG, "We are in pause");
                    }
                });
        next.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {
                        Log.d(TAG, "We are in next");
                    }});
        previous.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {
                        Log.d(TAG, "We are in previous");
                    }});

    }

}
