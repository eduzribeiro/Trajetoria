package com.example.eduardo.coletar_dados_agw;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class AGWActivity extends AppCompatActivity implements SensorEventListener {

    private double current_timeAcel=0;
    private double tns;

    private SensorState estadoDoSensor = new SensorState();

    SensorManager sm;
    Sensor acelerometro;
    Sensor giro;
    Sensor gravi;

    TextView Estado=null;

    EditText Nome;

    boolean aux = false;

    int COUNTER;
    int OFFSET=50; //numero de amostras jogadas

    ArrayList<Double> DadosSalvos;

   // DecimalFormat decimal = new DecimalFormat( "0.###" );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agw);

        //Atribuindo variáveis aos campos do layout

        //DadosSalvos = new ArrayList<Double>();

        Estado = (TextView) findViewById(R.id.situacao);
        Button btn2 = (Button) findViewById(R.id.botao2);
        btn2.setEnabled(false);

        Nome = (EditText) findViewById(R.id.nome);

        //Acessando sensores

        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        acelerometro = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// Acessando o acelerometro (linear_acceleration??)

        giro = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);// Acessando o girômetro

        gravi = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);// Acessando o sensor de gravidade

    }


    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_FASTEST); // Inicia o processo de captura do acelerometro

        sm.registerListener(this, giro, SensorManager.SENSOR_DELAY_FASTEST);

        sm.registerListener(this, gravi, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this, acelerometro);// Irá parar o processo de captura do sensor. Estes métodos(onResume/onPause)fazem poupar bateria, pois sem eles o aplicativo vai continuar

        sm.unregisterListener(this, giro);

        sm.unregisterListener(this, gravi);
    }


    //Funções para cada botão

    public void onClickIniciar(View v){

        Button btn = (Button) findViewById(R.id.botao1);
        btn.setEnabled(false);

        estadoDoSensor = new SensorState();

        DadosSalvos = new ArrayList<Double>();

        aux = true;

        Estado.setText("Iniciado");
        Button btn2 = (Button) findViewById(R.id.botao2);
        btn2.setEnabled(true);

        COUNTER=0;

    }

    public void onClickSalvar(View v){

        Button btn2 = (Button) findViewById(R.id.botao2);
        btn2.setEnabled(false);
        aux = false;

        salvarDados2(v.getContext().getExternalFilesDir(null).getAbsolutePath());
        Estado.setText("Dados Salvos");

        DadosSalvos.clear();

        Button btn = (Button) findViewById(R.id.botao1);
        btn.setEnabled(true);
    }


    //Função para salvar os dados

    private void salvarDados(String dir) {

        String name = Nome.getText().toString();

        FileOutputStream outputStream;
        String entrada = new String();
        int ii=0;
        for (Double d : DadosSalvos) {
            if(ii==13) ii=0;
            entrada = entrada.concat(d.toString());
            if(ii!=12) entrada = entrada.concat("\t");
            else     entrada = entrada.concat("\n");
            ii++;
        }

        File file = new File(dir, name);
        try {
            Toast.makeText(AGWActivity.this, "Saving file ...", Toast.LENGTH_LONG).show();

            outputStream = new FileOutputStream(file);
            outputStream.write(entrada.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void salvarDados2(String dir) {

        String name = Nome.getText().toString();

        FileOutputStream outputStream;
        String entrada = new String();


        File file = new File(dir, name);
        try {
            Toast.makeText(AGWActivity.this, "Saving file ...", Toast.LENGTH_LONG).show();

            outputStream = new FileOutputStream(file);
            int ii=0;
            for (Double d : DadosSalvos) {

                entrada = d.toString();
                if (ii<12) {
                    entrada = entrada.concat("\t");
                    ii++;
                }
                else{
                    entrada = entrada.concat("\n");
                    ii=0;
                }
                outputStream.write(entrada.getBytes());

            }

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if (aux) {

            estadoDoSensor.setSensor(event.sensor.getType(), event.values);//getvalues

            if (estadoDoSensor.todosAtivos()) {
                if(COUNTER==OFFSET) Toast.makeText(AGWActivity.this, "Iniciado", Toast.LENGTH_LONG).show();

                if(OFFSET<COUNTER) {
                    if (current_timeAcel == 0) {
                        this.current_timeAcel = event.timestamp; //The time in nanosecond at which the event happened
                    }
                    tns = (event.timestamp - current_timeAcel) / 1000000000.0;

                    DadosSalvos.add(tns);

                    DadosSalvos.add(estadoDoSensor.getAcc(0));
                    DadosSalvos.add(estadoDoSensor.getAcc(1));
                    DadosSalvos.add(estadoDoSensor.getAcc(2));

                    DadosSalvos.add(estadoDoSensor.getGiro(0));
                    DadosSalvos.add(estadoDoSensor.getGiro(1));
                    DadosSalvos.add(estadoDoSensor.getGiro(2));

                    DadosSalvos.add(estadoDoSensor.getGravi(0));
                    DadosSalvos.add(estadoDoSensor.getGravi(1));
                    DadosSalvos.add(estadoDoSensor.getGravi(2));

                    DadosSalvos.add(estadoDoSensor.getContAcel() - 1.0);
                    DadosSalvos.add(estadoDoSensor.getContGiro() - 1.0);
                    DadosSalvos.add(estadoDoSensor.getContGravi() - 1.0);
                }
                else{
                    COUNTER=COUNTER+1;
                }
                estadoDoSensor.desativarTodos();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {



    }
}
