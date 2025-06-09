import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, GridSortColumn, HorizontalLayout, Icon, Select, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import { CategoriaService } from 'Frontend/generated/endpoints';
import { useEffect, useState } from 'react';
import Categoria from 'Frontend/generated/fori/ing/com/base/models/Categoria';
import TipoArchivo from 'Frontend/generated/fori/ing/com/base/models/TipoArchivo';
import { order } from 'Frontend/generated/CategoriaService';


export const config: ViewConfig = {
  title: 'Categoria',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 3,
    title: 'Categoria',
  },
};


type CategoriaEntryFormProps = {
  onCategoriaCreated?: () => void;
};

type CategoriaEntryFormUpdateProps = {
  onCategoriaUpdate: () => void;
};

function CategoriaEntryForm(props: CategoriaEntryFormProps) {
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

  const nombre = useSignal('');
  const descripcion = useSignal('');

  const createCategoria = async () => {
    try {
      if (nombre.value.trim().length > 0 && descripcion.value.trim().length > 0) {


        await CategoriaService.createCategoria(nombre.value, descripcion.value);

        if (props.onCategoriaCreated) {
          props.onCategoriaCreated();
        }
        nombre.value = '';
        descripcion.value = '';

        dialogOpened.value = false;
        Notification.show('Categoria creada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };



  return (
    <>
      <Dialog
        aria-label="Registrar Categoria"
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
            Registrar Categoria
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createCategoria}>
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
              placeholder='Ingrese el nombre de la Categoria'
              aria-label='Ingrese el nombre de la Categoria'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <TextField label="Descripcion"
              placeholder='Ingrese la descripcion de la Categoria'
              aria-label='Ingrese la descripcion de la Categoria'
              value={descripcion.value}
              onValueChanged={(evt) => (descripcion.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Registrar</Button>
    </>
  );
}

///update Categoria 
function CategoriaEntryFormUpdate(props: CategoriaEntryFormUpdateProps) {
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


  const nombre = useSignal(props.arguments.nombre);
  const descripcion = useSignal(props.arguments.descripcion);
  const ident = useSignal(props.arguments.id);

  const updateCategoria = async () => {
    try {
      if (nombre.value.trim().length > 0 && descripcion.value.trim().length > 0) {

        await CategoriaService.updateCategoria(parseInt(ident.value), nombre.value, descripcion.value);

        if (props.onCategoriaUpdate) {
          props.onCategoriaUpdate();
        }
        nombre.value = '';
        descripcion.value = '';

        dialogOpened.value = false;
        Notification.show('Categoria editada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo editar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };



  return (
    <>
      <Dialog
        aria-label="Editar Categoria"
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
            Editar Categoria
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateCategoria}>
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
            <TextField label="Nombre"
              placeholder='Ingrese el nombre de la Categoria'
              aria-label='Ingrese el nombre de la Categoria'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <TextField label="Descripcion"
              placeholder='Ingrese la descripcion de la Categoria'
              aria-label='Ingrese la descripcion de la Categoria'
              value={descripcion.value}
              onValueChanged={(evt) => (descripcion.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Editar</Button>
    </>
  );
}


function index({ model }: { model: GridItemModel<Categoria> }) {
  return (
    <span>
      {model.index + 1}
    </span>
  );
}


export default function CategoriaListView() {
  const callData = () => {
    CategoriaService.listAllCategoria().then(function (data) {
    console.log('Datos cargados:', data); // <-- Asegúrate de ver esto
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
    CategoriaService.order(columnID).then(function (data) {
      setItems(data);
    });
  }
  const criterio = useSignal('');
  const texto = useSignal('');

  const itemSelect = [
    {
      label: 'Nombre',
      value: 'nombre',
    },
    {
      label: 'Descripcion',
      value: 'descripcion',
    },
  ];

  const search = async () => {
    try {
      console.log(criterio.value + " " + texto.value);
      CategoriaService.search(criterio.value, texto.value, 0).then(function (data) {
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

  function link({ item }: { item: Categoria }) {
    return (
      <span>
        <CategoriaEntryFormUpdate arguments={item} onCategoriaUpdate={callData} />
      </span>
    );
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Categoria">
        <Group>
          <CategoriaEntryForm onCategoriaCreated={callData} />
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
      <GridSortColumn path="nombre" header="Nombre" onDirectionChanged={(e) => order(e, 'nombre')} />
      <GridSortColumn path="descripcion" header="Descripcion" onDirectionChanged={(e) => order(e, 'descripcion')} />
      <GridColumn header="Acciones" renderer={link} />
    </Grid>
     </main >
   );
}


