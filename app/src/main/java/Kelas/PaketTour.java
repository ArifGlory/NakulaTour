package Kelas;

import java.io.Serializable;

@SuppressWarnings("serial") //With this annotation we are going to hide compiler warnings
public class PaketTour implements Serializable {
    public int idPaket;
    public String namaPaket;
    public String keteranganPaket;
    public String downloadUrl;

    public PaketTour(){

    }

    public PaketTour(int idPaket,String namaPaket, String keteranganPaket,String downloadUrl) {
        this.idPaket = idPaket;
        this.namaPaket = namaPaket;
        this.downloadUrl = downloadUrl;
        this.keteranganPaket = keteranganPaket;
    }

    public int getIdPaket() {
        return idPaket;
    }

    public void setIdPaket(int idPaket) {
        this.idPaket = idPaket;
    }

    public String getNamaPaket() {
        return namaPaket;
    }

    public void setNamaPaket(String namaPaket) {
        this.namaPaket = namaPaket;
    }

    public String getKeteranganPaket() {
        return keteranganPaket;
    }

    public void setKeteranganPaket(String keteranganPaket) {
        this.keteranganPaket = keteranganPaket;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }


}
