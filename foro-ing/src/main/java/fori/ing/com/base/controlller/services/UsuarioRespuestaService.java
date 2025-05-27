package fori.ing.com.base.controlller.servicios;

import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import fori.ing.com.base.controlller.dao.dao_models.DaoUsuarioRespuesta;
import fori.ing.com.base.models.UsuarioRespuesta;

@BrowserCallable
@AnonymousAllowed
public class UsuarioRespuestaService {

    private DaoUsuarioRespuesta db;
    public UsuarioRespuestaService() {
        db = new DaoUsuarioRespuesta();
    }

    public List<UsuarioRespuesta> lisAllUsuarioRespuesta(){
        return Arrays.asList(db.listAll().toArray());
        
    }
    
}