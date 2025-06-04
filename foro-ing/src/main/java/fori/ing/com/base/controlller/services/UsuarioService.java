package fori.ing.com.base.controlller.services;

import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import fori.ing.com.base.controlller.dao.dao_models.DaoUsuario;
import fori.ing.com.base.models.Usuario;

@BrowserCallable
@AnonymousAllowed
public class UsuarioService {

    private DaoUsuario db;
    public UsuarioService() {
        db = new DaoUsuario();
    }

    public List<Usuario> lisAllUsuario(){
        return Arrays.asList(db.listAll().toArray());
        
    }
    
}