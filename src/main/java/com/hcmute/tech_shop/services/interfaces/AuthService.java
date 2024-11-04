package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.requests.AuthRequest;
import com.hcmute.tech_shop.dtos.requests.IntrospectRequest;
import com.hcmute.tech_shop.dtos.responses.AuthResponse;
import com.hcmute.tech_shop.dtos.responses.IntrospectResponse;
//import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthService {
    AuthResponse authenticate(AuthRequest authRequest) ;
//    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
