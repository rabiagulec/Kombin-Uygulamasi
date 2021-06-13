package tr.edu.yildiz.rabiagulecproje;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class KabinOdasiActivity extends AppCompatActivity {
    public static int seciliBolge;
    public static int yeniKiyafet;
    public static Bitmap yeniFoto;
    private VeriTabani vt;
    ImageView basustu;
    ImageView surat;
    ImageView ustbeden;
    ImageView altbeden;
    ImageView ayak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabin_odasi);

        basustu= (ImageView) findViewById(R.id.basustu);
        surat= (ImageView) findViewById(R.id.surat);
        ustbeden= (ImageView) findViewById(R.id.ustbeden);
        altbeden= (ImageView) findViewById(R.id.altbeden);
        ayak= (ImageView) findViewById(R.id.ayak);
        vt = new VeriTabani(getApplicationContext());
        Button geriDon = findViewById(R.id.geriDonKabin);


        geriDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button kombiniSil = (Button) findViewById(R.id.kombiniSil);
        kombiniSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(KabinOdasiActivity.this);
                adb.setMessage("Emin misiniz?");
                adb.setCancelable(true);

                adb.setPositiveButton(
                        "Evet",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                vt.kombiniSil(KombinFragment.seciliKombin.getId());
                                KombinFragment.silindi=true;
                                setResult(RESULT_OK);
                                finish();
                            }
                        });

                adb.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = adb.create();
                alert.show();
            }
        });

        surat.setImageBitmap(vt.fotoEkle(MainActivity.kullaniciID));

        Bitmap[] kombin = vt.kombinGetir(KombinFragment.seciliKombin.getId());

        if(kombin[0] != null){
            basustu.setImageBitmap(kombin[0]);
        }
        if(kombin[1] != null){
            ustbeden.setImageBitmap(kombin[1]);
        }
        if(kombin[2] != null){
            altbeden.setImageBitmap(kombin[2]);
        }
        if(kombin[3] != null){
            ayak.setImageBitmap(kombin[3]);
        }

        basustu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seciliBolge=0;
                kiyafetFotosuGetir();
            }
        });

        ustbeden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seciliBolge=1;
                kiyafetFotosuGetir();
            }
        });

        altbeden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seciliBolge=2;
                kiyafetFotosuGetir();
            }
        });

        ayak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seciliBolge=3;
                kiyafetFotosuGetir();
            }
        });
    }


    public void kiyafetFotosuGetir (){
        KiyafetSecActivity.activity=1;
        Intent anIntent = new Intent(KabinOdasiActivity.this, KiyafetSecActivity.class);
        startActivityForResult(anIntent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RESULT_OK==resultCode&&requestCode==1){
            if(seciliBolge==0){
                basustu.setImageBitmap(yeniFoto);
            } else if(seciliBolge==1){
                ustbeden.setImageBitmap(yeniFoto);
            } else if (seciliBolge==2){
                altbeden.setImageBitmap(yeniFoto);
            } else {
                ayak.setImageBitmap(yeniFoto);
            }
        }
    }
}