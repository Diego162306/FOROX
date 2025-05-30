import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type Categoria_1 from "./fori/ing/com/base/models/Categoria.js";
async function create_1(nombre: string | undefined, descripcion: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CategoriaService", "create", { nombre, descripcion }, init); }
async function listAllCategoria_1(init?: EndpointRequestInit_1): Promise<Array<Categoria_1 | undefined> | undefined> { return client_1.call("CategoriaService", "listAllCategoria", {}, init); }
async function update_1(id: number | undefined, nombre: string | undefined, descripcion: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CategoriaService", "update", { id, nombre, descripcion }, init); }
export { create_1 as create, listAllCategoria_1 as listAllCategoria, update_1 as update };
