package fori.ing.com.base.controlller.dao.dao_models;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;
import fori.ing.com.base.controlller.dao.AdapterDao;
import fori.ing.com.base.controlller.excepcion.ListEmptyException;
import fori.ing.com.base.models.Pregunta;
import fori.ing.com.base.models.TipoArchivo;


public class DaoPregunta extends AdapterDao<Pregunta> {
    private Pregunta obj;
    private Linkendlist<Pregunta> aux;

    public DaoPregunta() {
        super(Pregunta.class);
        // TODO Auto-generated constructor stub
    }

    // getter and setter
    public Pregunta getObj() {
        if (obj == null) {
            this.obj = new Pregunta();

        }
        return this.obj;
    }

    public void setObj(Pregunta obj) {
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
    public Linkendlist<Pregunta> getListAll() {
        if (aux == null) {
            this.aux = listAll();
        }
        return aux;
    }

    public Linkendlist<HashMap<String, String>> All() throws ListEmptyException {
        Linkendlist<HashMap<String, String>> list = new Linkendlist<>();
        if (!this.listAll().isEmpty()) {
            Pregunta[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                list.add(toDic(arreglo[i]));
            }
        }
        return list;
    }

    private HashMap<String, String> toDic(Pregunta arreglo) throws ListEmptyException {
        DaoUsuario da = new DaoUsuario();
        DaoCategoria dg = new DaoCategoria();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, String> aux = new HashMap<>();
        aux.put("id", arreglo.getId().toString());
        aux.put("contenido", arreglo.getContenido());
        aux.put("tipo", arreglo.getIdArchivoadjunto().getDeclaringClass().getSimpleName());
        aux.put("fecha", sdf.format(arreglo.getFecha()));
        aux.put("id_usuario", da.listAll().get(arreglo.getIdUsuario() - 1).getNombre());
        aux.put("id_categoria", dg.listAll().get(arreglo.getIdCategoria() - 1).getNombre());
        return aux;
    }
    //Metodo de ordenamiento
    // quickShort
    public Linkendlist<HashMap<String, String>> orderByAttribute(Integer type, String atributo) throws Exception {
        Linkendlist<HashMap<String, String>> listaOrdenada = new Linkendlist<>();
        if (!this.listAll().isEmpty()) {
            Pregunta[] arreglo = this.listAll().toArray();

            quickSort(arreglo, 0, arreglo.length - 1, atributo);

            // Si type == 2 => orden descendente
            if (type != null && type == 2) {
                for (int i = arreglo.length - 1; i >= 0; i--) {
                    listaOrdenada.add(toDic(arreglo[i]));
                }
            } else {
                for (Pregunta Pregunta : arreglo) {
                    listaOrdenada.add(toDic(Pregunta));
                }
            }
        }
        return listaOrdenada;
    }

    public Linkendlist<HashMap<String, String>> orderByDate(Integer type) throws Exception {
        return orderByAttribute(type, "fecha");
    }

    public void quickSort(Pregunta arr[], int begin, int end, String atributo) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, atributo);

            quickSort(arr, begin, partitionIndex - 1, atributo);
            quickSort(arr, partitionIndex + 1, end, atributo);
        }
    }

    private int partition(Pregunta arr[], int begin, int end, String atributo) {
        Pregunta pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (compare(arr[j], pivot, atributo) <= 0) {
                i++;

                Pregunta temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        Pregunta temp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = temp;

        return i + 1;
    }

    private int compare(Pregunta a1, Pregunta a2, String atributo) {
        switch (atributo.toLowerCase()) {
            case "contenido":
                return a1.getContenido().compareToIgnoreCase(a2.getContenido());
            case "archivoadjunto":
                return a1.getIdArchivoadjunto().compareTo(a2.getIdArchivoadjunto());
            case "fecha":
                return a1.getFecha().compareTo(a2.getFecha());
            case "Usuario":
                return a1.getIdUsuario().compareTo(a2.getIdUsuario());
            case "categoria":
                return a1.getIdCategoria().compareTo(a2.getIdCategoria());
            default:
                throw new IllegalArgumentException("Atributo no válido: " + atributo);
        }
    }

    //metodo de busqueda

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

    public static void main(String[] args) {
        DaoPregunta dp = new DaoPregunta();
        dp.getObj().setId(dp.listAll().getLength() + 1);
        dp.getObj().setContenido( "Hola");
        dp.getObj().setIdArchivoadjunto(TipoArchivo.AUDIO);
        dp.getObj().setFecha(new Date());
        dp.getObj().setIdUsuario(1);
        dp.getObj().setIdCategoria(2);
        if (dp.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }
      
    }

   

    

}
