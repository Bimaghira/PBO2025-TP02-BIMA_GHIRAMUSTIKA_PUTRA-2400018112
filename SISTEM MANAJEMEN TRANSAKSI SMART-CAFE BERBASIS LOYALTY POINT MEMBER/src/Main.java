import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean sistemBerjalan = true;

        // Data Produk Master yang diperbanyak
        Produk[] menu = {
                new Minuman("Espresso", 18000),
                new Minuman("Caramel Latte", 28000),
                new Minuman("Matcha Latte", 25000),
                new Minuman("Ice Lemon Tea", 15000),
                new Makanan("Chocolate Lava", 25000),
                new Makanan("Beef Sandwich", 35000),
                new Makanan("Croissant", 22000),
                new Makanan("Club Sandwich", 40000)
        };

        while (sistemBerjalan) { // LOOP UTAMA PELANGGAN
            ArrayList<Produk> keranjang = new ArrayList<>(); // Reset keranjang untuk pelanggan baru

            System.out.println("\n===============================");
            System.out.println("   WELCOME TO SMART-CAFE 2026  ");
            System.out.println("===============================");
            System.out.println("1. Input Transaksi Baru");
            System.out.println("0. Matikan Sistem");
            System.out.print("Pilih: ");
            int opsiSistem = input.nextInt();
            input.nextLine(); // Consume newline

            if (opsiSistem == 0) {
                sistemBerjalan = false;
                System.out.println("Sistem dimatikan. Terima kasih.");
                break;
            }

            System.out.print("Input Nama Pelanggan : ");
            String nama = input.nextLine();
            System.out.print("Tipe Member (1.Silver / 2.Gold): ");
            int tipe = input.nextInt();

            // Polimorfisme Member
            Member member = (tipe == 2) ? new MemberGold(nama) : new MemberSilver(nama);

            boolean prosesTransaksi = true;
            while (prosesTransaksi) { // LOOP INTERNAL TRANSAKSI
                System.out.println("\n--- DASHBOARD TRANSAKSI: " + member.getNama().toUpperCase() + " ---");
                System.out.println("1. Tambah Pesanan");
                System.out.println("2. Lihat / Hapus Pesanan");
                System.out.println("3. Bayar & Selesai");
                System.out.print("Pilih menu: ");
                int menuUtama = input.nextInt();

                switch (menuUtama) {
                    case 1:
                        System.out.println("\nDAFTAR MENU CAFE:");
                        for (int i = 0; i < menu.length; i++) {
                            System.out.printf("%d. %-15s | Rp%,.0f\n", (i+1), menu[i].getNama(), menu[i].getHarga());
                        }
                        System.out.print("Pilih item (1-" + menu.length + "): ");
                        int p = input.nextInt();
                        if (p >= 1 && p <= menu.length) {
                            keranjang.add(menu[p-1]);
                            System.out.println("✓ Berhasil dimasukkan.");
                        }
                        break;

                    case 2:
                        if (keranjang.isEmpty()) {
                            System.out.println("! Keranjang kosong.");
                        } else {
                            System.out.println("\nKERANJANG " + member.getNama() + ":");
                            for (int i = 0; i < keranjang.size(); i++) {
                                System.out.println((i+1) + ". " + keranjang.get(i).getNama());
                            }
                            System.out.print("Hapus No (0 batal): ");
                            int hapus = input.nextInt();
                            if (hapus > 0 && hapus <= keranjang.size()) {
                                keranjang.remove(hapus-1);
                            }
                        }
                        break;

                    case 3:
                        if (keranjang.isEmpty()) {
                            System.out.println("! Tidak ada pesanan untuk dibayar.");
                        } else {
                            simpanDanTampilkanStruk(member, keranjang);
                            prosesTransaksi = false; // Keluar ke menu pelanggan selanjutnya
                        }
                        break;
                }
            }
        }
        input.close();
    }

    public static void simpanDanTampilkanStruk(Member m, ArrayList<Produk> list) {
        double total = 0;
        StringBuilder isiStruk = new StringBuilder();

        isiStruk.append("\n***********************************\n");
        isiStruk.append("           SMART-CAFE 2026         \n");
        isiStruk.append("***********************************\n");
        isiStruk.append("Pelanggan : ").append(m.getNama()).append("\n");
        isiStruk.append("Tipe      : ").append(m.getTipe()).append("\n");
        isiStruk.append("-----------------------------------\n");

        for (Produk p : list) {
            isiStruk.append(String.format("%-18s : Rp%,10.0f\n", p.getNama(), p.getHarga()));
            total += p.getHarga();
        }

        int perolehanPoin = m.hitungPoin(total);
        m.tambahPoin(perolehanPoin);

        isiStruk.append("-----------------------------------\n");
        isiStruk.append(String.format("TOTAL BELANJA      : Rp%,10.0f\n", total));
        isiStruk.append("POIN DIDAPAT       : ").append(perolehanPoin).append(" pts\n");
        isiStruk.append("TOTAL SALDO POIN   : ").append(m.getPoin()).append(" pts\n");
        isiStruk.append("***********************************\n");

        System.out.println(isiStruk.toString());

        try (FileWriter writer = new FileWriter("Struk_" + m.getNama() + ".txt")) {
            writer.write(isiStruk.toString());
            System.out.println("✓ Struk tersimpan: Struk_" + m.getNama() + ".txt");
        } catch (IOException e) {
            System.out.println("! Gagal simpan file.");
        }
    }
}