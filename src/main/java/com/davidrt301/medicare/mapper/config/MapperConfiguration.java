package com.davidrt301.medicare.mapper.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

/**
 * Interfaz de configuración centralizada para MapStruct.
 * 
 * Esta configuración es heredada por todos los mappers que utilicen el atributo 'config'.
 * 
 * Atributos:
 * - componentModel = "spring": Genera implementaciones compatibles con la inyección de dependencias de Spring (@Component).
 * - injectionStrategy = CONSTRUCTOR: Define que las dependencias entre mappers se inyecten vía constructor (Práctica recomendada).
 * - unmappedTargetPolicy = IGNORE: Silencia los warnings cuando existen campos en el DTO que no están presentes en la Entidad.
 */
@MapperConfig(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MapperConfiguration {

}
