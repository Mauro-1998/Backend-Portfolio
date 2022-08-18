package com.souldev.security.resource;


import com.souldev.security.dto.CarreraDTO;
import com.souldev.security.resource.rest.CarreraClient;
import com.souldev.security.services.CarreraService;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carrera")
public class CarreraResource implements CarreraClient {

    @Autowired
    CarreraService carreraService;

    @Override
    public ResponseEntity<JsonObject> listarCarreras() {
        return carreraService.listarCarreras();
    }

    @Override
    public ResponseEntity<JsonObject> guardarCarrera(CarreraDTO c) {
        return carreraService.guardar(c);
    }

    @Override
    public ResponseEntity<JsonObject> borrarCarrera(Integer id) {
        return carreraService.eliminarCarrera(id);
    }

    @Override
    public ResponseEntity<JsonObject> actualizarCarrera(Integer id, CarreraDTO carrera) { return carreraService.actualizarCarrera(id,carrera); }
}
