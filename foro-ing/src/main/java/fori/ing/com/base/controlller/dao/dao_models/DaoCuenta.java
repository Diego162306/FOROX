package fori.ing.com.base.controlller.dao.dao_models;

import fori.ing.com.base.models.Cuenta;
import fori.ing.com.base.controlller.dao.AdapterDao;
import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;

public class DaoCuenta extends AdapterDao<Cuenta> {

    private Linkendlist<Cuenta> listAll;
    private Cuenta obj;

    public DaoCuenta() {
        super(Cuenta.class);
        // TODO Auto-generated constructor stub
    }

    // getter and setter
    public Cuenta getObj() {
        if (obj == null) {
            this.obj = new Cuenta();

        }
        return this.obj;
    }

    public void setObj(Cuenta obj) {
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

    public Linkendlist<Cuenta> getListAll() {
        if (listAll == null) {
            listAll = listAll();
        }
        return listAll;
    }


    
    public static void main(String[] args) {
        DaoCuenta dp = new DaoCuenta();
        dp.getObj().setId(dp.listAll().getLength() + 1);
        dp.getObj().setCorreo("correo");
        dp.getObj().setClave("clave");
        dp.getObj().setId_usuario(1);
        dp.getObj().setRol("rol");
        
        if (dp.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }
        System.out.println(dp.getListAll().print());
      
    }

   
    
}