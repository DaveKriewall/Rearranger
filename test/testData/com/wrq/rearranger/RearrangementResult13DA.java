public class RearrangerTest13
{
    void GF() { // grandfather
        F1();
        F2();
    }

    private void F1() {
        S1B();
        S1A();
    }
    void S1A() {}
    void S1B() {}

    private void F2() {
        S2A();
        S2B();
    }
    void S2A() {}
    void S2B() {}
}
