package sample.modelos;

public class insumo {
    int idInsumo;
    String insumo;
    double costo;
    proveedor idProveedor;

    public insumo(){}

    public insumo (int idInsumo,String insumo, double costo, proveedor idProveedor){
        this.idInsumo=idInsumo;
        this.insumo=insumo;
        this.costo=costo;
        this.idProveedor=idProveedor;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getInsumo() {
        return insumo;
    }

    public void setInsumo(String insumo) {
        this.insumo = insumo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public proveedor getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(proveedor idProveedor) {
        this.idProveedor = idProveedor;
    }
}
