import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, GridSortColumn, HorizontalLayout, Icon, Select, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import { PreguntaService } from 'Frontend/generated/endpoints';
import { useEffect, useState } from 'react';
import Pregunta from 'Frontend/generated/fori/ing/com/base/models/Pregunta';
import TipoArchivo from 'Frontend/generated/fori/ing/com/base/models/TipoArchivo';


export const config: ViewConfig = {
  title: 'Pregunta',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Pregunta',
  },
};


type PreguntaEntryFormProps = {
  onPreguntaCreated?: () => void;
};

type PreguntaEntryFormUpdateProps = {
  onPreguntaUpdate: () => void;
};

function PreguntaEntryForm(props: PreguntaEntryFormProps) {
  const dialogOpened = useSignal(false);
  const [usuarios, setUsuarios] = useState<String[]>([]);
  const [Categorias, setCategorias] = useState<String[]>([]);
  const [TipoArchivoEnum, setTipoArchivoEnum] = useState<any>({});


  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const contenido = useSignal('');
  const tipo = useSignal('');
  const fecha = useSignal('');
  const id_usuario = useSignal('');
  const id_categoria = useSignal('');

  const createPregunta = async () => {
    try {
      if (contenido.value.trim().length > 0 && tipo.value.trim().length > 0 && fecha.value.trim().length > 0 && id_usuario.value.trim().length > 0 && id_categoria.value.trim().length > 0) {
        const tipoArchivoVakue = TipoArchivo[tipo.value as keyof typeof TipoArchivo];
        const idUsuarioValue = parseInt(id_usuario.value) + 1;
        const idCategoriaValue = parseInt(id_categoria.value) + 1;

        await PreguntaService.createPregunta(contenido.value, tipoArchivoVakue, fecha.value, idUsuarioValue, idCategoriaValue);

        if (props.onPreguntaCreated) {
          props.onPreguntaCreated();
        }
        contenido.value = '';
        tipo.value = '';
        fecha.value = '';
        id_usuario.value = '';
        id_categoria.value = '';

        dialogOpened.value = false;
        Notification.show('Pregunta creada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  useEffect(() => {
    PreguntaService.listTipoArchivo()
      .then((result) => setTipoArchivoEnum((result || []).filter((tipo): tipo is string => tipo !== undefined)))
      .catch(console.error);
  }, []);

  useEffect(() => {
    PreguntaService.listaCategoriasCombo()
      .then((result) => setCategorias(result))
      .catch(console.error);
  }, []);

  useEffect(() => {
    PreguntaService.listaUsuariosCombo()
      .then((result) => setUsuarios(result))
      .catch(console.error);
  }, []);


  return (
    <>
      <Dialog
        aria-label="Registrar Pregunta"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Registrar Pregunta
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createPregunta}>
              Registrar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="contenido"
              placeholder='Ingrese el contenido de la Pregunta'
              aria-label='Ingrese el contenido de la Pregunta'
              value={contenido.value}
              onValueChanged={(evt) => (contenido.value = evt.detail.value)}
            />
            <ComboBox
              label="Tipo de Archivo"
              items={TipoArchivoEnum}
              value={tipo.value}
              onValueChanged={(e) => (tipo.value = e.detail.value)}
              placeholder="Seleccione el tipo de archivo"
            />
            <DatePicker
              label="Fecha"
              placeholder="Seleccione una fecha"
              aria-label="Seleccione una fecha"
              value={fecha.value ?? undefined}
              onValueChanged={(evt) => (fecha.value = evt.detail.value)}
            />
            <ComboBox
              label="Usuario"
              items={usuarios}
              value={id_usuario.value}
              onValueChanged={(e) => (id_usuario.value = e.detail.value)}
              placeholder="Seleccione el Usuario"
            />
            <ComboBox
              label="Categoria"
              items={Categorias}
              value={id_categoria.value}
              onValueChanged={(e) => (id_categoria.value = e.detail.value)}
              placeholder="Seleccione el Categoria"
            />

          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Registrar</Button>
    </>
  );
}

///update Pregunta 
function PreguntaEntryFormUpdate(props: PreguntaEntryFormUpdateProps) {
  const dialogOpened = useSignal(false);
  const [usuarios, setUsuarios] = useState<String[]>([]);
  const [Categorias, setCategorias] = useState<String[]>([]);
  const [TipoArchivoEnum, setTipoArchivoEnum] = useState<any>({});


  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };


  const contenido = useSignal(props.arguments.contenido);
  const tipo = useSignal(props.arguments.tipo);
  const fecha = useSignal(props.arguments.fecha);
  const id_usuario = useSignal(props.arguments.id_usuario);
  const id_categoria = useSignal(props.arguments.id_categoria);
  const ident = useSignal(props.arguments.id);

  const updatePregunta = async () => {
    try {
      if (contenido.value.trim().length > 0 && tipo.value.trim().length > 0 && fecha.value.trim().length > 0 && id_usuario.value.trim().length > 0 && id_categoria.value.trim().length > 0) {
        const tipoArchivoVakue = TipoArchivo[tipo.value as keyof typeof TipoArchivo];
        const idUsuarioValue = parseInt(id_usuario.value) + 1;
        const idCategoriaValue = parseInt(id_categoria.value) + 1;

        await PreguntaService.updatePregunta(parseInt(ident.value), contenido.value, tipoArchivoVakue, fecha.value, idUsuarioValue, idCategoriaValue);

        if (props.onPreguntaUpdate) {
          props.onPreguntaUpdate();
        }
        contenido.value = '';
        tipo.value = '';
        fecha.value = '';
        id_usuario.value = '';
        id_categoria.value = '';

        dialogOpened.value = false;
        Notification.show('Pregunta editada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo editar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  useEffect(() => {
    PreguntaService.listTipoArchivo()
      .then((result) => setTipoArchivoEnum((result || []).filter((tipo): tipo is string => tipo !== undefined)))
      .catch(console.error);
  }, []);

  useEffect(() => {
    PreguntaService.listaCategoriasCombo()
      .then((result) => setCategorias(result))
      .catch(console.error);
  }, []);

  useEffect(() => {
    PreguntaService.listaUsuariosCombo()
      .then((result) => setUsuarios(result))
      .catch(console.error);
  }, []);

  return (
    <>
      <Dialog
        aria-label="Editar Pregunta"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Editar Pregunta
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updatePregunta}>
              Actualiar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="contenido"
              placeholder='Ingrese el contenido de la Pregunta'
              aria-label='Ingrese el contenido de la Pregunta'
              value={contenido.value}
              onValueChanged={(evt) => (contenido.value = evt.detail.value)}
            />
            <ComboBox
              label="Tipo de Archivo"
              items={TipoArchivoEnum}
              value={tipo.value}
              onValueChanged={(e) => (tipo.value = e.detail.value)}
              placeholder="Seleccione el tipo de archivo"
            />
            <DatePicker
              label="Fecha"
              placeholder="Seleccione una fecha"
              aria-label="Seleccione una fecha"
              value={fecha.value ?? undefined}
              onValueChanged={(evt) => (fecha.value = evt.detail.value)}
            />
            <ComboBox
              label="Usuario"
              items={usuarios}
              value={id_usuario.value}
              onValueChanged={(e) => (id_usuario.value = e.detail.value)}
              placeholder="Seleccione el Usuario"
            />
            <ComboBox
              label="Categoria"
              items={Categorias}
              value={id_categoria.value}
              onValueChanged={(e) => (id_categoria.value = e.detail.value)}
              placeholder="Seleccione el Categoria"
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Editar</Button>
    </>
  );
}


function index({ model }: { model: GridItemModel<Pregunta> }) {
  return (
    <span>
      {model.index + 1}
    </span>
  );
}

const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});

function fechaRenderer({ item }: { item: Pregunta }) {
  return (
    <span>
      {item.fecha ? dateFormatter.format(new Date(item.fecha)) : ''}
    </span>
  );
}


export default function PreguntaListView() {
    const callData = () => {
    PreguntaService.lisAll().then(function (data) {
      setItems(data);
    });
  }

  const [items, setItems] = useState([]);
  useEffect(() => {
    callData();
  }, []);

  const order = (event, columnID) => {
    console.log(event);
    const direction = event.detail.value;

    var dir = (direction === 'asc' ? 1 : -1);
    PreguntaService.order(columnID, dir).then(function (data) {
      setItems(data);
    });
  }

  const orderByFecha = (event) => {
    const direction = event.detail.value;
    const dir = direction === 'asc' ? 1 : -1; 
    PreguntaService.orderbyDte(dir).then((data) => {
      setItems(data);
    });
  };


  const criterio = useSignal('');
  const texto = useSignal('');

  const itemSelect = [
    {
      label: 'Contenido',
      value: 'contenido',
    },
    {
      label: 'Usuario',
      value: 'id_usuario',
    },
    {
      label: 'Categoria',
      value: 'id_categoria',
    },
  ];

  const search = async () => {
    try {
      console.log(criterio.value + " " + texto.value);
      PreguntaService.search(criterio.value, texto.value, 0).then(function (data) {
        setItems(data);
      });

      criterio.value = '';
      texto.value = '';

      Notification.show('Busqueda realizada', { duration: 5000, position: 'bottom-end', theme: 'success' });


    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  function link({ item }: { item: Pregunta }) {
    return (
      <span>
        <PreguntaEntryFormUpdate arguments={item} onPreguntaUpdate={callData} />
      </span>
    );
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Preguntas">
        <Group>
          <PreguntaEntryForm onPreguntaCreated={callData} />
        </Group>
      </ViewToolbar>
      <HorizontalLayout theme="spacing">
        <Select items={itemSelect}
          value={criterio.value}
          onValueChanged={(evt) => (criterio.value = evt.detail.value)}
          placeholder="Selecione un cirterio">


        </Select>

        <TextField
          placeholder="Search"
          style={{ width: '50%' }}
          value={texto.value}
          onValueChanged={(evt) => (texto.value = evt.detail.value)}
        >
          <Icon slot="prefix" icon="vaadin:search" />
        </TextField>
        <Button onClick={search} theme="primary">
          BUSCAR
        </Button>
      </HorizontalLayout>
      <Grid items={items}>
        <GridColumn header="Nro" renderer={index} />
        <GridSortColumn onDirectionChanged={(e) => order(e, "contenido")} path="contenido" header="contenido" />
        {/* <GridColumn path="idArchivoadjunto" header="ArchivoAdjunto" /> */}
        <GridSortColumn path="fecha" header="Fecha de creación" renderer={fechaRenderer} onDirectionChanged={orderByFecha} />
        <GridSortColumn onDirectionChanged={(e) => order(e, "id_usuario")} path="id_usuario" header=" Usuario" />
        <GridSortColumn onDirectionChanged={(e) => order(e, "id_categoria")} path="id_categoria" header="Categoria" />
        <GridColumn header="Acciones" renderer={link} />
      </Grid>
    </main>
  );
}
