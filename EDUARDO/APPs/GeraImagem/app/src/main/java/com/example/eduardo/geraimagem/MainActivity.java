package com.example.eduardo.geraimagem;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText caminho,Nome;

    GeraMatriz G;

    int A[][];

    Bitmap B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        caminho = (EditText) findViewById(R.id.editText);
        Nome = (EditText) findViewById(R.id.editText2);

        Button btn1 = (Button) findViewById(R.id.button1);
        btn1.setEnabled(false);


        CheckPermissions();

        if (CheckPermissions()==false) {

            RequestPermissions();
        }


    }
    public void onClickCarregar(View v) {

        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setEnabled(false);


        String name = caminho.getText().toString();

        G = new GeraMatriz("/sdcard/Android/data/com.example.eduardo.appcompleto_v1/files/"+name,780);

        this.A = G.Convert(1,2);

        Toast.makeText(MainActivity.this, "File loaded ...", Toast.LENGTH_LONG).show();

        Button btn1 = (Button) findViewById(R.id.button1);
        btn1.setEnabled(true);


    }



    public void onClickGerar(View v) {

        this.B = G.bitmapFromArray(this.A);

        escreveImagens(this.B);

        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setEnabled(true);

        Toast.makeText(MainActivity.this, "Image saved ...", Toast.LENGTH_LONG).show();
    }


    public void escreveImagens(Bitmap bmp){
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            byte[] bytes = stream.toByteArray();
            String nome = Nome.getText().toString();
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), nome);

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Teste permissões

    private boolean CheckPermissions(){
        String[] permissions = new String[2];
        permissions[0] = Manifest.permission.READ_EXTERNAL_STORAGE;
        permissions[1] = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        int res = 0;
        for (int L = 0; L < permissions.length; L++) {
            res = checkCallingOrSelfPermission(permissions[L]);
            if(!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }

        return true;
    }

    private void RequestPermissions(){
        String[] permissions = new String[2];
        permissions[0] = Manifest.permission.READ_EXTERNAL_STORAGE;
        permissions[1] = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        if(Build.VERSION.SDK_INT >= 23){
            requestPermissions(permissions, 1);
        }
    }



}
