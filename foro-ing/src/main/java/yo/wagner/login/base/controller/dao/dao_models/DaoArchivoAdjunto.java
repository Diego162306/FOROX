package yo.wagner.login.base.controller.dao.dao_models;

import java.util.HashMap;

import yo.wagner.login.base.controller.Utiles;
import yo.wagner.login.base.controller.DataStruc.List.Linkendlist;
import yo.wagner.login.base.controller.dao.AdapterDao;
import yo.wagner.login.base.models.ArchivoAdjunto;
import yo.wagner.login.base.models.TipoArchivo;

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
        dp.getObj().setNombre("Vscode");
        dp.getObj().setUrl("https://code.visualstudio.com/");
        dp.getObj().setTipoArchivo(TipoArchivo.IMAGEN);
        dp.getObj().setIdRespuesta(1);
        dp.getObj().setIdPregunta(2);
        if (dp.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }

    }

    public Linkendlist<HashMap<String, String>> all() throws Exception {
        Linkendlist<HashMap<String, String>> lista = new Linkendlist<>();
        if (!this.listAll().isEmpty()) {
            ArchivoAdjunto[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                lista.add(toDict(arreglo[i]));
            }
        }
        return lista;
    }

    private HashMap<String, String> toDict(ArchivoAdjunto arreglo) throws Exception {
        DaoPregunta dp = new DaoPregunta();
        DaoUsuario du = new DaoUsuario();
        DaoRespuesta dr = new DaoRespuesta();
        HashMap<String, String> aux = new HashMap<>();
        aux.put("id", arreglo.getId().toString());
        aux.put("nombre", arreglo.getNombre());
        aux.put("url", arreglo.getUrl());
        aux.put("tipoArchivo", arreglo.getTipoArchivo() != null ? arreglo.getTipoArchivo().name() : "Desconocido");
        aux.put("pregunta", (arreglo.getIdPregunta() != null && arreglo.getIdPregunta() > 0)
                ? dp.get(arreglo.getIdPregunta()).getContenido()
                : "N/A");

        aux.put("respuesta", (arreglo.getIdRespuesta() != null && arreglo.getIdRespuesta() > 0)
                ? dr.get(arreglo.getIdRespuesta()).getContenido()
                : "N/A");

        return aux;
    }

}
