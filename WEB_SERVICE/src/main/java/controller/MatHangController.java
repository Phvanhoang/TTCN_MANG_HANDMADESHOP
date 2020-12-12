package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/admin/mat_hang/create")
	public ResponseEntity<Void> taoMatHang(@RequestBody MatHang matHang) {
		matHangService.save(matHang);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@GetMapping("/mat_hang/{maMatHang}")
	public ResponseEntity<MatHang> timMatHang(@PathVariable Long maMatHang) {
		MatHang matHang = matHangService.findOne(maMatHang);
		return new ResponseEntity<MatHang>(matHang, HttpStatus.OK);
	}
	
}
