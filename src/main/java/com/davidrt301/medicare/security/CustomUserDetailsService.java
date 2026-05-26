package com.davidrt301.medicare.security;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.davidrt301.medicare.model.Employee;
import com.davidrt301.medicare.model.Person;
import com.davidrt301.medicare.model.Role;
import com.davidrt301.medicare.repository.EmployeeRepository;
import com.davidrt301.medicare.repository.PatientRepository;
import com.davidrt301.medicare.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.davidrt301.medicare.model.User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Person person = user.getPerson();

        Role role;
        if (patientRepository.existsByPerson(person)) {
            role = Role.PACIENTE;
    }else{
        Employee employee = employeeRepository.findByPerson(person)
                .orElseThrow(() -> new UsernameNotFoundException("Empleado no encontrado"));
        role = employee.getRole();
    }

    List<SimpleGrantedAuthority> authorities = Collections.singletonList(
        new SimpleGrantedAuthority("ROLE_" + role.name())
    );
    return new User(user.getUserName(), user.getPassword(), authorities);
    }


}
