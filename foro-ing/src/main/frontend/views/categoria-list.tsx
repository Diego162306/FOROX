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
    order: 1,
    title: 'Categoria',
  },
};


type CategoriaEntryFormProps = {
  onCategoriaCreated?: () => void;
};

type CategoriaEntryFormUpdateProps = {
  onCategoriaUpdated?: () => void;
};


//GUARDAR CATEGORIA
function CancionEntryForm(props: CategoriaEntryFormProps) {
  const open= () =>{
    dialogOpened.value= true;
  };

  const close= () =>{
    dialogOpened.value= false;
  };
  
  const nombre = useSignal('');
  const descripcion = useSignal('');


  const createCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 && descripcion.value.trim().length > 0) {
        await CategoriaService.create(nombre.value,descripcion.value);
        if (props.onCategoriaCreated) {
          props.onCategoriaCreated();
        }
       
        nombre.value = '';
        descripcion.value = '';
        dialogOpened.value = false;
        Notification.show('Categoria creada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };
  
  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        modeless
        headerTitle="Nueva Categoria"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button
              onClick={() => {
                dialogOpened.value = false;
              }}
            >
              Cancelar
            </Button>
            <Button onClick={createCategoria} theme="primary">
              Registrar
            </Button>
            
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre " 
            placeholder="Ingrese el nombre de la categoria"
            aria-label="Nombre de la categoria"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <TextField label="Descripcion" 
            placeholder="Ingrese la descripcion de la categoria"
            aria-label="Descripcion de la categoria"
            value={descripcion.value}
            onValueChanged={(evt) => (descripcion.value = evt.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button
            onClick={() => {
              dialogOpened.value = true;
            }}
          >
            Agregar
          </Button>
    </>
  );
}
// ACTUALIZAR CATEGORIA
function CategoriaEntryFormUpdate(props: CategoriaEntryFormUpdateProps) {
   const open= () =>{
    dialogOpened.value= true;
  };

  const close= () =>{
    dialogOpened.value= false;
  };

  const nombre = useSignal(props.arguments.nombre);
  const descripcion = useSignal(props.arguments.descripcion);
  const ident =useSignal(props.arguments.id);

  
  useEffect(() =>{
    nombre.value= props.arguments.nombre;
    descripcion.value= props.arguments.descripcion;
    ident.value= props.arguments.id;
  }, [props.arguments]);

  const updateCategoria = async () => {
    try {
      if (nombre.value.trim().length > 0 && 
          descripcion.value.trim().length > 0) {
        await CategoriaService.update(parseInt(ident.value),nombre.value, descripcion.value);
        if (props.onCategoriaUpdated) {
          props.onCategoriaUpdated();
        }
        nombre.value = '';
        descripcion.value = '';
        dialogOpened.value = false;
        Notification.show('Categoria actualizada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo actualizar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };
  

  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
      aria-label="Editar Categoria"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        header={
          <h2
          className='draggable'
          style={{
            flex: 1,
            cursor: 'move',
            margin: 0,
            fronSize: '1.5em',
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
          <Button theme='primary' onClick={updateCategoria}>
            Actualizar
          </Button>
          </>
        )}
      >
        <VerticalLayout
        theme='spacing'
        style={{width: '300px', maxWidth: '100%', alignItems:'stretch'}}
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre" 
            placeholder="Ingrese el nombre de la categoria"
            aria-label="Nombre de la categoria"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <TextField label="Descripcion" 
            placeholder="Ingrese la descripcion de la categoria"
            aria-label="Descripcion de la categoria"
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

const dateFormatter= new Intl.DateTimeFormat(undefined,{
  dateStyle: 'medium',
}); 

export default function CategoriaView() {
  
  const dataProvider = useDataProvider<Categoria>({
    list: () => CategoriaService.listCategoria(),
  });

  function link({item}: {item: Categoria}){
    return(
      <span>
        <CategoriaEntryFormUpdate arguments={item} onCategoriaUpdated={dataProvider.refresh} />
      </span>
    )
  }
  function index({model}:{model:GridItemModel<Categoria>}) {
    return (
      <span>
        {model.index + 1} 
      </span>
    );
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Canciones">
        <Group>
          <CategoriaEntryForm onCategoriaCreated={dataProvider.refresh}/>
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn renderer={index} header="Nro" />
        <GridColumn path="nombre" header="Nombre" />
        <GridColumn path="descripcion" header="descripcion" >
        </GridColumn>
          <GridColumn header="Acciones" renderer={link} />
      </Grid>
    </main>
  );
}