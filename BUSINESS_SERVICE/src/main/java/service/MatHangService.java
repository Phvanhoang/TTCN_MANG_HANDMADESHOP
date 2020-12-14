package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import model.MatHang;

public interface MatHangService {
	void save(MatHang matHang);
	MatHang findByMaMatHangAndDeletedFalse(long maMatHang);
	Page<MatHang> findByDeletedFalse(Pageable pageable);

}
