package tr.edu.yildiz.rabiagulecproje;

import android.graphics.Bitmap;

import java.util.Date;

public class Kiyafet {
    private int kiyafetID;
    private String tur;
    private int cekmeceID;
    private Bitmap foto;
    private String renk;
    private String desen;
    private String alinmaTarihi;
    private int fiyat;
    private int kullaniciID;

    public Kiyafet(int kiyafetID, String tur, int cekmeceID, Bitmap foto, String renk, String desen, String alinmaTarihi, int fiyat, int kullaniciID) {
        this.kiyafetID = kiyafetID;
        this.tur = tur;
        this.cekmeceID = cekmeceID;
        this.foto = foto;
        this.renk = renk;
        this.desen = desen;
        this.alinmaTarihi = alinmaTarihi;
        this.fiyat = fiyat;
        this.kullaniciID = kullaniciID;
    }

    public int getKiyafetID() {
        return kiyafetID;
    }

    public void setKiyafetID(int kiyafetID) {
        this.kiyafetID = kiyafetID;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public int getCekmeceID() {
        return cekmeceID;
    }

    public void setCekmeceID(int cekmeceID) {
        this.cekmeceID = cekmeceID;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getRenk() {
        return renk;
    }

    public void setRenk(String renk) {
        this.renk = renk;
    }

    public String getDesen() {
        return desen;
    }

    public void setDesen(String desen) {
        this.desen = desen;
    }

    public String getAlinmaTarihi() {
        return alinmaTarihi;
    }

    public void setAlinmaTarihi(String alinmaTarihi) {
        this.alinmaTarihi = alinmaTarihi;
    }

    public int getFiyat() {
        return fiyat;
    }

    public void setFiyat(int fiyat) {
        this.fiyat = fiyat;
    }

    public int getKullaniciID() {
        return kullaniciID;
    }

    public void setKullaniciID(int kullaniciID) {
        this.kullaniciID = kullaniciID;
    }
}
