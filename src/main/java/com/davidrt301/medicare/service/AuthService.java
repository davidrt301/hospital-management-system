package com.davidrt301.medicare.service;

import com.davidrt301.medicare.dto.request.AuthLoginRequest;
import com.davidrt301.medicare.dto.request.AuthRegisterRequest;
import com.davidrt301.medicare.dto.response.AuthResponse;
import com.davidrt301.medicare.dto.response.MessageResponse;

public interface AuthService {

    

    MessageResponse register(AuthRegisterRequest request);

    AuthResponse login (AuthLoginRequest request); 
}
