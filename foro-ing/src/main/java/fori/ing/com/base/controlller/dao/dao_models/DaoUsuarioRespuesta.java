package fori.ing.com.base.controlller.dao.dao_models;

import fori.ing.com.base.controlller.dao.AdapterDao;
import fori.ing.com.base.models.UsuarioRespuesta;

public class DaoUsuarioRespuesta extends AdapterDao<UsuarioRespuesta> {

    
    private UsuarioRespuesta obj;

    public DaoUsuarioRespuesta() {
        super(UsuarioRespuesta.class);
        // TODO Auto-generated constructor stub
    }

    // getter and setter
    public UsuarioRespuesta getObj() {
        if (obj == null) {
            this.obj = new UsuarioRespuesta();

        }
        return this.obj;
    }

    public void setObj(UsuarioRespuesta obj) {
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
        DaoUsuarioRespuesta dp = new DaoUsuarioRespuesta();
        dp.getObj().setId(dp.listAll().getLength() + 1);
        dp.getObj().setContenido("contenido");
        dp.getObj().setIdPregunta(1);
        dp.getObj().setIdRespuesta(1);
        dp.getObj().setIdUsuario(1);
        
        if (dp.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }
      
    }

   
    
}