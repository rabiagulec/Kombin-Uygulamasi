package tr.edu.yildiz.rabiagulecproje;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class KiyafetEkleActivity extends AppCompatActivity {
    Bitmap bitmap;
    Uri kiyafetUri;
    ImageView kiyafetFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiyafet_ekle);
        int cekmeceID = DolapFragment.seciliCekmece;

        kiyafetFoto = (ImageView) findViewById(R.id.kiyafetFoto);
        kiyafetFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent anIntent = new Intent();
                anIntent.setType("image/*");
                anIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(anIntent,"Kıyafetin fotoğrafını ekleyin"),1);
            }
        });

        Button kiyafetiKaydet = (Button) findViewById(R.id.kiyafetiKaydet);
        EditText tur = (EditText) findViewById(R.id.tur);
        EditText ucret = (EditText) findViewById(R.id.ucret);
        EditText desen = (EditText) findViewById(R.id.desen);
        EditText renk = (EditText) findViewById(R.id.renk);
        EditText alinmaTarihi = (EditText) findViewById(R.id.alinmaTarihi);
        VeriTabani vt = new VeriTabani(this);
        kiyafetiKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tur.getText().toString().equals("")||ucret.getText().toString().equals("")||desen.getText().toString().equals("")||renk.getText().toString().equals("")||
                alinmaTarihi.getText().toString().equals("")||bitmap==null){
                    AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
                    adb.setMessage("Lütfen bütün değerleri giriniz.");
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
                } else {
                    int no=vt.kiyafetEkle(tur.getText().toString(), cekmeceID, bitmap, renk.getText().toString(), desen.getText().toString(),
                            alinmaTarihi.getText().toString(), Integer.parseInt(ucret.getText().toString()), MainActivity.kullaniciID);
                    Toast.makeText(getApplicationContext(), "Kıyafet çekmeceye eklendi", Toast.LENGTH_LONG).show();
                    CekmeceActivity.yeniKiyafet=new Kiyafet(no, tur.getText().toString(),cekmeceID,bitmap,renk.getText().toString(),
                            desen.getText().toString(), alinmaTarihi.getText().toString(), Integer.parseInt(ucret.getText().toString()), MainActivity.kullaniciID);
                    //Intent intent = new Intent(KiyafetEkleActivity.this, CekmeceActivity.class);
                    //startActivity(intent);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        Button geriDon = (Button) findViewById(R.id.geriDonKiyafet);
        geriDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode == RESULT_OK){
            kiyafetUri=data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),kiyafetUri);
                kiyafetFoto.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}