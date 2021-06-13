package tr.edu.yildiz.rabiagulecproje;

public class Kombin {
    private int id;
    private int kullaniciID;
    private String ad;

    public Kombin(int id, int kullaniciID, String ad) {
        this.id = id;
        this.kullaniciID = kullaniciID;
        this.ad = ad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKullaniciID() {
        return kullaniciID;
    }

    public void setKullaniciID(int kullaniciID) {
        this.kullaniciID = kullaniciID;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }
}
