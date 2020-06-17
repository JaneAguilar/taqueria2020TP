package sample.modelos;

public class empleado {
    int noEmpleado;
    String nombreE;
    String apellidoP;
    String apellidoM;
    String usuario;
    String contrasena;
    puesto idPuesto;


    public empleado() {}

    public empleado(int noEmpleado, String nombreE, String apellidoP, String apellidoM, String usuario, String contrasena, puesto idPuesto) {
        this.noEmpleado = noEmpleado;
        this.nombreE = nombreE;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.idPuesto = idPuesto;
    }

    public int getNoEmpleado() {
        return noEmpleado;
    }

    public void setNoEmpleado(int noEmpleado) {
        this.noEmpleado = noEmpleado;
    }

    public String getNombreE() {
        return nombreE;
    }

    public void setNombreE(String nombreE) {
        this.nombreE = nombreE;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public puesto getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(puesto idPuesto) {
        this.idPuesto = idPuesto;
    }

    @Override
    public String toString() {
        return nombreE;
    }
}