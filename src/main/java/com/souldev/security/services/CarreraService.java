package com.souldev.security.services;

import com.souldev.security.dto.CarreraDTO;
import com.souldev.security.entities.Carrera;
import com.souldev.security.enums.EstadoMensaje;
import com.souldev.security.repositories.CarreraRepository;
import io.vertx.core.json.JsonObject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.souldev.security.common.Functions.ucNombres;

@Service
public class CarreraService {

    Logger logger = Logger.getLogger(getClass().getName());
    @Autowired
    CarreraRepository carreraRepository;



    public ResponseEntity<JsonObject> listarCarreras() {
        JsonObject response = new JsonObject();
        Set<Carrera> carreras = carreraRepository.findAll();
        response.put(EstadoMensaje.SUCCESS.toString(), carreras);
        return ResponseEntity.status(200).body(response);
    }



    public ResponseEntity<JsonObject> guardar(CarreraDTO c) {
        JsonObject response = new JsonObject();
        c.setCarrera(ucNombres(c.getCarrera()));
        c.setFacultad(ucNombres(c.getFacultad()));
        if (carreraRepository.findByCarreraAndFacultad(c.getCarrera(),c.getFacultad()) == null) {
            Carrera car = new Carrera();
            car.setCarrera(c.getCarrera());
            car.setFacultad(c.getFacultad());
            Carrera carrera = carreraRepository.save(car);
            response.put(EstadoMensaje.SUCCESS.toString(), carrera);
            return ResponseEntity.status(200).body(response);
        } else {
            response.put(EstadoMensaje.ERROR.toString(), "Ya hay una carrera registrada con ese nombre y en esa facultad");
            return ResponseEntity.status(409).body(response);
        }
    }

    public ResponseEntity<JsonObject> eliminarCarrera(Integer id) {
        JsonObject response = new JsonObject();
        Optional<Carrera> entityOptional = carreraRepository.findById(id);
        if (entityOptional.isPresent()) {
            Carrera entity = entityOptional.get();
            carreraRepository.delete(entity);
        }
        response.put(EstadoMensaje.SUCCESS.toString(), "Eliminacion exitosa");
        return ResponseEntity.status(200).body(response);
    }


    public ResponseEntity<JsonObject> actualizarCarrera(Integer id, CarreraDTO c) {
        JsonObject response = new JsonObject();
        logger.info("EXISTE CARRERA: "+ carreraRepository.existsByCarreraAndFacultad(c.getCarrera(),c.getFacultad()));

        if (!carreraRepository.existsByCarreraAndFacultad(c.getCarrera(),c.getFacultad())) {
            return carreraRepository.findById(id).
                    map(carrera -> {
                        carrera.setCarrera(c.getCarrera());
                        carrera.setFacultad(c.getFacultad());
                        response.put(EstadoMensaje.SUCCESS.toString(), carreraRepository.save(carrera));
                        return ResponseEntity.status(200).body(response);
                    }).get();
        }else{
            response.put(EstadoMensaje.ERROR.toString(), "La carrera en dicha facultad ya está registrada");
            return ResponseEntity.status(409).body(response);
        }


    }


}
