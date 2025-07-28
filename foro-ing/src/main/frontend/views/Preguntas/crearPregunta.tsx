import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import {
  Button,
  ComboBox,
  DatePicker,
  Dialog,
  TextField,
  Upload,
  VerticalLayout,
  Notification,
  Icon,
} from '@vaadin/react-components';
import { useSignal } from '@vaadin/hilla-react-signals';
import { useEffect, useState } from 'react';
import { PreguntaService } from 'Frontend/generated/endpoints';
import { useNavigate } from 'react-router';

import handleError from 'Frontend/views/_ErrorHandler';
import TipoArchivo from 'Frontend/generated/yo/wagner/login/base/models/TipoArchivo';

export const config: ViewConfig = {
  title: 'Pregunta',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 3,
    title: 'Pregunta',
  },
};

export default function PreguntaForm() {
  const dialogOpened = useSignal(true);

  const [usuarios, setUsuarios] = useState<String[]>([]);
  const [categorias, setCategorias] = useState<String[]>([]);
  const [tipoArchivoEnum, setTipoArchivoEnum] = useState<string[]>([]);
  const [archivoSubido, setArchivoSubido] = useState<File | null>(null);
  const [urlArchivo, setUrlArchivo] = useState<string>('');

  const contenido = useSignal('');
  const fecha = useSignal('');
  const idUsuario = useSignal('');
  const idCategoria = useSignal('');
  const tipoArchivoSeleccionado = useSignal('');

  const navigate = useNavigate();

  const crearPregunta = async () => {
    try {
      if (
        contenido.value &&
        tipoArchivoSeleccionado.value &&
        fecha.value &&
        idUsuario.value &&
        idCategoria.value
      ) {
        const tipo = tipoArchivoSeleccionado.value;
        let contenidoArchivo = '';

        if (tipo === 'url') {
          if (!urlArchivo) {
            Notification.show('Ingrese la URL del archivo.', {
              duration: 3000,
              theme: 'error',
            });
            return;
          }
          contenidoArchivo = urlArchivo;
        } else if (archivoSubido) {
          const base64 = await convertirArchivoABase64(archivoSubido);
          const respuesta = await PreguntaService.uploadArchivo(archivoSubido.name, base64);

          if (!respuesta.exito) {
            Notification.show('Error al subir el archivo');
            return;
          }
          contenidoArchivo = respuesta.ruta; 
        }

        await PreguntaService.createPregunta(
          contenido.value,
          tipo,
          fecha.value,
          parseInt(idUsuario.value) + 1,
          parseInt(idCategoria.value) + 1,
          contenidoArchivo
        );

        Notification.show('Pregunta publicada exitosamente', {
          duration: 4000,
          position: 'bottom-end',
          theme: 'success',
        });

        setTimeout(() => navigate('/'), 1000);

        // Limpiar
        contenido.value = '';
        tipoArchivoSeleccionado.value = '';
        fecha.value = '';
        idUsuario.value = '';
        idCategoria.value = '';
        setArchivoSubido(null);
        setUrlArchivo('');
      } else {
        Notification.show('Por favor complete todos los campos.', {
          duration: 5000,
          position: 'top-center',
          theme: 'error',
        });
      }
    } catch (error) {
      handleError(error);
    }
  };

  const convertirArchivoABase64 = (file: File): Promise<string> => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        const base64 = (reader.result as string).split(',')[1];
        resolve(base64);
      };
      reader.onerror = reject;
    });
  };

  useEffect(() => {
    PreguntaService.listTipoArchivo()
      .then((result) => setTipoArchivoEnum(result ?? []))
      .catch(console.error);

    PreguntaService.listaCategoriasCombo()
      .then(setCategorias)
      .catch(console.error);

    PreguntaService.listaUsuariosCombo()
      .then(setUsuarios)
      .catch(console.error);
  }, []);

  return (
    <div className="flex justify-center items-center min-h-screen px-4">
      <Dialog
        aria-label="Registrar Pregunta"
        draggable
        modeless
        opened={dialogOpened.value}
        style={{ width: '100%', maxWidth: '600px' }}
      >
        <div className="bg-gradient-to-br from-white via-blue-50 to-blue-100 rounded-3xl shadow-xl p-xl border border-blue-200">
          <VerticalLayout theme="spacing" style={{ gap: '1rem', alignItems: 'stretch' }}>
            <div className="flex items-center gap-s mb-s">
              <Icon icon="vaadin:lightbulb" className="text-yellow-500 icon-l" />
              <h2 className="text-3xl font-extrabold text-blue-900 tracking-tight"> Publicar Pregunta</h2>
            </div>

            <TextField
              label="Contenido"
              placeholder="Escriba su pregunta"
              value={contenido.value}
              onValueChanged={(e) => (contenido.value = e.detail.value)}
            />

            <ComboBox
              label="Tipo de Archivo"
              items={tipoArchivoEnum}
              value={tipoArchivoSeleccionado.value}
              onValueChanged={(e) => {
                tipoArchivoSeleccionado.value = e.detail.value;
                setUrlArchivo('');
                setArchivoSubido(null);
              }}
              placeholder="Seleccione"
            />

            {tipoArchivoSeleccionado.value === 'url' ? (
              <TextField
                label="Pegar URL del archivo"
                placeholder="https://ejemplo.com/archivo.pdf"
                value={urlArchivo}
                onValueChanged={(e) => setUrlArchivo(e.detail.value)}
              />
            ) : (
              <Upload
                maxFiles={1}
                accept=".pdf,.jpg,.png,.docx"
                onUploadSuccess={(e) => {
                  const file = e.detail.file;
                  Notification.show(`Archivo ${file.name} seleccionado`);
                  setArchivoSubido(file);
                }}
              />
            )}

            <DatePicker
              label="Fecha"
              value={fecha.value ?? undefined}
              onValueChanged={(e) => (fecha.value = e.detail.value)}
            />

            <ComboBox
              label="Usuario"
              items={usuarios}
              value={idUsuario.value}
              onValueChanged={(e) => (idUsuario.value = e.detail.value)}
              placeholder="Seleccione"
            />

            <ComboBox
              label="Categoría"
              items={categorias}
              value={idCategoria.value}
              onValueChanged={(e) => (idCategoria.value = e.detail.value)}
              placeholder="Seleccione"
            />

            <Button
              theme="primary contrast"
              className="bg-blue-600 hover:bg-blue-700 text-white rounded-lg"
              onClick={crearPregunta}
            >
               Publicar
            </Button>
          </VerticalLayout>
        </div>
      </Dialog>
    </div>
  );
}
