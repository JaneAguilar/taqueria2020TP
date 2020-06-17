package sample.modelos;

public class proveedor {
    int idProveedor;
    String nombreP;
    String direccion;
    String telefono;

    public proveedor(){}

    public proveedor(int idProveedor, String nombreP, String direccion, String telefono) {
        this.idProveedor = idProveedor;
        this.nombreP = nombreP;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return nombreP;
    }
}
