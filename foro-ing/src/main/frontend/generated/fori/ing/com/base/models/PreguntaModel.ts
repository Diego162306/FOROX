import { _getPropertyModel as _getPropertyModel_1, makeObjectEmptyValueCreator as makeObjectEmptyValueCreator_1, NumberModel as NumberModel_1, ObjectModel as ObjectModel_1, StringModel as StringModel_1 } from "@vaadin/hilla-lit-form";
import type Pregunta_1 from "./Pregunta.js";
import TipoArchivoModel_1 from "./TipoArchivoModel.js";
class PreguntaModel<T extends Pregunta_1 = Pregunta_1> extends ObjectModel_1<T> {
    static override createEmptyValue = makeObjectEmptyValueCreator_1(PreguntaModel);
    get id(): NumberModel_1 {
        return this[_getPropertyModel_1]("id", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
    get contenido(): StringModel_1 {
        return this[_getPropertyModel_1]("contenido", (parent, key) => new StringModel_1(parent, key, true, { meta: { javaType: "java.lang.String" } }));
    }
    get idArchivoadjunto(): TipoArchivoModel_1 {
        return this[_getPropertyModel_1]("idArchivoadjunto", (parent, key) => new TipoArchivoModel_1(parent, key, true));
    }
    get fecha(): StringModel_1 {
        return this[_getPropertyModel_1]("fecha", (parent, key) => new StringModel_1(parent, key, true, { meta: { javaType: "java.util.Date" } }));
    }
    get idUsuario(): NumberModel_1 {
        return this[_getPropertyModel_1]("idUsuario", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
    get idCategoria(): NumberModel_1 {
        return this[_getPropertyModel_1]("idCategoria", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
}
export default PreguntaModel;
