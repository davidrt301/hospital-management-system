package com.davidrt301.medicare.service.imp;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.davidrt301.medicare.dto.request.AuthLoginRequest;
import com.davidrt301.medicare.dto.request.AuthRegisterRequest;
import com.davidrt301.medicare.dto.response.AuthResponse;
import com.davidrt301.medicare.dto.response.MessageResponse;
import com.davidrt301.medicare.exception.ResourceNotFoundException;
import com.davidrt301.medicare.model.Employee;
import com.davidrt301.medicare.model.Person;
import com.davidrt301.medicare.model.Role;
import com.davidrt301.medicare.model.User;
import com.davidrt301.medicare.repository.EmployeeRepository;
import com.davidrt301.medicare.repository.PatientRepository;
import com.davidrt301.medicare.repository.PersonRepository;
import com.davidrt301.medicare.repository.UserRepository;
import com.davidrt301.medicare.security.JwtUtil;
import com.davidrt301.medicare.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final PatientRepository patientRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public MessageResponse register(AuthRegisterRequest request) {
        if (userRepository.existsByUserName(request.userName())) {
            throw new IllegalArgumentException("El User ya existe: " + request.userName());
        }

        Person Person = personRepository.findById(request.personId())
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Person no encontrada con id: " + request.personId());
                });

        User nuevoUser = User.builder()
                .userName(request.userName())
                .password(passwordEncoder.encode(request.password()))
                .person(Person)
                .build();

        userRepository.save(nuevoUser);
        log.info("User registrado exitosamente: {}", request.userName());

        return new MessageResponse("User registrado exitosamente: " + request.userName());
    }

    @Override
    public AuthResponse login(AuthLoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos");
        }

        log.debug("Autenticación exitosa para usuario: {}", request.username());

        User usuario = userRepository.findByUserName(request.username())
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Usuario no encontrado: " + request.username());
                });

        Person person = usuario.getPerson();

        Role role;
        if (patientRepository.existsByPerson(person)) {
            role = Role.PACIENTE;
        } else {
            Employee employee = employeeRepository.findByPerson(person)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Empleado no encontrado"));
            role = employee.getRole();
        }

        String token = jwtUtil.generateToken(request.username(), role);

        return new AuthResponse(token, "Bearer");

    }

}
