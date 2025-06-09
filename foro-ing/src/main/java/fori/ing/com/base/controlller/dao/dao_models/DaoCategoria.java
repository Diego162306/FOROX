package fori.ing.com.base.controlller.dao.dao_models;

import java.util.HashMap;

import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;
import fori.ing.com.base.controlller.dao.AdapterDao;
import fori.ing.com.base.controlller.excepcion.ListEmptyException;
import fori.ing.com.base.models.Categoria;

public class DaoCategoria extends AdapterDao<Categoria>{
    
    private Linkendlist<Categoria> listAll;
    private Categoria obj;

     public DaoCategoria(){
        super(Categoria.class);
    }
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

     public Linkendlist<Categoria> getListAll() {
        if (listAll == null) {
            listAll = listAll();
        }
        return listAll;
    }

     public Linkendlist<HashMap<String, String>> All() throws ListEmptyException {
        Linkendlist<HashMap<String, String>> list = new Linkendlist<>();
        if (!this.listAll().isEmpty()) {
            Categoria[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                list.add(toDic(arreglo[i]));
            }
        }
        return list;
    }

     private HashMap<String, String> toDic(Categoria arreglo) throws ListEmptyException {
        HashMap<String, String> aux = new HashMap<>();
        aux.put("id", arreglo.getId().toString());
        aux.put("nombre", arreglo.getNombre());
        aux.put("descripcion", arreglo.getDescripcion());
        return aux;
    }
     public Linkendlist<HashMap<String, String>> orderByAttribute(Integer type, String atributo) throws Exception {
        Linkendlist<HashMap<String, String>> listaOrdenada = new Linkendlist<>();
        if (!this.listAll().isEmpty()) {
            Categoria[] arreglo = this.listAll().toArray();

            quickSort(arreglo, 0, arreglo.length - 1, atributo);

            // Si type == 2 => orden descendente
            if (type != null && type == 2) {
                for (int i = arreglo.length - 1; i >= 0; i--) {
                    listaOrdenada.add(toDic(arreglo[i]));
                }
            } else {
                for (Categoria Categoria : arreglo) {
                    listaOrdenada.add(toDic(Categoria));
                }
            }
        }
        return listaOrdenada;
    }

    public void quickSort(Categoria arr[], int begin, int end, String atributo) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, atributo);

            quickSort(arr, begin, partitionIndex - 1, atributo);
            quickSort(arr, partitionIndex + 1, end, atributo);
        }
    }

    private int partition(Categoria arr[], int begin, int end, String atributo) {
        Categoria pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (compare(arr[j], pivot, atributo) <= 0) {
                i++;

                
                Categoria temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        Categoria temp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = temp;

        return i + 1;
    }

    private int compare(Categoria a1, Categoria a2, String atributo) {
        switch (atributo.toLowerCase()) {
            case "nombre":
                return a1.getNombre().compareToIgnoreCase(a2.getNombre());
            case "descripcion":
                return a1.getDescripcion().compareTo(a2.getDescripcion());
            default:
                throw new IllegalArgumentException("Atributo no válido: " + atributo);
        }
    }


    public Linkendlist<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        Linkendlist<HashMap<String, String>> lista = All();
        Linkendlist<HashMap<String, String>> resp = new Linkendlist<>();

        if (!lista.isEmpty()) {
            HashMap<String, String>[] arr = lista.toArray();
            switch (type) {
                case 1:
                    System.out.println(attribute + " " + text + " UNO");
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