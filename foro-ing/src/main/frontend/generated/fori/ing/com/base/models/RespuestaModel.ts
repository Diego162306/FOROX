import { _getPropertyModel as _getPropertyModel_1, makeObjectEmptyValueCreator as makeObjectEmptyValueCreator_1, NumberModel as NumberModel_1, ObjectModel as ObjectModel_1, StringModel as StringModel_1 } from "@vaadin/hilla-lit-form";
import type Respuesta_1 from "./Respuesta.js";
class RespuestaModel<T extends Respuesta_1 = Respuesta_1> extends ObjectModel_1<T> {
    static override createEmptyValue = makeObjectEmptyValueCreator_1(RespuestaModel);
    get id(): NumberModel_1 {
        return this[_getPropertyModel_1]("id", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
    get contenido(): StringModel_1 {
        return this[_getPropertyModel_1]("contenido", (parent, key) => new StringModel_1(parent, key, true, { meta: { javaType: "java.lang.String" } }));
    }
    get idtipoArchivo(): NumberModel_1 {
        return this[_getPropertyModel_1]("idtipoArchivo", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
    get fecha(): StringModel_1 {
        return this[_getPropertyModel_1]("fecha", (parent, key) => new StringModel_1(parent, key, true, { meta: { javaType: "java.util.Date" } }));
    }
    get idPregunta(): NumberModel_1 {
        return this[_getPropertyModel_1]("idPregunta", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
    get idUsuario(): NumberModel_1 {
        return this[_getPropertyModel_1]("idUsuario", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
}
export default RespuestaModel;
