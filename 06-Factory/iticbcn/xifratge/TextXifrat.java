package iticbcn.xifratge;

public class TextXifrat {
    private byte[] data;

    public TextXifrat(byte[] data){
        this.data = data;
    }

    public byte[] getBytes(){ return data; }

    @Override
    public String toString() {
        return new String(data);
    }

}
