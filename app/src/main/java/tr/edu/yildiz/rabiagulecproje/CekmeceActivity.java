package tr.edu.yildiz.rabiagulecproje;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CekmeceActivity extends AppCompatActivity {

    public static Kiyafet seciliKiyafet;
    public static Kiyafet yeniKiyafet;
    public static boolean guncelle=false;
    public int selectedPosition;
    private ArrayList <Kiyafet> kiyafetler;
    private VeriTabani vt;
    private KiyafetAdapter anAdapter;
    private RecyclerView kiyafetlerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cekmece);
        vt = new VeriTabani(this);
        int cekmeceNo=DolapFragment.seciliCekmece;
        kiyafetler = vt.kiyafetleriGetir(cekmeceNo, MainActivity.kullaniciID);
        Button cekmeceyiSil = (Button) findViewById(R.id.cekmeceyiSil);
        cekmeceyiSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(CekmeceActivity.this);
                adb.setMessage("Çekmeceyi silerseniz çekmecedeki kıyafetleriniz de silinecek. Emin misiniz?");
                adb.setCancelable(true);

                adb.setPositiveButton(
                        "Evet",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                vt.cekmeceSil(MainActivity.kullaniciID, cekmeceNo);
                                Toast.makeText(getApplicationContext(), "Çekmece Silindi", Toast.LENGTH_LONG).show();
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

        Button kiyafetEkle = (Button) findViewById(R.id.kiyafetEkle);
        kiyafetEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goIntent = new Intent (CekmeceActivity.this, KiyafetEkleActivity.class);
                startActivityForResult(goIntent, 1);
            }
        });

        Button geriDon = (Button) findViewById(R.id.geriDonCekmece);
        geriDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(kiyafetler.size()==0){
            findViewById(R.id.kiyafetYok).setVisibility(View.VISIBLE);
        }else{
           listeOlustur();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1){
                kiyafetler.add(0,yeniKiyafet);
                if(anAdapter!=null){
                    anAdapter.notifyItemInserted(0);
                } else {
                    listeOlustur();
                }
            } else if(requestCode==2){
                if(guncelle){
                    kiyafetler.set(selectedPosition,seciliKiyafet);
                    anAdapter.notifyDataSetChanged();
                } else {
                    kiyafetler.remove(selectedPosition);
                    anAdapter.notifyItemRemoved(selectedPosition);
                    if(kiyafetler.size()==0){
                        findViewById(R.id.kiyafetYok).setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }
    private void listeOlustur(){
        anAdapter = new KiyafetAdapter(kiyafetler);
        kiyafetlerList = (RecyclerView) findViewById(R.id.kiyafetlerList);
        kiyafetlerList.setHasFixedSize(true);
        kiyafetlerList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        kiyafetlerList.setAdapter(anAdapter);
        findViewById(R.id.kiyafetYok).setVisibility(View.GONE);

        anAdapter.setOnButtonListener(new KiyafetAdapter.OnItemClicked() {
            @Override
            public void onClicked(int position) {
                seciliKiyafet=kiyafetler.get(position);
                selectedPosition=position;
                Intent anIntent = new Intent(CekmeceActivity.this, KiyafetGuncelleActivity.class);
                startActivityForResult(anIntent,2);
            }
        });
    }
}