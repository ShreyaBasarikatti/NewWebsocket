package com.example.newwebsocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button connect;
    private EditText ip,port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect = (Button) findViewById(R.id.connect);
        ip=(EditText)findViewById(R.id.ip);
        port=(EditText)findViewById(R.id.port);


        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip1= ip.getText().toString();
                String port1= port.getText().toString();
                Bundle bundle= new Bundle();
                bundle.putString("ip address",ip1);
                bundle.putString("port number",port1);
                Toast.makeText(MainActivity.this, "Connecting to "+ "ws://"+ip.getText().toString() +":"+ port.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,message.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }



}