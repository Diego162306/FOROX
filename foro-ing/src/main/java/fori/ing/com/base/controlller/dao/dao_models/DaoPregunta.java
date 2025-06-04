package fori.ing.com.base.controlller.dao.dao_models;
import java.util.Date;

import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;
import fori.ing.com.base.controlller.dao.AdapterDao;
import fori.ing.com.base.models.Pregunta;
import fori.ing.com.base.models.TipoArchivo;


public class DaoPregunta extends AdapterDao<Pregunta> {
    private Pregunta obj;
    private Linkendlist<Pregunta> aux;

    public DaoPregunta() {
        super(Pregunta.class);
        // TODO Auto-generated constructor stub
    }

    // getter and setter
    public Pregunta getObj() {
        if (obj == null) {
            this.obj = new Pregunta();

        }
        return this.obj;
    }

    public void setObj(Pregunta obj) {
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
    public Linkendlist<Pregunta> getListAll() {
        if (aux == null) {
            this.aux = listAll();
        }
        return aux;
    }
   

    public static void main(String[] args) {
        DaoPregunta dp = new DaoPregunta();
        dp.getObj().setId(dp.listAll().getLength() + 1);
        dp.getObj().setContenido( "Hola");
        dp.getObj().setIdArchivoadjunto(TipoArchivo.AUDIO);
        dp.getObj().setFecha(new Date());
        dp.getObj().setIdUsuario(1);
        dp.getObj().setIdCategoria(2);
        if (dp.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }
      
    }

   

    

}
