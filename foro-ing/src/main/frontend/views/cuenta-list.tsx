import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, Dialog, Grid, GridColumn, GridItemModel, PasswordField, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import { CuentaService } from 'Frontend/generated/endpoints';
import { useEffect, useState } from 'react';
import Cuenta from 'Frontend/generated/fori/ing/com/base/models/Cuenta';

export const config: ViewConfig = {
  title: 'Cuenta',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 3,
    title: 'Cuenta',
  },
};

type CuentaEntryFormProps = {
  onCuentaCreated?: () => void;
};

function CuentaEntryForm(props: CuentaEntryFormProps) {
  const dialogOpened = useSignal(false);
  const [tipos, setTipos] = useState<String[]>([]);


  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const correo = useSignal('');
  const clave = useSignal('');
  const estado = useSignal('');






  const createCuenta = async () => {
    try {
      if (correo.value.trim().length > 0 && clave.value.trim().length > 0 && estado.value.trim().length > 0) {
        await CuentaService.createCuenta(correo.value, clave.value, estado.value.toLowerCase() === 'true');
    
        
        if (props.onCuentaCreated) {
          props.onCuentaCreated();
        }
        correo.value = '';
        clave.value = '';
        estado.value = '';
        dialogOpened.value = false;
        Notification.show('Cuenta creada exitosamsrc/main/frontend/views/persona-list.tsxente', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
        aria-label="Registrar Cuenta"
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
            Registrar Cuenta
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createCuenta}>
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
            <TextField label="mail"
              placeholder='Ingrese el nombre de la Cuenta'
              aria-label='Ingrese el nombre de la Cuenta'
              value={correo.value}
              onValueChanged={(evt) => (correo.value = evt.detail.value)}
            />
            <TextField label="clave"
              placeholder='Ingrese la edad de la Cuenta'
              aria-label='Ingrese la edad de la Cuenta'
              value={clave.value}
              onValueChanged={(evt) => (clave.value = evt.detail.value)}
            />
            <ComboBox label="Estado"
              placeholder='Seleccione el estado de la Cuenta'
              aria-label='Seleccione el estado de la Cuenta'
              items={['true', 'false']}
              value={estado.value}
              onValueChanged={(evt) => (estado.value = evt.detail.value)}
            />

          
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Registrar</Button>

    </>
  );
}

const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});

function link({ item }: { item: Cuenta }) {
  return (
    <span>
      <Button>
        Editar
      </Button>
      <Button>
        Eliminar
      </Button>
      
    </span>
  );
}



function index({ model }: { model: GridItemModel<Cuenta> }) {
  return (
    <span>
      {model.index + 1}
    </span>
  );
}




export default function CuentaLisView() {
  const dataProvider = useDataProvider<Cuenta>({
    list: () => CuentaService.lisAllCuenta(),
  });

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Cuentaes">
        <Group>
          <CuentaEntryForm onCuentaCreated={() => dataProvider.refresh()} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn path="index" header="Index" renderer={index} />
        <GridColumn path="correo" header="Usuario" />
        <GridColumn path="clave" header="clave" />
        <GridColumn path="estado" header="Estado" />
        
        

        <GridColumn header="Acciones" renderer={link} />
      </Grid>
    </main>
  );
}

