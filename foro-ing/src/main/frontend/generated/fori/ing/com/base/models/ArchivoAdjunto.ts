import type TipoArchivo_1 from "./TipoArchivo.js";
interface ArchivoAdjunto {
    id?: number;
    nombre?: string;
    url?: string;
    tipoArchivo?: TipoArchivo_1;
    idRespuesta?: number;
    idPregunta?: number;
}
export default ArchivoAdjunto;
