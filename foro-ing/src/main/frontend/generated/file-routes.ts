import { createRoute as createRoute_1 } from "@vaadin/hilla-file-router/runtime.js";
import type { AgnosticRoute as AgnosticRoute_1 } from "@vaadin/hilla-file-router/types.js";
import * as Page_1 from "../views/@index.js";
import * as Layout_1 from "../views/@layout.js";
import * as Page_2 from "../views/cuenta-list.js";
import * as Page_3 from "../views/login.js";
import * as Page_4 from "../views/Preguntas/crearPregunta.js";
import * as Page_5 from "../views/Preguntas/PreguntaCard.js";
import * as Page_6 from "../views/task-list.js";
const routes: readonly AgnosticRoute_1[] = [
    createRoute_1("", Layout_1, [
        createRoute_1("", Page_1),
        createRoute_1("cuenta-list", Page_2),
        createRoute_1("login", Page_3),
        createRoute_1("Preguntas", [
            createRoute_1("crearPregunta", Page_4),
            createRoute_1("PreguntaCard", Page_5)
        ]),
        createRoute_1("task-list", Page_6)
    ])
];
export default routes;
