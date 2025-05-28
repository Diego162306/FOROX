import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type Cuenta_1 from "./fori/ing/com/base/models/Cuenta.js";
async function createCuenta_1(correo: string | undefined, clave: string | undefined, idUsuario: number | undefined, rol: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CuentaService", "createCuenta", { correo, clave, idUsuario, rol }, init); }
async function lisAllCuenta_1(init?: EndpointRequestInit_1): Promise<Array<Cuenta_1 | undefined> | undefined> { return client_1.call("CuentaService", "lisAllCuenta", {}, init); }
async function listAll_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CuentaService", "listAll", {}, init); }
async function listaUsuarioCombo_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CuentaService", "listaUsuarioCombo", {}, init); }
async function updateCuenta_1(id: number | undefined, clave: string | undefined, idUsuario: number | undefined, rol: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CuentaService", "updateCuenta", { id, clave, idUsuario, rol }, init); }
export { createCuenta_1 as createCuenta, lisAllCuenta_1 as lisAllCuenta, listAll_1 as listAll, listaUsuarioCombo_1 as listaUsuarioCombo, updateCuenta_1 as updateCuenta };
