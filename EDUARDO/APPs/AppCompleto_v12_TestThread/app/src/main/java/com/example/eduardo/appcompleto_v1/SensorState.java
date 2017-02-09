package com.example.eduardo.appcompleto_v1;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import java.util.ArrayList;

/**
 * Created by Eduardo on 02/07/2016.
 */
public class SensorState {

    private SensorInfo acelerometro;
    private SensorInfo giro;
    private SensorInfo gravi;

    public  SensorState() {
        acelerometro = new SensorInfo();
        giro = new SensorInfo();
        gravi = new SensorInfo();
    }

    public boolean todosAtivos() {

        return (acelerometro.ativado);// && giro.ativado); // && gravi.ativado);
    }

    public void desativarTodos() {


        acelerometro.cont=1;
        giro.cont=1;
        gravi.cont=1;

        acelerometro.ativado = false;
        giro.ativado = false;
        gravi.ativado = false;
    }

    public double getAcc(int i) {
        return acelerometro.valores[i];
    }

    public double getGiro(int i) {
        return giro.valores[i];
    }

    public double getGravi(int i) {
        return gravi.valores[i];
    }

    public double getContAcel() {
        return acelerometro.cont ;
    }

    public double getContGiro() {
        return giro.cont ;
    }

    public double getContGravi() {
        return gravi.cont ;
    }


    public void setSensor(int type, float[] values){

        switch (type) {
            case Sensor.TYPE_ACCELEROMETER:
                acelerometro.ativado = true;
                if(acelerometro.cont==1) {
                    acelerometro.valores[0] = values[0];
                    acelerometro.valores[1] = values[1];
                    acelerometro.valores[2] = values[2];
                }else{
                    acelerometro.valores[0] = (values[0]+(acelerometro.cont-1)*acelerometro.valores[0])/acelerometro.cont;
                    acelerometro.valores[1] = (values[1]+(acelerometro.cont-1)*acelerometro.valores[1])/acelerometro.cont;
                    acelerometro.valores[2] = (values[2]+(acelerometro.cont-1)*acelerometro.valores[2])/acelerometro.cont;
                }
                acelerometro.cont = acelerometro.cont + 1;
                break;

            case Sensor.TYPE_GYROSCOPE:
                giro.ativado = true;

                if(giro.cont==1) {
                    giro.valores[0] = values[0];
                    giro.valores[1] = values[1];
                    giro.valores[2] = values[2];
                }else{
                    giro.valores[0] = (values[0]+(giro.cont-1)*giro.valores[0])/giro.cont;
                    giro.valores[1] = (values[1]+(giro.cont-1)*giro.valores[1])/giro.cont;
                    giro.valores[2] = (values[2]+(giro.cont-1)*giro.valores[2])/giro.cont;
                }
                giro.cont = giro.cont + 1;
                break;

            case Sensor.TYPE_GRAVITY:

                gravi.ativado = true;


                if(gravi.cont==1) {
                    gravi.valores[0] = values[0];
                    gravi.valores[1] = values[1];
                    gravi.valores[2] = values[2];
                }else{
                    gravi.valores[0] = (values[0]+(gravi.cont-1)*gravi.valores[0])/gravi.cont;
                    gravi.valores[1] = (values[1]+(gravi.cont-1)*gravi.valores[1])/gravi.cont;
                    gravi.valores[2] = (values[2]+(gravi.cont-1)*gravi.valores[2])/gravi.cont;
                }

                gravi.cont = gravi.cont + 1;
                break;
        }
    }



    private class SensorInfo {
        boolean ativado;
        int cont;
         float [] valores;
        SensorInfo () {
            valores= new float[3];
            cont=1;

        }
    }
}
