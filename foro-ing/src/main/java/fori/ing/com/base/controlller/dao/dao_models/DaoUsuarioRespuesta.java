package fori.ing.com.base.controlller.dao.dao_models;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.LinkedList;
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

    public Linkendlist<HashMap<String, String>> orderByAttribute(Integer type, String attribute) throws Exception {
        Linkendlist<HashMap<String, String>> lista = new Linkendlist<>();

        if (!all().isEmpty()) {

            HashMap<String, String> arr[] = all().toArray();
            
            quickSort(arr, 0, arr.length - 1, type, attribute);
            lista.toList(arr);
            
        }
        return lista;
    }

    private void quickSort(HashMap<String, String> arr[], int begin, int end, Integer type, String attribute) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, type, attribute);
            quickSort(arr, begin, partitionIndex - 1, type, attribute);
            quickSort(arr, partitionIndex + 1, end, type, attribute);
        }
    }

    private int partition(HashMap<String, String> arr[], int begin, int end, Integer type, String attribute) {
        HashMap<String, String> pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                String valJ = arr[j].get(attribute) != null ? arr[j].get(attribute).toString() : "";
                String valPivot = pivot.get(attribute) != null ? pivot.get(attribute).toString() : "";
                if (valJ.toLowerCase().compareTo(valPivot.toLowerCase()) < 0) {
                    i++;
                    HashMap<String, String> temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                String valJ = arr[j].get(attribute) != null ? arr[j].get(attribute).toString() : "";
                String valPivot = pivot.get(attribute) != null ? pivot.get(attribute).toString() : "";
                if (valJ.toLowerCase().compareTo(valPivot.toLowerCase()) > 0) {
                    i++;
                    HashMap<String, String> temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        } 
        HashMap<String, String> temp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = temp;
        return i + 1;
    }


    public Linkendlist<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        Linkendlist<HashMap<String, String>> lista = all();
        Linkendlist<HashMap<String, String>> resp = new Linkendlist<>();
        if (!lista.isEmpty()) {
            lista = orderByAttribute(Utiles.ASCEDENTE, attribute);
            HashMap<String, String>[] arr = lista.toArray();
            Integer n = bynaryLineal(arr, attribute, text);
            System.out.println("La N de la mitad es: " + n);
            // System.out.println(attribute+" "+text+" ** *** * * ** * * * *");
            switch (type) {
                case 1:
                    if (n > 0) {
                        for (int i = n; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else if (n < 0) {
                        for (int i = 0; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else {
                        for (int i = 0; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    }

                    break;
                case 2:
                    if (n > 0) {
                        for (int i = n; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else if (n < 0) {
                        n *= -1;
                        for (int i = 0; i < n; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else {
                        for (int i = 0; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    }

                    break;

                default:

                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                    break;
            }
        }
        return resp;
    }



    public Integer bynaryLineal(HashMap<String, String>[] array, String attribute, String text)
            throws Exception {
        // 1 = lineal
        // 2 = binario
        Integer half = 0;

        if (!(array.length == 0) && !text.isEmpty()) {
            half = array.length / 2;
            int aux = 0;
            
            if (text.trim().toLowerCase().charAt(0) > array[half].get(attribute).toString().trim().toLowerCase()
                    .charAt(0))
                aux = 1;
            else if (text.trim().toLowerCase().charAt(0) < array[half].get(attribute).toString().trim().toLowerCase()
                    .charAt(0))
                aux = -1;
            half = half * aux;

        }
        return half;
    }

    // public Linkendlist<HashMap<String, String>> orderByUsuarioRespuesta(Integer type, String attribute) throws Exception {

    //     Linkendlist<HashMap<String, String>> lista = all();
    //     if (!lista.isEmpty()) {
    //         HashMap arr[] = lista.toArray();
    //         int n = arr.length;
    //         if (type == Utiles.ASCEDENTE) {
    //             // ascendente
    //             for (int i = 0; i < n - 1; i++) {
    //                 int min_index = i;
    //                 for (int j = i + 1; j < n; j++) {
    //                     if (arr[j].get(attribute).toString().toLowerCase()
    //                             .compareTo(arr[min_index].get(attribute).toString().toLowerCase()) < 0) {
    //                         min_index = j;
    //                     }
    //                 }

    //                 HashMap temp = arr[min_index];
    //                 arr[min_index] = arr[i];
    //                 arr[i] = temp;
    //             }
    //         } else {
    //             // descendente
    //             for (int i = 0; i < n - 1; i++) {
    //                 int min_index = i;
    //                 for (int j = i + 1; j < n; j++) {
    //                     if (arr[j].get(attribute).toString().toLowerCase()
    //                             .compareTo(arr[min_index].get(attribute).toString().toLowerCase()) > 0) {
    //                         min_index = j;
    //                     }
    //                 }

    //                 HashMap temp = arr[min_index];
    //                 arr[min_index] = arr[i];
    //                 arr[i] = temp;
    //             }
    //         }

    //     }
    //     return lista;
    // }

    // public Linkendlist<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
    //     Linkendlist<HashMap<String, String>> lista = all();
    //     Linkendlist<HashMap<String, String>> resp = new Linkendlist<>();
    //     if (!lista.isEmpty()) {
    //         HashMap<String, String>[] arr = lista.toArray();
    //         // System.out.println(attribute+" "+text+" ** *** * * ** * * * *");
    //         switch (type) {
    //             case 1:
    //                 for (HashMap m : arr) {
    //                     if (m.get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
    //                         resp.add(m);
    //                     }
    //                 }

    //                 break;
    //             case 2:
    //                 for (HashMap m : arr) {
    //                     if (m.get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
    //                         resp.add(m);
    //                     }
    //                 }

    //                 break;

    //             default:
    //                 for (HashMap m : arr) {
    //                     if (m.get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
    //                         resp.add(m);
    //                     }
    //                 }
    //                 break;
    //         }
    //     }
    //     return resp;
    // }

}
