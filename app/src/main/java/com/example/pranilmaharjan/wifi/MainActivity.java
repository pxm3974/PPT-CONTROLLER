package com.example.pranilmaharjan.wifi;


import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;

import android.widget.RelativeLayout;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.Color;
import android.widget.EditText;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import android.util.Log;

public class MainActivity extends ActionBarActivity {

    String ip_address="192.168.0.6";
    //String ip_address="10182228";
    //String ip_address="7618724120";
    String port_address="8080";
    private Socket client;
    private PrintWriter printwriter;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button=(Button)findViewById(R.id.button);
        final EditText myEditText1=(EditText)findViewById(R.id.IPAddress);
        final EditText myEditText2=(EditText)findViewById(R.id.port);

        button.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {
                        if (myEditText1.getText().toString().equals(ip_address) && myEditText2.getText().toString().equals(port_address)) {
                            Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                            try {
                                Log.d(TAG, "We are inside try block");
                                String message="You are now connected ";
                                //client = new Socket("10.182.2.28", 8080);  //connect to server
                                client=new Socket("192.168.0.6", 8080);
                                //client=new Socket("76.187.241.20", 8080);
                                printwriter = new PrintWriter(client.getOutputStream(), true);
                                printwriter.write(message);  //write the message to output stream
                                Log.d(TAG, "We are after printwriter");
                                printwriter.flush();
                                printwriter.close();
                                client.close();   //10closing the co10nnection

                            } catch (UnknownHostException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
