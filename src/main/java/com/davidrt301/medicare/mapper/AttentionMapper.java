package com.davidrt301.medicare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.davidrt301.medicare.dto.request.AttentionRequest;
import com.davidrt301.medicare.dto.response.AttentionResponse;
import com.davidrt301.medicare.mapper.config.MapperConfiguration;
import com.davidrt301.medicare.model.Attention;

@Mapper(config = MapperConfiguration.class, uses = { PatientMapper.class, EmployeeMapper.class })
public interface AttentionMapper {

    @Mapping(source = "patientId", target = "patient.id")
    @Mapping(source = "employeeId", target = "employee.id")
    Attention toEntity(AttentionRequest request);

    AttentionResponse toResponse(Attention entity);

    @Mapping(source = "patientId", target = "patient.id")
    @Mapping(source = "employeeId", target = "employee.id")
    void updateEntity(AttentionRequest request, @MappingTarget Attention entity);

    /*
     * 1. La anotación @Mapper y su configuración
     * config = MapperConfiguration.class: Esto es una práctica de nivel senior. En
     * lugar de repetir configuraciones en cada Mapper (como estrategias de nombres
     * o manejo de valores nulos), las centralizas en esa clase. Mantiene tu código
     * limpio y consistente en todo el proyecto.
     * 
     * uses = { PatientMapper.class, EmployeeMapper.class }: Esta es la parte más
     * potente. Le estás diciendo a MapStruct:
     * "Si al mapear un Attention necesitas convertir un Patient o un Employee, no lo hagas solo; usa estos otros Mappers"
     * . Esto evita que tengas que mapear manualmente los objetos anidados dentro de
     * la entidad Attention.
     */

    /*
     * 2. Análisis de los Métodos
     * A. toEntity(AttentionRequest request)
     * Qué hace: Convierte tu objeto de petición (Request) en una entidad de base de
     * datos (Attention).
     * 
     * El truco: Aquí estás haciendo un mapeo "plano a objeto". Como en el Request
     * probablemente solo recibes el ID del paciente (patientId), pero en la entidad
     * tienes un objeto Patient completo, le indicas que tome ese ID y lo asigne
     * directamente al id dentro del objeto patient (target = "patient.id").
     */

    /*
     * toResponse(Attention entity)
     * Qué hace: Convierte la entidad de la base de datos en una respuesta
     * (Response) lista para enviar al cliente a través de tu API.
     * 
     * Por qué no tiene @Mapping: Porque probablemente los nombres de los campos en
     * Attention y AttentionResponse coinciden exactamente, por lo que MapStruct
     * hace el trabajo sucio por ti automáticamente.
     */

    /*
     * C. updateEntity(...)
     * Qué hace: Es un método de actualización parcial.
     * 
     * @MappingTarget: Esta es la clave. Le dice a MapStruct:
     * "No crees un objeto Attention nuevo. Toma los datos que vienen en el request y sobreescribe los valores del objeto entity que ya existe"
     * .
     * 
     * Uso profesional: Esto es lo que usarías en tu servicio dentro de un método
     * update después de hacer el repository.findById(). Es extremadamente eficiente
     * y evita tener que hacer setters manuales por cada campo.
     */
}
