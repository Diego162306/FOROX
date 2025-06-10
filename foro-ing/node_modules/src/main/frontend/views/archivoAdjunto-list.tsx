import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { ArchivoAdjuntoService, TaskService, UsuarioService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import Task from 'Frontend/generated/fori/ing/com/taskmanagement/domain/Task';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import { useEffect, useState } from 'react';
import Usuario from 'Frontend/generated/fori/ing/com/base/models/Usuario';
import ArchivoAdjunto from 'Frontend/generated/fori/ing/com/base/models/ArchivoAdjunto';

export const config: ViewConfig = {
  title: 'Archivos Adjuntos',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'ARCHIVOS ADJUNTOS',
  },
};

type ArchivoAdjuntoEntryFormProps = {
  onArchivoAdjuntoCreated?: () => void;
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
function ArchivoAdjuntoEntryForm(props: ArchivoAdjuntoEntryFormProps) {

  

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };


  const nombre = useSignal('');
  const url = useSignal('');
  const tipo = useSignal();
  const respuesta = useSignal('');
  const pregunta = useSignal('');


  const createArchivoAdjunto = async () => {
    try {
      if (nombre.value.trim().length > 0 &&
        url.value.trim().length > 0 &&
        respuesta.value.trim().length > 0 &&
        pregunta.value.trim().length > 0
      ) {
        const id_respuesta = parseInt(respuesta.value) +1 ;
        const id_pregunta = parseInt(pregunta.value) + 1;
        await ArchivoAdjuntoService.createArchivoAdjunto(nombre.value, url.value, tipo.value, id_respuesta, id_pregunta);
        if (props.onArchivoAdjuntoCreated) {
          props.onArchivoAdjuntoCreated();
        }
        nombre.value = '';
        url.value = '';
        tipo.value = undefined;
        respuesta.value = '';
        pregunta.value = '';
        dialogOpened.value = false;
        Notification.show('Archivo Adjunto creado', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
    ArchivoAdjuntoService.listaPregunta().then(data =>
      //console.log(data)
      listaPregunta.value = data
    );
  }, []);
  let listaRespuesta = useSignal<String[]>([]);
  useEffect(() => {
    ArchivoAdjuntoService.listaRespuesta().then(data =>
      //console.log(data)
      listaRespuesta.value = data
    );
  }, []);

  let listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    ArchivoAdjuntoService.listTipoArchivo().then(data =>
      //console.log(data)
      listaTipo.value = data
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
            <Button theme="primary" onClick={createArchivoAdjunto}>
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
            <TextField label="Nombre"
              placeholder="Ingrese el nombre del Archivo"
              arial-label='Nombre del Archivo'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />

          </VerticalLayout>
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="URL"
              placeholder="Ingrese la URL del Archivo"
              arial-label='URL del Archivo'
              value={url.value}
              onValueChanged={(evt) => (url.value = evt.detail.value)}
            />

          </VerticalLayout>
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <ComboBox label="Tipo de Archivo"
              items={listaTipo.value}
              placeholder='Seleccione un tipo de archivo'
              aria-label='Seleccione un tipo de archivo de la lista'
              value={tipo.value}
              onValueChanged={(evt) => (tipo.value = evt.detail.value)}
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
export default function ArchivoAdjuntoListView () {
  const dataProvider = useDataProvider<ArchivoAdjunto>({
    list: () => ArchivoAdjuntoService.listAllArchivoAdjunto(),
  });

  function indexIndex({ model }: { model: GridItemModel<Usuario> }) {
    return (
      <span>
        {model.index + 1}
      </span>
    );
  }

  function link({ item }: { item: Usuario }) {
    return (
      <span>
        Editar
        {/*<BandaEntryFormUpdate arguments={item} onArtistaUpdateted={dataProvider.refresh} />*/}
      </span>
    )
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Archivos Adjuntos">
        <Group>
          <ArchivoAdjuntoEntryForm onArchivoAdjuntoCreated={dataProvider.refresh} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider} theme="row-stripes">
        {/*<GridColumn path="id" header="ID" />*/}
        <GridColumn renderer={indexIndex} header='Nro' />
        <GridColumn path="nombre" header="Nombre" />
        <GridColumn path="url" header="URL" />
        <GridColumn path="tipoArchivo" header="Tipo" />
        <GridColumn path={'respuesta'} header="Respuesta " />
         <GridColumn path={'pregunta'} header="Pregunta " >

        {/* <GridColumn
          header="Imagen"
          renderer={({ item }) => {
            return (
              <img
                src={item.imagen || 'https://via.placeholder.com/150'} // Imagen por defecto si no hay URL
                alt={`Imagen de ${item.nombre}`}
                style={{ width: '80px', height: '80px', objectFit: 'cover', borderRadius: '50%' }}
              />
            );
          }}
        >*/}
        


        </GridColumn>
        <GridColumn renderer={link} header='Acciones' />
      </Grid>

    </main>
  );
}