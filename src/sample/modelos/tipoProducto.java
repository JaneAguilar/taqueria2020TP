package sample.modelos;

public class tipoProducto {
    int idTipoProducto;
    String tipo;

    public tipoProducto(){}

    public tipoProducto(int idTipoProducto,String tipo){
        this.idTipoProducto=idTipoProducto;
        this.tipo=tipo;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return tipo;
    }
}
