package com.notificacion.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.notificacion.service.coleccion.Notificacion;

public interface NotificacionRepository extends MongoRepository<Notificacion, String>{

}
