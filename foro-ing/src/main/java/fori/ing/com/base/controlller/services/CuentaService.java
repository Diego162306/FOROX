package fori.ing.com.base.controlller.services;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import fori.ing.com.base.controlller.dao.dao_models.DaoCuenta;
import fori.ing.com.base.controlller.dao.dao_models.DaoRol;
import fori.ing.com.base.models.Cuenta;
import fori.ing.com.base.models.Usuario;
import fori.ing.com.base.controlller.dao.dao_models.DaoUsuario;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class CuentaService {

    private DaoCuenta db;

    private SecurityContext context;
    // private Authentication auth;

    public CuentaService() {
        db = new DaoCuenta();
        context = SecurityContextHolder.getContext();
        // auth = context.getAuthentication();
    }

    public HashMap<String, String> createRoles() {
        HashMap<String, String> mapa = new HashMap<>();
        mapa.put("resp", "ya creado");
        mapa.put("code", "201");
        DaoRol dr = new DaoRol();
        if (dr.listAll().isEmpty()) {
            dr.getObj().setNombre("ADMIN");
            dr.save();
            dr.setObj(null);
            dr.getObj().setNombre("USER");
            dr.save();
            dr.setObj(null);
            mapa.put("resp", "Roles creados");
            mapa.put("code", "200");
        }

        return mapa;
    }

    public Authentication getAuthentication() {

        System.out.println("autentication *_**");
        System.out.println(context.getAuthentication());

        return context.getAuthentication();
    }

    public Boolean isLogin() {
        if (getAuthentication() != null)
            return getAuthentication().isAuthenticated();
        return false;

    }

    public HashMap<String, Object> login(String correo, String Clave) throws Exception {
        HashMap<String, Object> mapa = new HashMap<>();
        try {
            HashMap<String, Object> aux = db.login(correo, Clave);
            if (aux != null) {
                context.setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                aux.get("usuario").toString(),
                                aux.get("id").toString(),
                                getAuthorities(aux)));
                mapa.put("user", context.getAuthentication());
                mapa.put("message", "ok");
                mapa.put("estado", "true");
            }
        } catch (Exception e) {
            mapa.put("user", new HashMap<>());
            mapa.put("message", "usuario o calve incorectos intente nuevamente");
            mapa.put("estado", "false");
            context.setAuthentication(null);
            System.out.println(e);

        }
        return mapa;
    }

    private static List<GrantedAuthority> getAuthorities(HashMap<String, Object> user) throws Exception {
        // DaoRol dr = new DaoRol();
        // dr.setObj(dr.get(Integer.parseInt(user.get("rol").toString())));
        List<GrantedAuthority> list = new ArrayList<>();
        // list.add(new SimpleGrantedAuthority("ROLE" + dr.getObj().getNombre()));
        return list;
    }

    public HashMap<String, String> logout() {
        context.setAuthentication(null);
        HashMap<String, String> mapa = new HashMap<>();
        mapa.put("message", "ok");
        return mapa;

    }

    public void createCuenta(@Email @NotEmpty @NotBlank String correo, @NotEmpty String clave, Integer idUsuario, boolean estado)
            throws Exception {
        if (correo.trim().length() > 0 && clave.trim().length() > 0 && idUsuario > 0 ) {
            db.getObj().setCorreo(correo);
            db.getObj().setId_usuario(idUsuario);
            db.getObj().setEstado(estado);

            db.getObj().setClave(clave);

            if (!db.save()) {
                throw new Exception("Error al guardar la Cuenta");
            }
        }
    }

    public void updateCuenta(Integer id, @NotEmpty String clave, Integer idUsuario) throws Exception {
        if (id != null && id > 0 && clave.trim().length() > 0 && idUsuario > 0) {
            db.setObj(db.listAll().get(id - 1));

            db.getObj().setClave(clave);
            db.getObj().setId_usuario(idUsuario);

            if (!db.update(id - 1)) {
                throw new Exception("Error al modificar la Cuenta");
            }
        }
    }

    public List<HashMap<String, Object>> listAll() throws Exception {
        List<HashMap<String, Object>> list = new ArrayList<>();
        if (!db.listAll().isEmpty()) {
            Cuenta[] arreglo = db.listAll().toArray();
            DaoUsuario dbUsuario = new DaoUsuario();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, Object> aux = new HashMap<>();
                aux.put("id", arreglo[i].getId().toString());
                aux.put("correo", arreglo[i].getCorreo());
                aux.put("clave", arreglo[i].getClave());
                aux.put("id_usuario", dbUsuario.listAll().get(arreglo[i].getId_usuario() - 1).getNombre());
                aux.put("estado", arreglo[i].getEstado());

                list.add(aux);

            }

        }

        return list;
    }

    public List<HashMap> listaUsuarioCombo() {
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

    public List<Cuenta> lisAllCuenta() {
        return Arrays.asList(db.listAll().toArray());

    }

    // public List<HashMap<String, String>> order(String atributo, Integer type) {
    // try {
    // return Arrays.asList(db.orderByAttribute(type, atributo).toArrary());
    // } catch (Exception e) {
    // e.printStackTrace();
    // return new ArrayList<>();
    // }
    // }

    // public List<HashMap> search(String attribute, String text, Integer type)
    // throws Exception {
    // LinkedList<HashMap<String, String>> lista = db.search(attribute, text, type);
    // if (!lista.isEmpty())
    // return Arrays.asList(lista.toArray());
    // else
    // return new ArrayList<>();
    // }

}