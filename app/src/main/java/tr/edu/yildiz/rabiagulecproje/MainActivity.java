package tr.edu.yildiz.rabiagulecproje;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static int kullaniciID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signUp = (Button) findViewById(R.id.signUp);
        Button giris = (Button) findViewById(R.id.giris);
        EditText ad = (EditText) findViewById(R.id.editTextUserName);
        EditText sifre = (EditText) findViewById(R.id.editTextPass);

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VeriTabani vt = new VeriTabani(getApplicationContext());

                if(vt.isInDB(ad.getText().toString())){
                    int no=vt.sifreDogruMu(ad.getText().toString(),sifre.getText().toString());
                    if(no != -1){
                        kullaniciID=no;
                        Intent anIntent = new Intent(MainActivity.this, MenuActivity.class);
                        startActivity(anIntent);
                    } else {
                        kutuGoster("Şifreniz yanlış");
                    }

                } else {
                    kutuGoster("Bu kullanıcı adı kayıtlı değildir");
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent anIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(anIntent);
            }
        });
    }

    public void kutuGoster(String s){
        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setMessage(s);
        adb.setCancelable(true);

        adb.setPositiveButton(
                "Tamam",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = adb.create();
        alert.show();
    }
}