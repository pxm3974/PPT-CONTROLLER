package com.example.pranilmaharjan.wifi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.os.StrictMode;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import android.util.Log;
import java.io.DataOutputStream;


public class MainActivity extends ActionBarActivity {

    Socket client;
    private PrintWriter printwriter;
    private static final String TAG = "MyActivity";
    public String ip_address;
    public Integer port_address;
    public Button button;
    public EditText myEditText1;
    public EditText myEditText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        initializeData();     // initializing buttons and textfields

        button.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {
                        ip_port();
                        //ip_address=10.0.2.2 and port=8888 and 10.0.3.2 for Genymotion
                        //if(ip_address.equals("10.0.2.15")&& port_address==8888){
                            Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                            connection();
                        //}
                        /*else
                        {
                            Toast.makeText(getApplicationContext(), "Error....Please enter again", Toast.LENGTH_SHORT).show();
                        }*/

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

    public void initializeData()
    {
        button=(Button)findViewById(R.id.button);
        myEditText1=(EditText)findViewById(R.id.IPAddress);
        myEditText2=(EditText)findViewById(R.id.port);
    }

    public void ip_port(){
        ip_address = myEditText1.getText().toString();
        port_address = Integer.parseInt(myEditText2.getText().toString());
    }

    public void connection()
    {
        try {
            Log.d(TAG, "We are inside try block");
            String message = "You are now connected ";
            //connect to server
            client = new Socket(ip_address, port_address);

            Bundle b = new Bundle();
            b.putString("ip_address",ip_address);
            b.putInt("port_address", port_address);

            Intent in=new Intent(getApplicationContext(), remote_control.class);

            in.putExtras(b);
            startActivity(in);

            //startActivity(new Intent(getApplicationContext(), remote_control.class));
            DataOutputStream dout=new DataOutputStream(client.getOutputStream());
            dout.writeUTF("Hello");
            dout.flush();
            dout.close();

            Log.d(TAG, "We are after printwriter");

            client.close();   //10closing the co10nnection

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
