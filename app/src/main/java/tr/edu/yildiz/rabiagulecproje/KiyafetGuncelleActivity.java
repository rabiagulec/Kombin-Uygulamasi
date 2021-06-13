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

public class KiyafetGuncelleActivity extends AppCompatActivity {
    Uri fotoUri;
    Bitmap bitmap;
    ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiyafet_guncelle);
        foto = (ImageView) findViewById(R.id.kiyafetFotoGuncelle);
        EditText tur = (EditText) findViewById(R.id.turGuncelle);
        EditText renk = (EditText) findViewById(R.id.renkGuncelle);
        EditText desen = (EditText) findViewById(R.id.desenGuncelle);
        EditText fiyat = (EditText) findViewById(R.id.ucretGuncelle);
        EditText alinmaTarihi = (EditText) findViewById(R.id.alinmaTarihiGuncelle);
        Button geriDon = (Button) findViewById(R.id.geriDonGuncelle);
        Button guncelle = (Button) findViewById(R.id.Guncelle);
        Button kiyafetiSil= (Button) findViewById(R.id.kiyafetiSil);

        foto.setImageBitmap(CekmeceActivity.seciliKiyafet.getFoto());
        bitmap=CekmeceActivity.seciliKiyafet.getFoto();
        tur.setText(CekmeceActivity.seciliKiyafet.getTur());
        renk.setText(CekmeceActivity.seciliKiyafet.getRenk());
        desen.setText(CekmeceActivity.seciliKiyafet.getDesen());
        fiyat.setText(String.valueOf(CekmeceActivity.seciliKiyafet.getFiyat()));
        alinmaTarihi.setText(CekmeceActivity.seciliKiyafet.getAlinmaTarihi());
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent anIntent = new Intent();
                anIntent.setType("image/*");
                anIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(anIntent,"Yüz fotonuzu ekleyin"),1);
            }
        });

        geriDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        VeriTabani vt = new VeriTabani(getApplicationContext());
        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CekmeceActivity.guncelle=true;
                vt.kiyafetGuncelle(new Kiyafet(CekmeceActivity.seciliKiyafet.getKiyafetID(),tur.getText().toString(),0, bitmap,
                        renk.getText().toString(), desen.getText().toString(), alinmaTarihi.getText().toString(),Integer.parseInt(fiyat.getText().toString()),MainActivity.kullaniciID));
                Toast.makeText(getApplicationContext(), "Kıyafet Güncellendi", Toast.LENGTH_LONG).show();
                CekmeceActivity.seciliKiyafet.setAlinmaTarihi(alinmaTarihi.getText().toString());
                CekmeceActivity.seciliKiyafet.setTur(tur.getText().toString());
                CekmeceActivity.seciliKiyafet.setFoto(bitmap);
                CekmeceActivity.seciliKiyafet.setRenk(renk.getText().toString());
                CekmeceActivity.seciliKiyafet.setDesen(desen.getText().toString());
                CekmeceActivity.seciliKiyafet.setAlinmaTarihi(alinmaTarihi.getText().toString());
                CekmeceActivity.seciliKiyafet.setFiyat(Integer.parseInt(fiyat.getText().toString()));
                setResult(RESULT_OK);
                finish();
            }
        });

        kiyafetiSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(KiyafetGuncelleActivity.this);
                adb.setMessage("Emin misiniz?");
                adb.setCancelable(true);

                adb.setPositiveButton(
                        "Evet",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                vt.kiyafetiSil(CekmeceActivity.seciliKiyafet.getKiyafetID());
                                CekmeceActivity.guncelle=false;
                                Toast.makeText(getApplicationContext(), "Kıyafet Silindi", Toast.LENGTH_LONG).show();
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode == RESULT_OK){
            fotoUri=data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),fotoUri);
                foto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}