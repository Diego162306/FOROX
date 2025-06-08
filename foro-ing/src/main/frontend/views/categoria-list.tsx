import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, DatePicker, Dialog, Grid, GridColumn, GridItemModel, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { CategoriaService } from 'Frontend/generated/endpoints';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import { useEffect } from 'react';
import Categoria from 'Frontend/generated/fori/ing/com/base/models/Categoria';

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
      if (nombre.value.trim().length > 0 && descripcion.value.trim().length > 0 ) {
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
            <TextField label="NOmbre"
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
      if (nombre.value.trim().length > 0 && descripcion.value.trim().length > 0 ) {
        
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

const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});




export default function CategoriaListView() {
  const dataProvider = useDataProvider<Categoria>({
    list: () => CategoriaService.listAll(),
  });

  function link({ item }: { item: Categoria }) {
    return (
      <span>
        <CategoriaEntryFormUpdate arguments={item} onCategoriaUpdate={dataProvider.refresh} />
      </span>
    );
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Categorias">
        <Group>
          <CategoriaEntryForm onCategoriaCreated={dataProvider.refresh} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn header="Nro" renderer={index} />
        <GridColumn path="nombre" header="Nombre" />
        <GridColumn path="descripcion" header="Descripcion" />
        <GridColumn header="Acciones" renderer={link} />
      </Grid>
    </main>
  );
}