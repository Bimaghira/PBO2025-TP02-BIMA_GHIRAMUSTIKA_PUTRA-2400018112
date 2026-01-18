abstract class Member {
    private String nama;
    private int poin;

    public Member(String nama) {
        this.nama = nama;
        this.poin = 0;
    }

    public String getNama() { return nama; }
    public int getPoin() { return poin; }
    public void tambahPoin(int p) { this.poin += p; }

    public abstract String getTipe();
    public abstract int hitungPoin(double totalBelanja);
}