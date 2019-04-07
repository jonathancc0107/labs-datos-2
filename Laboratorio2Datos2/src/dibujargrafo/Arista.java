
package dibujargrafo;

public class Arista {
    private String nodoI;
    private String nodoD;
    private String relation;

    public Arista(String nodoI, String nodoD, String relation) {
        this.nodoI = nodoI;
        this.nodoD = nodoD;
        this.relation = relation;
    }

    public String getNodoI() {
        return nodoI;
    }

    public String getNodoD() {
        return nodoD;
    }

    public String getRelation() {
        return relation;
    }
    
    
}
