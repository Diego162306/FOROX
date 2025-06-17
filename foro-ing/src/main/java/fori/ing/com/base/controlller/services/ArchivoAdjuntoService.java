package fori.ing.com.base.controlller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.hibernate.validator.constraints.URL;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;
import fori.ing.com.base.controlller.dao.dao_models.DaoArchivoAdjunto;
import fori.ing.com.base.controlller.dao.dao_models.DaoPregunta;
import fori.ing.com.base.controlller.dao.dao_models.DaoRespuesta;
import fori.ing.com.base.models.ArchivoAdjunto;
import fori.ing.com.base.models.Pregunta;
import fori.ing.com.base.models.Respuesta;
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

    public void createArchivoAdjunto( String nombre,  String url, String tipoArchivo,
            Integer idRespuesta, Integer idPregunta) throws Exception {
        if (nombre.trim().length() > 0 && url.trim().length() > 0 && tipoArchivo.trim().length() > 0 && idRespuesta > 0
                && idPregunta > 0) {
            dp.getObj().setNombre(nombre);
            dp.getObj().setUrl(url);
            dp.getObj().setTipoArchivo(TipoArchivo.valueOf(tipoArchivo));
            dp.getObj().setIdRespuesta(idRespuesta);
            dp.getObj().setIdPregunta(idPregunta);
            if (!dp.save()) {
                throw new Exception("Error al guardar la ArchivoAdjunto");
            }
        }
    }

    public void updateArchivoAdjunto(Integer id, @NotBlank @NotEmpty String nombre, @URL @NotEmpty String url,
            String tipoArchivo, Integer idRespuesta, Integer idPregunta) throws Exception {
        if (id != null && id > 0 && nombre.trim().length() > 0 && url.trim().length() > 0
                && tipoArchivo.trim().length() > 0 && idRespuesta > 0 && idPregunta > 0) {
            dp.setObj(dp.listAll().get(id - 1));
            dp.getObj().setNombre(nombre);
            dp.getObj().setUrl(url);
            dp.getObj().setTipoArchivo(TipoArchivo.valueOf(tipoArchivo));
            dp.getObj().setIdRespuesta(idRespuesta);
            dp.getObj().setIdPregunta(idPregunta);
            if (!dp.update(id - 1)) {
                throw new Exception("Error al actualizar la ArchivoAdjunto");
            }
        }
    }

    public List<HashMap> listaRespuesta() {
        List<HashMap> lista = new ArrayList<>();
        DaoRespuesta dr = new DaoRespuesta();
        if (!dr.listAll().isEmpty()) {
            Respuesta[] arreglo = dr.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getContenido());
                lista.add(aux);
            }
        }

        return lista;
    }

    public List<HashMap> listaPregunta() {
        List<HashMap> lista = new ArrayList<>();
        DaoPregunta dr = new DaoPregunta();
        if (!dr.listAll().isEmpty()) {
            Pregunta[] arreglo = dr.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getContenido());
                lista.add(aux);
            }
        }

        return lista;
    }

    public List<String> listTipoArchivo() {
        List<String> lista = new ArrayList<>();
        for (TipoArchivo tipo : TipoArchivo.values()) {
            lista.add(tipo.name());
        }
        return lista;
    }


        public List<HashMap> order(String attribute, Integer type) throws Exception {
        return Arrays.asList(dp.orderByAttribute(type, attribute).toArray());
    }

    public List<HashMap> search(String attribute, String text, Integer type) throws Exception {
        Linkendlist<HashMap<String, String>> lista = dp.search(attribute, text, type);
        if (!lista.isEmpty())
            return Arrays.asList(lista.toArray());
        else
            return new ArrayList<>();
    }
    
       public List<HashMap> lisAll() throws Exception {
        return Arrays.asList(dp.all().toArray());

    }

    public List<ArchivoAdjunto> lisAllArchivoAdjunto() {
        return Arrays.asList(dp.listAll().toArray());

    }



}