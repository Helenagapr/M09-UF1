package iticbcn.xifratge;

public class TextXifrat {
    private byte[] text;

    public TextXifrat(byte[] text){
        this.text = text;
    }

    public byte[] getText(){ return text; }

    @Override
    public String toString() {
        return new String(text.toString());
    }

}
