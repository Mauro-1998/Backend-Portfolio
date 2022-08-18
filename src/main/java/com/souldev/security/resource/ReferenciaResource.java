package com.souldev.security.resource;



import com.souldev.security.dto.ReferenciaDTO;
import com.souldev.security.resource.rest.ReferenciaClient;
import com.souldev.security.services.ReferenciaService;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/referencia")
public class ReferenciaResource implements ReferenciaClient {

    @Autowired
    ReferenciaService referenciaService;


    @Override
    public ResponseEntity<JsonObject> listarReferencias(Integer idPersona) { return referenciaService.listarReferencias(idPersona); }

    @Override
    public ResponseEntity<JsonObject> guardarReferencia(ReferenciaDTO r) {
        return referenciaService.guardar(r);
    }

    @Override
    public ResponseEntity<JsonObject> borrarReferencia(Integer idReferencia) { return referenciaService.eliminarReferencia(idReferencia); }

    @Override
    public ResponseEntity<JsonObject> actualizarReferencia(Integer idReferencia, ReferenciaDTO r) { return referenciaService.actualizarReferencia(idReferencia, r); }
}
