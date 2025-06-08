package fori.ing.com.base.models;

import java.util.Date;

public class Pregunta {
    private Integer id;
    private String contenido;
    private TipoArchivo idArchivoadjunto;
    private Date fecha;
    private Integer idUsuario;
    private Integer idCategoria;

    //getters and setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getContenido() {
        return contenido;
    }
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    public TipoArchivo getIdArchivoadjunto() {
        return idArchivoadjunto;
    }
    public void setIdArchivoadjunto(TipoArchivo idArchivoadjunto) {
        this.idArchivoadjunto = idArchivoadjunto;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    
}
