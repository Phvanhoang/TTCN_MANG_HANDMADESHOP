package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.MatHang;
import service.MatHangService;
import net.minidev.json.JSONObject;


@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class MatHangController {
	@Autowired
	private MatHangService matHangService;

	@PreAuthorize("hasAnyAuthority({'ROLE_ADMIN', 'ROLE_USER'})")
	@PostMapping(value="/authorizied/mat_hang/create")
	public ResponseEntity<Void> taoMatHang(@RequestBody JSONObject matHang) {
//		matHangService.save(matHang);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@GetMapping("/mat_hang/{maMatHang}")
	public ResponseEntity<MatHang> timMatHang(@PathVariable Long maMatHang) {
		MatHang matHang = matHangService.findByMaMatHangAndIsDeletedFalse(maMatHang);
		if(matHang==null) {
			return new ResponseEntity<MatHang>(matHang, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MatHang>(matHang, HttpStatus.OK);
	}
	
	@GetMapping("/mat_hang/getAll")
	public ResponseEntity<JSONObject> getAllMatHang(
			@RequestParam(name="page", required=false, defaultValue="0") int page,
			@RequestParam(name="size", required=false, defaultValue="15") int size,
			@RequestParam(name="sort", required=false, defaultValue="ASC") String sort) throws JSONException{
		Sort sortable = null;
		if(sort.equals("ASC")) {
			sortable = Sort.by("maMatHang").ascending();
		}
		if(sort.equals("DESC")) {
			sortable = Sort.by("maMatHang").descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		
		
		Page<MatHang> returnedPage = matHangService.findByIsDeletedFalse(pageable);
		List<MatHang> listMatHang = returnedPage.getContent();
		JSONObject returnedObject = new JSONObject();
		returnedObject.put("data", listMatHang);
		returnedObject.put("currentPage", returnedPage.getNumber());
	    returnedObject.put("totalItems", returnedPage.getTotalElements());
	    returnedObject.put("totalPages", returnedPage.getTotalPages());
		
		return new ResponseEntity<JSONObject>(returnedObject, HttpStatus.CREATED);

	}
	
}
