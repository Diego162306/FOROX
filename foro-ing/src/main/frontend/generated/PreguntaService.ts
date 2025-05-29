import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type Pregunta_1 from "./fori/ing/com/base/models/Pregunta.js";
import type TipoArchivo_1 from "./fori/ing/com/base/models/TipoArchivo.js";
async function createPregunta_1(contenido: string | undefined, tipoArchivo: TipoArchivo_1 | undefined, fecha: string | undefined, idUsuario: number | undefined, idCategoria: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("PreguntaService", "createPregunta", { contenido, tipoArchivo, fecha, idUsuario, idCategoria }, init); }
async function lisAll_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("PreguntaService", "lisAll", {}, init); }
async function lisAllPregunta_1(init?: EndpointRequestInit_1): Promise<Array<Pregunta_1 | undefined> | undefined> { return client_1.call("PreguntaService", "lisAllPregunta", {}, init); }
async function listTipoArchivo_1(init?: EndpointRequestInit_1): Promise<Array<string | undefined> | undefined> { return client_1.call("PreguntaService", "listTipoArchivo", {}, init); }
async function listaCategoriasCombo_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("PreguntaService", "listaCategoriasCombo", {}, init); }
async function listaUsuariosCombo_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("PreguntaService", "listaUsuariosCombo", {}, init); }
export { createPregunta_1 as createPregunta, lisAll_1 as lisAll, lisAllPregunta_1 as lisAllPregunta, listaCategoriasCombo_1 as listaCategoriasCombo, listaUsuariosCombo_1 as listaUsuariosCombo, listTipoArchivo_1 as listTipoArchivo };
