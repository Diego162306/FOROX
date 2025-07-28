package yo.wagner.login.base.controller.services;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.nio.file.Paths;

import java.util.Base64;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import yo.wagner.login.base.controller.DataStruc.List.Linkendlist;
import yo.wagner.login.base.controller.dao.dao_models.DaoCategoria;
import yo.wagner.login.base.controller.dao.dao_models.DaoPregunta;
import yo.wagner.login.base.controller.dao.dao_models.DaoUsuario;
import yo.wagner.login.base.models.ArchivoAdjunto;
import yo.wagner.login.base.models.Categoria;
import yo.wagner.login.base.models.Pregunta;
import yo.wagner.login.base.models.TipoArchivo;
import yo.wagner.login.base.models.UploadResponse;
import yo.wagner.login.base.models.Usuario;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class PreguntaService {

    private DaoPregunta dp;

    public PreguntaService() {
        dp = new DaoPregunta();
    }

    public void createPregunta(String contenido, String tipoArchivo, Date fecha, Integer idUsuario, Integer idCategoria,
            String urlArchivo) throws Exception {

        if (contenido.trim().length() > 0 && tipoArchivo != null && !tipoArchivo.isEmpty() && idUsuario > 0
                && idCategoria > 0) {

            Pregunta nuevaPregunta = new Pregunta();
            nuevaPregunta.setContenido(contenido);
            nuevaPregunta.setTipoArchivo(tipoArchivo);
            nuevaPregunta.setFecha(fecha);
            nuevaPregunta.setIdUsuario(idUsuario);
            nuevaPregunta.setIdCategoria(idCategoria);

            ArchivoAdjunto archivo = new ArchivoAdjunto();

            if ("url".equalsIgnoreCase(tipoArchivo)) {
                archivo.setNombre("enlace_externo");
                archivo.setUrl(urlArchivo);

            } else {
                String nombreArchivo = Paths.get(urlArchivo).getFileName().toString();
                archivo.setNombre(nombreArchivo);
                archivo.setUrl(urlArchivo);

            }

            nuevaPregunta.setArchivoAdjunto(archivo);

            dp.setObj(nuevaPregunta);
            if (!dp.save()) {
                throw new Exception("Error al guardar la Pregunta");
            }
        }
    }

    public void updatePregunta(Integer id, String contenido, String tipoArchivo, Date fecha, Integer idUsuario,
            Integer idCategoria) throws Exception {
        if (id != null && id > 0 && contenido.trim().length() > 0 && tipoArchivo != null && idUsuario > 0
                && idCategoria > 0) {
            dp.setObj(dp.listAll().get(id - 1));
            dp.getObj().setContenido(contenido);
            dp.getObj().setTipoArchivo(tipoArchivo);
            dp.getObj().setFecha(fecha);
            dp.getObj().setIdUsuario(idUsuario);
            dp.getObj().setIdCategoria(idCategoria);
            if (!dp.update(id - 1)) {
                throw new Exception("Error al actualizar la Pregunta");
            }
        }
    }

    public List<HashMap> listaUsuariosCombo() {
        List<HashMap> lista = new ArrayList<>();
        DaoUsuario da = new DaoUsuario();
        if (!da.listAll().isEmpty()) {
            Usuario[] arreglo = da.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<HashMap> listaCategoriasCombo() {
        List<HashMap> lista = new ArrayList<>();
        DaoCategoria da = new DaoCategoria();
        if (!da.listAll().isEmpty()) {
            Categoria[] arreglo = da.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<Pregunta> lisAllPregunta() {
        return Arrays.asList(dp.listAll().toArray());

    }

    public List<String> listTipoArchivo() {
        List<String> lista = new ArrayList<>();
        for (TipoArchivo tipo : TipoArchivo.values()) {
            lista.add(tipo.name());
        }
        return lista;
    }

    public List<HashMap> lisAll() throws Exception {
        return Arrays.asList(dp.All().toArray());
    }

    public List<HashMap<String, String>> order(String atributo, Integer type) {
        try {
            return Arrays.asList(dp.orderByAttribute(type, atributo).toArray());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<HashMap<String, String>> orderbyDte(Integer type) {
        try {
            return Arrays.asList(dp.orderByDate(type).toArray());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<HashMap> search(String attribute, String text, Integer type) throws Exception {
        Linkendlist<HashMap<String, String>> lista = dp.search(attribute, text, type);
        if (!lista.isEmpty())
            return Arrays.asList(lista.toArray());
        else
            return new ArrayList<>();
    }

    public List<HashMap> listarPreguntasConRuta() throws Exception {
        return Arrays.asList(dp.All().toArray());
    }
    

    // subida de archivo

    public UploadResponse uploadArchivo(String nombre, String base64Data) throws Exception {
        final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

        if (base64Data == null || base64Data.isEmpty()) {
            throw new Exception("El archivo está vacío.");
        }

        byte[] data = Base64.getDecoder().decode(base64Data);
        if (data.length > MAX_FILE_SIZE) {
            throw new Exception("El archivo excede el tamaño máximo de 5MB.");
        }

        // Ruta relativa al directorio de trabajo
        String rutaFisica = "uploads/archivos_subidos";
        File carpeta = new File(rutaFisica);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        File archivo = new File(rutaFisica + "/" + nombre);

        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            fos.write(data);
        }

        UploadResponse respuesta = new UploadResponse();
        respuesta.setNombre(nombre);
        respuesta.setRuta("/archivos_subidos/" + nombre); // Mantener consistente
        respuesta.setExito(true);
        return respuesta;
    }
}
