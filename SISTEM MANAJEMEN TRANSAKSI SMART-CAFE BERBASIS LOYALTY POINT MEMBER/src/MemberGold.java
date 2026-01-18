class MemberGold extends Member {
    public MemberGold(String n) { super(n); }
    @Override public String getTipe() { return "Gold Member"; }
    @Override public int hitungPoin(double total) { return (int) (total / 10000) * 2; }
}