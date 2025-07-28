package yo.wagner.login.base.controller.dao.dao_models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import yo.wagner.login.base.controller.DataStruc.List.Linkendlist;
import yo.wagner.login.base.controller.dao.AdapterDao;
import yo.wagner.login.base.models.ArchivoAdjunto;
import yo.wagner.login.base.models.Pregunta;
import yo.wagner.login.base.models.Respuesta;
import yo.wagner.login.base.models.TipoArchivo;

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

    public Linkendlist<HashMap<String, String>> All() throws Exception {
        Linkendlist<HashMap<String, String>> list = new Linkendlist<>();
        if (!this.listAll().isEmpty()) {
            Pregunta[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                list.add(toDic(arreglo[i]));
            }
        }
        return list;
    }

    private HashMap<String, String> toDic(Pregunta arreglo) throws Exception {
        DaoUsuario da = new DaoUsuario();
        DaoCategoria dg = new DaoCategoria();
        DaoRespuesta dr = new DaoRespuesta(); 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        HashMap<String, String> aux = new HashMap<>();
        aux.put("id", arreglo.getId().toString());
        aux.put("contenido", arreglo.getContenido() != null ? arreglo.getContenido() : "");
        aux.put("fecha", arreglo.getFecha() != null ? sdf.format(arreglo.getFecha()) : "");

        // Usuario
        String nombreUsuario = "Desconocido";
        if (arreglo.getIdUsuario() != null && arreglo.getIdUsuario() > 0
                && arreglo.getIdUsuario() <= da.listAll().getLength()) {
            nombreUsuario = da.listAll().get(arreglo.getIdUsuario() - 1).getNombre();
        }
        aux.put("usuario", nombreUsuario);
        aux.put("idUsuario", String.valueOf(arreglo.getIdUsuario()));

        // Categoria
        String nombreCategoria = "Sin categoría";
        if (arreglo.getIdCategoria() != null && arreglo.getIdCategoria() > 0
                && arreglo.getIdCategoria() <= dg.listAll().getLength()) {
            nombreCategoria = dg.listAll().get(arreglo.getIdCategoria() - 1).getNombre();
        }
        aux.put("categoria", nombreCategoria);
        aux.put("idCategoria", String.valueOf(arreglo.getIdCategoria()));

        // Archivo adjunto
        ArchivoAdjunto archivo = arreglo.getArchivoAdjunto();
        if (archivo != null && archivo.getNombre() != null && !archivo.getNombre().isEmpty()) {
            aux.put("archivoNombre", archivo.getNombre());
            aux.put("archivoUrl", "/archivos_subidos/" + archivo.getNombre());
        }

        // Aqui añadimos la respuesta, si existe
        Respuesta respuesta = dr.findByPreguntaId(arreglo.getId());
        if (respuesta != null) {
            aux.put("respuesta", respuesta.getContenido());

            // Obtener el nombre del usuario que respondio
            String usuarioRespuesta = "Desconocido";
            if (respuesta.getIdUsuario() != null && respuesta.getIdUsuario() > 0
                    && respuesta.getIdUsuario() <= da.listAll().getLength()) {
                usuarioRespuesta = da.listAll().get(respuesta.getIdUsuario() - 1).getNombre();
            }
            aux.put("usuarioRespuesta", usuarioRespuesta);
        } else {
            aux.put("respuesta", "");
            aux.put("usuarioRespuesta", "");
        }

        return aux;
    }

    // Metodo de ordenamiento
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
                return a1.getTipoArchivo().compareToIgnoreCase(a2.getTipoArchivo());
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

    // metodo de busqueda

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
