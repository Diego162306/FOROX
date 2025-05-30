import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type Categoria_1 from "./fori/ing/com/base/models/Categoria.js";
async function createCategoria_1(nombre: string | undefined, descripcion: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CategoriaService", "createCategoria", { nombre, descripcion }, init); }
async function listAll_1(init?: EndpointRequestInit_1): Promise<Array<Categoria_1 | undefined> | undefined> { return client_1.call("CategoriaService", "listAll", {}, init); }
async function updateCategoria_1(id: number | undefined, nombre: string | undefined, decripcion: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CategoriaService", "updateCategoria", { id, nombre, decripcion }, init); }
export { createCategoria_1 as createCategoria, listAll_1 as listAll, updateCategoria_1 as updateCategoria };
