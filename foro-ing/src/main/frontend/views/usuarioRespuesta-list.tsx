import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, GridSortColumn, HorizontalLayout, Icon, Select, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { ArchivoAdjuntoService, TaskService, UsuarioRespuestaService, UsuarioService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import Task from 'Frontend/generated/fori/ing/com/taskmanagement/domain/Task';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import { useEffect, useState } from 'react';
import Usuario from 'Frontend/generated/fori/ing/com/base/models/Usuario';
import ArchivoAdjunto from 'Frontend/generated/fori/ing/com/base/models/ArchivoAdjunto';
import UsuarioRespuesta from 'Frontend/generated/fori/ing/com/base/models/UsuarioRespuesta';

export const config: ViewConfig = {
  title: 'Usuario Respuestas',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'USUARIO RESPUESTAS',
  },
};

type UsuarioRespuestaEntryFormProps = {
  onUsuarioRespuestaCreated?: () => void;
};
//===============================================================
/*type CancionEntryFormUpdateProps = {
  onCancionUpdateted?: () => void;
};*/
const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});

//===============================================================
//GUARDAR Usuario
function UsuarioRespuestaEntryForm(props: UsuarioRespuestaEntryFormProps) {

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };


  const contenido = useSignal('');
  const respuesta = useSignal('');
  const usuario = useSignal('');
  const pregunta = useSignal('');


  const createUsuarioRespuesta = async () => {
    try {
      if (contenido.value.trim().length > 0 &&
        usuario.value.trim().length > 0 &&
        respuesta.value.trim().length > 0 &&
        pregunta.value.trim().length > 0
      ) {
        const id_respuesta = parseInt(respuesta.value) + 1;
        const id_pregunta = parseInt(pregunta.value) + 1;
        const id_usuario = parseInt(usuario.value) + 1;
        await UsuarioRespuestaService.createUsuarioRespuesta(id_usuario, id_respuesta, id_pregunta, contenido.value);
        if (props.onUsuarioRespuestaCreated) {
          props.onUsuarioRespuestaCreated();
        }
        contenido.value = '';
        usuario.value = '';
        respuesta.value = '';
        pregunta.value = '';
        dialogOpened.value = false;
        Notification.show('Usuario Respuesta creado', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  let listaPregunta = useSignal<String[]>([]);
  useEffect(() => {
    UsuarioRespuestaService.listaPregunta().then(data =>
      //console.log(data)
      listaPregunta.value = data
    );
  }, []);
  let listaRespuesta = useSignal<String[]>([]);
  useEffect(() => {
    UsuarioRespuestaService.listaRespuesta().then(data =>
      //console.log(data)
      listaRespuesta.value = data
    );
  }, []);

  let listaUsuario = useSignal<String[]>([]);
  useEffect(() => {
    UsuarioRespuestaService.listaUsuario().then(data =>
      //console.log(data)
      listaUsuario.value = data
    );
  }, []);
  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        aria-label="Registrar Archivo Adjunto"
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
            Agregar Archivo Adjunto
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createUsuarioRespuesta}>
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
            <TextField label="Contenido"
              placeholder="Ingrese el contenido de la respuesta"
              arial-label='Contenido de la Respuesta'
              value={contenido.value}
              onValueChanged={(evt) => (contenido.value = evt.detail.value)}
            />

          </VerticalLayout>
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <ComboBox label="Usuario"
              items={listaUsuario.value}
              placeholder='Seleccione un usuario'
              aria-label='Seleccione un usuario de la lista'
              value={usuario.value}
              onValueChanged={(evt) => (usuario.value = evt.detail.value)}
            />

            <ComboBox label="Pregunta"
              items={listaPregunta.value}
              placeholder='Seleccione una pregunta'
              aria-label='Seleccione una pregunta de la lista'
              value={pregunta.value}
              onValueChanged={(evt) => (pregunta.value = evt.detail.value)}
            />

            <ComboBox label="Respuesta"
              items={listaRespuesta.value}
              placeholder='Seleccione una respuesta'
              aria-label='Seleccione una respuesta de la lista'
              value={respuesta.value}
              onValueChanged={(evt) => (respuesta.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Registrar</Button>
    </>
  );
}

/*
//===============================================================
//update usuario
function UsuarioEntryFormUpdate(props: UsuarioEntryFormUpdateProps) {
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal(props.arguments.nombre);
  const nacionalidad = useSignal(props.arguments.nacionalidad);
  const ident = useSignal(props.arguments.id);
  const apellido = useSignal(props.arguments.apellido);
  const rolArtista = useSignal<RolArtistaEnum>(props.arguments.rolArtista);

  useEffect(() => {
    nombre.value = props.arguments.nombre;
    nacionalidad.value = props.arguments.nacionalidad;
    ident.value = props.arguments.id;
    apellido.value = props.arguments.apellido;
    rolArtista.value = props.arguments.rolArtista;
  }, [props.arguments]);

  const updateArtista = async () => {
    try {
      if (nombre.value.trim().length > 0 &&
        nacionalidad.value.trim().length > 0 && apellido.value.trim().length > 0) {
        await ArtistaService.updateArtista(parseInt(ident.value), nombre.value, nacionalidad.value, rolArtista.value, apellido.value);
        if (props.onArtistaUpdateted) {
          props.onArtistaUpdateted();
        }
        nombre.value = '';
        nacionalidad.value = '';
        apellido.value = '';
        rolArtista.value = undefined;
        dialogOpened.value = false;
        Notification.show('Artista actualizada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo actualizar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  //ayuda a cargar los paiseas, y no que es un DAtaProvider(EL ING LO MENCIONO)
  let pais = useSignal<String[]>([]);
  useEffect(() => {
    ArtistaService.listCountry().then(data =>
      pais.value = data
    );
  }, []);


  return (
    <>
      <Dialog
        aria-label="Editar Banda"
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
            Actualizar Artista
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateArtista}>
              Actualizar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
            <TextField label="Nombre del artista"
              placeholder="Ingrese el nombre del artista UWU"
              aria-label="Nombre del artista"
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <TextField label="Apellidos del artista"
              placeholder='Ingrese el apellido del artista'
              aria-label='Apellido del artista'
              value={apellido.value}
              onValueChanged={(evt) => (apellido.value = evt.detail.value)}
            />
            <ComboBox label="Nacionalidad"
              items={pais.value}
              placeholder='Seleccione un pais'
              aria-label='Seleccione un pais de la lista'
              value={nacionalidad.value}
              onValueChanged={(evt) => (nacionalidad.value = evt.detail.value)}
            />
            <ComboBox label="Rol del artista"
              items={Object.values(RolArtistaEnum)}
              placeholder='Seleccione un rol'
              aria-label='Seleccione un rol de la lista'
              value={rolArtista.value}
              onValueChanged={(evt) => (rolArtista.value = evt.detail.value)}
            />

          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Editar</Button>
    </>
  );
}
*/
//===============================================================
//LISTA de ARCHIVOS ADJUNTOS
export default function UsuarioRespuestaListView() {
  const [items, setItems] = useState([]);
  useEffect(() => {
    UsuarioRespuestaService.listAll().then((data) => {
      setItems(data);
    });
  }, []);
  const reloadUsuarioRespuestas = () => {
    UsuarioRespuestaService.listAll().then((data) => setItems(data));
  };
  const order = (event, columnId) => {
    console.log(event);
    const direction = event.detail.value;
    console.log(`sorting by ${columnId} to ${direction}`);
    var dir = (direction == "asc") ? 1 : 2;
    UsuarioRespuestaService.order(columnId, dir).then((data) => {
      setItems(data);
    });
  };
  const criterio = useSignal('');
  const texto = useSignal('');
  const itemSelect = [
    {
      label: 'Usuario',
      value: 'usuario',
    },
    {
      label: 'Respuesta',
      value: 'respuesta',
    },
    {
      label: 'Pregunta',
      value: 'pregunta',
    },
    {
      label: 'Contenido',
      value: 'contenido',
    },
  ];
  const search = async () => {
    try {
      console.log(criterio.value + " " + texto.value);
      UsuarioRespuestaService.search(criterio.value, texto.value, 0).then(function (data) {
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





  function indexIndex({ model }: { model: GridItemModel<UsuarioRespuesta> }) {
    return (
      <span>
        {model.index + 1}
      </span>
    );
  }

  function link({ item }: { item: UsuarioRespuesta }) {
    return (
      <span>
        <Button>Editar</Button>
      </span>
    )
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Respuestas de Usuarios">
        <Group>
          <UsuarioRespuestaEntryForm onUsuarioRespuestaCreated={reloadUsuarioRespuestas} />
        </Group>

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
        
      </ViewToolbar>
      <Grid items={items} theme="row-stripes">
        {/*<GridColumn path="id" header="ID" />*/}
        <GridColumn renderer={indexIndex} header='Nro' />
        <GridSortColumn onDirectionChanged={(e) => order(e, "contenido")} path="contenido" header="Contenido" />
        <GridSortColumn onDirectionChanged={(e) => order(e, "usuario")} path={'usuario'} header="Usuario " />
        <GridSortColumn onDirectionChanged={(e) => order(e, "respuesta")} path={'respuesta'} header="Respuesta " />
        <GridSortColumn onDirectionChanged={(e) => order(e, "pregunta")} path={'pregunta'} header="Pregunta " />




        <GridColumn renderer={link} header='Acciones' />
      </Grid>

    </main>
  );
}