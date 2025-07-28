import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
async function crearRespuesta_1(contenido: string | undefined, idPregunta: number | undefined, idUsuario: number | undefined, idtipoArchivo: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("RespuestaService", "crearRespuesta", { contenido, idPregunta, idUsuario, idtipoArchivo }, init); }
async function puedeResponder_1(idPregunta: number, init?: EndpointRequestInit_1): Promise<boolean> { return client_1.call("RespuestaService", "puedeResponder", { idPregunta }, init); }
export { crearRespuesta_1 as crearRespuesta, puedeResponder_1 as puedeResponder };
