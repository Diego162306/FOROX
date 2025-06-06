package fori.ing.com.base.controlller.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.mappedtypes.Pageable;

import fori.ing.com.base.controlller.dao.dao_models.DaoCategoria;
import fori.ing.com.base.models.Categoria;
import fori.ing.com.base.models.TipoArchivo;
import io.micrometer.common.lang.NonNull;
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

    public List<Categoria>listAll(){
        return(List<Categoria>)Arrays.asList(db.listAll().toArray());
    }
}
