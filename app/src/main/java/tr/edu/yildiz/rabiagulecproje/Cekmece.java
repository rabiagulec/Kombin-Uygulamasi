package tr.edu.yildiz.rabiagulecproje;

public class Cekmece {
    private String cekmeceAdi;
    private int cekmeceNo;

    public Cekmece(String cekmeceAdi, int cekmeceNo) {
        this.cekmeceAdi = cekmeceAdi;
        this.cekmeceNo = cekmeceNo;
    }

    public String getCekmeceAdi() {
        return cekmeceAdi;
    }

    public void setCekmeceAdi(String cekmeceAdi) {
        this.cekmeceAdi = cekmeceAdi;
    }

    public int getCekmeceNo() {
        return cekmeceNo;
    }

    public void setCekmeceNo(int cekmeceNo) {
        this.cekmeceNo = cekmeceNo;
    }
}
