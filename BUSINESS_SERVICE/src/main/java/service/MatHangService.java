package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import model.MatHang;
import model.NguoiDung;

public interface MatHangService {
	void save(MatHang matHang);
	MatHang findByMaMatHangAndIsDeletedFalse(long maMatHang);
	Page<MatHang> findByIsDeletedFalse(Pageable pageable);

}
