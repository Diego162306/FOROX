import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, DatePicker, Dialog, Grid, GridColumn, GridItemModel, GridSortColumn, HorizontalLayout, Icon, Select, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { TaskService, UsuarioService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import Task from 'Frontend/generated/fori/ing/com/taskmanagement/domain/Task';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import { useEffect, useState } from 'react';
import Usuario from 'Frontend/generated/fori/ing/com/base/models/Usuario';

export const config: ViewConfig = {
  title: 'Usuario',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'USUARIOS',
  },
};

//===============================================================
//Esta funcion sera un disparador de eventos
type UsuarioEntryFormProps = {
  onUsuarioCreated?: () => void;
}
/*
//update Usuario
type UsuarioEntryFormUpdateProps = {
  onUsuarioUpdated?: () => void;
};
*/
const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});

//===============================================================
//GUARDAR Usuario
function UsuarioEntryForm(props: UsuarioEntryFormProps) {

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal('');
  const apellido = useSignal('');
  const telefono = useSignal('');
  const dni = useSignal('');


  const createUsuario = async () => {
    try {
      if (nombre.value.trim().length > 0 &&
        telefono.value.trim().length > 0 &&
        apellido.value.trim().length > 0) {
        await UsuarioService.createUsuario(nombre.value, apellido.value, telefono.value, dni.value);
        if (props.onUsuarioCreated) {
          props.onUsuarioCreated();
        }
        nombre.value = '';
        telefono.value = '';
        apellido.value = '';
        dni.value = '';
        // Limpiar los campos del formulario
        dialogOpened.value = false;
        Notification.show('Usuario creado', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };



  //===========================================================================================
  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        aria-label="Registrar Uusuario"
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
            Agregar Usuario
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createUsuario}>
              Registrar
            </Button>
          </>
        )}
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre del usuario"
            placeholder="Ingrese el nombre del usuario UWU"
            aria-label="Nombre del usuario"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <TextField label="Apellidos del usuario"
            placeholder='Ingrese el apellido del usuario'
            aria-label='Apellido del usuario'
            value={apellido.value}
            onValueChanged={(evt) => (apellido.value = evt.detail.value)}
          />
          <TextField
            label="Teléfono del usuario"
            placeholder="Ingrese el teléfono del usuario"
            aria-label="Teléfono del usuario"
            value={telefono.value}
            onValueChanged={(evt) => (telefono.value = evt.detail.value)}
          />
          <TextField
            label="DNI del usuario"
            placeholder="Ingrese el DNI del usuario"
            aria-label="DNI del usuario"
            value={dni.value}
            onValueChanged={(evt) => (dni.value = evt.detail.value)}
          />

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
//LISTA de USUARIOS
export default function UsuarioListView() {
  const [items, setItems] = useState([]);
  useEffect(() => {
    UsuarioService.listAllUsuario().then((data) => {
      setItems(data);
    });
  }, []);
  const reloadUsuario = () => {
    UsuarioService.listAllUsuario().then((data) => setItems(data));
  };
  const order = (event, columnId) => {
    console.log(event);
    const direction = event.detail.value;
    console.log(`sorting by ${columnId} to ${direction}`);
    var dir = (direction == "asc") ? 1 : 2;
    UsuarioService.order(columnId, dir).then((data) => {
      setItems(data);
    });
  };
  const criterio = useSignal('');
  const texto = useSignal('');
  const itemSelect = [
    {
      label: 'Nombre',
      value: 'nombre',
    },
    {
      label: 'Apellido',
      value: 'apellido',
    },
    {
      label: 'Dni',
      value: 'dni',
    },
  ];
  const search = async () => {
    try {
      console.log(criterio.value + " " + texto.value);
      UsuarioService.search(criterio.value, texto.value, 0).then(function (data) {
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
        <Button >Editar</Button>
      </span>
    )
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Usuarios">
        <Group>
          <UsuarioEntryForm onUsuarioCreated={reloadUsuario} />
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
        <GridSortColumn onDirectionChanged={(e) => order(e, "nombre")}  path="nombre" header="Nombre" />
        <GridSortColumn onDirectionChanged={(e) => order(e, "apellido")}  path="apellido" header="Apellido" />
        <GridSortColumn onDirectionChanged={(e) => order(e, "telefono")}  path="telefono" header="Telefono" />
        <GridSortColumn onDirectionChanged={(e) => order(e, "dni")}  path="dni" header="DNI " />
        
        <GridColumn renderer={link} header='Acciones' />
      </Grid>

    </main>
  );
}