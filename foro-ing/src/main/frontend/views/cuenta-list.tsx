import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, NumberField, PasswordField, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import { CuentaService, UsuarioService } from 'Frontend/generated/endpoints';

import { useEffect, useState } from 'react';
import Usuario from 'Frontend/generated/fori/ing/com/base/models/Usuario';
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
type CuentaEntryFormUpdateProps = {
  onCuentaUpdate: () => void;
};

function CuentaEntryForm(props: CuentaEntryFormProps) {
  const dialogOpened = useSignal(false);
  const [usuarios, setUsuarios] = useState<Usuario[]>([]);



  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const correo = useSignal('');
  const clave = useSignal('');
  const id_usuario = useSignal('');
  const rol = useSignal('');


  const createCuenta = async () => {
    try {
      if (correo.value.trim().length > 0 && clave.value.trim().length > 0 && id_usuario.value.trim().length > 0 && rol.value.trim().length > 0) {
        const id_usuariovalue = parseInt(id_usuario.value) + 1;
        await CuentaService.createCuenta(correo.value, clave.value, id_usuariovalue, rol.value);



        if (props.onCuentaCreated) {
          props.onCuentaCreated();
        }
        correo.value = '';
        clave.value = '';
        id_usuario.value = '';
        rol.value = '';
        dialogOpened.value = false;
        Notification.show('Cuenta creada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };
  useEffect(() => {
    CuentaService.listaUsuarioCombo()
      .then((result) => setUsuarios(result))
      .catch(console.error);
  }, []);
   

  


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
            <TextField label="correo"
              placeholder='Ingrese el email de la Cuenta'
              aria-label='Ingrese el email de la Cuenta'
              value={correo.value}
              onValueChanged={(evt) => (correo.value = evt.detail.value)}
            />
            <PasswordField
              label="Clave"
              placeholder="Ingrese la clave de la Cuenta"
              aria-label="Ingrese la clave de la Cuenta"
              value={clave.value}
              onValueChanged={(evt) => (clave.value = evt.detail.value)}
              errorMessage="La contraseña debe tener al menos 6 caracteres"
              invalid={clave.value.length > 0 && clave.value.length < 6}
            />

            <ComboBox
              label="Rol"
              placeholder="Seleccione el rol de la Cuenta"
              items={[
                { label: 'Administrador', value: 'ADMIN' },
                { label: 'Usuario', value: 'USER' }
              ]}
              itemLabelPath="label"
              itemValuePath="value"
              value={rol.value}
              onValueChanged={(e) => (rol.value = e.detail.value)}
            />
           <ComboBox
              label="Usuarios"
              items={usuarios}
              value={id_usuario.value}
              onValueChanged={(e) => (id_usuario.value = e.detail.value)}
              placeholder="Seleccione la Persona"
            />  
          


          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Registrar</Button>

    </>
  );
}

// CuentaEntryFormUpdate

function CuentaEntryFormUpdate(props: CuentaEntryFormUpdateProps) {
  const dialogOpened = useSignal(false);
  const [Usuarios, setUsuarios] = useState<String[]>([]);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const clave = useSignal(props.arguments.clave);
  const estado = useSignal(props.arguments.estado);
  const ident = useSignal(props.arguments.id);
  const id_usuario = useSignal(props.arguments.id_usuario);
  const rol = useSignal(props.arguments.rol);   



  const updateCuenta = async () => {
    try {
      if (clave.value.trim().length > 0 && estado.value.trim().length > 0) {
        const idUsuariovalue = parseInt(id_usuario.value) + 1;

        await CuentaService.updateCuenta(parseInt(ident.value), clave.value,idUsuariovalue, estado.value, rol.value);
        if (props.onCuentaUpdate) {
          props.onCuentaUpdate();
        }
        clave.value = '';
        id_usuario.value = '';
        rol.value = '';
        dialogOpened.value = false;

        Notification.show('Cuenta actualizada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo actualizar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    } catch (error) {
      console.log(error);
      handleError(error);

    }


  };
  


  return (
    <>
      <Dialog
        aria-label="Actualizar Cuenta"
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
            Actualizar Cuenta
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateCuenta}>
              Actualizar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
           <VerticalLayout style={{ alignItems: 'stretch' }}>
           
            <PasswordField
              label="Clave"
              placeholder="Ingrese la clave de la Cuenta"
              aria-label="Ingrese la clave de la Cuenta"
              value={clave.value}
              onValueChanged={(evt) => (clave.value = evt.detail.value)}
              errorMessage="La contraseña debe tener al menos 6 caracteres"
              invalid={clave.value.length > 0 && clave.value.length < 6}
            />

            
          <ComboBox
              label="Usuarios"
              items={Usuarios}
              value={id_usuario.value}
              onValueChanged={(e) => (id_usuario.value = e.detail.value)}
              placeholder="Seleccione el tipo de archivo"
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Editar</Button>
    </>
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
    list: () => CuentaService.listAll(),
  });

  const link = ({ item }: { item: Cuenta }) => {
    return (
      <span>
        <CuentaEntryFormUpdate arguments={item} onCuentaUpdate={dataProvider.refresh} />
      </span>
    );
  };


  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Cuentas">
        <Group>
          <CuentaEntryForm onCuentaCreated={() => dataProvider.refresh()} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn path="index" header="Index" renderer={index} />
        <GridColumn path="correo" header="Email" />
        <GridColumn
          header="Clave"
          renderer={({ item }) => <span>{'*'.repeat(item.clave.length)}</span>}
        />
       
        <GridColumn path="id_usuario" header=" Usuario" />
        <GridColumn path="rol" header="Rol" />


        <GridColumn header="Acciones" renderer={link} />

      </Grid>
    </main>
  );
}