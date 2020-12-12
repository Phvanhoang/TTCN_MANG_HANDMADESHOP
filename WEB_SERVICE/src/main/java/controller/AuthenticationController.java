package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import model.TaiKhoan;
import security.jwt.JwtTokenProvider;
import security.payload.LoginRequest;
import security.payload.LoginResponse;
import security.user.CustomUserDetails;
import service.TaiKhoanService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class AuthenticationController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getTenDangNhap(), loginRequest.getMatKhau()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = "";
		jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
		
		if(jwt.equals("")) {
			return new ResponseEntity<LoginResponse>(new LoginResponse(jwt, loginRequest.getTenDangNhap()), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<LoginResponse>(new LoginResponse(jwt, loginRequest.getTenDangNhap()), HttpStatus.OK);

	}

}
