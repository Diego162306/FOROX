package yo.wagner.login.base.controller.services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import yo.wagner.login.base.controller.dao.dao_models.DaoUsuario;
import yo.wagner.login.base.models.Usuario;
import java.util.Arrays;
import java.util.List;

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