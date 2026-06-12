# 🏥 Hospital Management System - Documentación Completa

**Versión:** 0.0.1-SNAPSHOT  
**Autor:** davidrt301  
**Lenguaje:** Java 21  
**Framework:** Spring Boot 4.0.6

---

## 📋 Tabla de Contenidos

1. [Descripción General](#descripción-general)
2. [Stack Tecnológico](#stack-tecnológico)
3. [Requisitos Previos](#requisitos-previos)
4. [Instalación y Ejecución](#instalación-y-ejecución)
5. [Estructura del Proyecto](#estructura-del-proyecto)
6. [Descripción de Paquetes](#descripción-de-paquetes)
7. [Entidades y Modelos](#entidades-y-modelos)
8. [Autenticación y Seguridad](#autenticación-y-seguridad)
9. [Guía Completa de Endpoints](#guía-completa-de-endpoints)
10. [Flujos de Datos](#flujos-de-datos)
11. [Configuración](#configuración)
12. [Documentación API](#documentación-api)

---

## 📖 Descripción General

**Hospital Management System** es una aplicación de gestión hospitalaria construida con **Spring Boot 4.0.6** y **Java 21**. El sistema proporciona una solución integral para la administración de:

- **Personas:** Registro base de individuos en el sistema
- **Pacientes:** Gestión de información de pacientes
- **Empleados:** Control de personal hospitalario
- **Especialidades:** Registro de especialidades médicas
- **Atenciones Médicas:** Registro y seguimiento de consultas y atenciones

### Características Principales

✅ **Autenticación y Autorización** con JWT (JSON Web Tokens)  
✅ **API RESTful** completamente documentada con Swagger/OpenAPI 3.0  
✅ **Validación de datos** con Jakarta Validation  
✅ **Mapeo de entidades** con MapStruct  
✅ **Persistencia** con Spring Data JPA  
✅ **Base de datos PostgreSQL** containerizada  
✅ **Manejo de excepciones** centralizado  
✅ **Logging** con SLF4J  
✅ **Paginación** en listados  
✅ **Seguridad** con Spring Security  

---

## 🛠️ Stack Tecnológico

### Backend
| Tecnología | Versión | Propósito |
|---|---|---|
| Java | 21 | Lenguaje de programación |
| Spring Boot | 4.0.6 | Framework principal |
| Spring Data JPA | Latest | ORM y persistencia |
| Spring Security | Latest | Autenticación y autorización |
| PostgreSQL Driver | Latest | Conector de base de datos |
| JWT (JJWT) | 0.13.0 | Tokens seguros |
| MapStruct | 1.6.3 | Mapeo de objetos |
| Lombok | Latest | Reducción de código boilerplate |
| Validation | Latest | Validación de datos |

### Herramientas y Librerías
| Herramienta | Propósito |
|---|---|
| SpringDoc OpenAPI | Documentación interactiva Swagger UI |
| Maven | Gestor de dependencias y build |
| Docker Compose | Orquestación de contenedores |
| SLF4J | Logging |
| JUnit | Testing (incluido en Spring Boot) |

---

## 📦 Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Java 21** o superior
- **Maven 3.8** o superior
- **Docker y Docker Compose**
- **Git** (opcional)

### Verificar Instalación

```bash
# Verificar Java
java -version

# Verificar Maven
mvn -version

# Verificar Docker
docker --version
docker-compose --version
```

---

## 🚀 Instalación y Ejecución

### Paso 1: Clonar el Proyecto

```bash
cd C:\Users\David\Documents\curso DevSenior JAVA\prueba_tect\MVC\hospital-management-system
```

### Paso 2: Iniciar la Base de Datos

```bash
# Iniciar PostgreSQL en Docker
docker-compose up -d

# Verificar que el contenedor está corriendo
docker ps
```

**Detalles de la Base de Datos:**
- **Imagen:** PostgreSQL 17
- **Contenedor:** postgres-medicare
- **Usuario:** postgres
- **Contraseña:** admin
- **Base de datos:** medicare
- **Puerto:** 5434 (mapeado a 5432 interno)

### Paso 3: Compilar el Proyecto

```bash
# Opción 1: Con Maven (Windows)
mvnw clean install

# Opción 2: Con Maven directo
mvn clean install

# Opción 3: Sin ejecutar tests
mvn clean install -DskipTests
```

### Paso 4: Ejecutar la Aplicación

```bash
# Opción 1: Con Maven
mvn spring-boot:run

# Opción 2: Ejecutar el JAR generado
java -jar target/hospital-management-system-0.0.1-SNAPSHOT.jar

# Opción 3: Desde el IDE (Spring Boot Run)
```

### Paso 5: Acceder a la Aplicación

- **Servidor:** http://localhost:8080
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **API Docs:** http://localhost:8080/api-docs

---

## 📁 Estructura del Proyecto

```
hospital-management-system/
├── src/
│   ├── main/
│   │   ├── java/com/davidrt301/medicare/
│   │   │   ├── HospitalManagementSystemApplication.java    # Clase principal
│   │   │   ├── KeyGeneratorUtil.java                       # Utilidades
│   │   │   ├── config/                                     # Configuración
│   │   │   ├── controller/                                 # Controladores REST
│   │   │   ├── dto/                                        # Data Transfer Objects
│   │   │   ├── exception/                                  # Manejo de excepciones
│   │   │   ├── mapper/                                     # Mapeadores (MapStruct)
│   │   │   ├── model/                                      # Entidades JPA
│   │   │   ├── repository/                                 # Interfaces JPA
│   │   │   ├── security/                                   # Configuración de seguridad
│   │   │   └── service/                                    # Lógica de negocio
│   │   ├── resources/
│   │   │   ├── application.yml                             # Configuración
│   │   │   ├── application.properties
│   │   │   ├── static/                                     # Recursos estáticos
│   │   │   └── templates/                                  # Plantillas
│   ├── test/
│   │   └── java/com/davidrt301/medicare/
│   │       └── HospitalManagementSystemApplicationTests.java
├── target/                                                  # Artefactos compilados
├── docker-compose.yml                                       # Configuración Docker
├── pom.xml                                                  # Dependencias Maven
├── mvnw / mvnw.cmd                                         # Maven Wrapper
└── README.md
```

---

## 📦 Descripción de Paquetes

### 1. `config/` - Configuración
**Propósito:** Configuración de la aplicación (OpenAPI, Security, Database)

- **OpenApiConfig.java:** Configuración de Swagger/OpenAPI 3.0
  - Define información del proyecto (título, descripción, versión)
  - Configura autenticación Bearer Token
  - Proporciona esquema para documentación automática

### 2. `controller/` - Controladores REST
**Propósito:** Puntos de entrada HTTP, manejo de solicitudes

**Clases:**
- **AuthController:** Autenticación (login/registro)
- **PersonController:** CRUD de personas
- **PatientController:** CRUD de pacientes
- **EmployeeController:** CRUD de empleados
- **SpecialtyController:** CRUD de especialidades
- **AttentionController:** CRUD de atenciones médicas

**Características comunes:**
- Validación con `@Valid`
- Respuestas HTTP apropiadas (201, 200, 400, 404)
- Logging de operaciones
- Documentación con OpenAPI annotations

### 3. `dto/` - Data Transfer Objects
**Propósito:** Objetos de transferencia de datos entre capas

**Estructura:**
```
dto/
├── request/              # DTOs para solicitudes (entrada)
│   ├── AuthLoginRequest
│   ├── AuthRegisterRequest
│   ├── PersonRequest
│   ├── PatientRequest
│   ├── EmployeeRequest
│   ├── SpecialtyRequest
│   └── AttentionRequest
└── response/             # DTOs para respuestas (salida)
    ├── AuthResponse
    ├── MessageResponse
    ├── PersonResponse
    ├── PatientResponse
    ├── EmployeeResponse
    ├── SpecialtyResponse
    └── AttentionResponse
```

### 4. `exception/` - Manejo de Excepciones
**Propósito:** Excepciones personalizadas y manejador global

**Clases:**
- **BusinessException:** Excepciones de lógica de negocio
- **InvalidRequestException:** Solicitudes inválidas
- **ResourceNotFoundException:** Recurso no encontrado
- **GlobalExceptionHandler:** Manejador centralizado (@RestControllerAdvice)

**Beneficios:**
- Respuestas consistentes de error
- Mensajes de error claros
- Códigos HTTP correctos

### 5. `mapper/` - Mapeadores
**Propósito:** Conversión entre entidades y DTOs con MapStruct

**Características:**
- Mapeo automático basado en nombres
- Mapeo personalizado cuando es necesario
- Generación de código en compile-time
- Alto rendimiento (sin reflexión)

**Mapeadores:**
- `PersonMapper` → Entidad Person ↔ PersonRequest/PersonResponse
- `PatientMapper` → Entidad Patient ↔ PatientRequest/PatientResponse
- `EmployeeMapper` → Entidad Employee ↔ EmployeeRequest/EmployeeResponse
- `SpecialtyMapper` → Entidad Specialty ↔ SpecialtyRequest/SpecialtyResponse
- `AttentionMapper` → Entidad Attention ↔ AttentionRequest/AttentionResponse
- `UserMapper` → Entidad User (para autenticación)

### 6. `model/` - Entidades JPA
**Propósito:** Modelos de datos/Entidades de base de datos

**Entidades principales:**
- **Person:** Base para pacientes y empleados
- **Patient:** Información de pacientes
- **Employee:** Información de empleados
- **User:** Usuarios del sistema (autenticación)
- **Specialty:** Especialidades médicas
- **MedicalSpecialty:** Especialidades asociadas a empleados
- **Attention:** Registro de atenciones médicas
- **Role:** Roles de usuario
- **Status:** Estados (ACTIVO, INACTIVO, etc.)

### 7. `repository/` - Acceso a Datos
**Propósito:** Interfaces JPA para operaciones CRUD

- Extienden `JpaRepository<T, ID>`
- Queries automáticas
- Queries personalizadas con `@Query`
- Paginación nativa

### 8. `security/` - Seguridad
**Propósito:** Configuración de seguridad y JWT

**Componentes:**
- Filtros de seguridad
- Validación de JWT
- Configuración de CORS
- Puntos de entrada de seguridad

### 9. `service/` - Lógica de Negocio
**Propósito:** Implementación de lógica empresarial

**Estructura:**
```
service/
├── AuthService           # Autenticación y registro
├── PersonService         # Operaciones de personas
├── PatientService        # Operaciones de pacientes
├── EmployeeService       # Operaciones de empleados
├── SpecialtyService      # Operaciones de especialidades
├── AttentionService      # Operaciones de atenciones
└── imp/                  # Implementaciones
    ├── AuthServiceImpl
    ├── PersonServiceImpl
    ├── PatientServiceImpl
    ├── EmployeeServiceImpl
    ├── SpecialtyServiceImpl
    └── AttentionServiceImpl
```

**Responsabilidades:**
- Lógica de negocio
- Validaciones
- Transacciones
- Llamadas a repositorios

---

## 🗂️ Entidades y Modelos

### 1. **Person** (Persona Base)
```
┌─ ID (PK)
├─ Nombre
├─ Apellido
├─ Email (Único)
├─ Teléfono
├─ Identificación (Único)
├─ Dirección
├─ Tipo Identificación
├─ Fecha Nacimiento
├─ Género
├─ Estado (ACTIVO/INACTIVO)
└─ Fecha Creación
```

**Relaciones:**
- 1:1 con Patient (opcional)
- 1:1 con Employee (opcional)
- 1:1 con User (opcional)

### 2. **Patient** (Paciente)
```
┌─ ID (PK)
├─ Persona (FK) → Person
├─ Número Afiliación Seguros
├─ Tipo Sangre
├─ Alergias
├─ Antecedentes Médicos
├─ Contacto Emergencia
├─ Teléfono Emergencia
├─ Estado (ACTIVO/INACTIVO)
└─ Fecha Registro
```

**Relaciones:**
- 1:1 con Person
- 1:N con Attention (una cita puede tener muchas atenciones)

### 3. **Employee** (Empleado)
```
┌─ ID (PK)
├─ Persona (FK) → Person
├─ Número Empleado
├─ Departamento
├─ Posición
├─ Fecha Contratación
├─ Salario
├─ Estado (ACTIVO/INACTIVO)
└─ Especialidades (M:N)
```

**Relaciones:**
- 1:1 con Person
- M:N con MedicalSpecialty
- 1:N con Attention

### 4. **User** (Usuario del Sistema)
```
┌─ ID (PK)
├─ Persona (FK) → Person
├─ Nombre Usuario (Único)
├─ Contraseña (Hasheada)
├─ Roles (M:N)
├─ Activo (boolean)
└─ Fecha Creación
```

**Relaciones:**
- 1:1 con Person
- M:N con Role

### 5. **Role** (Rol)
```
┌─ ID (PK)
├─ Nombre (ADMIN, USER, DOCTOR, PATIENT)
└─ Descripción
```

**Relaciones:**
- M:N con User

### 6. **Specialty** (Especialidad Médica)
```
┌─ ID (PK)
├─ Nombre
├─ Descripción
└─ Estado (ACTIVO/INACTIVO)
```

**Relaciones:**
- 1:N con MedicalSpecialty

### 7. **MedicalSpecialty** (Especialidad de Empleado)
```
┌─ ID (PK)
├─ Empleado (FK) → Employee
├─ Especialidad (FK) → Specialty
├─ Número Licencia
├─ Fecha Obtención
└─ Vigencia
```

**Relaciones:**
- N:1 con Employee
- N:1 con Specialty

### 8. **Attention** (Atención Médica)
```
┌─ ID (PK)
├─ Paciente (FK) → Patient
├─ Empleado (FK) → Employee
├─ Especialidad (FK) → Specialty
├─ Fecha Atención
├─ Hora
├─ Motivo Consulta
├─ Diagnóstico
├─ Tratamiento
├─ Observaciones
├─ Estado (PENDIENTE, EN_CURSO, COMPLETADA, CANCELADA)
├─ Tiempo Duración
└─ Fecha Registro
```

**Relaciones:**
- N:1 con Patient
- N:1 con Employee
- N:1 con Specialty

### 9. **Status** (Enumeración)
Estados posibles en el sistema:
- `ACTIVO` - Recurso activo
- `INACTIVO` - Recurso inactivo/eliminado lógicamente
- `PENDIENTE` - En espera
- `EN_CURSO` - En proceso
- `COMPLETADA` - Finalizado
- `CANCELADA` - Cancelado

---

## 🔐 Autenticación y Seguridad

### Autenticación con JWT

#### Flujo de Autenticación

```
┌─────────────────────────────────────┐
│   Cliente                            │
└────────────────┬────────────────────┘
                 │
                 │ 1. POST /api/auth/register
                 │    o
                 │    POST /api/auth/login
                 ▼
┌─────────────────────────────────────┐
│   AuthController                    │
└────────────────┬────────────────────┘
                 │
                 │ 2. Validar credenciales
                 ▼
┌─────────────────────────────────────┐
│   AuthService                       │
├─────────────────────────────────────┤
│ - register(AuthRegisterRequest)     │
│ - login(AuthLoginRequest)           │
└────────────────┬────────────────────┘
                 │
                 │ 3. Generar JWT
                 │    Token = Header.Payload.Signature
                 ▼
┌─────────────────────────────────────┐
│   Response: AuthResponse            │
│   ├─ token: String (JWT)            │
│   ├─ mensaje: String                │
│   └─ código: String                 │
└─────────────────────────────────────┘
```

### Token JWT

**Estructura:**
- **Header:** Tipo de token (Bearer) y algoritmo (HS256)
- **Payload:** Información del usuario (claims)
- **Signature:** Firma digital para verificar autenticidad

**Configuración en `application.yml`:**
```yaml
jwt:
  secret: 5Flv1cPPcMXnjXJcg6uNtRnIF7LbhDKk3q/eMYu+kgU=
  expirationMs: 86400000  # 24 horas
```

### Uso del Token

En cada solicitud autenticada, enviar:
```http
Authorization: Bearer <tu_token_jwt>
```

### Seguridad con Spring Security

- Validación de contraseñas con `BCryptPasswordEncoder`
- Filtros de seguridad personalizados
- Punto de entrada de autenticación
- Control de acceso basado en roles (RBAC)

---

## 🔌 Guía Completa de Endpoints

### 📌 Convenciones

- **Base URL:** `http://localhost:8080/api`
- **Formato:** JSON
- **Autenticación:** JWT Token (Bearer)
- **Paginación:** ?page=0&size=10&sort=id,asc

### 1️⃣ Autenticación (`/api/auth`)

#### 1.1 Registrar Usuario
```http
POST /api/auth/register
Content-Type: application/json
```

**Request:**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@example.com",
  "userName": "juanperez",
  "password": "Password123!",
  "telefono": "3001234567",
  "identificacion": "1234567890",
  "genero": "M"
}
```

**Response (201 Created):**
```json
{
  "mensaje": "Usuario registrado exitosamente"
}
```

**Códigos de Error:**
- `400 Bad Request` - Datos inválidos
- `400 Bad Request` - Usuario ya existe

---

#### 1.2 Iniciar Sesión
```http
POST /api/auth/login
Content-Type: application/json
```

**Request:**
```json
{
  "username": "juanperez",
  "password": "Password123!"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFucGVyZXoiLCJpYXQiOjE2OTk1NjAwMDAsImV4cCI6MTY5OTY0NjQwMH0.xyz123...",
  "mensaje": "Login exitoso"
}
```

**Códigos de Error:**
- `400 Bad Request` - Credenciales inválidas

---

### 2️⃣ Personas (`/api/persons`)

#### 2.1 Crear Persona
```http
POST /api/persons
Content-Type: application/json
Authorization: Bearer <token>
```

**Request:**
```json
{
  "nombre": "María",
  "apellido": "García",
  "email": "maria@example.com",
  "telefono": "3009876543",
  "identificacion": "9876543210",
  "tipoIdentificacion": "CC",
  "direccion": "Calle 10 #20-30",
  "fechaNacimiento": "1990-05-15",
  "genero": "F"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "nombre": "María",
  "apellido": "García",
  "email": "maria@example.com",
  "telefono": "3009876543",
  "identificacion": "9876543210",
  "tipoIdentificacion": "CC",
  "direccion": "Calle 10 #20-30",
  "fechaNacimiento": "1990-05-15",
  "genero": "F",
  "estado": "ACTIVO",
  "fechaCreacion": "2024-11-15T10:30:00"
}
```

---

#### 2.2 Listar Personas (Paginado)
```http
GET /api/persons?page=0&size=10&sort=id,asc
Authorization: Bearer <token>
```

**Response (200 OK):** Página de personas

---

#### 2.3 Obtener Persona por ID
```http
GET /api/persons/{id}
Authorization: Bearer <token>
```

**Response (200 OK):** Detalles de persona

---

#### 2.4 Actualizar Persona
```http
PUT /api/persons/{id}
Content-Type: application/json
Authorization: Bearer <token>
```

**Response (200 OK):** Persona actualizada

---

#### 2.5 Eliminar Persona (Lógico)
```http
DELETE /api/persons/{id}
Authorization: Bearer <token>
```

**Response (204 No Content)**

---

### 3️⃣ Pacientes (`/api/patients`)

#### 3.1 Crear Paciente
```http
POST /api/patients
Content-Type: application/json
Authorization: Bearer <token>
```

**Request:**
```json
{
  "personaId": 1,
  "numeroAfiliacionSeguros": "AFF-123456",
  "tipoSangre": "O+",
  "alergias": "Penicilina, Lactosa",
  "antecedentesmedicos": "Diabetes tipo 2",
  "contactoEmergencia": "Carlos García",
  "telefonoEmergencia": "3101234567"
}
```

**Response (201 Created):** Paciente creado

---

#### 3.2 Listar Pacientes (Paginado)
```http
GET /api/patients?page=0&size=10
Authorization: Bearer <token>
```

**Response:** Página de pacientes

---

#### 3.3 Obtener Paciente por ID
```http
GET /api/patients/{id}
Authorization: Bearer <token>
```

**Response (200 OK):** Detalles del paciente

---

#### 3.4 Listar Pacientes Activos
```http
GET /api/patients/activos
Authorization: Bearer <token>
```

**Response (200 OK):** Lista de pacientes activos

---

#### 3.5 Listar Pacientes por Estado
```http
GET /api/patients/status/{status}?page=0&size=10
Authorization: Bearer <token>
```

**Response:** Página de pacientes

---

#### 3.6 Actualizar Paciente
```http
PUT /api/patients/{id}
Content-Type: application/json
Authorization: Bearer <token>
```

**Response (200 OK):** Paciente actualizado

---

#### 3.7 Eliminar Paciente (Lógico)
```http
DELETE /api/patients/{id}
Authorization: Bearer <token>
```

**Response (204 No Content)**

---

### 4️⃣ Empleados (`/api/employees`)

#### 4.1 Crear Empleado
```http
POST /api/employees
Content-Type: application/json
Authorization: Bearer <token>
```

**Request:**
```json
{
  "personaId": 2,
  "numeroEmpleado": "EMP-001",
  "departamento": "Cardiología",
  "posicion": "Doctor Cirujano",
  "fechaContratacion": "2023-01-15",
  "salario": 5000000.00
}
```

**Response (201 Created):** Empleado creado

---

#### 4.2 Listar Empleados
```http
GET /api/employees
Authorization: Bearer <token>
```

**Response (200 OK):** Lista de empleados

---

#### 4.3 Obtener Empleado por ID
```http
GET /api/employees/{id}
Authorization: Bearer <token>
```

**Response (200 OK):** Detalles del empleado

---

#### 4.4 Listar Empleados por Estado
```http
GET /api/employees/status/{status}?page=0&size=10
Authorization: Bearer <token>
```

**Response:** Página de empleados

---

#### 4.5 Actualizar Empleado
```http
PUT /api/employees/{id}
Content-Type: application/json
Authorization: Bearer <token>
```

**Response (200 OK):** Empleado actualizado

---

#### 4.6 Eliminar Empleado (Lógico)
```http
DELETE /api/employees/{id}
Authorization: Bearer <token>
```

**Response (204 No Content)**

---

### 5️⃣ Especialidades (`/api/specialties`)

#### 5.1 Crear Especialidad
```http
POST /api/specialties
Content-Type: application/json
Authorization: Bearer <token>
```

**Request:**
```json
{
  "nombre": "Cardiología",
  "descripcion": "Especialidad médica dedicada al corazón y sistema circulatorio"
}
```

**Response (201 Created):** Especialidad creada

---

#### 5.2 Listar Especialidades (Paginado)
```http
GET /api/specialties?page=0&size=10
Authorization: Bearer <token>
```

**Response:** Página de especialidades

---

#### 5.3 Obtener Especialidad por ID
```http
GET /api/specialties/{id}
Authorization: Bearer <token>
```

**Response (200 OK):** Detalles de especialidad

---

#### 5.4 Buscar Especialidades por Nombre
```http
GET /api/specialties/name?name=Cardiología&page=0&size=10
Authorization: Bearer <token>
```

**Response:** Página con especialidades coincidentes

---

#### 5.5 Actualizar Especialidad
```http
PUT /api/specialties/{id}
Content-Type: application/json
Authorization: Bearer <token>
```

**Response (200 OK):** Especialidad actualizada

---

#### 5.6 Eliminar Especialidad (Lógico)
```http
DELETE /api/specialties/{id}
Authorization: Bearer <token>
```

**Response (204 No Content)**

---

### 6️⃣ Atenciones Médicas (`/api/attentions`)

#### 6.1 Crear Atención
```http
POST /api/attentions
Content-Type: application/json
Authorization: Bearer <token>
```

**Request:**
```json
{
  "pacienteId": 1,
  "empleadoId": 1,
  "especialidadId": 1,
  "fechaAtencion": "2024-11-15",
  "hora": "10:30:00",
  "motivoConsulta": "Dolor en el pecho",
  "diagnostico": "Arritmia cardíaca",
  "tratamiento": "Prescripción de beta-bloqueadores",
  "observaciones": "Paciente estable, seguimiento en 2 semanas",
  "tiempoDuracion": 45
}
```

**Response (201 Created):** Atención creada

---

#### 6.2 Listar Atenciones (Paginado)
```http
GET /api/attentions?page=0&size=10
Authorization: Bearer <token>
```

**Response:** Página de atenciones

---

#### 6.3 Listar Atenciones por Rango de Fechas
```http
GET /api/attentions/dates?fechaInicio=2024-11-01T00:00:00&fechaFin=2024-11-30T23:59:59&page=0&size=10
Authorization: Bearer <token>
```

**Response:** Página de atenciones en el rango

---

#### 6.4 Buscar Atenciones por Motivo
```http
GET /api/attentions/serch?reason=Dolor&page=0&size=10
Authorization: Bearer <token>
```

**Response:** Página de atenciones que coinciden

---

#### 6.5 Obtener Atención por ID
```http
GET /api/attentions/{id}
Authorization: Bearer <token>
```

**Response (200 OK):** Detalles de atención

---

#### 6.6 Actualizar Atención
```http
PUT /api/attentions/{id}
Content-Type: application/json
Authorization: Bearer <token>
```

**Response (200 OK):** Atención actualizada

---

#### 6.7 Eliminar Atención (Lógico)
```http
DELETE /api/attentions/{id}
Authorization: Bearer <token>
```

**Response (204 No Content)**

---

## 🔄 Flujos de Datos

### 📊 Flujo General de Solicitud

```
┌──────────────────┐
│   Cliente HTTP   │
└────────┬─────────┘
         │ HTTP Request
         │ (JSON Payload)
         ▼
┌──────────────────────────────────┐
│   Spring Dispatcher Servlet      │
│   (DispatcherServlet)            │
└────────┬─────────────────────────┘
         │
         │ Routing
         ▼
┌──────────────────────────────────┐
│   Controlador (@RestController)  │
│   - Valida anotaciones           │
│   - Valida @Valid               │
│   - Extrae parámetros           │
└────────┬─────────────────────────┘
         │
         │ Conversión Request DTO
         ▼
┌──────────────────────────────────┐
│   Servicio (Service)             │
│   - Lógica de negocio            │
│   - Validaciones complejas       │
│   - Transacciones                │
└────────┬─────────────────────────┘
         │
         │ Acceso a datos
         ▼
┌──────────────────────────────────┐
│   Repositorio (JpaRepository)    │
│   - Consultas a DB               │
│   - Operaciones CRUD             │
└────────┬─────────────────────────┘
         │
         │ SQL Query
         ▼
┌──────────────────────────────────┐
│   PostgreSQL Database            │
│   - Almacenamiento persistente   │
└────────┬─────────────────────────┘
```

---

## ⚙️ Configuración

### `application.yml` - Configuración Principal

```yaml
spring:
  application:
    name: hospital-management-system
    
  datasource:
    url: jdbc:postgresql://localhost:5434/medicare
    username: postgres
    password: admin
    driver-class-name: org.postgresql.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
    show-sql: true

jwt:
  secret: 5Flv1cPPcMXnjXJcg6uNtRnIF7LbhDKk3q/eMYu+kgU=
  expirationMs: 86400000
```

---

## 📚 Documentación API

### Acceso a Swagger UI

```
http://localhost:8080/swagger-ui.html
```

### Documentación JSON OpenAPI

```
http://localhost:8080/api-docs
```

---

## 🧪 Testing

### Ejecutar Tests
```bash
mvn test
```

---

## 🚧 Troubleshooting

### Base de Datos No se Conecta
```bash
docker ps -a
docker logs postgres-medicare
docker-compose restart db
```

### Puerto 5434 en Uso
```bash
# Cambiar puerto en docker-compose.yml
# De: "5434:5432"
# A:  "5433:5432"
```

---

## 📝 Notas Importantes

- ✅ Todas las entidades tienen estado lógico (no se elimina físicamente)
- ✅ JWT expira cada 24 horas
- ✅ Las contraseñas se almacenan hasheadas con BCrypt
- ✅ La paginación es 0-indexada
- ✅ Se requiere autenticación para la mayoría de endpoints

