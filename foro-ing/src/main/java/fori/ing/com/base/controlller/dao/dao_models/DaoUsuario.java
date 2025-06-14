package fori.ing.com.base.controlller.dao.dao_models;

import java.util.HashMap;

import fori.ing.com.base.controlller.Utiles;
import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;
import fori.ing.com.base.controlller.dao.AdapterDao;
import fori.ing.com.base.models.Usuario;
import fori.ing.com.base.models.UsuarioRespuesta;



public class DaoUsuario extends AdapterDao<Usuario> {

   private Linkendlist<Usuario> listAll;
    private Usuario obj;

    public DaoUsuario() {
        super(Usuario.class);
        // TODO Auto-generated constructor stub
    }

    // getter and setter
    public Usuario getObj() {
        if (obj == null) {
            this.obj = new Usuario();

        }
        return this.obj;
    }

    public void setObj(Usuario obj) {
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

    public Linkendlist<Usuario> getListAll() {
        if (listAll == null) {
            listAll = listAll();
        }
        return listAll;
    }

    
    public static void main(String[] args) {
        DaoUsuario dp = new DaoUsuario();
        dp.getObj().setId(dp.listAll().getLength() + 1);
        dp.getObj().setNombre("Juan . j");
        dp.getObj().setApellido("Pérez");
        dp.getObj().setTelefono("123476789");   
        dp.getObj().setDni("123456789");
        
        if (dp.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }
      
    }

   
    


    public Linkendlist<HashMap<String, String>> all() throws Exception {
        Linkendlist<HashMap<String, String>> lista = new Linkendlist<>();
        if (!this.listAll().isEmpty()) {
            Usuario[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                lista.add(toDict(arreglo[i]));
            }
        }
        return lista;
    }

    private HashMap<String, String> toDict(Usuario arreglo) throws Exception {
        
        
        HashMap<String, String> aux = new HashMap<>();
        aux.put("id", arreglo.getId().toString());
        aux.put("nombre", arreglo.getNombre());
        aux.put("apellido", arreglo.getApellido());
        aux.put("telefono", arreglo.getTelefono());
        aux.put("dni", arreglo.getDni());
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


}

