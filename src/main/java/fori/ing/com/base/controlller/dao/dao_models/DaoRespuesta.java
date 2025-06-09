package fori.ing.com.base.controlller.dao.dao_models;

import java.util.Date;

import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;
import fori.ing.com.base.controlller.dao.AdapterDao;
import fori.ing.com.base.models.Respuesta;


public class DaoRespuesta extends AdapterDao<Respuesta> {
    private Respuesta obj;
    private Linkendlist<Respuesta> aux;

    public DaoRespuesta() {
        super(Respuesta.class);
        // TODO Auto-generated constructor stub
    }

    // getter and setter
    public Respuesta getObj() {
        if (obj == null) {
            this.obj = new Respuesta();

        }
        return this.obj;
    }

    public void setObj(Respuesta obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(this.listAll().getLength() + 1);
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
    public Linkendlist<Respuesta> getListAll() {
        if (aux == null) {
            this.aux = listAll();
        }
        return aux;
    }
   

    public static void main(String[] args) {
        DaoRespuesta dp = new DaoRespuesta();
        dp.getObj().setId(dp.listAll().getLength() + 1);
        dp.getObj().setContenido( "Wagner gey   UWU");
        dp.getObj().setIdtipoArchivo(2);
        dp.getObj().setFecha(new Date());
        dp.getObj().setIdUsuario(1);
        dp.getObj().setIdUsuario(3);
        if (dp.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }
      
    }

   

    

}
