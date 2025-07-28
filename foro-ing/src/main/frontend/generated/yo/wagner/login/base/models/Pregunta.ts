import type ArchivoAdjunto_1 from "./ArchivoAdjunto.js";
interface Pregunta {
    id?: number;
    contenido?: string;
    tipoArchivo?: string;
    fecha?: string;
    idUsuario?: number;
    idCategoria?: number;
    archivoAdjunto?: ArchivoAdjunto_1;
}
export default Pregunta;
