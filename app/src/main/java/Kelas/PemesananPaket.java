package Kelas;

public class PemesananPaket {
    public String tanggal_berangkat;
    public String nama;
    public String nama_rombongan;
    public String jk;
    public String paket;
    public String transportasi;
    public String jumlah_peserta;
    public String jumlah_transportasi;
    public String status;
    public String hp;
    public String alamat;
    public String lokasi_penjemputan;
    public String kode;
    public String idPelanggan;
    public String konfirmasi;


    public PemesananPaket(String tanggal_berangkat, String nama, String nama_rombongan, String jk, String paket, String transportasi, String jumlah_peserta, String jumlah_transportasi, String status, String hp, String alamat, String lokasi_penjemputan,
                          String idPelanggan) {
        this.tanggal_berangkat = tanggal_berangkat;
        this.nama = nama;
        this.nama_rombongan = nama_rombongan;
        this.jk = jk;
        this.paket = paket;
        this.transportasi = transportasi;
        this.jumlah_peserta = jumlah_peserta;
        this.jumlah_transportasi = jumlah_transportasi;
        this.status = status;
        this.hp = hp;
        this.alamat = alamat;
        this.lokasi_penjemputan = lokasi_penjemputan;
        this.idPelanggan = idPelanggan;
    }

    public String getKonfirmasi() {
        return konfirmasi;
    }

    public void setKonfirmasi(String konfirmasi) {
        this.konfirmasi = konfirmasi;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getTanggal_berangkat() {
        return tanggal_berangkat;
    }

    public void setTanggal_berangkat(String tanggal_berangkat) {
        this.tanggal_berangkat = tanggal_berangkat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama_rombongan() {
        return nama_rombongan;
    }

    public void setNama_rombongan(String nama_rombongan) {
        this.nama_rombongan = nama_rombongan;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getPaket() {
        return paket;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }

    public String getTransportasi() {
        return transportasi;
    }

    public void setTransportasi(String transportasi) {
        this.transportasi = transportasi;
    }

    public String getJumlah_peserta() {
        return jumlah_peserta;
    }

    public void setJumlah_peserta(String jumlah_peserta) {
        this.jumlah_peserta = jumlah_peserta;
    }

    public String getJumlah_transportasi() {
        return jumlah_transportasi;
    }

    public void setJumlah_transportasi(String jumlah_transportasi) {
        this.jumlah_transportasi = jumlah_transportasi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLokasi_penjemputan() {
        return lokasi_penjemputan;
    }

    public void setLokasi_penjemputan(String lokasi_penjemputan) {
        this.lokasi_penjemputan = lokasi_penjemputan;
    }
}
