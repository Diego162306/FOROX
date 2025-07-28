package yo.wagner.login.base.models;



public class Usuario {

    private Integer id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String dni;
    private Integer idRol;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    public Integer getIdRol() {
        return this.idRol;
    }
    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

  

}
