import { _getPropertyModel as _getPropertyModel_1, makeObjectEmptyValueCreator as makeObjectEmptyValueCreator_1, NumberModel as NumberModel_1, ObjectModel as ObjectModel_1, StringModel as StringModel_1 } from "@vaadin/hilla-lit-form";
import type ArchivoAdjunto_1 from "./ArchivoAdjunto.js";
import TipoArchivoModel_1 from "./TipoArchivoModel.js";
class ArchivoAdjuntoModel<T extends ArchivoAdjunto_1 = ArchivoAdjunto_1> extends ObjectModel_1<T> {
    static override createEmptyValue = makeObjectEmptyValueCreator_1(ArchivoAdjuntoModel);
    get id(): NumberModel_1 {
        return this[_getPropertyModel_1]("id", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
    get nombre(): StringModel_1 {
        return this[_getPropertyModel_1]("nombre", (parent, key) => new StringModel_1(parent, key, true, { meta: { javaType: "java.lang.String" } }));
    }
    get url(): StringModel_1 {
        return this[_getPropertyModel_1]("url", (parent, key) => new StringModel_1(parent, key, true, { meta: { javaType: "java.lang.String" } }));
    }
    get tipoArchivo(): TipoArchivoModel_1 {
        return this[_getPropertyModel_1]("tipoArchivo", (parent, key) => new TipoArchivoModel_1(parent, key, true));
    }
    get idRespuesta(): NumberModel_1 {
        return this[_getPropertyModel_1]("idRespuesta", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
    get idPregunta(): NumberModel_1 {
        return this[_getPropertyModel_1]("idPregunta", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
}
export default ArchivoAdjuntoModel;
