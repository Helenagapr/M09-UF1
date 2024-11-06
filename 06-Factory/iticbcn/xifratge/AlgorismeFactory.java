package iticbcn.xifratge;

public abstract class AlgorismeFactory {
    public abstract Xifrador creaXifrador() {
        return new Xifrador();
    }
}
