import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type UsuarioRespuesta_1 from "./fori/ing/com/base/models/UsuarioRespuesta.js";
async function lisAllUsuarioRespuesta_1(init?: EndpointRequestInit_1): Promise<Array<UsuarioRespuesta_1 | undefined> | undefined> { return client_1.call("UsuarioRespuestaService", "lisAllUsuarioRespuesta", {}, init); }
export { lisAllUsuarioRespuesta_1 as lisAllUsuarioRespuesta };
