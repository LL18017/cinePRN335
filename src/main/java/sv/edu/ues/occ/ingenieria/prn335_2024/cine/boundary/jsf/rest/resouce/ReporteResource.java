package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.annotation.Resource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import net.sf.jasperreports.engine.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("reporte")
public class ReporteResource implements Serializable {
    @Resource(lookup = "jdbc/pgdb")
    DataSource pool;
    @GET
    @Path("{reporte}/{idReserva}")
    public Response getReporte(
            @PathParam("reporte") String reporte,
            @PathParam("idReserva") Integer idReserva
    ) {
        HashMap<String, Object> parametros = new HashMap<>();
        String path = null;
        switch (reporte) {
            case "tiposala":
                path = "/reportes/TipoSalaRPT.jasper";
                break;
            case "salas":
                path = "/reportes/Sala.jasper";
                parametros.put("SUBREPORT_DIR", getClass().getResource("/reportes/").getPath());
                break;
            case "salacaracteristica":
                path = "/reportes/SalaCaracteristicas.jasper";
                break;
            case "ticket":
                path = "/reportes/ticketRPT.jasper";
                if (idReserva == null) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .header("Missing-Parameter", "idReserva")
                            .build();
                }
                parametros.put("idReserva", idReserva); // Agregar parámetro para el reporte
                break;
            default:
                return Response.status(Response.Status.NOT_FOUND)
                        .header("Report-NotFound", reporte)
                        .build();
        }
        if (path != null) {
            try {
                InputStream fuenteDeReporte = getClass().getResourceAsStream(path);

                if (fuenteDeReporte != null) {
                    JasperPrint impresor = JasperFillManager.fillReport(fuenteDeReporte, parametros, pool.getConnection());
                    StreamingOutput salida = new StreamingOutput() {
                        @Override
                        public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                            try {
                                JasperExportManager.exportReportToPdfStream(impresor, outputStream);
                            } catch (Exception e) {
                                Logger.getLogger(ReporteResource.class.getName()).log(Level.SEVERE, null, e);
                                throw new IOException("NO SE PUDO EXPORTAR EL REPORTE");
                            }
                        }
                    };
                    return Response.ok(salida, "application/pdf").build();
                } else {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Resource not found at: " + path);
                    return Response.status(Response.Status.NOT_FOUND)
                            .header("Resource-NotFound", path)
                            .build();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return Response.serverError().build();
    }
}