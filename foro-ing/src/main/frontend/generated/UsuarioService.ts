import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type Usuario_1 from "./fori/ing/com/base/models/Usuario.js";
async function createUsuario_1(nombre: string | undefined, apellido: string | undefined, telefono: string | undefined, dni: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("UsuarioService", "createUsuario", { nombre, apellido, telefono, dni }, init); }
async function listAll_1(init?: EndpointRequestInit_1): Promise<Array<Usuario_1 | undefined> | undefined> { return client_1.call("UsuarioService", "listAll", {}, init); }
async function listAllUsuario_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("UsuarioService", "listAllUsuario", {}, init); }
async function order_1(attribute: string | undefined, type: number | undefined, init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("UsuarioService", "order", { attribute, type }, init); }
async function search_1(attribute: string | undefined, text: string | undefined, type: number | undefined, init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("UsuarioService", "search", { attribute, text, type }, init); }
export { createUsuario_1 as createUsuario, listAll_1 as listAll, listAllUsuario_1 as listAllUsuario, order_1 as order, search_1 as search };
