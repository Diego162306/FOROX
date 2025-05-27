package fori.ing.com.base.controlller.services;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


import fori.ing.com.base.controlller.dao.dao_models.DaoCuenta;
import fori.ing.com.base.models.Cuenta;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed


public class CuentaService {

    private DaoCuenta db;
    public CuentaService() {
        db = new DaoCuenta();
    }
    public void createCuenta(@Email @NotEmpty @NotBlank String correo, @NotEmpty String clave, Integer idUsuario, String rol) throws Exception {
        if (correo.trim().length() > 0 && clave.trim().length() > 0 && idUsuario > 0 && rol.trim().length() > 0) {
            db.getObj().setCorreo(correo);
            db.getObj().setId_usuario(idUsuario);
            db.getObj().setRol(rol);
            db.getObj().setClave(clave);
          
            

            if (!db.save()) {
                throw new Exception("Error al guardar la Cuenta");
            }
        }
    }
                
    public void updateCuenta(Integer id,@NotEmpty String clave, Integer idUsuario, String rol) throws Exception {
        if (id != null && id > 0 && clave.trim().length() > 0 && idUsuario > 0 && rol.trim().length() > 0) {
            db.setObj(db.listAll().get(id-1));
    
            db.getObj().setClave(clave);
            db.getObj().setId_usuario(idUsuario);
            db.getObj().setRol(rol);

            

            if (!db.update(id-1)) {
                throw new Exception("Error al modificar la Cuenta");
            }
        }
    }
//     public List<HashMap<String, Object>> listAll() throws Exception{
//         List<HashMap<String, Object>> list = new ArrayList<>();
//         if (!db.listAll().isEmpty()) {
//             Cuenta[] arreglo = db.listAll().toArrary();
//             DaoPersona dbPersona = new DaoPersona();
//             for (int i = 0; i < arreglo.length; i++) {
//                 HashMap<String, Object> aux = new HashMap<>();
//                 aux.put("id", arreglo[i].getId().toString());
//                 aux.put("email", arreglo[i].getEmail());
//                 aux.put("clave", arreglo[i].getClave());
//                 aux.put("estado", arreglo[i].isEstado());
//                 aux.put("idPersona", dbPersona.listAll().get(arreglo[i].getIdPersona()-1).getUsuario());
//                 list.add(aux);
        
//             }

//         }

//         return list;
//     }
//    public List<HashMap> listaPersonaCombo() {
//         List<HashMap> lista = new ArrayList<>();
//         DaoPersona da = new DaoPersona();
//         if (!da.listAll().isEmpty()) {
//             Persona[] arreglo = da.listAll().toArrary();
//             for (int i = 0; i < arreglo.length; i++) {
//                 HashMap<String, String> aux = new HashMap<>();
//                 aux.put("value", arreglo[i].getId().toString(i));
//                 aux.put("label", arreglo[i].getUsuario());
//                 lista.add(aux);
//             }
//         }
//         return lista;
//     }

    

    public List<Cuenta> lisAllCuenta(){
        return Arrays.asList(db.listAll().toArray());
        
    }

 
    
}