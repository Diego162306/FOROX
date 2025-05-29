import { _enum as _enum_1, EnumModel as EnumModel_1, makeEnumEmptyValueCreator as makeEnumEmptyValueCreator_1 } from "@vaadin/hilla-lit-form";
import TipoArchivo_1 from "./TipoArchivo.js";
class TipoArchivoModel extends EnumModel_1<typeof TipoArchivo_1> {
    static override createEmptyValue = makeEnumEmptyValueCreator_1(TipoArchivoModel);
    readonly [_enum_1] = TipoArchivo_1;
}
export default TipoArchivoModel;
