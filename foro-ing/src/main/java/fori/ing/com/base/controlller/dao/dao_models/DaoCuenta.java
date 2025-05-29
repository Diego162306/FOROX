package fori.ing.com.base.controlller.dao.dao_models;

import fori.ing.com.base.models.Cuenta;
import fori.ing.com.base.controlller.dao.AdapterDao;
import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;
public class DaoCuenta extends AdapterDao<Cuenta> {
    private Cuenta obj;
    private Linkendlist<Cuenta> listAll;

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
        obj.setId(listAll().getLength() + 1);
        try {
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
        DaoCuenta da = new DaoCuenta();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setCorreo("wagner@.com");
        da.getObj().setClave("xxxxx");
        da.getObj().setEstado(true);

        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }
        System.out.println(da.getListAll().getLength());
        
    }
    

}