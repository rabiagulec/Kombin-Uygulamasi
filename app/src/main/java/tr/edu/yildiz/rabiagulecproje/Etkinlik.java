package tr.edu.yildiz.rabiagulecproje;

public class Etkinlik {
    private int etkinlikNo;
    private String etkinlikAdi;
    private String etkinlikTuru;
    private String etkinlikTarihi;
    private String etkinlikMekani;

    public Etkinlik(int etkinlikNo, String etkinlikAdi, String etkinlikTuru, String etkinlikTarihi, String etkinlikMekani) {
        this.etkinlikNo=etkinlikNo;
        this.etkinlikAdi = etkinlikAdi;
        this.etkinlikTuru = etkinlikTuru;
        this.etkinlikTarihi = etkinlikTarihi;
        this.etkinlikMekani = etkinlikMekani;
    }

    public String getEtkinlikAdi() {
        return etkinlikAdi;
    }

    public String getEtkinlikTuru() {
        return etkinlikTuru;
    }

    public String getEtkinlikTarihi() {
        return etkinlikTarihi;
    }

    public String getEtkinlikMekani() {
        return etkinlikMekani;
    }

    public int getEtkinlikNo() {
        return etkinlikNo;
    }

    public void setEtkinlikNo(int etkinlikNo) {
        this.etkinlikNo = etkinlikNo;
    }

    public void setEtkinlikAdi(String etkinlikAdi) {
        this.etkinlikAdi = etkinlikAdi;
    }

    public void setEtkinlikTuru(String etkinlikTuru) {
        this.etkinlikTuru = etkinlikTuru;
    }

    public void setEtkinlikTarihi(String etkinlikTarihi) {
        this.etkinlikTarihi = etkinlikTarihi;
    }

    public void setEtkinlikMekani(String etkinlikMekani) {
        this.etkinlikMekani = etkinlikMekani;
    }
}
