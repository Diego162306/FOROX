import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type ArchivoAdjunto_1 from "./fori/ing/com/base/models/ArchivoAdjunto.js";
import type TipoArchivo_1 from "./fori/ing/com/base/models/TipoArchivo.js";
async function createArchivoAdjunto_1(nombre: string | undefined, url: string | undefined, tipoArchivo: TipoArchivo_1 | undefined, idRespuesta: number | undefined, idPregunta: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("ArchivoAdjuntoService", "createArchivoAdjunto", { nombre, url, tipoArchivo, idRespuesta, idPregunta }, init); }
async function lisAllArchivoAdjunto_1(init?: EndpointRequestInit_1): Promise<Array<ArchivoAdjunto_1 | undefined> | undefined> { return client_1.call("ArchivoAdjuntoService", "lisAllArchivoAdjunto", {}, init); }
async function listTipoArchivo_1(init?: EndpointRequestInit_1): Promise<Array<string | undefined> | undefined> { return client_1.call("ArchivoAdjuntoService", "listTipoArchivo", {}, init); }
async function updateArchivoAdjunto_1(id: number | undefined, nombre: string | undefined, url: string | undefined, tipoArchivo: TipoArchivo_1 | undefined, idRespuesta: number | undefined, idPregunta: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("ArchivoAdjuntoService", "updateArchivoAdjunto", { id, nombre, url, tipoArchivo, idRespuesta, idPregunta }, init); }
export { createArchivoAdjunto_1 as createArchivoAdjunto, lisAllArchivoAdjunto_1 as lisAllArchivoAdjunto, listTipoArchivo_1 as listTipoArchivo, updateArchivoAdjunto_1 as updateArchivoAdjunto };
