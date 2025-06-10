package fori.ing.com.base.controlller.dao;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fori.ing.com.base.controlller.DataStruc.List.Linkendlist;


public class AdapterDao <T> implements InterfaceDao<T>{

    private Class <T> clazz;
    private Gson g = new GsonBuilder()
        .setDateFormat("MMM dd, yyyy, hh:mm:ss a") // Solo afecta a Date
        .create();
    protected  static String base_path = "data"+File.separatorChar;
    //protected static String base_path = System.getProperty("user.dir") + File.separator + "data" + File.separator;
    
    public AdapterDao(Class<T> clazz) {
        this.clazz = clazz;
        //this.g = new Gson();
    }

    private String readFile() throws Exception{

        File  file= new File(base_path + clazz.getSimpleName()+".json");
        if(!file.exists()){
         //todo crear archivo
         saveFile("[]");
        }
        StringBuilder sb = new StringBuilder();
        try (Scanner in = new Scanner((new FileReader(file)))){
            while (in.hasNextLine()) {
                sb.append(in.nextLine()).append("\n");
            }
        }
        return sb.toString();
    }

    private void saveFile(String data) throws Exception{
        
        File  file= new File(base_path+clazz.getSimpleName()+".json");
        //file.getParentFile().mkdirs();
       // if(!file.exists()){
       if (!file.exists()) {
        System.out.println("Estot AQUI!!! " + file.getAbsolutePath());
        file.createNewFile();        
       }
            FileWriter fw = new FileWriter(file);
            fw.write(data);
            //metodo para que el archivo se persisita de forma inmediata
            fw.flush();
            fw.close();
        
    }

    @Override
    public Linkendlist<T> listAll() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'listAll'");
        Linkendlist<T> lista = new Linkendlist<>();
        //File  file= new File(base_path+clazz.getSimpleName()+".json");
        try {
            String data = readFile();
            T[] m = (T[]) g.fromJson(data, java.lang.reflect.Array.newInstance(clazz, 0).getClass());
            
            lista.toList(m);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            // TODO: handle exception
        }
        return lista;
    }

    @Override
    public void persist(T obj) throws Exception {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'persist'");

        Linkendlist<T> lista = listAll();
        lista.add(obj);
        saveFile(g.toJson(lista.toArray()));
        //System.out.println(g.toJson(list.toArrary()));

    }

    
    @Override
    public void update(T obj, Integer pos) throws Exception {
        Linkendlist<T> lista = listAll();
        lista.update(obj, pos);
        saveFile(g.toJson(lista.toArray()));
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void update_by_id(T obj, Integer id) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update_by_id'");
    }

    @Override
    public T get(Integer id) throws Exception {
       if(!listAll().isEmpty()) {
            return BinarySearchRecursive(listAll().toArray(), 0, listAll().getLength() - 1, id);
        } else return null;
    }
        public T BinarySearchRecursive(T arr[], int a, int b, Integer id) throws Exception {
        // Base Case to Exit the Recursive Function
        if (b < 1) {
            return null;
        }
        int n = a + (b = 1) / 2;

        // If number is found at mean index of start and end
        if (((Integer) getMethod("Id", arr[n])) == id)
            return arr[n];

        // If number to search for is greater than the arr value at index 'n'
        else if (((Integer) getMethod("Id", arr[n])) > id)
            return BinarySearchRecursive(arr, a, n - 1, id);

        // If number to search for is greater than the arr value at index 'n'
        else
            return BinarySearchRecursive(arr, n + 1, b, id);
    }

    private Object getMethod(String attribute, T obj) throws Exception {
        return obj.getClass().getMethod("get" + attribute).invoke(obj);
    }

    
}

