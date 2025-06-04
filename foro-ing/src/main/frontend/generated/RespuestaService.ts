import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type Respuesta_1 from "./fori/ing/com/base/models/Respuesta.js";
async function createRespuesta_1(contenido: string | undefined, idTipoArchivo: number | undefined, fecha: string | undefined, idPregunta: number | undefined, idUsuario: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("RespuestaService", "createRespuesta", { contenido, idTipoArchivo, fecha, idPregunta, idUsuario }, init); }
async function lisAll_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("RespuestaService", "lisAll", {}, init); }
async function lisAllRespuesta_1(init?: EndpointRequestInit_1): Promise<Array<Respuesta_1 | undefined> | undefined> { return client_1.call("RespuestaService", "lisAllRespuesta", {}, init); }
async function listTipoArchivo_1(init?: EndpointRequestInit_1): Promise<Array<string | undefined> | undefined> { return client_1.call("RespuestaService", "listTipoArchivo", {}, init); }
async function updateRespuesta_1(id: number | undefined, contenido: string | undefined, idTipoArchivo: number | undefined, fecha: string | undefined, idUsuario: number | undefined, idPregunta: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("RespuestaService", "updateRespuesta", { id, contenido, idTipoArchivo, fecha, idUsuario, idPregunta }, init); }
export { createRespuesta_1 as createRespuesta, lisAll_1 as lisAll, lisAllRespuesta_1 as lisAllRespuesta, listTipoArchivo_1 as listTipoArchivo, updateRespuesta_1 as updateRespuesta };
