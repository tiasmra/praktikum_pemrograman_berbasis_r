public class Menu {
    String nama;
    int harga;
    String kategori;

    public Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public void showMenu() {
        System.out.println(nama + " - Rp" + harga);
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
