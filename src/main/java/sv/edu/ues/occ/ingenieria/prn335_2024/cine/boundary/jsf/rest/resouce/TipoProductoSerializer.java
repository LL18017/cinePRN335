package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;

import java.io.IOException;

public class TipoProductoSerializer extends StdSerializer<TipoProducto> {

    public TipoProductoSerializer() {
        this(null);
    }

    public TipoProductoSerializer(Class<TipoProducto> t) {
        super(t);
    }

    @Override
    public void serialize(TipoProducto value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("idTipoProducto", value.getIdTipoProducto());
        gen.writeStringField("nombre", value.getNombre());
        gen.writeEndObject();
    }
}
