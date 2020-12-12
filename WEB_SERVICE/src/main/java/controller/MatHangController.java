package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import model.MatHang;
import service.MatHangService;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class MatHangController {
	@Autowired
	private MatHangService matHangService;

	@PreAuthorize("hasAnyAuthority({'ROLE_ADMIN', 'ROLE_USER'})")
	//@RequestMapping(value = "/admin/mat_hang/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value="/admin/mat_hang/create", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> taoMatHang(@RequestBody JSONObject matHang) {
//		matHangService.save(matHang);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@GetMapping("/mat_hang/{maMatHang}")
	public ResponseEntity<MatHang> timMatHang(@PathVariable Long maMatHang) {
		MatHang matHang = matHangService.findOne(maMatHang);
		return new ResponseEntity<MatHang>(matHang, HttpStatus.OK);
	}
	
}
