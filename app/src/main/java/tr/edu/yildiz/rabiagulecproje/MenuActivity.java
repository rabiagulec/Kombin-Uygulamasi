package tr.edu.yildiz.rabiagulecproje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity {

    DrawerLayout menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        menu = (DrawerLayout) findViewById(R.id.menuLayout);
        menu.closeDrawer(GravityCompat.START);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, menu, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        menu.addDrawerListener(toggle);
        toggle.syncState();

        VeriTabani vt = new VeriTabani(getApplicationContext());
        com.google.android.material.navigation.NavigationView v = findViewById(R.id.navigation);
        View li = getLayoutInflater().inflate(R.layout.menu_baslik, v);
        TextView ad = li.findViewById(R.id.menuAd);
        ImageView foto = li.findViewById(R.id.menuFoto);

        foto.setImageBitmap(vt.fotoEkle(MainActivity.kullaniciID));
        ad.setText(vt.adEkle(MainActivity.kullaniciID));

        Intent intent = getIntent();
        int no = intent.getIntExtra("comingfrom", -1);
        if(no== -1|| no==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new DolapFragment()).commit();
        } else if (no==2){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new KombinFragment()).commit();
        } else if(no==3){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new EtkinlikFragment()).commit();
        }

        v.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.dolabim){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new DolapFragment()).commit();
                } else if (item.getItemId()==R.id.kombinlerim){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new KombinFragment()).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new EtkinlikFragment()).commit();
                }

                menu.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(menu.isDrawerOpen(GravityCompat.START)){
            menu.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}