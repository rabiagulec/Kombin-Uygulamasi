package tr.edu.yildiz.rabiagulecproje;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class EtkinlikEkleActivity extends AppCompatActivity {

    public ArrayList<Kiyafet> kiyafetListesi;
    public static Etkinlik etkinlik= new Etkinlik (0,"","","","");
    public static HashSet<Integer> aSet= new HashSet<>(0);
    public static Kiyafet yeniKiyafet;
    private KiyafetAdapter anAdapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkinlik_ekle);
        Button kaydet = (Button) findViewById(R.id.etkinligiKaydet);
        Button geriDon= (Button) findViewById(R.id.geriDonEtkinlik);
        Button kiyafetEkle= (Button) findViewById(R.id.etkinligeKiyafetEkle);
        EditText tur= (EditText) findViewById(R.id.turEtkinlik);
        EditText ad= (EditText) findViewById(R.id.adEtkinlik);
        EditText tarih= (EditText) findViewById(R.id.tarihEtkinlik);
        EditText mekan= (EditText) findViewById(R.id.yerEtkinlik);
        RecyclerView kiyafetler = (RecyclerView) findViewById(R.id.etkinlikKiyafetler);
        VeriTabani vt = new VeriTabani(this);
        geriDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                finish();
            }
        });
        kiyafetler.setHasFixedSize(true);
        kiyafetler.setLayoutManager(new GridLayoutManager(this, 2));
        kiyafetListesi= new ArrayList<>(0);
        anAdapter = new KiyafetAdapter(kiyafetListesi);
        kiyafetler.setAdapter(anAdapter);

        ad.setText(etkinlik.getEtkinlikAdi());
        tarih.setText(etkinlik.getEtkinlikTarihi());
        mekan.setText(etkinlik.getEtkinlikMekani());
        tur.setText(etkinlik.getEtkinlikTuru());
        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kiyafetListesi.size()==0||tur.getText().toString().equals("")||ad.getText().toString().equals("")||
                tarih.getText().toString().equals("")||mekan.getText().toString().equals("")){
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
                    Etkinlik etkinlik =new Etkinlik(0,ad.getText().toString(),tur.getText().toString(),
                            tarih.getText().toString(),mekan.getText().toString());
                    int no=vt.etkinlikEkle(etkinlik);
                    etkinlik.setEtkinlikNo(no);
                    for(int i=0; i<kiyafetListesi.size(); i++){
                        vt.etkinligeKiyafetEkle(no,kiyafetListesi.get(i).getKiyafetID());
                    }
                    EtkinlikFragment.yeniEtkinlik=etkinlik;
                    Toast.makeText(EtkinlikEkleActivity.this, "Etkinlik Eklendi", Toast.LENGTH_LONG).show();
                    clear();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        kiyafetEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KiyafetSecActivity.activity=2;
                if(!ad.getText().toString().equals("")){
                    etkinlik.setEtkinlikAdi(ad.getText().toString());
                }
                if(!mekan.getText().toString().equals("")){
                    etkinlik.setEtkinlikMekani(mekan.getText().toString());
                }
                if(!tarih.getText().toString().equals("")){
                    etkinlik.setEtkinlikTarihi(tarih.getText().toString());
                }
                if(!tur.getText().toString().equals("")){
                    etkinlik.setEtkinlikTuru(tur.getText().toString());
                }
                Intent anIntent = new Intent(EtkinlikEkleActivity.this, KiyafetSecActivity.class);
                startActivityForResult(anIntent,1);

            }
        });



    }

    public void clear (){
        etkinlik=new Etkinlik(0,"","","","");
        aSet=new HashSet<>(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==1){
            kiyafetListesi.add(0,yeniKiyafet);
            anAdapter.notifyItemInserted(0);
        }
    }
}