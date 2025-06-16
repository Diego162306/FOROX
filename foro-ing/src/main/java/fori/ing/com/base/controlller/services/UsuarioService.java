package fori.ing.com.base.controlller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;
import fori.ing.com.base.controlller.dao.dao_models.DaoUsuario;
import fori.ing.com.base.models.Cuenta;
import fori.ing.com.base.models.Usuario;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class UsuarioService {

    private DaoUsuario db;

    public UsuarioService() {
        db = new DaoUsuario();
    }

    public void createUsuario(String nombre, String apellido,
            String telefono, String dni) throws Exception {
        if (nombre.trim().length() > 0 && apellido.trim().length() > 0) {

            db.getObj().setNombre(nombre);
            db.getObj().setApellido(apellido);
            db.getObj().setTelefono(telefono);
            db.getObj().setDni(dni);
        }
        if (!db.save()) {
            throw new Exception("Error al guardar el artista");

        }
    }

    public List<HashMap> order(String attribute, Integer type) throws Exception {
        return Arrays.asList(db.orderByAttribute(type, attribute).toArray());
    }

    public List<HashMap> search(String attribute, String text, Integer type) throws Exception {
        Linkendlist<HashMap<String, String>> lista = db.search(attribute, text, type);
        if (!lista.isEmpty())
            return Arrays.asList(lista.toArray());
        else
            return new ArrayList<>();
    }

    public List<Usuario> listAll() {
        return Arrays.asList(db.listAll().toArray());

    }

    public List<HashMap> listAllUsuario() throws Exception {
        return Arrays.asList(db.all().toArray());

    }

}
