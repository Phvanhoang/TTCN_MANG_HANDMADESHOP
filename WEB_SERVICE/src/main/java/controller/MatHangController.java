package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.MatHang;
import service.MatHangService;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/mat_hang")
public class MatHangController {
	@Autowired
	private MatHangService matHangService;
	
	@PostMapping("/create")
	public ResponseEntity<Void> taoMatHang(@RequestBody MatHang matHang) {
		matHangService.save(matHang);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
