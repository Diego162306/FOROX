package yo.wagner.login.base.controller.services;

import java.util.Date;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import yo.wagner.login.base.controller.dao.dao_models.DaoRespuesta;
import yo.wagner.login.base.models.Respuesta;

@BrowserCallable
@AnonymousAllowed
public class RespuestaService {

    private DaoRespuesta daoRespuesta = new DaoRespuesta();

    public boolean puedeResponder(int idPregunta) {
        return daoRespuesta.puedeResponder(idPregunta);
    }

    public void crearRespuesta(String contenido, Integer idPregunta, Integer idUsuario, Integer idtipoArchivo)
            throws Exception {
        if (contenido == null || contenido.trim().isEmpty()) {
            throw new Exception("El contenido no puede estar vacío.");
        }
        if (idPregunta == null || idUsuario == null) {
            throw new Exception("ID de pregunta y usuario son obligatorios.");
        }

        Respuesta respuesta = new Respuesta();
        respuesta.setContenido(contenido);
        respuesta.setFecha(new Date());
        respuesta.setIdPregunta(idPregunta);
        respuesta.setIdUsuario(idUsuario);
        respuesta.setIdtipoArchivo(idtipoArchivo != null ? idtipoArchivo : 0); // 0 si no aplica

        daoRespuesta.setObj(respuesta);
        if (!daoRespuesta.save()) {
            throw new Exception("No se pudo guardar la respuesta.");
        }
    }

}
