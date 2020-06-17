package sample.modelos;

public class mesa {
    int idMesa;
    String descripcion;

    public mesa(){}

    public mesa(int idMesa,String descripcion){
        this.idMesa=idMesa;
        this.descripcion=descripcion;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return String.valueOf(idMesa);
    }
}
