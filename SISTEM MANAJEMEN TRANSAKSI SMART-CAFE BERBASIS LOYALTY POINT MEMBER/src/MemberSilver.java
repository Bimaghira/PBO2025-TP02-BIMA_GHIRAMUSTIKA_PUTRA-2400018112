class MemberSilver extends Member {
    public MemberSilver(String n) { super(n); }
    @Override public String getTipe() { return "Silver Member"; }
    @Override public int hitungPoin(double total) { return (int) (total / 10000); }
}