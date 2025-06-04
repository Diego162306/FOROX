import type TipoArchivo_1 from "./TipoArchivo.js";
interface Pregunta {
    id?: number;
    contenido?: string;
    idArchivoadjunto?: TipoArchivo_1;
    fecha?: string;
    idUsuario?: number;
    idCategoria?: number;
}
export default Pregunta;
