package tr.edu.yildiz.rabiagulecproje;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class VeriTabani extends SQLiteOpenHelper {

    Context aContext;

    public VeriTabani(Context context) {
        super(context,  "VT", null, 1);
        aContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE kullanicilar (id INTEGER PRIMARY KEY AUTOINCREMENT, ad text, sifre text, foto blob)");
        db.execSQL("CREATE TABLE cekmeceler (id INTEGER PRIMARY KEY AUTOINCREMENT, ad text, kullaniciID INTEGER)");
        db.execSQL("CREATE TABLE kiyafetler (id INTEGER PRIMARY KEY AUTOINCREMENT, tur text, cekmeceNo INTEGER, foto blob, renk text, desen text, alinmaTarihi text, fiyat int, kullaniciID INTEGER)");
        db.execSQL("CREATE TABLE kombinler (id INTEGER PRIMARY KEY AUTOINCREMENT, ad text, kullaniciID INTEGER)");
        db.execSQL("CREATE TABLE kombinvekiyafetler (kombinNo int, kiyafetNo int, kombinYeri int, kullaniciID int)");
        db.execSQL("CREATE TABLE etkinlikler (id INTEGER PRIMARY KEY AUTOINCREMENT, ad text, tur text, mekan text, tarih text, kullaniciID int)");
        db.execSQL("CREATE TABLE etkinlikvekiyafetler (kiyafetNo int, etkinlikNo int, kullaniciID int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void kullaniciEkle(String ad, String sifre, Bitmap foto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ad", ad);
        cv.put("sifre", sifre);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        cv.put("foto",outputStream.toByteArray());
        db.insert("kullanicilar", null, cv);
        db.close();
    }

    public int cekmeceEkle (String s, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ad", s);
        cv.put("kullaniciID", id);
        int cekmeceID=(int) db.insert("cekmeceler", null, cv);
        db.close();
        return cekmeceID;
    }

    public int kombinEkle (String s, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ad", s);
        cv.put("kullaniciID", id);
        int kombinNo = (int) db.insert("kombinler", null, cv);
        db.close();
        return kombinNo;
    }

    public int kiyafetEkle (String tur, int cekmeceNo, Bitmap foto, String renk, String desen, String alinmaTarihi, int fiyat, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tur", tur);
        cv.put("cekmeceNo", cekmeceNo);
        cv.put("renk", renk);
        cv.put("desen",desen);
        cv.put("alinmaTarihi",alinmaTarihi);
        cv.put("fiyat",fiyat);
        cv.put("kullaniciID", id);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        cv.put("foto",outputStream.toByteArray());
        int no=(int)db.insert("kiyafetler", null, cv);
        db.close();
        return no;
    }

    public void kombineKiyafetEkle (int kombinNo, int kiyafetNo, int kombinYeri){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("kombinvekiyafetler", "kombinNo=? AND kombinYeri=? AND kullaniciID=?",
                new String[]{String.valueOf(kombinNo), String.valueOf(kombinYeri), String.valueOf(MainActivity.kullaniciID)});
        ContentValues cv = new ContentValues();
        cv.put("kombinNo", kombinNo);
        cv.put("kombinYeri", kombinYeri);
        cv.put("kullaniciID",MainActivity.kullaniciID);
        cv.put("kiyafetNo", kiyafetNo);
        db.insert("kombinvekiyafetler", null,cv);
        db.close();
    }



    public boolean isInDB (String s){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor aCursor =db.rawQuery("SELECT * FROM kullanicilar WHERE ad=?",new String[]{s});
        if(aCursor.moveToFirst()){
            aCursor.close();
            db.close();
            return true;
        }
        aCursor.close();
        db.close();
        return false;
    }

    public int sifreDogruMu(String ad, String sifre){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor aCursor =db.rawQuery("SELECT * FROM kullanicilar WHERE ad=? AND sifre=?",new String[]{ad, sifre});
        if(aCursor.moveToFirst()){
            int no= aCursor.getInt(0);
            aCursor.close();
            db.close();
            return no;
        }
        aCursor.close();
        db.close();
        return -1;
    }

    public Bitmap fotoEkle(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor aCursor =db.rawQuery("SELECT foto FROM kullanicilar WHERE id=?",new String[]{String.valueOf(id)});
        aCursor.moveToFirst();
        Bitmap bitmap = BitmapFactory.decodeByteArray(aCursor.getBlob(0),0,aCursor.getBlob(0).length);
        aCursor.close();
        db.close();
        return bitmap;
    }

    public String adEkle(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor aCursor =db.rawQuery("SELECT ad FROM kullanicilar WHERE id=?",new String[]{String.valueOf(id)});
        aCursor.moveToFirst();
        String s= aCursor.getString(0);
        aCursor.close();
        db.close();
        return s;
    }

    public ArrayList<Cekmece> cekmeceleriGetir (int id){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Cekmece> cekmeceler = new ArrayList<>(0);
        Cursor aCursor =db.rawQuery("SELECT * FROM cekmeceler WHERE kullaniciID=? ORDER BY id DESC", new String[]{String.valueOf(id)});
        if (aCursor.moveToFirst()) {
            do {
                cekmeceler.add(new Cekmece(aCursor.getString(1), aCursor.getInt(0)));
            }while(aCursor.moveToNext());
        }
        aCursor.close();
        db.close();
        return cekmeceler;
    }

    public ArrayList <Kiyafet> kiyafetleriGetir (int cekmeceID, int kullaniciID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Kiyafet> kiyafetler = new ArrayList<>(0);
        Cursor aCursor =db.rawQuery("SELECT * FROM kiyafetler WHERE kullaniciID=? AND cekmeceNo=? ORDER BY id DESC", new String[]{String.valueOf(kullaniciID),String.valueOf(cekmeceID)});
        if (aCursor.moveToFirst()) {
            do {
                kiyafetler.add(new Kiyafet(aCursor.getInt(0), aCursor.getString(1),aCursor.getInt(2), BitmapFactory.decodeByteArray(aCursor.getBlob(3),0,aCursor.getBlob(3).length),
                        aCursor.getString(4),aCursor.getString(5), aCursor.getString(6), aCursor.getInt(7),MainActivity.kullaniciID));

            }while(aCursor.moveToNext());
        }
        aCursor.close();
        db.close();
        return kiyafetler;
    }


    public void cekmeceSil (int kullaniciID, int cekmeceID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cekmeceler", "kullaniciID=? AND id=?", new String[]{String.valueOf(kullaniciID), String.valueOf(cekmeceID)});
        db.delete("kiyafetler", "kullaniciID=? AND cekmeceNo=?", new String[]{String.valueOf(kullaniciID), String.valueOf(cekmeceID)});
        db.close();
    }

    public void kiyafetGuncelle(Kiyafet kiyafet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tur", kiyafet.getTur());
        cv.put("renk", kiyafet.getRenk());
        cv.put("desen", kiyafet.getDesen());
        cv.put("alinmaTarihi", kiyafet.getAlinmaTarihi());
        cv.put("fiyat", kiyafet.getFiyat());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        kiyafet.getFoto().compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        cv.put("foto",outputStream.toByteArray());
        db.update("kiyafetler", cv, "id=?", new String[]{String.valueOf(kiyafet.getKiyafetID())});
        db.close();
    }

    public void kiyafetiSil (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("kiyafetler", "id=? AND kullaniciID=?", new String[]{String.valueOf(id), String.valueOf(MainActivity.kullaniciID)});
        db.close();
    }

    public ArrayList<Kombin> kombinleriGetir (){
        ArrayList<Kombin> kombinler = new ArrayList<>(0);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor aCursor =db.rawQuery("SELECT * FROM kombinler WHERE kullaniciID=? ", new String[]{String.valueOf(MainActivity.kullaniciID)});
        if (aCursor.moveToFirst()) {
            do {
                kombinler.add(new Kombin(aCursor.getInt(0), aCursor.getInt(2),aCursor.getString(1)));

            }while(aCursor.moveToNext());
        }
        aCursor.close();
        db.close();
        return kombinler;
    }

    public Bitmap[] kombinGetir (int id){
        Bitmap[] kiyafetler = new Bitmap[4];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor aCursor =db.rawQuery("SELECT kombinYeri,foto FROM kombinvekiyafetler,kiyafetler WHERE kombinNo=? AND kombinvekiyafetler.kullaniciID=? AND kiyafetler.kullaniciID=? AND kiyafetler.id=kombinvekiyafetler.kiyafetNo",new String[]{
                String.valueOf(id), String.valueOf(MainActivity.kullaniciID), String.valueOf(MainActivity.kullaniciID)});
        if(aCursor.moveToFirst()){
            System.out.println("cursor loop");
            do {
               kiyafetler[aCursor.getInt(0)]=BitmapFactory.decodeByteArray(aCursor.getBlob(1),0,aCursor.getBlob(1).length);

            }while(aCursor.moveToNext());
        }

        aCursor.close();
        db.close();
        return kiyafetler;
    }

    public void kombiniSil (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("kombinler", "id=? AND kullaniciID=?", new String[]{String.valueOf(id), String.valueOf(MainActivity.kullaniciID)});
        db.delete("kombinvekiyafetler", "kullaniciID=? AND kombinNo=?", new String[]{String.valueOf(MainActivity.kullaniciID), String.valueOf(id)});
        db.close();
    }

    public int etkinlikEkle (Etkinlik etkinlik){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ad", etkinlik.getEtkinlikAdi());
        cv.put("tur", etkinlik.getEtkinlikTuru());
        cv.put("mekan", etkinlik.getEtkinlikMekani());
        cv.put("tarih", etkinlik.getEtkinlikTarihi());
        cv.put("kullaniciID", MainActivity.kullaniciID);
        int no = (int) db.insert("etkinlikler", null, cv);
        db.close();
        return no;
    }

    public void etkinligeKiyafetEkle (int etkinlikNo, int kiyafetNo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("etkinlikNo", etkinlikNo);
        cv.put("kiyafetNo", kiyafetNo);
        cv.put("kullaniciID", MainActivity.kullaniciID);
        db.insert("etkinlikvekiyafetler", null,cv);
        db.close();
    }

    public ArrayList<Etkinlik> etkinlikleriGetir (){
        ArrayList<Etkinlik> etkinlikler = new ArrayList<Etkinlik>(0);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor aCursor =db.rawQuery("SELECT * FROM etkinlikler WHERE kullaniciID=? ORDER BY id DESC", new String[]{String.valueOf(MainActivity.kullaniciID)});
        if (aCursor.moveToFirst()) {
            do {
                etkinlikler.add(new Etkinlik(aCursor.getInt(0), aCursor.getString(1),aCursor.getString(2),aCursor.getString(3),aCursor.getString(4)));

            }while(aCursor.moveToNext());
        }
        aCursor.close();
        db.close();
        return etkinlikler;
    }

    public Etkinlik etkinlikGetir(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("etkinlik getir: "+ id);
        Cursor aCursor =db.rawQuery("SELECT * FROM etkinlikler WHERE id=? AND kullaniciID=?",new String[]{String.valueOf(id), String.valueOf(MainActivity.kullaniciID)});
        aCursor.moveToFirst();
        Etkinlik etkinlik= new Etkinlik(aCursor.getInt(0), aCursor.getString(1),
                aCursor.getString(2), aCursor.getString(4), aCursor.getString(3));
        aCursor.close();
        db.close();
        return etkinlik;
    }

    public ArrayList<Kiyafet> etkinligeGoreKiyafetleriGetir (int etkinlikNo){
        ArrayList<Kiyafet> kiyafetler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor aCursor =db.rawQuery("SELECT kiyafetler.* FROM etkinlikvekiyafetler,kiyafetler WHERE etkinlikNo=? AND etkinlikvekiyafetler.kullaniciID=? AND kiyafetler.kullaniciID=? AND etkinlikvekiyafetler.kiyafetNo=kiyafetler.id",
                new String[]{String.valueOf(etkinlikNo),String.valueOf(MainActivity.kullaniciID), String.valueOf(MainActivity.kullaniciID)});
        if (aCursor.moveToFirst()) {
            do {
                kiyafetler.add(new Kiyafet(aCursor.getInt(0), aCursor.getString(1),aCursor.getInt(2), BitmapFactory.decodeByteArray(aCursor.getBlob(3),0,aCursor.getBlob(3).length),
                        aCursor.getString(4),aCursor.getString(5), aCursor.getString(6), aCursor.getInt(7),MainActivity.kullaniciID));

            }while(aCursor.moveToNext());
        }
        aCursor.close();
        db.close();
        return kiyafetler;
    }

    public void etkinligiGuncelle(Etkinlik etkinlik){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ad",etkinlik.getEtkinlikAdi());
        cv.put("tur", etkinlik.getEtkinlikTuru());
        cv.put("mekan", etkinlik.getEtkinlikMekani());
        cv.put("tarih", etkinlik.getEtkinlikTarihi());
        db.update("etkinlikler", cv, "id=? AND kullaniciID=?", new String[]{String.valueOf(etkinlik.getEtkinlikNo()), String.valueOf(MainActivity.kullaniciID)});
        db.close();
    }

}
