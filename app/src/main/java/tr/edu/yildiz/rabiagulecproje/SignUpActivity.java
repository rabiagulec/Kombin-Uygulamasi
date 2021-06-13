package tr.edu.yildiz.rabiagulecproje;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {
    EditText kullaniciAdi;
    EditText pass1;
    EditText pass2;
    ImageView fotoEkle;
    Bitmap bitmap;
    Uri fotoUri;
    TextView bilgi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button uyeOl = (Button) findViewById(R.id.uyeOl);
        kullaniciAdi = (EditText) findViewById(R.id.signUpName);
        pass1 = (EditText) findViewById(R.id.signUpPass1);
        pass2 = (EditText) findViewById(R.id.signUpPass2);
        bilgi = (TextView) findViewById(R.id.bilgi);
        fotoEkle = (ImageView) findViewById(R.id.fotoekle);

        fotoEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent anIntent = new Intent();
                anIntent.setType("image/*");
                anIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(anIntent,"Yüz fotonuzu ekleyin"),1);
            }
        });


        uyeOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                VeriTabani vt = new VeriTabani(getApplicationContext());
                if(kullaniciAdi.getText().toString().equals("")){
                    bilgi.setText("Lütfen kullanıcı adınızı giriniz");
                    kullaniciAdi.setElevation(4);
                    kullaniciAdi.setTranslationZ(50);
                } else if(pass1.getText().toString().equals("")){
                    bilgi.setText("Lütfen şifrenizi giriniz");
                    pass1.setElevation(4);
                    pass1.setTranslationZ(50);
                } else if(pass2.getText().toString().equals("")){
                    bilgi.setText("Lütfen şifrenizi bir kere daha giriniz");
                    pass2.setElevation(4);
                    pass2.setTranslationZ(50);
                } else if(!pass1.getText().toString().equals(pass2.getText().toString())){
                    System.out.println(pass1.getText().toString());
                    System.out.println(pass2.getText().toString());
                    bilgi.setText("Girdiğiniz şifreler uyuşmamaktadır");
                    pass1.setElevation(4);
                    pass1.setTranslationZ(30);
                    pass2.setElevation(4);
                    pass2.setTranslationZ(30);
                } else if(vt.isInDB(kullaniciAdi.getText().toString())){
                    bilgi.setText("Bu kullanıcı adı mevcuttur. Başka bir ad seçiniz");
                    kullaniciAdi.setElevation(4);
                    kullaniciAdi.setTranslationZ(50);
                } else if (bitmap == null){
                    bilgi.setText("Lütfen yüzünüzün fotoğrafını ekleyiniz");
                    fotoEkle.setElevation(4);
                    fotoEkle.setTranslationZ(50);
                } else {
                    vt.kullaniciEkle(kullaniciAdi.getText().toString(), pass1.getText().toString(), bitmap);
                    Toast.makeText(getApplicationContext(),"Kaydınız tamamlandı", Toast.LENGTH_LONG).show();
                    Intent anIntent = new Intent (getApplicationContext(), MainActivity.class);
                    startActivity(anIntent);
                }
            }
        });
    }

    private void clear(){

        kullaniciAdi.setElevation(0);
        kullaniciAdi.setTranslationZ(0);
        pass1.setElevation(0);
        pass1.setTranslationZ(0);
        pass2.setElevation(0);
        pass2.setTranslationZ(0);
        fotoEkle.setElevation(0);
        fotoEkle.setTranslationZ(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode == RESULT_OK){
            fotoUri=data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),fotoUri);
                fotoEkle.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}