package fori.ing.com.base.controlller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;
import fori.ing.com.base.controlller.dao.dao_models.DaoPregunta;
import fori.ing.com.base.controlller.dao.dao_models.DaoRespuesta;
import fori.ing.com.base.controlller.dao.dao_models.DaoUsuario;
import fori.ing.com.base.controlller.dao.dao_models.DaoUsuarioRespuesta;
import fori.ing.com.base.models.Pregunta;
import fori.ing.com.base.models.Respuesta;
import fori.ing.com.base.models.Usuario;
import fori.ing.com.base.models.UsuarioRespuesta;

@BrowserCallable
@AnonymousAllowed
public class UsuarioRespuestaService {

    private DaoUsuarioRespuesta db;

    public UsuarioRespuestaService() {
        db = new DaoUsuarioRespuesta();
    }

    public void createUsuarioRespuesta(Integer idUsuario, Integer idRespuesta, Integer idPregunta, String contenido)
            throws Exception {
        if (idUsuario > 0 && idRespuesta > 0 && idPregunta > 0 && contenido.trim().length() > 0) {
            db.getObj().setIdUsuario(idUsuario);
            db.getObj().setIdRespuesta(idRespuesta);
            db.getObj().setIdPregunta(idPregunta);
            db.getObj().setContenido(contenido);
            if (!db.save()) {
                throw new Exception("Error al guardar la respuesta del usuario");
            }
        } else {
            throw new Exception("Datos incompletos para crear la respuesta del usuario");
        }
    }

    public void updateUsuarioRespuesta(Integer id, Integer idUsuario, Integer idRespuesta, Integer idPregunta,
            String contenido) throws Exception {
        if (id > 0 && idUsuario > 0 && idRespuesta > 0 && idPregunta > 0 && contenido.trim().length() > 0) {
            db.setObj(db.listAll().get(id - 1));
            db.getObj().setIdUsuario(idUsuario);
            db.getObj().setIdRespuesta(idRespuesta);
            db.getObj().setIdPregunta(idPregunta);
            db.getObj().setContenido(contenido);
            if (!db.update(id - 1)) {
                throw new Exception("Error al actualizar la respuesta del usuario");
            }
        } else {
            throw new Exception("Datos incompletos para actualizar la respuesta del usuario");
        }
    }
    public List<HashMap> listaUsuario() {
        List<HashMap> lista = new ArrayList<>();
        DaoUsuario du = new DaoUsuario();
        if (!du.listAll().isEmpty()) {
            Usuario[] arreglo = du.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }

        return lista;
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
        DaoPregunta dp = new DaoPregunta();
        if (!dp.listAll().isEmpty()) {
            Pregunta[] arreglo = dp.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getContenido());
                lista.add(aux);
            }
        }

        return lista;
    }

    // public List<HashMap> listUsuREs(){
    // List<HashMap> lista = new ArrayList<>();
    // if (!db.listAll().isEmpty()) {
    // UsuarioRespuesta[] arreglo = db.listAll().toArray();
    // for (int i = 0; i < arreglo.length; i++) {
    // HashMap<String, String> aux = new HashMap<>();
    // aux.put("id", arreglo[i].getId().toString(i));
    // aux.put("idRespuesta", new
    // DaoRespuesta().listAll().get(arreglo[i].getIdRespuesta() -
    // 1).getId().toString());
    // aux.put("respuesta", new
    // DaoRespuesta().listAll().get(arreglo[i].getIdRespuesta() -
    // 1).getContenido());
    // aux.put("idUsuario", new DaoUsuario().listAll().get(arreglo[i].getIdUsuario()
    // - 1).getId().toString());
    // aux.put("usuario", new DaoUsuario().listAll().get(arreglo[i].getIdUsuario() -
    // 1).getNombre());
    // aux.put("idPregunta", new
    // DaoPregunta().listAll().get(arreglo[i].getIdPregunta() -
    // 1).getId().toString());
    // aux.put("pregunta", new
    // DaoPregunta().listAll().get(arreglo[i].getIdPregunta() - 1).getContenido());
    // aux.put("contenido", arreglo[i].getContenido());
    // lista.add(aux);
    // }

    // }

    // return lista;
    // }

    public List<HashMap> order(String attribute, Integer type) throws Exception {
        return Arrays.asList(db.orderByUsuarioRespuesta(type, attribute).toArray());
    }

    public List<HashMap> search(String attribute, String text, Integer type) throws Exception {
        Linkendlist<HashMap<String, String>> lista = db.search(attribute, text, type);
        if (!lista.isEmpty())
            return Arrays.asList(lista.toArray());
        else
            return new ArrayList<>();
    }
    // public List<UsuarioRespuesta> listAll() {
    //     return Arrays.asList(db.listAll().toArray());

    // }
        public List<HashMap> listAll() throws Exception {
        return Arrays.asList(db.all().toArray());
    }
    public List<UsuarioRespuesta> listAllUsuarioRespuesta() throws Exception {
        return Arrays.asList(db.listAll().toArray());
    }




}