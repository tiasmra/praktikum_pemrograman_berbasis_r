import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Menu[] menuAwal = {
        new Menu("Rose Topokki", 22000, "Makanan"),
        new Menu("Kimbab Ayam", 24500, "Makanan"),
        new Menu("Kimbab Tuna", 26500, "Makanan"),
        new Menu("Mandu", 35000, "Makanan"),
        new Menu("Omija Cha", 20000, "Minuman"),
        new Menu("Oolong Milk Tea", 15000, "Minuman"),
        new Menu("Korean Banana Milk", 25000, "Minuman"),
        new Menu("Korean Corn Tea", 33500, "Minuman"),
    };

    static ArrayList<Menu> listMenu = new ArrayList<>();
    static ArrayList<String> orderName = new ArrayList<>();
    static ArrayList<Integer> orderQty = new ArrayList<>();
    static ArrayList<Integer> orderPrice = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static int countOrders = 0;

    public static void isiMenu() {
        for (Menu menu : menuAwal) {
            listMenu.add(menu);
        }
    }

    public static void showMenu() {
        for (String kategori : new String[]{"Makanan", "Minuman"}) {
            System.out.println("\n===== MENU " + kategori.toUpperCase() + "=====");
            for (Menu menu : listMenu) {
                if (menu.kategori.equalsIgnoreCase(kategori)) {
                    menu.showMenu();
                }
            }
        }
    }

    public static int searchMenu(String nama) {
        for (int i = 0; i < listMenu.size(); i++) {
            if (nama.equalsIgnoreCase(listMenu.get(i).nama)) {
                return i;
            }
        }
        return -1;
    }

    public static int inputAngka(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
            }
        }
    }

    public static boolean konfirmasi() {
        while (true) {
            System.out.print("Yakin? (Ya/Tidak): ");
            String jawab = input.nextLine().trim();

            if (jawab.equalsIgnoreCase("Ya")) {
                return true;
            } else if (jawab.equalsIgnoreCase("Tidak")) {
                return false;
            } else {
                System.out.println("Input harus Ya atau Tidak.");
            }
        }
    }

    public static void showMenuBernomor() {
        for (int i = 0; i < listMenu.size(); i++) {
            Menu menu = listMenu.get(i);
            System.out.println((i + 1) + ". " + menu.nama
                + " - Rp" + menu.harga + " (" + menu.kategori + ")");
        }
    }
    
    public static int pilihMenu(String pesan) {
        showMenuBernomor();

        while (true) {
            int nomor = inputAngka(pesan);
            if (nomor >= 1 && nomor <= listMenu.size()) {
                return nomor - 1;
            } else {
                System.out.println("Nomor menu tidak tersedia.");
            }
        }
    }

    public static void addOrder() {
        orderName.clear();
        orderQty.clear();
        orderPrice.clear();
        countOrders = 0;

        if (listMenu.isEmpty()) {
            System.out.println("Menu restoran kosong.");
            return;
        }

        showMenu();
        System.out.println("\nKetik 'selesai' untuk mengakhiri pesanan.");

        while (true) {
            System.out.print("Masukkan nama menu: ");
            String nama = input.nextLine().trim();

            if (nama.equalsIgnoreCase("selesai")) {
                break;
            }

            int indeks = searchMenu(nama);
            if (indeks == -1) {
                System.out.println("Menu tidak ditemukan. Silahkan input kembali.");
                continue;
            }

            int jumlah;
            do {
                jumlah = inputAngka("Jumlah pesanan: ");
                if (jumlah <= 0) {
                    System.out.println("Jumlah harus lebih dari 0.");
                }
            } while (jumlah <= 0);

            Menu menu = listMenu.get(indeks);
            orderName.add(menu.nama);
            orderQty.add(jumlah);
            orderPrice.add(menu.harga);
            countOrders++;
        }

        if (countOrders > 0) {
            printReceipt();
        } else {
            System.out.println("Tidak ada pesanan");
        }
    }

    public static int calculateSubtotal() {
        int total = 0;

        for (int i = 0; i < countOrders; i++) {
            total += orderPrice.get(i) * orderQty.get(i);
        }
        return total;
    }

    public static int calculateDiscount(int subtotal) {
        if (subtotal > 100000) {
            return subtotal * 10 / 100;
        } else {
            return 0;
        }
    }

    public static int freeOmijaChaQty(int subtotal) {
        int omijaChaQty = 0;

        if (subtotal > 50000) {
            for (int i = 0; i < countOrders; i++) {
                if ("Omija Cha". equalsIgnoreCase(orderName.get(i))) {
                    omijaChaQty += orderQty.get(i);
                }
            }
        }
        return omijaChaQty / 2;
    }

    public static void printItem(int posisi) {
        int totalItem = orderPrice.get(posisi) * orderQty.get(posisi);

        System.out.println(orderName.get(posisi));
        System.out.println(" " + orderQty.get(posisi)
                + " x Rp" + orderPrice.get(posisi)
                + " = Rp" + totalItem);
    }

    public static void printReceipt() {
        int subtotal = calculateSubtotal();
        int discount = calculateDiscount(subtotal);
        int tax = subtotal * 10 / 100;
        int service = 20000;

        int freeOmijaCha = freeOmijaChaQty(subtotal);
        int hargaOmija = 35000;

        for (int i = 0; i < countOrders; i++) {
            if ("Omija Cha". equalsIgnoreCase(orderName.get(i))) {
                hargaOmija = orderPrice.get(i);
                break;
            }
        }

        int potonganMinuman = freeOmijaCha * hargaOmija;
        int grandTotal = subtotal - discount - potonganMinuman + tax + service;

        System.out.println("\n======== STRUK PEMBAYARAN ========");

        for (int i = 0; i < countOrders; i++) {
            printItem(i);
        }

        System.out.println("-------------------------------------");
        System.out.printf("Subtotal                    :Rp%d%n", subtotal);
        System.out.printf("Diskon                      :Rp%d%n", discount);
        System.out.printf("Promo Omija Cha             :Rp%d%n", potonganMinuman);
        System.out.printf("Pajak 10%%                  :Rp%d%n", tax);
        System.out.printf("Biaya Pelayanan             :Rp%d%n", service);
        System.out.println("--------------------------------------");
        System.out.printf("TOTAL PEMBAYARAN            : Rp%d%n", grandTotal);
        System.out.println("=======================================");
    }

    public static void tambahMenu() {
        String nama;

        while (true) {
            System.out.print("Nama menu baru: ");
            nama = input.nextLine().trim();

            if (nama.isEmpty()) {
                System.out.println("Nama menu tidak boleh kosong.");
            } else if (searchMenu(nama) != -1) {
                System.out.println("Nama menu sudah tersedia.");
            } else {
                break;
            }
        }

        int harga;
        do {
            harga = inputAngka("Harga: ");
            if (harga <= 0) {
                System.out.println("Harga harus positif.");
            }
        } while (harga <= 0);

        String kategori;
        while (true) {
            System.out.print("Kategori (Makanan/Minuman): ");
            kategori = input.nextLine().trim();

            if (kategori.equalsIgnoreCase("Makanan")) {
                kategori = "Makanan";
                break;
            } else if (kategori.equalsIgnoreCase("Minuman")) {
                kategori = "Minuman";
                break;
            } else {
                System.out.println("Kategori tidak valid.");
            }
        }

        if (konfirmasi()) {
            listMenu.add(new Menu(nama, harga, kategori));
            System.out.println("Menu berhasil ditambahkan.");
        } else {
            System.out.println("Penambahan menu dibatalkan.");
        }
    }

    public static void ubahMenu() {
        if (listMenu.isEmpty()) {
            System.out.println("Menu kosong.");
            return;
        }

        int indeks = pilihMenu("Nomor menu yang diubah: ");
        int harga;

        do {
            harga = inputAngka("Harga baru: ");
            if (harga <= 0) {
                System.out.println("Harga harus positif.");
            }
        } while (harga <= 0);

        if (konfirmasi()) {
            listMenu.get(indeks).setHarga(harga);
            System.out.println("Harga berhasil diubah.");
        } else {
            System.out.println("Perubahan dibatalkan.");
        }
    }

    public static void hapusMenu() {
        if (listMenu.isEmpty()) {
            System.out.println("Menu kosong.");
            return;
        }

        int indeks = pilihMenu("Nomor menu yang dihapus: ");
        System.out.println("Menu terpilih: " + listMenu.get(indeks).nama);

        if (konfirmasi()) {
            listMenu.remove(indeks);
            System.out.println("Menu berhasil dihapus.");
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }

    public static void menuPengelolaan() {
        int pilih;

        do {
            System.out.println("\n===== PENGELOLAAN MENU =====");
            System.out.println("1. Tambah menu");
            System.out.println("2. Ubah Harga");
            System.out.println("3. Hapus menu");
            System.out.println("4. Lihat menu");
            System.out.println("0. Kembali");

            pilih = inputAngka("pilihan: ");

            switch (pilih) {
                case 1:
                    tambahMenu();
                    break;
                case 2:
                    ubahMenu();
                    break;
                case 3:
                    hapusMenu();
                    break;
                case 4:
                    showMenuBernomor();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia.");
            }
        } while (pilih != 0);
    }

    public static void main(String[] args) {
        isiMenu();
        int pilih;

        do {
            System.out.println("\n===== MENU UTAMA =====");
            System.out.println("1. Pemesanan Pelanggan");
            System.out.println("2. Pengelolaan Menu");
            System.out.println("0. Keluar");

            pilih = inputAngka("Pilihan: ");

            switch (pilih) {
                case 1:
                    addOrder();
                    break;
                case 2:
                    menuPengelolaan();
                    break;
                case 0:
                    System.out.println("Terima kasih.");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia.");
            }
        } while (pilih != 0);

        input.close();
    }
}