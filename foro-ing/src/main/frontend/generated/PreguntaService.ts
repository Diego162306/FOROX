import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type Pregunta_1 from "./fori/ing/com/base/models/Pregunta.js";
async function createPregunta_1(contenido: string | undefined, idTipoArchivo: number | undefined, fecha: string | undefined, idUsuario: number | undefined, idCategoria: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("PreguntaService", "createPregunta", { contenido, idTipoArchivo, fecha, idUsuario, idCategoria }, init); }
async function lisAllPregunta_1(init?: EndpointRequestInit_1): Promise<Array<Pregunta_1 | undefined> | undefined> { return client_1.call("PreguntaService", "lisAllPregunta", {}, init); }
async function updatePregunta_1(id: number | undefined, contenido: string | undefined, idTipoArchivo: number | undefined, fecha: string | undefined, idUsuario: number | undefined, idCategoria: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("PreguntaService", "updatePregunta", { id, contenido, idTipoArchivo, fecha, idUsuario, idCategoria }, init); }
export { createPregunta_1 as createPregunta, lisAllPregunta_1 as lisAllPregunta, updatePregunta_1 as updatePregunta };
