package fori.ing.com.base.controlller.dao.dao_models;

import fori.ing.com.base.controlller.dao.AdapterDao;
import fori.ing.com.base.models.Categoria;

public class DaoCategoria extends AdapterDao<Categoria>{
    private Categoria obj;

    //getter anda setter
    public Categoria getObj() {
        if (obj == null) {
            this.obj = new Categoria();

        }
        return this.obj;
    }

    public void setObj(Categoria obj) {
        this.obj = obj;
    }

    public DaoCategoria(){
        super(Categoria.class);
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength() + 1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            // LOG DE ERROR
            e.printStackTrace();
            return false;
            // TODO: handle exception
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            // LOG DE ERROR
            e.printStackTrace();
            return false;
            // TODO: handle exception
        }
    }
    
    public static void main(String[] args){
        DaoCategoria da= new DaoCategoria();
        da.getObj().setId(da.listAll().getLength()+1);
        da.getObj().setNombre("GTDVCDCBCB");
        da.getObj().setDescripcion("DJDHJDHHD");
    
        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");
        }
    }
}