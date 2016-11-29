package com.example.pranilmaharjan.wifi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import java.io.DataOutputStream;
import java.net.Socket;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;


public class remote_control extends AppCompatActivity {

    Socket client;
    public Button start, pause, next, previous, go;
    public TextView currentpage, totalpage;
    public EditText slideToGo;

    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.remote_control);
        initializeData();      //Initializing the parameters

        Log.d(TAG, "We are in remote_control");
        Intent in = getIntent();

        Bundle b = in.getExtras();
        final String ipadd = b.get("ip_address").toString();
        final  int  port = Integer.parseInt(b.get("port_address").toString());

        Toast.makeText(getApplicationContext(),"connected to server....",Toast.LENGTH_SHORT).show();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=sendMessage(start.getText().toString(), ipadd, port);
                Log.d(TAG, ": " + message);
                currentpage.setText(message);

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message= sendMessage(pause.getText().toString(), ipadd, port);
                Log.d(TAG, ": " + message);
                currentpage.setText(message);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=sendMessage(next.getText().toString(), ipadd, port);
                Log.d(TAG, ": " + message);
                currentpage.setText(message);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=sendMessage(previous.getText().toString(), ipadd, port);
                Log.d(TAG, ": " + message);
                currentpage.setText(message);
            }
        });

        go.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String message=sendMessage(slideToGo.getText().toString(),ipadd,port);
                Log.d(TAG, ": " + message);
                currentpage.setText(message);
                slideToGo.setText("");
            }
        });


    }

    public void initializeData()
    {
        start=(Button)findViewById(R.id.start_slide);
        pause=(Button)findViewById(R.id.pause_slide);
        next=(Button)findViewById(R.id.next_slide);
        previous=(Button)findViewById(R.id.previous_slide);
        go=(Button)findViewById(R.id.go_slide);
        totalpage=(TextView)findViewById(R.id.totalpageNo);
        currentpage=(TextView)findViewById(R.id.currentpageNo);
        slideToGo=(EditText)findViewById(R.id.slide_No);
    }



    public String sendMessage(String message,String ipadd,int port){
        String reply=null;
        String page=null;
        DataInputStream stdIn = new DataInputStream(System.in);
        try {
            client = new Socket(ipadd, port);
            DataOutputStream dt = new DataOutputStream(client.getOutputStream());
            dt.writeUTF(message);

            Log.d(TAG, ": " + message);

            DataInputStream dam=new DataInputStream(client.getInputStream());
            Log.d(TAG, ": " + dam);

            List<String> strArray = new ArrayList<String>();

            byte[] bs = new byte[4];
            dam.read(bs);
            Log.d(TAG, ":"+ bs);

            for (byte b:bs)
            {
                // convert byte into character
                char c = (char)b;
                if (Character.isDigit(c)) {
                    String val=String.valueOf(c);
                    strArray.add(val);
                }

            }
            //String byte_Str = new String(bs);

            Log.d(TAG, ": " + strArray);

            if (strArray.size()==2)
            {
                reply=strArray.get(0);
                page=strArray.get(1);
            }

            if ( strArray.size()==3)
            {
                reply=strArray.get(0);
                page=strArray.get(1).concat(strArray.get(2));
            }

            if(strArray.size()==4)
            {
                reply=strArray.get(0).concat(strArray.get(1));
                page=strArray.get(2).concat(strArray.get(3));
            }
            /*
            char c=(char)bs[0];
            Log.d(TAG, ": " + c);
            reply=String.valueOf(c);
            char m=(char)bs[1];
            Log.d(TAG, ":" +m);

            String page=String.valueOf(m);*/
            /*for (byte b:bs)
            {
                // convert byte into character
                char c = (char)b;
                Log.d(TAG, ": " + c);
                // print the character

            }*/
           //String response =br.readLine();
            //Log.d(TAG, ": " + response);
            totalpage.setText(page);
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reply;
    }

}
