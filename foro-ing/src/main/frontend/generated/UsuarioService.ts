import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type Usuario_1 from "./fori/ing/com/base/models/Usuario.js";
async function lisAllUsuario_1(init?: EndpointRequestInit_1): Promise<Array<Usuario_1 | undefined> | undefined> { return client_1.call("UsuarioService", "lisAllUsuario", {}, init); }
export { lisAllUsuario_1 as lisAllUsuario };
