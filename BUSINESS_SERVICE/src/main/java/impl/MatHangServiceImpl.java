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
	
	public MatHang findByMaMatHangAndDeletedFalse(long maMatHang) {
		return matHangRepository.findByMaMatHangAndDeletedFalse(maMatHang);
	}

	public Page<MatHang> findByDeletedFalse(Pageable pageable) {
		return matHangRepository.findByDeletedFalse(pageable);
	}
}
