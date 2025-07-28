import { useState } from 'react';
import { TextArea, Notification, Card, Icon, Button } from '@vaadin/react-components';
import { RespuestaService } from 'Frontend/generated/endpoints';
import { useNavigate } from 'react-router';

export default function PreguntaCard({ pregunta }: { pregunta: any }) {
  const navigate = useNavigate();
  console.log("Datos de pregunta recibidos:", pregunta); // <-- Añade esto

  // Verifica si pregunta tiene los datos esperados
  if (!pregunta || typeof pregunta !== 'object') {
    return (
      <Card className="w-full max-w-sm">
        <div className="p-4">
          <p>Datos de pregunta no válidos</p>
        </div>
      </Card>
    );
  }

  const irAResponder = () => {
    navigate(`/Preguntas/responder/${pregunta.id}`);
  };

  const [mostrarFormulario, setMostrarFormulario] = useState(false);
  const [respuestaTexto, setRespuestaTexto] = useState('');

  const handleEnviarRespuesta = async () => {
    try {
      await RespuestaService.crearRespuesta(
        respuestaTexto,
        parseInt(pregunta.id),
        1, // Reemplaza con ID real del usuario si ya lo tienes
        0  // Por ahora tipoArchivo es 0
      );
      Notification.show('Respuesta enviada exitosamente');
      setRespuestaTexto('');
      setMostrarFormulario(false);
    } catch (err) {
      Notification.show('Error al enviar la respuesta', { theme: 'error' });
    }
  };


  return (
    <Card className="w-full max-w-sm border border-blue-300 rounded-2xl shadow-md transition hover:shadow-lg bg-gradient-to-br from-white via-blue-50 to-blue-100">
      <div className="p-4 flex flex-col justify-between h-full">
        <div>
          <h3 className="text-xl font-bold text-blue-900 mb-2">
            {pregunta.contenido || "Pregunta sin contenido"}
          </h3>

          <div className="flex items-center gap-2 text-sm text-gray-700 mb-1">
            <Icon icon="vaadin:calendar" />
            <span>{pregunta.fecha || "Fecha no disponible"}</span>
          </div>

          <div className="flex items-center gap-2 text-sm text-gray-700 mb-1">
            <Icon icon="vaadin:user" />
            <span>{pregunta.usuario || "Usuario desconocido"}</span>
          </div>

          <div className="flex items-center gap-2 text-sm text-gray-700 mb-2">
            <Icon icon="vaadin:book" />
            <span>{pregunta.categoria || "Sin categoría"}</span>
          </div>

          {/* Mostrar archivo si está disponible */}
          {pregunta.archivoNombre && (
            <div className="mt-3">
              {pregunta.archivoNombre.match(/\.(jpg|jpeg|png|gif|bmp)$/i) ? (
                <div className="relative">
                  <img
                    src={pregunta.archivoUrl}
                    alt={pregunta.archivoNombre}
                    className="rounded-xl max-h-48 w-full object-contain border border-gray-300 shadow-sm"
                    onError={(e) => {
                      const target = e.target as HTMLImageElement;
                      target.src = 'https://via.placeholder.com/300x200?text=Imagen+no+disponible';
                      target.className = 'rounded-xl max-h-48 w-full object-contain border border-gray-300 shadow-sm bg-gray-100';
                    }}
                  />
                  <span className="absolute bottom-2 left-2 bg-black bg-opacity-50 text-white text-xs px-2 py-1 rounded">
                    {pregunta.archivoNombre}
                  </span>
                </div>
              ) : (
                <a
                  href={pregunta.archivoUrl}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="inline-flex items-center text-blue-600 hover:text-blue-800"
                >
                  <Icon icon="vaadin:paperclip" className="mr-1" />
                  {pregunta.archivoNombre}
                </a>
              )}
            </div>
          )}
        </div>

        {!mostrarFormulario ? (
          <Button theme="primary small" className="mt-4 self-end" onClick={() => setMostrarFormulario(true)}>
            Responder
          </Button>
        ) : (
          <div className="mt-4 flex flex-col gap-2">
            <TextArea
              label="Tu respuesta"
              value={respuestaTexto}
              onChange={(e) => setRespuestaTexto(e.target.value)}
              className="w-full"
            />
            <div className="flex justify-end gap-2">
              <Button theme="tertiary small" onClick={() => setMostrarFormulario(false)}>Cancelar</Button>
              <Button theme="success small" onClick={handleEnviarRespuesta}>Enviar</Button>
            </div>
          </div>
        )}

      </div>
    </Card>
  );
}