package analizador.sintactico;

public class Automata {
    
    private boolean isAccepted = false;
    private String[] id;
    private Automata[] address;

    public Automata[] getAddress() {
        return address;
    }

    public void setAddress(Automata[] address) {
        this.address = address;
    }

    public String[] getId() {
        return id;
    }

    public void setId(String[] id) {
        this.id = id;
    }

    public boolean IsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
    
}
