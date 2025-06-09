package fori.ing.com.base.controlller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;
import fori.ing.com.base.controlller.dao.dao_models.DaoCategoria;
import fori.ing.com.base.models.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class CategoriaService {
     private DaoCategoria db;
    public CategoriaService(){
        db= new DaoCategoria();
    }

    public void createCategoria(@NotEmpty String nombre, @NotEmpty String descripcion) throws Exception{
        if (nombre.trim().length() > 0 && descripcion.trim().length() > 0) {
            db.getObj().setNombre(nombre);
            db.getObj().setDescripcion(descripcion);
            if (!db.save()) {
                throw new Exception("No se puede guardar los datos de artista");
            }
        }
    }

     public void updateCategoria(Integer id, @NotBlank @NotEmpty String nombre, String decripcion) throws Exception {
        if (id != null && id > 0 && nombre.trim().length() > 0 && decripcion.trim().length() > 0) {
            db.setObj(db.listAll().get(id - 1));
            db.getObj().setNombre(nombre);
            db.getObj().setDescripcion(decripcion);
            if (!db.update(id - 1)) {
                throw new Exception("Error al actualizar la Pregunta");
            }
        }
    }

    public List<Categoria> listAllCategoria() {
        return Arrays.asList(db.listAll().toArray());
    }
    
    public List<Categoria>listAll(){
        return(List<Categoria>)Arrays.asList(db.listAll().toArray());
    }

    public List<HashMap<String, String>> order(String atributo, Integer type) {
        try {
            return Arrays.asList(db.orderByAttribute(type, atributo).toArray());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<HashMap> search(String attribute, String text, Integer type) throws Exception {
        Linkendlist<HashMap<String, String>> lista = db.search(attribute, text, type);
        if (!lista.isEmpty())
            return Arrays.asList(lista.toArray());
        else
            return new ArrayList<>();
    }

    

}
