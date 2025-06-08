package fori.ing.com.base.controlller.dao.dao_models;

import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;
import fori.ing.com.base.controlller.dao.AdapterDao;
import fori.ing.com.base.models.ArchivoAdjunto;
import fori.ing.com.base.models.TipoArchivo;

public class DaoArchivoAdjunto extends AdapterDao<ArchivoAdjunto> {
    private ArchivoAdjunto obj;
    private Linkendlist<ArchivoAdjunto> aux;

    public DaoArchivoAdjunto() {
        super(ArchivoAdjunto.class);
        // TODO Auto-generated constructor stub
    }

    // getter and setter
    public ArchivoAdjunto getObj() {
        if (obj == null) {
            this.obj = new ArchivoAdjunto();

        }
        return this.obj;
    }

    public void setObj(ArchivoAdjunto obj) {
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
    public Linkendlist<ArchivoAdjunto> getListAll() {
        if (aux == null) {
            this.aux = listAll();
        }
        return aux;
    }
   

    public static void main(String[] args) {
        DaoArchivoAdjunto dp = new DaoArchivoAdjunto();
        dp.getObj().setId(dp.listAll().getLength() + 1);
        dp.getObj().setNombre( "Vscode");
        dp.getObj().setUrl("https://code.visualstudio.com/");
        dp.getObj().setTipoArchivo(TipoArchivo.IMAGEN);
        dp.getObj().setIdRespuesta(1);
        dp.getObj().setIdPregunta(1);
        if (dp.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }
      
    }
   

}
