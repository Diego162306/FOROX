package fori.ing.com.base.controlller.services;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import fori.ing.com.base.controlller.dao.dao_models.DaoCategoria;
import fori.ing.com.base.controlller.dao.dao_models.DaoPregunta;
import fori.ing.com.base.controlller.dao.dao_models.DaoUsuario;
import fori.ing.com.base.controlller.excepcion.ListEmptyException;
import fori.ing.com.base.models.Categoria;
import fori.ing.com.base.models.Pregunta;
import fori.ing.com.base.models.TipoArchivo;
import fori.ing.com.base.models.Usuario;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class PreguntaService {

    private DaoPregunta dp;
    public PreguntaService() {
        dp = new DaoPregunta();
    }
    public void createPregunta(@NotBlank @NotEmpty String contenido, TipoArchivo tipoArchivo, @NonNull Date fecha, Integer idUsuario, Integer idCategoria) throws Exception {
        if (contenido.trim().length() > 0 && tipoArchivo != null && idUsuario > 0 && idCategoria > 0) {
           dp.getObj().setContenido(contenido);
            dp.getObj().setIdArchivoadjunto(tipoArchivo);
            dp.getObj().setFecha(fecha);
            dp.getObj().setIdUsuario(idUsuario);
            dp.getObj().setIdCategoria(idCategoria);
            if (!dp.save()) {
                throw new Exception("Error al guardar la Pregunta");
            }
        }
    }

    // public void updatePregunta(Integer id, String contenido, Integer idTipoArchivo, Date fecha, Integer idUsuario, Integer idCategoria) throws Exception {
    //     if (id != null && id > 0 && contenido.trim().length() > 0 && idTipoArchivo > 0 && idUsuario > 0 && idCategoria > 0) {
    //         dp.setObj(dp.listAll().get(id - 1));
    //         dp.getObj().setContenido(contenido);
    //         dp.getObj().setIdArchivoadjunto(idTipoArchivo);
    //         dp.getObj().setFecha(fecha);
    //         dp.getObj().setIdUsuario(idUsuario);
    //         dp.getObj().setIdCategoria(idCategoria);
    //         if (!dp.update(id - 1)) {
    //             throw new Exception("Error al actualizar la Pregunta");
    //         }
    //     }
    // }

    //Proximamente
    public List<HashMap> lisAll() throws ListEmptyException {
        List<HashMap> list = new ArrayList<>();
        if (!dp.listAll().isEmpty()) {
            Pregunta[] arreglo = dp.listAll().toArray();
            DaoUsuario da = new DaoUsuario();
            DaoCategoria dg = new DaoCategoria();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", arreglo[i].getId().toString());
                aux.put("contenido", arreglo[i].getContenido());
                aux.put("tipo", arreglo[i].getIdArchivoadjunto().getDeclaringClass().getSimpleName());
                aux.put("fecha", sdf.format(arreglo[i].getFecha()));
                aux.put("id_usuario", da.listAll().get(arreglo[i].getIdUsuario() - 1).getNombre());
                aux.put("id_categoria", dg.listAll().get(arreglo[i].getIdCategoria() - 1).getNombre());
                list.add(aux);
            }
        }
        return list;
    }

    public List<HashMap> listaUsuariosCombo() {
        List<HashMap> lista = new ArrayList<>();
        DaoUsuario da = new DaoUsuario();
        if (!da.listAll().isEmpty()) {
            Usuario[] arreglo = da.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<HashMap> listaCategoriasCombo() {
        List<HashMap> lista = new ArrayList<>();
        DaoCategoria da = new DaoCategoria();
        if (!da.listAll().isEmpty()) {
            Categoria[] arreglo = da.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<Pregunta> lisAllPregunta(){
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
