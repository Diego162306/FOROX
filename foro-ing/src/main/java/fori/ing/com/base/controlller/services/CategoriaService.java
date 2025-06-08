package fori.ing.com.base.controlller.services;


import java.util.Arrays;
import java.util.List;


import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

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
                throw new Exception("No se puede guardar los datos de categoría");
            }
        }
    }

     public void updateCategoria(Integer id, @NotBlank @NotEmpty String nombre, String decripcion) throws Exception {
        if (id != null && id > 0 && nombre.trim().length() > 0 && decripcion.trim().length() > 0) {
            db.setObj(db.listAll().get(id - 1));
            db.getObj().setNombre(nombre);
            db.getObj().setDescripcion(decripcion);
            if (!db.update(id - 1)) {
                throw new Exception("Error al actualizar la categoría");
            }
        }
    }

    public List<Categoria>listAll(){
        return(List<Categoria>)Arrays.asList(db.listAll().toArray());
    }

    
    public List<Categoria> order(String atributo, Integer type) {
        System.out.println(atributo + "  " + type);
        if (atributo.equalsIgnoreCase("nombre"))
            return (List<Categoria>) Arrays.asList(db.orderQ(type).toArray());
        else if (atributo.equalsIgnoreCase("descripcion"))
            return (List<Categoria>) Arrays.asList(db.orderLocate(type).toArray());
        else
            return (List<Categoria>) Arrays.asList(db.listAll().toArray());
    }
}