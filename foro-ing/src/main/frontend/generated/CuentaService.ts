import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type Authentication_1 from "./org/springframework/security/core/Authentication.js";
import type Cuenta_1 from "./yo/wagner/login/base/models/Cuenta.js";
async function createCuenta_1(correo: string | undefined, clave: string | undefined, idUsuario: number | undefined, estado: boolean, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CuentaService", "createCuenta", { correo, clave, idUsuario, estado }, init); }
async function createRoles_1(init?: EndpointRequestInit_1): Promise<Record<string, string | undefined> | undefined> { return client_1.call("CuentaService", "createRoles", {}, init); }
async function getAuthentication_1(init?: EndpointRequestInit_1): Promise<Authentication_1 | undefined> { return client_1.call("CuentaService", "getAuthentication", {}, init); }
async function isLogin_1(init?: EndpointRequestInit_1): Promise<boolean | undefined> { return client_1.call("CuentaService", "isLogin", {}, init); }
async function lisAllCuenta_1(init?: EndpointRequestInit_1): Promise<Array<Cuenta_1 | undefined> | undefined> { return client_1.call("CuentaService", "lisAllCuenta", {}, init); }
async function listAll_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CuentaService", "listAll", {}, init); }
async function listaUsuarioCombo_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CuentaService", "listaUsuarioCombo", {}, init); }
async function login_1(correo: string | undefined, Clave: string | undefined, init?: EndpointRequestInit_1): Promise<Record<string, unknown> | undefined> { return client_1.call("CuentaService", "login", { correo, Clave }, init); }
async function logout_1(init?: EndpointRequestInit_1): Promise<Record<string, string | undefined> | undefined> { return client_1.call("CuentaService", "logout", {}, init); }
async function updateCuenta_1(id: number | undefined, clave: string | undefined, idUsuario: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CuentaService", "updateCuenta", { id, clave, idUsuario }, init); }
export { createCuenta_1 as createCuenta, createRoles_1 as createRoles, getAuthentication_1 as getAuthentication, isLogin_1 as isLogin, lisAllCuenta_1 as lisAllCuenta, listAll_1 as listAll, listaUsuarioCombo_1 as listaUsuarioCombo, login_1 as login, logout_1 as logout, updateCuenta_1 as updateCuenta };
