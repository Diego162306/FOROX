package fori.ing.com.base.controlller.dao.dao_models;

import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;
import fori.ing.com.base.controlller.dao.AdapterDao;
import fori.ing.com.base.controlller.services.Utiles;
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
    public Linkendlist<Categoria> orderLastName(Integer type) {
        Linkendlist<Categoria> lista = new Linkendlist<>();
        if (!listAll().isEmpty()) {
            Integer cont = 0;
            long startTime = System.currentTimeMillis();
            Categoria arr[] = listAll().toArray();
            int n = arr.length;
            if (type == Utiles.ASCEDENTE) {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j].getNombre().toLowerCase()
                                .compareTo(arr[min_idx].getDescripcion().toLowerCase()) < 0) {
                            min_idx = j;
                            cont++;
                        }
                    }
                    Categoria temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            } else {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j].getNombre().toLowerCase()
                                .compareTo(arr[min_idx].getNombre().toLowerCase()) > 0) {
                            min_idx = j;
                            cont++;
                        }
                    }
                    Categoria temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            }

            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("se ha demorado " + endTime + " he hizo " + cont);
            lista.toList(arr);
        }
        return lista;
    }

    public Linkendlist<Categoria> orderLocate(Integer type) {
        Linkendlist<Categoria> lista = new Linkendlist<>();
        if (!listAll().isEmpty()) {
            Integer cont = 0;
            long startTime = System.currentTimeMillis();
            Categoria arr[] = listAll().toArray();
            int n = arr.length;
            if (type == Utiles.ASCEDENTE) {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++)
                        if (arr[j].getDescripcion().toLowerCase()
                                .compareTo(arr[min_idx].getDescripcion().toLowerCase()) < 0) {
                            min_idx = j;
                            cont++;
                        }

                    Categoria temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            } else {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++)
                        if (arr[j].getDescripcion().toLowerCase()
                                .compareTo(arr[min_idx].getDescripcion().toLowerCase()) > 0) {
                            min_idx = j;
                            cont++;
                        }

                    Categoria temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            }

            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("se ha demorado " + endTime + " he hizo " + cont);
            lista.toList(arr);
        }
        return lista;
    }
    private int partition(Categoria arr[], int begin, int end, Integer type) {
        Categoria pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                if (arr[j].getNombre().toLowerCase().compareTo(pivot.getNombre().toLowerCase()) < 0){
                    i++;
                    Categoria swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                if (arr[j].getNombre().toLowerCase().compareTo(pivot.getNombre().toLowerCase()) > 0) {
                    i++;
                    Categoria swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        Categoria swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    private void quickSort(Categoria arr[], int begin, int end, Integer type) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, type);

            quickSort(arr, begin, partitionIndex - 1, type);
            quickSort(arr, partitionIndex + 1, end, type);
        }
    }

    public Linkendlist<Categoria> orderQ(Integer type) {
        Linkendlist<Categoria> lista = new Linkendlist<>();
        if (!listAll().isEmpty()) {

            Categoria arr[] = listAll().toArray();
            quickSort(arr, 0, arr.length - 1, type);
            lista.toList(arr);
        }
        return lista;
    }
}