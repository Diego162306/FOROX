package fori.ing.com.base.models;

public class Cuenta {

    private Integer id;
    private String correo;
    private String clave;
    private Integer id_usuario;
    private String rol;
    private boolean estado;

    public boolean isEstado() {
        return this.estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getId_usuario() {
        return this.id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }
    public String getRol() {
        return this.rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    
}
