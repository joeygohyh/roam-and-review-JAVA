package app.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class CORSconfig {

//   @Bean
//   public WebMvcConfigurer corsConfigurator() {
//     return new WebMvcConfigurer() {
//       @Override
//       public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**").allowedOrigins("http://localhost:4200");
//       }
//     };
//   }
// }

// @Configuration
// public class CORSconfig {

//   @Bean
//   public WebMvcConfigurer corsConfigurator() {
//     return new WebMvcConfigurer() {
//       @Override
//       public void addCorsMappings(CorsRegistry registry) {
//         registry
//           .addMapping("/**")
//           .allowedOrigins("http://localhost:4200")
//           .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//           .allowedHeaders("*")
//           .allowCredentials(true);
//       }
//     };
//   }
// }


// @Configuration
// public class CORSconfig {

//     @Bean
//     public WebMvcConfigurer corsConfigurator() {
//         return new WebMvcConfigurer() {
//             @Override
//             public void addCorsMappings(CorsRegistry registry) {
//                 registry
//                     .addMapping("/**")
//                     .allowedOrigins("http://localhost:4200")
//                     // .allowedOrigins("*")
//                     .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                     .allowedHeaders("*")
//                     .allowCredentials(true);
//             }
//         };
//     }
// }
