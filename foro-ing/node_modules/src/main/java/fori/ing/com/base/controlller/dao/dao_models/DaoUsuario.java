package fori.ing.com.base.controlller.dao.dao_models;

import java.util.HashMap;

import fori.ing.com.base.controlller.Utiles;
import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;
import fori.ing.com.base.controlller.dao.AdapterDao;
import fori.ing.com.base.models.Usuario;



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
        aux.put("telefono", arreglo.getTelefono().toString());
        aux.put("dni", arreglo.getDni().toString());

        return aux;
    }

        public Linkendlist<HashMap<String, String>> orderByUsuario(Integer type, String attribute) throws Exception {

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

