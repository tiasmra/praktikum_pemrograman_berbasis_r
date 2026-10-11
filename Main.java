import java.util.Scanner;

public class Main {
    static Menu[] listMenu = {
        new Menu("Rose Topokki", 22000, "Makanan"),
        new Menu("Kimbab Ayam", 24500, "Makanan"),
        new Menu("Kimbab Tuna", 26500, "Makanan"),
        new Menu("Mandu", 20000, "Makanan"),
        new Menu ("Omija Cha", 35000, "Minuman"),
        new Menu ("Oolong Milk Tea", 15000, "Minuman"),
        new Menu ("Korean Banana Milk", 25000, "Minuman"),
        new Menu ("Korean Corn tea", 33500, "Minuman")
    };

    static Scanner input = new Scanner(System.in);

    static String[] orderName = new String[4];
    static int[] orderQty = new int[4];
    static int[] orderPrice = new int[4];
    static int countOrders = 0;

    public static void showMenu() {
        System.out.println("===== MENU MAKANAN =====");
        listMenu[0].showMenu();
        listMenu[1].showMenu();
        listMenu[2].showMenu();
        listMenu[3].showMenu();

        System.out.println("===== MENU MINUMAN =====");
        listMenu[4].showMenu();
        listMenu[5].showMenu();
        listMenu[6].showMenu();
        listMenu[7].showMenu();
    }

    public static int searchMenu(String nama) {
        if (nama.equalsIgnoreCase(listMenu[0].nama)) {
            return 0;
        } else if (nama.equalsIgnoreCase(listMenu[1].nama)) {
            return 1;
        } else if (nama.equalsIgnoreCase(listMenu[2].nama)) {
            return 2;
        } else if (nama.equalsIgnoreCase(listMenu[3].nama)) {
            return 3;
        } else if (nama.equalsIgnoreCase(listMenu[4].nama)) {
            return 4;
        } else if (nama.equalsIgnoreCase(listMenu[5].nama)) {
            return 5;
        } else if (nama.equalsIgnoreCase(listMenu[6].nama)) {
            return 6;
        } else if (nama.equalsIgnoreCase(listMenu[7].nama)) {
            return 7;
        } else {
            return -1;
        }
    }

    public static void addOrder(int posisi) {
        if (posisi >= 0 && posisi < 4) {
            System.out.print("Masukkan nama menu ke-" +
                (posisi + 1) + ": ");
            String nama = input.nextLine();

            int indeks = searchMenu(nama);

            if (indeks != -1) {
                System.out.print("Jumlah Pesanan: ");
                int jumlah = Integer.parseInt(input.nextLine());

                if (jumlah > 0) {
                    orderName[posisi] = listMenu[indeks].nama;
                    orderQty[posisi] = jumlah;
                    orderPrice[posisi] = listMenu[indeks].harga;
                    countOrders++;
                } else {
                    System.out.println("Jumlah harus lebih dari 0.");
                    addOrder(posisi);
                }
            } else {
                System.out.println("Menu tidak ditemukan.");
                addOrder(posisi);
            }
        }
    }

    public static int calculateSubtotal() {
        int total = 0;

        if (countOrders >= 1) {
            total += orderPrice[0] * orderQty[0];
        }
        if (countOrders >= 2) {
            total += orderPrice[1] * orderQty[1];
        }
        if (countOrders >= 3) {
            total += orderPrice[2] * orderQty[2];
        }
        if (countOrders >= 4) {
            total += orderPrice[3] * orderQty[3];
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
        if (subtotal > 50000) {
            int omijaChaQty = 0;

            if (countOrders >= 1 &&
                "Omija Cha".equals(orderName[0])) {
                omijaChaQty += orderQty[0];
            }
            if (countOrders >= 2 &&
                "Omija Cha".equals(orderName[1])) {
                omijaChaQty += orderQty[1];
            }
            if (countOrders >= 3 &&
                "Omija Cha".equals(orderName[2])) {
                omijaChaQty += orderQty[2];
            }
            if (countOrders >= 4 &&
                "Omija Cha".equals(orderName[3])) {
                omijaChaQty += orderQty[3];
            }
            return omijaChaQty / 2;
        } else {
            return 0;
        }
    }

    public static void printItem(int posisi) {
        int totalItem = orderPrice[posisi] * orderQty[posisi];

        System.out.println(orderName[posisi]);
        System.out.println(" " + orderQty[posisi]
            + " x Rp" + orderPrice[posisi]
            + " = Rp" + totalItem);
    }

    public static void printReceipt() {
        int subtotal = calculateSubtotal();
        int discount = calculateDiscount(subtotal);
        int tax = subtotal * 10 / 100;
        int service = 20000;

        int freeOmijaCha = freeOmijaChaQty(subtotal);
        int potonganMinuman = freeOmijaCha * 35000;

        int grandTotal = subtotal - discount - potonganMinuman + tax + service;

        System.out.println("\n======== STRUK PEMBAYARAN ========");

        if (countOrders >= 1) {
            printItem(0);
        }
        if (countOrders >= 2) {
            printItem(1);
        }
        if (countOrders >= 3) {
            printItem(2);
        }
        if (countOrders >= 4) {
            printItem(3);
        }

        System.out.println("-----------------------------------");
        System.out.printf("subtotal      : Rp%d%n", subtotal);
        System.out.printf("Diskon      : Rp%d%n", discount);
        System.out.printf("Promo Omija Cha      : Rp%d%n", potonganMinuman);
        System.out.printf("Pajak 10%%      : Rp%d%n", tax);
        System.out.printf("Biaya pelayanan      : Rp%d%n", service);
        System.out.println("-----------------------------------");
        System.out.printf("TOTAL PEMBAYARAN     : Rp%d%n", grandTotal);
        System.out.println("====================================");
    }

    public static void main(String[] args) {
        showMenu();

        System.out.println("\nMaksimal pesanan 4 jenis menu.");
        System.out.print("Berapa jenis menu yang akan Anda pesan (1-4)?");
        countOrders = Integer.parseInt(input.nextLine());

        if (countOrders >= 1 && countOrders <= 4) {
            int jumlahInput = countOrders;
            countOrders = 0;

            if (jumlahInput >= 1) {
                addOrder(0);
            }
            if (jumlahInput >= 2) {
                addOrder(1);
            }
            if (jumlahInput >= 3) {
                addOrder(2);
            }
            if (jumlahInput >= 4) {
                addOrder(3);
            }
            printReceipt();
        } else {
            System.out.println("Jumlah jenis menu harus 1 sampai 4.");
        }
        input.close();
    }
}
