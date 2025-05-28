import { _getPropertyModel as _getPropertyModel_1, makeObjectEmptyValueCreator as makeObjectEmptyValueCreator_1, NumberModel as NumberModel_1, ObjectModel as ObjectModel_1, StringModel as StringModel_1 } from "@vaadin/hilla-lit-form";
import type UsuarioRespuesta_1 from "./UsuarioRespuesta.js";
class UsuarioRespuestaModel<T extends UsuarioRespuesta_1 = UsuarioRespuesta_1> extends ObjectModel_1<T> {
    static override createEmptyValue = makeObjectEmptyValueCreator_1(UsuarioRespuestaModel);
    get id(): NumberModel_1 {
        return this[_getPropertyModel_1]("id", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
    get idRespuesta(): NumberModel_1 {
        return this[_getPropertyModel_1]("idRespuesta", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
    get contenido(): StringModel_1 {
        return this[_getPropertyModel_1]("contenido", (parent, key) => new StringModel_1(parent, key, true, { meta: { javaType: "java.lang.String" } }));
    }
    get idUsuario(): NumberModel_1 {
        return this[_getPropertyModel_1]("idUsuario", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
    get idPregunta(): NumberModel_1 {
        return this[_getPropertyModel_1]("idPregunta", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
}
export default UsuarioRespuestaModel;
