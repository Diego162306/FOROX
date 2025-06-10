package fori.ing.com.base.controlller.dao.dao_models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import fori.ing.com.base.controlller.Utiles;
import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;
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

    public Linkendlist<HashMap<String, String>> all() throws Exception {
        Linkendlist<HashMap<String, String>> lista = new Linkendlist<>();
        if (!this.listAll().isEmpty()) {
            UsuarioRespuesta[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                lista.add(toDict(arreglo[i]));
            }
        }
        return lista;
    }

    private HashMap<String, String> toDict(UsuarioRespuesta arreglo) throws Exception {
        DaoPregunta dp = new DaoPregunta();
        DaoUsuario du = new DaoUsuario();
        DaoRespuesta dr = new DaoRespuesta();
        HashMap<String, String> aux = new HashMap<>();
        aux.put("id", arreglo.getId().toString());
        aux.put("contenido", arreglo.getContenido());
        aux.put("pregunta", dp.get(arreglo.getIdPregunta()).getContenido());
        aux.put("respuesta", dr.get(arreglo.getIdRespuesta()).getContenido());
        aux.put("usuario", du.get(arreglo.getIdUsuario()).getNombre());
        return aux;
    }



    public Linkendlist<HashMap<String, String>> orderByUsuarioRespuesta(Integer type, String attribute) throws Exception {

        Linkendlist<HashMap<String, String>> lista = all();
        if (!lista.isEmpty()) {
            HashMap arr[] = lista.toArray();
            int n = arr.length;
            if (type == Utiles.ASCEDENTE) {
                // ascendente
                for (int i = 0; i < n - 1; i++) {
                    int min_index = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j].get(attribute).toString().toLowerCase()
                                .compareTo(arr[min_index].get(attribute).toString().toLowerCase()) < 0) {
                            min_index = j;
                        }
                    }

                    HashMap temp = arr[min_index];
                    arr[min_index] = arr[i];
                    arr[i] = temp;
                }
            } else {
                // descendente
                for (int i = 0; i < n - 1; i++) {
                    int min_index = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j].get(attribute).toString().toLowerCase()
                                .compareTo(arr[min_index].get(attribute).toString().toLowerCase()) > 0) {
                            min_index = j;
                        }
                    }

                    HashMap temp = arr[min_index];
                    arr[min_index] = arr[i];
                    arr[i] = temp;
                }
            }

        }
        return lista;
    }

    public Linkendlist<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        Linkendlist<HashMap<String, String>> lista = all();
        Linkendlist<HashMap<String, String>> resp = new Linkendlist<>();
        if (!lista.isEmpty()) {
            HashMap<String, String>[] arr = lista.toArray();
            // System.out.println(attribute+" "+text+" ** *** * * ** * * * *");
            switch (type) {
                case 1:
                    for (HashMap m : arr) {
                        if (m.get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                            resp.add(m);
                        }
                    }

                    break;
                case 2:
                    for (HashMap m : arr) {
                        if (m.get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                            resp.add(m);
                        }
                    }

                    break;

                default:
                    for (HashMap m : arr) {
                        if (m.get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
                            resp.add(m);
                        }
                    }
                    break;
            }
        }
        return resp;
    }

}
