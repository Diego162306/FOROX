package fori.ing.com.base.controlller.services;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


import fori.ing.com.base.controlller.dao.dao_models.DaoCuenta;
import fori.ing.com.base.controlller.dao.dao_models.DaoUsuario;
import fori.ing.com.base.controlller.excepcion.ListEmptyException;
import fori.ing.com.base.models.Cuenta;
import fori.ing.com.base.models.Usuario;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed


public class CuentaService {

    private DaoCuenta db;
    public CuentaService() {
        db = new DaoCuenta();
    }
     public void createCuenta(String correo, String clave, boolean estado) throws Exception {
        if (correo.trim().length() > 0  && clave.trim().length() > 0 && estado)  {
            db.getObj().setCorreo(correo);
            db.getObj().setClave(clave);
            db.getObj().setEstado(estado);
            

            if (!db.save()) {
                throw new Exception("Error al guardar la Cuenta");
            }
        }
    }
                
    // public void updateCuenta(Integer id,@NotEmpty String clave, String rol, boolean estado) throws Exception {
    //     if (id != null && id > 0 && clave.trim().length() > 0  && rol.trim().length() > 0 ) {
    //         db.setObj(db.listAll().get(id-1));
    
    //         db.getObj().setClave(clave);
    //         db.getObj().setRol(rol);
    //         db.getObj().setEstado(estado);

            

    //         if (!db.update(id-1)) {
    //             throw new Exception("Error al modificar la Cuenta");
    //         }
    //     }
    // }
//     public List<HashMap<String, Object>> listAll() throws Exception{
//         List<HashMap<String, Object>> list = new ArrayList<>();
//         if (!db.listAll().isEmpty()) {
//             Cuenta[] arreglo = db.listAll().toArray();
//             DaoUsuario dbUsuario = new DaoUsuario();
//             for (int i = 0; i < arreglo.length; i++) {
//                 HashMap<String, Object> aux = new HashMap<>();
//                 aux.put("id", arreglo[i].getId().toString());
//                 aux.put("correo", arreglo[i].getCorreo());
//                 aux.put("clave", arreglo[i].getClave());
//                 aux.put("estado", arreglo[i].isEstado());
//                 aux.put("rol", arreglo[i].getRol());
//                 aux.put("id_usuario", dbUsuario.listAll().get(arreglo[i].getId_usuario()-1).getNombre());
//                 list.add(aux);
        
//             }

//         }

//         return list;
//     }



    
//    public List<HashMap> listaUsuarioCombo() {
//         List<HashMap> lista = new ArrayList<>();
//         DaoUsuario da = new DaoUsuario();
//         if (!da.listAll().isEmpty()) {
//             Usuario[] arreglo = da.listAll().toArray();
//             for (int i = 0; i < arreglo.length; i++) {
//                 HashMap<String, String> aux = new HashMap<>();
//                 aux.put("value", arreglo[i].getId().toString(i));
//                 aux.put("label", arreglo[i].getNombre());
//                 lista.add(aux);
//             }
//         }
//         return lista;
//     }

    

    public List<Cuenta> lisAllCuenta(){
        return Arrays.asList(db.listAll().toArray());
        
    }

 
    
}