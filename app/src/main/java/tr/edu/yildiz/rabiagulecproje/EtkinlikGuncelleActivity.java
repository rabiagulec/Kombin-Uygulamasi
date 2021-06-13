package tr.edu.yildiz.rabiagulecproje;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;

public class EtkinlikGuncelleActivity extends AppCompatActivity {
    ArrayList<Kiyafet> kiyafetler;
    KiyafetAdapter anAdapter;
    public static Kiyafet yeniKiyafet;
    public static HashSet<Integer> aSet= new HashSet<>(0);


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        aSet=new HashSet<>(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkinlik_guncelle);
        Button geriDon= (Button) findViewById(R.id.geriDonEtkinlikGuncelle);
        Button guncelle= (Button) findViewById(R.id.etkinligiGuncelle);
        Button kiyafetEkle= (Button) findViewById(R.id.etkinligeKiyafetEkleGuncelle);

        geriDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSet=new HashSet<>(0);
                finish();
            }
        });



        EditText tur=(EditText) findViewById(R.id.turEtkinlikGuncelle);
        EditText ad=(EditText) findViewById(R.id.adEtkinlikGuncelle);
        EditText tarih=(EditText) findViewById(R.id.tarihEtkinlikGuncelle);
        EditText yer=(EditText) findViewById(R.id.yerEtkinlikGuncelle);
        RecyclerView kiyafetListe = findViewById(R.id.etkinlikKiyafetlerGuncelle);

        VeriTabani vt = new VeriTabani(this);
        Etkinlik etkinlik = vt.etkinlikGetir(EtkinlikFragment.seciliEtkinlik);
        tur.setText(etkinlik.getEtkinlikTuru());
        ad.setText(etkinlik.getEtkinlikAdi());
        tarih.setText(etkinlik.getEtkinlikTarihi());
        yer.setText(etkinlik.getEtkinlikMekani());

        kiyafetler = vt.etkinligeGoreKiyafetleriGetir(EtkinlikFragment.seciliEtkinlik);
        for(int i=0; i<kiyafetler.size(); i++){
            aSet.add(kiyafetler.get(i).getKiyafetID());
        }
        anAdapter = new KiyafetAdapter(kiyafetler);
        kiyafetListe.setHasFixedSize(true);
        kiyafetListe.setLayoutManager(new GridLayoutManager(this,2));
        kiyafetListe.setAdapter(anAdapter);
        kiyafetEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KiyafetSecActivity.activity=3;
                Intent intent = new Intent (EtkinlikGuncelleActivity.this, KiyafetSecActivity.class);
                startActivityForResult(intent,1);
            }
        });

        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etkinlik.setEtkinlikTuru(tur.getText().toString());
                etkinlik.setEtkinlikTarihi(tarih.getText().toString());
                etkinlik.setEtkinlikMekani(yer.getText().toString());
                etkinlik.setEtkinlikAdi(ad.getText().toString());
                etkinlik.setEtkinlikNo(EtkinlikFragment.seciliEtkinlik);
                vt.etkinligiGuncelle(etkinlik);
                EtkinlikFragment.yeniEtkinlik=etkinlik;
                aSet=new HashSet<>(0);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==1){
            kiyafetler.add(0,yeniKiyafet);
            anAdapter.notifyItemInserted(0);
        }
    }
}