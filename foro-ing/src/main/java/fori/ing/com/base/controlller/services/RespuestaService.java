package fori.ing.com.base.controlller.services;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import fori.ing.com.base.controlller.dao.dao_models.DaoRespuesta;
import fori.ing.com.base.models.Respuesta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class RespuestaService {

    private DaoRespuesta dp;
    public RespuestaService() {
        dp = new DaoRespuesta();
    }
    public void createRespuesta(@NotBlank @NotEmpty String contenido, Integer idTipoArchivo, Date fecha, Integer idPregunta, Integer idUsuario) throws Exception {
        if (contenido.trim().length() > 0 && idTipoArchivo > 0 && idUsuario > 0 && idUsuario > 0) {
           dp.getObj().setContenido(contenido);
            dp.getObj().setIdtipoArchivo(idTipoArchivo);
            dp.getObj().setFecha(fecha);
            dp.getObj().setIdPregunta(idPregunta);
            dp.getObj().setIdUsuario(idUsuario);
            if (!dp.save()) {
                throw new Exception("Error al guardar la Respuesta");
            }
        }
    }

    public void updateRespuesta(Integer id, String contenido, Integer idTipoArchivo, Date fecha, Integer idUsuario, Integer idPregunta) throws Exception {
        if (id != null && id > 0 && contenido.trim().length() > 0 && idTipoArchivo > 0 && idUsuario > 0 && idPregunta > 0) {
            dp.setObj(dp.listAll().get(id - 1));
            dp.getObj().setContenido(contenido);
            dp.getObj().setIdtipoArchivo(idTipoArchivo);
            dp.getObj().setFecha(fecha);
            dp.getObj().setIdUsuario(idUsuario);
            dp.getObj().setIdPregunta(idPregunta);
            if (!dp.update(id - 1)) {
                throw new Exception("Error al actualizar la Respuesta");
            }
        }
    }

    //Proximamente
    // public List<HashMap> lisAll() throws ListEmptyException {
    //     List<HashMap> list = new ArrayList<>();
    //     if (!dp.listAll().isEmpty()) {
    //         Respuesta[] arreglo = dp.listAll().toArray();
    //         DaoCategoria dg = new DaoCtegoria();
    //         DaoUsuario da = new DaoUsuario();
    //         DaoArchivoAdjunto da = new DaoArchivoAdjunto();
    //         for (int i = 0; i < arreglo.length; i++) {
    //             HashMap<String, String> aux = new HashMap<>();
    //             aux.put("id", arreglo[i].getId().toString());
    //             aux.put("contenido", arreglo[i].getContenido());
    //             aux.put("id_album", da.listAll().get(arreglo[i].getIdArchivoadjunto() - 1).getNombre());
    //             aux.put("fecha", arreglo[i].getFecha().toString());
    //             aux.put("id_usuario", da.listAll().get(arreglo[i].getIdUsuario() - 1).getNombre());
    //             aux.put("id_categoria", dg.listAll().get(arreglo[i].getIdCategoria() - 1).getNombre());
    //             list.add(aux);
    //         }
    //     }
    //     return list;
    // }

    public List<Respuesta> lisAllRespuesta(){
        return Arrays.asList(dp.listAll().toArray());
        
    }

    // public List<String> listTipoArchivo() {
    //     List<String> lista = new ArrayList<>();
    //     for (TipoArchivo tipo : TipoArchivo.values()) {
    //         lista.add(tipo.name()); 
    //     }
    //     return lista;
    // }
    
    
    
}
