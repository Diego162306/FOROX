package yo.wagner.login.base.controller.dao.dao_models;

import java.util.Date;

import yo.wagner.login.base.controller.DataStruc.List.Linkendlist;
import yo.wagner.login.base.controller.dao.AdapterDao;
import yo.wagner.login.base.models.Respuesta;

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

    public boolean puedeResponder(int idPregunta) {
        int count = 0;
        for (Respuesta r : this.listAll().toArray()) {
            if (r.getIdPregunta() == idPregunta) {
                count++;
            }
        }
        return count < 2;
    }

    public boolean save() {
        try {
            // Validar que no exista ya una respuesta para la misma pregunta
            for (Respuesta r : this.listAll().toArray()) {
                if (r.getIdPregunta().equals(obj.getIdPregunta())) {
                    System.out.println("Ya existe una respuesta para la pregunta ID: " + obj.getIdPregunta());
                    return false;
                }
            }
            // Asignar ID y persistir
            obj.setId(this.listAll().getLength() + 1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

    public Respuesta findByPreguntaId(Integer idPregunta) {
        if (!this.listAll().isEmpty()) {
            for (Respuesta r : this.listAll().toArray()) {
                if (r.getIdPregunta().equals(idPregunta)) {
                    return r;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        DaoRespuesta dp = new DaoRespuesta();
        dp.getObj().setId(dp.listAll().getLength() + 1);
        dp.getObj().setContenido("Wagner gey   UWU");
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
