package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.server;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;


@Provider
public class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        // Permitir todas las solicitudes de cualquier origen
        HttpServletRequest responseContext;
        containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");

        // Métodos permitidos
        containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        // Encabezados permitidos
        containerResponseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // Permitir encabezados expuestos
        containerResponseContext.getHeaders().add("Access-Control-Expose-Headers", "Authorization");
        containerResponseContext.getHeaders().add("Access-Control-Expose-Headers", "Authorization, Total-records");
        containerResponseContext.getHeaders().add("JSESSIONID", "SameSite=None, Secure");

    }
}
