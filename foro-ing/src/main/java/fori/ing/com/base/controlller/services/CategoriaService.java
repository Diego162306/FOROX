package fori.ing.com.base.controlller.services;

import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.mappedtypes.Pageable;

import fori.ing.com.base.controlller.dao.dao_models.DaoCategoria;
import fori.ing.com.base.models.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@BrowserCallable
@AnonymousAllowed
public class CategoriaService {
     private DaoCategoria db;
    public CategoriaService(){
        db= new DaoCategoria();
    }

    public void create(@NotEmpty String nombre, @NotEmpty String descripcion) throws Exception{
        db.getObj().setNombre(nombre);
        db.getObj().setDescripcion(descripcion);
        if(!db.save()){
            throw new Exception("No se puede guardar los datos de artista");
        }
    }

    public void update(Integer id, @NotEmpty @NotBlank @NotNull String nombre, @NotEmpty String descripcion) throws Exception{
        if(id != null && id > 0 && nombre.trim().length() > 0 && descripcion.trim().length() > 0);
        db.getObj().setNombre(nombre);
        db.getObj().setDescripcion(descripcion);
        if(!db.update(id-1)){
            throw new Exception("No se puede guardar los datos de artista");
        }
    }

    public List<Categoria> list(Pageable pageable){
        return Arrays.asList(db.listAll().toArray());
    }

    public List<Categoria>listAllCategoria(){
        return(List<Categoria>)Arrays.asList(db.listAll().toArray());
    }

}

