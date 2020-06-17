package sample.modelos;

public class puesto {
    int idPuesto;
    String puesto;
    String descripcion;
    Double sueldo;

    public puesto(){}

    public puesto(int idPuesto,String puesto, String descripcion, double sueldo){
        this.idPuesto=idPuesto;
        this.puesto=puesto;
        this.descripcion=descripcion;
        this.sueldo=sueldo;
    }

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        return puesto;
    }
}
