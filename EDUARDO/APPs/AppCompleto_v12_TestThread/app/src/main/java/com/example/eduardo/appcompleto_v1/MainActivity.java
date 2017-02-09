package com.example.eduardo.appcompleto_v1;

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
import java.util.ArrayList;

import net.trucomanx.pdsplibj.pdsra.*;
import net.trucomanx.pdsplibj.pdsextras.*;
import fun.eduardo.funcoes.*;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private double current_timeAcel=0;
    private double tns,tns_aux,dt,Db,Sx,Sy,Sz;
    private String dir;


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
    int CONT, CONT2;
    int amostras=1500;

    ArrayList<Double> DadosSalvos;

    double[] ar,wr;

    // Variáveis de saída das funçoes

    PdsMatrix a,g0,w,ruido0,M,a_n,a_n2,ruido_d;

    //Variáveis classes utilizadas

    setup S;

    matriz_transf_rt MA;

    decide_g DEG;

    decide_r DER;

    detectordepausa_rt DETEC;

    Integrador INT;

    // DecimalFormat decimal = new DecimalFormat( "0.###" );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //Iniciliar variáveis de saída das funções

        a = new PdsMatrix(1,3);
        g0 = new PdsMatrix(1,3);
        w = new PdsMatrix(1,3);
        ruido0 = new PdsMatrix(1,1);
        M = new PdsMatrix(3,3);
        a_n = new PdsMatrix(1,3);
        a_n2 = new PdsMatrix(1,3);
        ruido_d = new PdsMatrix(1,1);

        //Iniciliar variáveis das classes

        S = new setup(amostras);
        MA = new matriz_transf_rt();
        DEG = new decide_g();
        DER = new decide_r();
        DETEC = new detectordepausa_rt("/sdcard/Download/CoeficientesFiltros/ValoresH_so_pausa_fir.dat","/sdcard/Download/CoeficientesFiltros/ValoresH_so_pausa_media.dat","/sdcard/Download/CoeficientesFiltros/ValoresH_so_ativo_fir.dat");
        INT = new Integrador(0);

        estadoDoSensor = new SensorState();

        DadosSalvos = new ArrayList<Double>();

        aux = true;

        Estado.setText("Iniciado");
        Button btn2 = (Button) findViewById(R.id.botao2);
        btn2.setEnabled(true);

        COUNTER=0;
        CONT=1;
        dt=0;
        Db=0;
        Sx=0;
        Sy=0;
        Sz=0;
        CONT2=0;

    }

    public void onClickSalvar(View v){

        Button btn2 = (Button) findViewById(R.id.botao2);
        btn2.setEnabled(false);
        aux = false;

        dir = v.getContext().getExternalFilesDir(null).getAbsolutePath();

        salvarDados2();


        Estado.setText("Aguardando");

        DadosSalvos.clear();

        Button btn = (Button) findViewById(R.id.botao1);
        btn.setEnabled(true);
    }


    //Função para salvar os dados

    private void salvarDados2() {

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String name = Nome.getText().toString();

                        FileOutputStream outputStream;
                        String entrada = new String();


                        File file = new File(dir, name);
                        try {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Saving file ...", Toast.LENGTH_LONG).show();
                                }
                            });


                            outputStream = new FileOutputStream(file);
                            int ii = 0;
                            for (Double d : DadosSalvos) {

                                entrada = d.toString();
                                if (ii < 3) {
                                    entrada = entrada.concat("\t");
                                    ii++;
                                } else {
                                    entrada = entrada.concat("\n");
                                    ii = 0;
                                }
                                outputStream.write(entrada.getBytes());

                            }

                            outputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();


    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if (aux) {

            estadoDoSensor.setSensor(event.sensor.getType(), event.values);//getvalues

            if (estadoDoSensor.todosAtivos()) {
                if(COUNTER==OFFSET) Toast.makeText(MainActivity.this, "Iniciado", Toast.LENGTH_LONG).show();

                if(OFFSET<COUNTER) {
                    if (current_timeAcel == 0) {
                        this.current_timeAcel = event.timestamp; //The time in nanosecond at which the event happened
                    }
                    tns = (event.timestamp - current_timeAcel) / 1000000000.0;

                    ar = new double [3];
                    wr = new double [3];

                    ar[0]=estadoDoSensor.getAcc(0);
                    ar[1]=estadoDoSensor.getAcc(1);
                    ar[2]=estadoDoSensor.getAcc(2);

                    wr[0]=estadoDoSensor.getGiro(0);
                    wr[1]=estadoDoSensor.getGiro(1);
                    wr[2]=estadoDoSensor.getGiro(2);


                    if (CONT == 1) {
                        tns_aux = tns;
                        dt = tns;
                        CONT=0;
                    } else {
                        dt = tns - tns_aux;
                        tns_aux=tns;
                        }

                    //Processo

                    //Atualização do estado

                    if (CONT2<=amostras){

                        Estado.setText("Calibrando!");

                        CONT2++;

                    }
                    if (CONT2==(amostras+1)){

                        Toast.makeText(MainActivity.this, "Dispositivo Calibrado", Toast.LENGTH_LONG).show();

                        Estado.setText("Pronto!");

                        CONT2++;
                    }

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            //Inicialização

                            ruido0 = S.setup_rt(ar,wr);
                            a = S.get_a();
                            g0 = S.get_g0();
                            w = S.get_w();

                            //Matriz

                            M = MA.matriz_transf(Db,w,dt);

                            //Atualizar matriz

                            a_n = (M.MulNew(a.TransposeNew())).TransposeNew();

                            //Calcular e decidir gravidade

                            a_n2 = DEG.decide_g_rt(a_n,g0,Db);

                            //Calcular e decidir ruido

                            ruido_d = DER.decide_r_rt(a_n,ruido0,Db);

                            //Detector de pausa

                            Db = DETEC.detector_pausa(a_n2,ruido_d);

                            //Integral

                            INT.integra_rt(dt,a_n2,Db);

                            Sx = INT.get_Sx();
                            Sy = INT.get_Sy();
                            Sz = INT.get_Sz();

                            DadosSalvos.add(dt);
                            DadosSalvos.add(Sx);
                            DadosSalvos.add(Sy);
                            DadosSalvos.add(Sz);


                        }
                    });

                    t.start();


                    /*DadosSalvos.add(tns);

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
                    DadosSalvos.add(estadoDoSensor.getContGravi() - 1.0);*/
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
