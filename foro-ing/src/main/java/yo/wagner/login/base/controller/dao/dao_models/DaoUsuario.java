package yo.wagner.login.base.controller.dao.dao_models;

import yo.wagner.login.base.controller.dao.AdapterDao;
import yo.wagner.login.base.models.Usuario;

public class DaoUsuario extends AdapterDao<Usuario> {

   
    private Usuario obj;

    public DaoUsuario() {
        super(Usuario.class);
        // TODO Auto-generated constructor stub
    }

    // getter and setter
    public Usuario getObj() {
        if (obj == null) {
            this.obj = new Usuario();

        }
        return this.obj;
    }

    public void setObj(Usuario obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength() + 1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            
            return false;
            // TODO: handle exception
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            
            return false;
            // TODO: handle exception
        }
    }

    
    public static void main(String[] args) {
        DaoUsuario dp = new DaoUsuario();
        dp.getObj().setId(dp.listAll().getLength() + 1);
        dp.getObj().setNombre("jachi");
        dp.getObj().setApellido("Pérez");
        dp.getObj().setTelefono("123456789");   
        dp.getObj().setDni("123456789");
        
        if (dp.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }
      
    }

   
    
}