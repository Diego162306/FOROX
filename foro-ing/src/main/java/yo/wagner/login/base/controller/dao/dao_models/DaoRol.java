package yo.wagner.login.base.controller.dao.dao_models;

import yo.wagner.login.base.controller.DataStruc.List.Linkendlist;
import yo.wagner.login.base.controller.dao.AdapterDao;
import yo.wagner.login.base.models.Rol;

public class DaoRol extends AdapterDao<Rol> {

    private Linkendlist<Rol> listAll;
    private Rol obj;

    public DaoRol() {
        super(Rol.class);
        // TODO Auto-generated constructor stub
    }

    // getter and setter
    public Rol getObj() {
        if (obj == null) {
            this.obj = new Rol();

        }
        return this.obj;
    }

    public void setObj(Rol obj) {
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
        DaoRol dp = new DaoRol();
        dp.getObj().setId(dp.listAll().getLength() + 1);
        dp.getObj().setNombre("ADMIN");

        if (dp.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }

    }

}