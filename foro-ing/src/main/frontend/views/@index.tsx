import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, Icon, TextField, DatePicker } from '@vaadin/react-components';
import { useNavigate } from 'react-router';
import { useEffect, useState } from 'react';
import { PreguntaService } from 'Frontend/generated/endpoints';
import { CategoriaService } from 'Frontend/generated/endpoints';
import PreguntaCard from './Preguntas/PreguntaCard';
import { RespuestaService } from 'Frontend/generated/endpoints';

export const config: ViewConfig = {
  menu: { exclude: true },
};

export default function MainView() {
  const navigate = useNavigate();
  const [preguntas, setPreguntas] = useState<Array<any>>([]);
  const [busqueda, setBusqueda] = useState('');
  const [modoBusqueda, setModoBusqueda] = useState<'categoria' | 'contenido' | null>(null);
  const [fecha, setFecha] = useState<string | null>(null);
  const [categorias, setCategorias] = useState<string[]>([]);
  const [puedeResponderMap, setPuedeResponderMap] = useState<Record<number, boolean>>({});

  useEffect(() => {
    const cargarPreguntas = async () => {
      const data = await PreguntaService.listarPreguntasConRuta();
      setPreguntas(data);

      const mapa: Record<number, boolean> = {};
      for (const p of data) {
        const puede = await RespuestaService.puedeResponder(p.id);
        mapa[p.id] = puede;
      }
      setPuedeResponderMap(mapa);
    };
    cargarPreguntas();
  }, []);

  const preguntasSinRespuesta = preguntas.filter(p => !p.respuesta || p.respuesta.trim() === '');
  const preguntasRespondidas = preguntas.filter(p => p.respuesta && p.respuesta.trim() !== '');

  useEffect(() => {
    const cargarCategorias = async () => {
      if (modoBusqueda === 'categoria') {
        const data = await PreguntaService.listaCategoriasCombo();
        setCategorias(data);
      }
    };
    cargarCategorias();
  }, [modoBusqueda]);

  const handleBuscar = async () => {
    if (!modoBusqueda || !busqueda.trim()) return;
    const resultados = await PreguntaService.search(modoBusqueda, busqueda, 0);
    setPreguntas(resultados);
  };

  return (
    <main className="flex flex-col items-center bg-gradient-to-br from-blue-50 to-white p-xl min-h-screen">
      {/* Sección de bienvenida */}
      <section className="bg-white shadow-xl rounded-2xl p-l text-center max-w-5xl w-full mb-xl">
        <Icon icon="vaadin:lightbulb" className="text-yellow-500 icon-xl mb-s" />
        <h1 className="text-4xl font-extrabold text-blue-800">Bienvenido a FOROX</h1>
        <p className="text-m text-gray-600 my-s">
          Explora, aprende y ayuda a otros respondiendo preguntas académicas.
        </p>

        <div className="mt-m mb-xl">
          <Button theme="primary" onClick={() => navigate('Preguntas/crearPregunta')}>
            Realizar Pregunta
          </Button>
        </div>

        {/* Sección de búsqueda */}
        <div className="bg-gradient-to-br from-white via-blue-50 to-blue-100 p-xl rounded-2xl shadow-xl border border-blue-200">
          <h2 className="text-xl font-bold text-blue-800 mb-m text-center">Buscar Preguntas</h2>
          <div className="grid grid-cols-1 md:grid-cols-4 gap-m items-end">
            {modoBusqueda === 'contenido' && (
              <TextField
                label="Contenido"
                className="w-full focus-within:ring-2 focus-within:ring-blue-500 rounded-xl"
                value={busqueda}
                onChange={(e) => setBusqueda(e.target.value)}
              />
            )}
            {modoBusqueda === 'categoria' && (
              <ComboBox
                label="Categoría"
                className="w-full focus-within:ring-2 focus-within:ring-blue-500 rounded-xl"
                items={categorias}
                value={busqueda}
                onValueChanged={(e) => setBusqueda(e.detail.value)}
                placeholder="Selecciona categoría"
              />
            )}
            <ComboBox
              label="Buscar por"
              className="w-full focus-within:ring-2 focus-within:ring-blue-500 rounded-xl"
              items={[
                { label: 'Categoría', value: 'categoria' },
                { label: 'Contenido', value: 'contenido' },
              ]}
              value={modoBusqueda ?? ''}
              onValueChanged={(e) =>
                setModoBusqueda(e.detail.value as 'categoria' | 'contenido')
              }
              itemLabelPath="label"
              itemValuePath="value"
              placeholder="Selecciona..."
            />
            <div className="flex justify-end col-span-full">
              <Button
                onClick={handleBuscar}
                theme="primary"
                className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-xl transition-all duration-200 shadow-md hover:shadow-lg"
              >
                🔍 Buscar
              </Button>

            </div>
          </div>
        </div>
      </section>


      {/* Preguntas sin respuesta */}
      <section className="w-full max-w-6xl mb-12 mt-xl">
        <h2 className="text-2xl font-bold text-blue-900 mb-4">Preguntas por responder</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-xl">
          {preguntasSinRespuesta.length > 0 ? (
            preguntasSinRespuesta.map((pregunta, index) => (
              <div
                key={`sin-${index}`}
                className="bg-white rounded-2xl shadow-lg p-m flex flex-col gap-2 transform transition-transform hover:scale-[1.01]"
              >
                <PreguntaCard pregunta={pregunta} />

              </div>
            ))
          ) : (
            <p className="text-gray-600">No hay preguntas pendientes por responder.</p>
          )}
        </div>
      </section>

      {/* Preguntas respondidas */}
      <section className="w-full max-w-6xl mt-xl">
        <h2 className="text-2xl font-bold text-green-800 mb-4">Preguntas respondidas</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-xl">
          {preguntasRespondidas.length > 0 ? (
            preguntasRespondidas.map((pregunta, index) => (
              <div
                key={`resp-${index}`}
                className="bg-white rounded-2xl shadow-lg p-m flex flex-col gap-2 transform transition-transform hover:scale-[1.01]"
              >
                <PreguntaCard pregunta={pregunta} />
                {pregunta.respuesta && (
                  <div className="mt-2 p-m bg-green-100 border border-green-300 rounded-xl text-sm text-green-900">
                    <div className="text-sm font-semibold text-green-800">
                      Respondido por: <span className="italic">{pregunta.usuarioRespuesta || 'Anónimo'}</span>
                    </div>
                    <div className="mt-1">
                      <strong>Respuesta:</strong>
                      <p className="mt-1 whitespace-pre-line">{pregunta.respuesta}</p>
                    </div>
                  </div>

                )}
              </div>
            ))
          ) : (
            <p className="text-gray-600">No hay preguntas respondidas aún.</p>
          )}
        </div>
      </section>
    </main>
  );
}
