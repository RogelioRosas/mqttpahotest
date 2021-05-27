package pp.mqttpahotest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView tv;
    //private String topic;
    String clientID = MqttClient.generateClientId();
    private static final String TAG = "Main Activity";
    MqttAndroidClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btAcionar);
        tv = findViewById(R.id.tvPrincipal);
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.hivemq.com:1883", clientID);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    IMqttToken token = client.connect();
                    token.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            Log.d(TAG, "onSuccess");
                            tv = findViewById(R.id.tvPrincipal);
                            String estado = "conectado";
                            tv.setText(estado);
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            Log.d(TAG, "onFailure");
                            tv = findViewById(R.id.tvPrincipal);
                            String estado = "No conectado";
                            tv.setText(estado);

                        }
                    });
                } catch (MqttException e){
                    e.printStackTrace();
                }
            }
        });

    }

}