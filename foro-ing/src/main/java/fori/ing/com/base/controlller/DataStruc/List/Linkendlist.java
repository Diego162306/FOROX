package fori.ing.com.base.controlller.DataStruc.List;

import fori.ing.com.base.controlller.excepcion.ListEmptyException;

public class Linkendlist<E> {

    private Node<E> head;
    private Node<E> last;
    private Integer length;

    public Integer getLength() {
        return length;
    }
    /*public void setLength(Integer length) {
        this.length = length;
    }*/

    public Linkendlist() {
        this.head = null;
        this.last = null;
        this.length = 0;
    }

    public Boolean isEmpty() {
        return head == null || length == 0;
    }

    public Node<E> getNode(Integer pos) throws ArrayIndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("Lista vacia");
            //System.out.println("La lista está vacía");
            //return null;
        } else if (pos < 0 || pos >= length) {
            //System.out.println("Posición no válida, Fuera de rango");
            //return null;
            throw new ArrayIndexOutOfBoundsException("Posición no válida, Fuera de rango");
        } else if( pos == 0){
            return head;
        } else if ((length.intValue() - 1) == pos.intValue()) {
            return last;
        } else {
            Node<E> search = head;
            Integer cont = 0;
            while (cont < pos) {  
                cont++;    
                search = search.getNext();     
                
               
            }
            return search;
        }
    } 

    private E getDataFist() throws ArrayIndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("Lista vacia");
        } else {
            return head.getData();
            
        }
    }
     
    private E getDataLast() throws ArrayIndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("Lista vacia");
        } else {
            return last.getData();
            
        }
    }
    public E get(Integer pos) throws ArrayIndexOutOfBoundsException {
        return getNode(pos).getData();/*
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("Lista vacia");
        } else if (pos < 0 || pos >= length) {
            throw new ArrayIndexOutOfBoundsException("Posición no válida, Fuera de rango");
        } else  if(pos == 0){
            return getDatFist();
        } else if (pos == length - 1) {
            return getDatLast();
        } else {
            Node<E> search = head;
            Integer cont = 0;
            while (cont < pos) {      
                search = search.getNext();     
                cont++;
               
            }
            return search.getData();
            
        }*/
    }

    private void addFirst (E data) {
       if (isEmpty()) {
            Node<E> aux = new Node<>(data);
            head = aux;
            last = aux;
        } else {
            Node<E> head_old= head;
            Node<E> aux = new Node<>(data, head_old);
            head = aux;
           
        
       }
       length++;
    }

    private void addLast (E data) {
        if (isEmpty()) {
            addFirst(data);
        } else {
            Node<E> aux = new Node<>(data);
            last.setNext(aux);
            last = aux;
            length++;
        }
        
    }


    public void add(E data, Integer pos) throws ArrayIndexOutOfBoundsException {
        if (pos == 0 ) {
            addFirst(data);
        } else if (length.intValue() == pos.intValue()) {
            addLast(data);
            /*Node<E> aux = new Node<>(data);
            head = aux;
            length++;*/

        }else {
            //
            Node<E> search_preview = getNode(pos-1);
            Node<E> search = getNode(pos);
            Node<E> aux = new Node<>(data, search);
            search_preview.setNext(aux);
            length++;
        }
    }

    public void add(E data) {
        addLast(data);
     }

    public String print(){
        if (isEmpty()) {
            return "esta vacio";
        }else{
            StringBuilder resp = new StringBuilder();
            Node<E> help = head;
            while (help != null) {
                //resp+= help.getData()+ " - ";
                resp.append(help.getData()).append(" -> **/ ");
                help = help.getNext();
            }
            resp.append("/n");
            return resp.toString();
        }
    }

    

    public void  update (E data, Integer pos) throws ArrayIndexOutOfBoundsException {
        getNode(pos).setData(data);
    }

    public void clear (){
        head = null;
        last = null;
        length = 0;
    }

    
public E deleteFirst () throws ArrayIndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("La lista se encuentra vacia");
        } else {
            E data = head.getData();
            Node<E> aux = head.getNext();
            head = aux;
            if (length.intValue() == 1) {
                last = null;
            }
            length--;
            return data;
        }
    }

    public E deleteLast() throws Exception {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("La lista se encuentra vacia");
        } else {
            E data = last.getData();
            Node<E> aux = getNode(length - 2);
            if (aux == null) {
                last = null;
                if (length == 2) {
                    last = head;
                } else {
                    head = null;
                }   
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            length --;
            return data;
        }
    }

    public E delete(Integer pos) throws Exception {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("La lista se encuentra vacia");
        } else if (pos < 0 || pos >= length) {
            throw new ArrayIndexOutOfBoundsException("Posición no válida, Fuera de rango");
        } else if (pos == 0) {
            return deleteFirst();
        } //else if (length.intValue() == pos.intValue()) {
           // return deleteLast();
        else if ((length.intValue() -1 )== pos.intValue()) {
            return deleteLast();
        }  else {
            Node<E> search_preview = getNode(pos - 1);
            Node<E> search = getNode(pos);
            E data = search_preview.getData();
            Node<E> aux = search.getNext();
            search = null;
            search_preview.setNext(aux);
            length--;
            return data;
        }

    }


//TRAnsformar la lista en un arreglo
    public E[] toArray(){
       
    
        Class clazz = null;
        E[] matriz = null;
        if (this.length > 0) {
            clazz = head.getData().getClass();
            matriz = (E[]) java.lang.reflect.Array.newInstance(clazz, this.length);
            Node<E> aux = head;
            for (int i = 0; i < length; i ++){
                matriz[i] = aux.getData();
                aux = aux.getNext();
            }
        }
        return matriz;
    }

    //UN ARREGLO A UNA LISTA
    public Linkendlist<E> toList(E[] matriz){
        clear();
        for (int i = 0; i < matriz.length; i++) {
            this.add(matriz[i]);   
        }  
        return this;
    }
    
   


/*


    public void fillFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                this.add((E) line); 
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public LinkedList<E> detectRepeatedElements() {
        HashMap<E, Integer> elementCount = new HashMap<>();
        Node<E> current = head;
        while (current != null) {
            E data = current.getData();
            elementCount.put(data, elementCount.getOrDefault(data, 0) + 1);
            current = current.getNext();
        }       
        LinkedList<E> repeatedElements = new LinkedList<>();
        for (E key : elementCount.keySet()) {
            if (elementCount.get(key) > 1) {
                repeatedElements.add(key);
            }
        }

        return repeatedElements;
    }

    public void printRepeatedElements(LinkedList<E> repeatedElements, HashMap<E, Integer> elementCount) {
        System.out.println("Elementos repetidos:");
        Node<E> current = repeatedElements.head;
        int totalRepeated = 0;
        while (current != null) {
            E data = current.getData();
            System.out.println(data + " - Repetido " + elementCount.get(data) + " veces");
            totalRepeated++;
            current = current.getNext();
        }

        System.out.println("Total repeated elements: " + totalRepeated);
        
    }


    public  static void main(String[] args) throws Exception {

        long inicioTiempo = System.currentTimeMillis();
        System.out.println("Inicio del programa");
        
     
        LinkedList<String> list = new LinkedList<>();
        String filePath = "/home/usuario/Descargas/practica02/src/data.txt"; ///home/usuario/Descargas/practica02/src

      
        list.fillFromFile(filePath);
        System.out.println("Lista original:");
        System.out.println(list.print());

       
        LinkedList<String> repeatedElements = list.detectRepeatedElements();

        
        HashMap<String, Integer> elementCount = new HashMap<>();
        Node<String> current = list.head;
        while (current != null) {
            String data = current.getData();
            elementCount.put(data, elementCount.getOrDefault(data, 0) + 1);
            current = current.getNext();
        }

       
        list.printRepeatedElements(repeatedElements, elementCount);

        long finTiempo = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución: " + (finTiempo - inicioTiempo) + " ms");
    }*/

    public static void main(String[] args) throws ArrayIndexOutOfBoundsException {
        Linkendlist<Double> lista = new Linkendlist<>();
        try {
            System.out.println("Hola");
            // lista.update(10.00, 0);

            lista.add(56.7);
            lista.add(65.7);
            lista.add(1.0, 0);
            lista.add(4.7);
            // System.out.println(lista.print());
            lista.add(9.0, 3);
            System.out.println(lista.print());
            System.out.println(lista.get(lista.getLength()-1));
            System.out.println("Actualizar");
            lista.update(10.00, 3);
            System.out.println(lista.print());
            // lista.delete(4);
            

        } catch (Exception e) {
            System.out.println("Error: " + e);
            // TODO: handle exception
        }
        System.out.println(lista.print());
        System.out.println("Final");
    }


    
}

