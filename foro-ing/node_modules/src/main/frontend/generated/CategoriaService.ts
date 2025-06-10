import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import type Pageable_1 from "./com/vaadin/hilla/mappedtypes/Pageable.js";
import client_1 from "./connect-client.default.js";
import type Categoria_1 from "./fori/ing/com/base/models/Categoria.js";
async function createCategoria_1(nombre: string | undefined, descripcion: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CategoriaService", "createCategoria", { nombre, descripcion }, init); }
async function list_1(pageable: Pageable_1 | undefined, init?: EndpointRequestInit_1): Promise<Array<Categoria_1 | undefined> | undefined> { return client_1.call("CategoriaService", "list", { pageable }, init); }
async function listAll_1(init?: EndpointRequestInit_1): Promise<Array<Categoria_1 | undefined> | undefined> { return client_1.call("CategoriaService", "listAll", {}, init); }
async function updateCategoria_1(id: number | undefined, nombre: string | undefined, descripcion: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CategoriaService", "updateCategoria", { id, nombre, descripcion }, init); }
export { createCategoria_1 as createCategoria, list_1 as list, listAll_1 as listAll, updateCategoria_1 as updateCategoria };
