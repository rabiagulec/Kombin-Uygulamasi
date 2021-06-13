package tr.edu.yildiz.rabiagulecproje;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class KiyafetSecActivity extends AppCompatActivity {

    public static int activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiyafet_sec);

        Spinner aSpinner = findViewById(R.id.spinner);
        VeriTabani vt = new VeriTabani(getApplicationContext());
        ArrayList<Cekmece> cekmeceler = vt.cekmeceleriGetir(MainActivity.kullaniciID);
        ArrayList<String> cekmeceIsimleri = new ArrayList<>(0);
        for(int i=0; i<cekmeceler.size(); i++){
            cekmeceIsimleri.add(cekmeceler.get(i).getCekmeceAdi());
        }
        ArrayAdapter<String> arrAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,cekmeceIsimleri);
        aSpinner.setAdapter(arrAdapter);

        RecyclerView kiyafetSecList = findViewById(R.id.kiyafetSecList);
        kiyafetSecList.setHasFixedSize(true);
        kiyafetSecList.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        ArrayList<Kiyafet> kiyafetler;
        aSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Kiyafet> kiyafetler =vt.kiyafetleriGetir(cekmeceler.get(position).getCekmeceNo(), MainActivity.kullaniciID);
                KiyafetAdapter anAdapter=new KiyafetAdapter(kiyafetler);
                kiyafetSecList.setAdapter(anAdapter);
                anAdapter.setOnButtonListener(new KiyafetAdapter.OnItemClicked() {
                    @Override
                    public void onClicked(int position) {
                        if(activity==1){
                            System.out.println("kiyafet odasi activity");
                            vt.kombineKiyafetEkle(KombinFragment.seciliKombin.getId(), kiyafetler.get(position).getKiyafetID(), KabinOdasiActivity.seciliBolge);
                            KabinOdasiActivity.yeniKiyafet=kiyafetler.get(position).getKiyafetID();
                            KabinOdasiActivity.yeniFoto=kiyafetler.get(position).getFoto();
                            setResult(RESULT_OK);
                            finish();
                        } else if(activity == 2){
                            if(EtkinlikEkleActivity.aSet.contains(kiyafetler.get(position).getKiyafetID())){
                                Toast.makeText(KiyafetSecActivity.this, "Bu kıyafet zaten etkinlikte kayıtlı", Toast.LENGTH_LONG).show();
                            } else {
                                EtkinlikEkleActivity.yeniKiyafet=kiyafetler.get(position);
                                EtkinlikEkleActivity.aSet.add(kiyafetler.get(position).getKiyafetID());
                                setResult(RESULT_OK);
                                finish();
                            }
                        } else {
                            if(EtkinlikGuncelleActivity.aSet.contains(kiyafetler.get(position).getKiyafetID())){
                                Toast.makeText(KiyafetSecActivity.this, "Bu kıyafet zaten etkinlikte kayıtlı", Toast.LENGTH_LONG).show();
                            } else {
                                vt.etkinligeKiyafetEkle(EtkinlikFragment.seciliEtkinlik, kiyafetler.get(position).getKiyafetID());
                                EtkinlikGuncelleActivity.yeniKiyafet = kiyafetler.get(position);
                                EtkinlikGuncelleActivity.aSet.add(kiyafetler.get(position).getKiyafetID());
                                setResult(RESULT_OK);
                                finish();
                            }
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}