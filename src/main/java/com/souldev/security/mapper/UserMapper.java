package com.souldev.security.mapper;


import com.souldev.security.dto.PersonaDTO;
import com.souldev.security.security.dtos.NewUser;
import com.souldev.security.security.entities.User;
import io.vertx.core.json.JsonObject;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;

public class UserMapper {
    ModelMapper mapper;

    Logger logger = Logger.getLogger(getClass().getName());

    public UserMapper() {
        if (mapper == null) {
            mapper = new ModelMapper();
        }
    }

    public User toEntity(NewUser p) {
        return mapper.map(p, User.class);
    }

    public NewUser toModel(User p) {
        return mapper.map(p, NewUser.class);
    }

    public JsonObject toJson(User p) {
        return mapper.map(p, JsonObject.class);
    }
}
