package fori.ing.com.base.controlller.servicios;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.validator.constraints.URL;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import fori.ing.com.base.controlller.dao.dao_models.DaoArchivoAdjunto;
import fori.ing.com.base.models.ArchivoAdjunto;
import fori.ing.com.base.models.TipoArchivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class ArchivoAdjuntoService {

    private DaoArchivoAdjunto dp;
    
    public ArchivoAdjuntoService() {
        dp = new DaoArchivoAdjunto();
    }
    public void createArchivoAdjunto(@NotBlank @NotEmpty String nombre,@URL @NotEmpty String url, TipoArchivo tipoArchivo, Integer idRespuesta, Integer idPregunta) throws Exception {
        if (nombre.trim().length() > 0 && url.trim().length() > 0 && idRespuesta > 0 && idPregunta > 0) {
           dp.getObj().setNombre(nombre);
            dp.getObj().setUrl(url);
            dp.getObj().setTipoArchivo(tipoArchivo);
            dp.getObj().setIdRespuesta(idRespuesta);
            dp.getObj().setIdPregunta(idPregunta);
            if (!dp.save()) {
                throw new Exception("Error al guardar la ArchivoAdjunto");
            }
        }
    }

    public void updateArchivoAdjunto(Integer id, @NotBlank @NotEmpty String nombre,@URL @NotEmpty String url, TipoArchivo tipoArchivo, Integer idRespuesta, Integer idPregunta) throws Exception {
        if (id != null && id > 0 && nombre.trim().length() > 0 && url.trim().length() > 0 && idRespuesta > 0 && idPregunta > 0){
            dp.setObj(dp.listAll().get(id - 1));
            dp.getObj().setNombre(nombre);
            dp.getObj().setUrl(url);
            dp.getObj().setTipoArchivo(tipoArchivo);
            dp.getObj().setIdRespuesta(idRespuesta);
            dp.getObj().setIdPregunta(idPregunta);
            if (!dp.update(id - 1)) {
                throw new Exception("Error al actualizar la ArchivoAdjunto");
            }
        }
    }

    public List<ArchivoAdjunto> lisAllArchivoAdjunto(){
        return Arrays.asList(dp.listAll().toArray());
        
    }

    public List<String> listTipoArchivo() {
        List<String> lista = new ArrayList<>();
        for (TipoArchivo tipo : TipoArchivo.values()) {
            lista.add(tipo.name()); 
        }
        return lista;
    }
    
    
    
}