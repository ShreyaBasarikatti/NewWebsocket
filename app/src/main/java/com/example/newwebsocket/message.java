package com.example.newwebsocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;


public class message extends AppCompatActivity {
    private TextView output,url;
    private OkHttpClient client;
    private EditText message;
    private Button receive,send;
    String ip1;
    String port1;
    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;
        @Override
        public void onOpen(WebSocket webSocket, Response response) {

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    webSocket.send(message.getText().toString());

                }
            });
            webSocket.send("Hello, it's Shreya here!");
            webSocket.send("This is TRF");
            webSocket.send("What's up ?");
            webSocket.send(ByteString.decodeHex("deadbeef"));
            //webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
        }
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output("Receiving : " + text);
        }
        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            output("Receiving bytes : " + bytes.hex());
        }
        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            output("Closing : " + code + " / " + reason);
        }
        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Error : " + t.getMessage());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        output=findViewById(R.id.output);
        url=findViewById(R.id.url);
        receive=findViewById(R.id.receive);
        send=findViewById(R.id.send);
        message=findViewById(R.id.message);
        client = new OkHttpClient();
        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { connect();}
        });


    }
    public void connect() {
        Bundle bundle=getIntent().getExtras();
        if(bundle != null){
            ip1 = bundle.getString("ip address");
            port1 = bundle.getString("port number");
            url.setText("The connected url is"+ "ws://" + ip1 +":"+ port1);
        }
        Request request= new Request.Builder().url("ws://" + ip1 + ":" + port1).build();
        message.EchoWebSocketListener listener = new message.EchoWebSocketListener();
        WebSocket ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    private void output(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                output.setText(output.getText().toString() + "\n\n" + txt);
            }
        });
    }
}