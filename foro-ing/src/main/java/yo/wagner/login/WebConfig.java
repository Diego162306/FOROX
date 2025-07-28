// package yo.wagner.login;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {

//     @Override
//     public void addResourceHandlers(ResourceHandlerRegistry registry) {
//         // Mapeo para archivos subidos
//         registry.addResourceHandler("/archivos_subidos/**")
//                 .addResourceLocations("file:uploads/archivos_subidos/");
    
//         // Mapeo para recursos estáticos de Vaadin
//         registry.addResourceHandler("/VAADIN/**")
//                 .addResourceLocations("classpath:/META-INF/resources/VAADIN/");
//     }
// }