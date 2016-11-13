package com.example.pranilmaharjan.wifi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class remote_control extends AppCompatActivity {

    Socket client;
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

        Intent in=getIntent();

        Bundle b=in.getExtras();



        Log.d(TAG, "We are in remote_control");
        start.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent in=getIntent();
                        Bundle b=in.getExtras();
                        String ip_address=b.getString("ip_address");

                        Integer port_address=b.getInt("port_address");
                        try {
                            client = new Socket(ip_address, port_address);
                            DataOutputStream dout=new DataOutputStream(client.getOutputStream());
                            dout.writeUTF("Hello");
                            Log.d(TAG, "We are in start");
                        }
                        catch (UnknownHostException e) {
                            e.printStackTrace();
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                });

        pause.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Log.d(TAG, "We are in pause");
                    }
                });
        next.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Log.d(TAG, "We are in next");
                    }
                });
        previous.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {
                        Log.d(TAG, "We are in previous");
                    }});

    }

}
