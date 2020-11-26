package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import model.Anh_MatHang;
import model.LoaiMatHang;
import model.MatHang;
import service.Anh_MatHangService;
import service.MatHangService;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class Anh_MatHangController {
	@Autowired
	private Anh_MatHangService anh_MatHangService;
	
	@Autowired 
	private MatHangService matHangService;
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/admin/anh_mat_hang/upload")
	public ResponseEntity<Void> upload(@RequestParam("image") MultipartFile multipartFile,
										@RequestParam("maMatHang") long maMatHang) {
		MatHang matHang = matHangService.findOne(maMatHang);
		try {
			anh_MatHangService.save(new Anh_MatHang(multipartFile.getBytes(), matHang));
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
