package fori.ing.com.base.models;

import java.util.Date;

public class Respuesta {
    private Integer id;
    private String contenido;
    private Integer idtipoArchivo;
    private Date fecha;
    private Integer idPregunta;
    private Integer idUsuario;

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

    public Integer getIdtipoArchivo() {
        return idtipoArchivo;
    }

    public void setIdtipoArchivo(Integer idtipoArchivo) {
        this.idtipoArchivo = idtipoArchivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public Integer getIdPregunta() {
        return idPregunta;
    }
    
    public void setIdPregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
