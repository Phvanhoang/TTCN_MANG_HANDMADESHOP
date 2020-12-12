package impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import model.MatHang;
import repository.MatHangRepository;
import service.MatHangService;

@Service
public class MatHangServiceImpl implements MatHangService{
	@Autowired
	private MatHangRepository matHangRepository;
	
	public void save(MatHang matHang) {
		matHangRepository.save(matHang);
	}
	
	public MatHang findByMaMatHangAndIsDeletedFalse(long maMatHang) {
		return matHangRepository.findByMaMatHangAndIsDeletedFalse(maMatHang);
	}

	public Page<MatHang> findByIsDeletedFalse(Pageable pageable) {
		return matHangRepository.findByIsDeletedFalse(pageable);
	}
}
