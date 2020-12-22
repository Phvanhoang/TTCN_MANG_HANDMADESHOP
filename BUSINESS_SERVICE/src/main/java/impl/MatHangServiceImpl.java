package impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import exception.MatHangNotFoundException;
import model.MatHang;
import repository.MatHangRepository;
import service.MatHangService;

@Service
public class MatHangServiceImpl implements MatHangService{
	@Autowired
	private MatHangRepository matHangRepository;
	
	public MatHang save(MatHang matHang) {
		return matHangRepository.save(matHang);
	}
	
	public MatHang findByMaMatHang(long maMatHang) {
		return matHangRepository.findByMaMatHangAndDeletedFalse(maMatHang);
	}

	public Page<MatHang> findAll(Pageable pageable) {
		return matHangRepository.findByDeletedFalse(pageable);
	}

	public Page<MatHang> findByMaLoaiMatHang(Pageable pageable, long maLoaiMatHang) {
		return matHangRepository.findByDeletedFalseAndLoaiMatHang(pageable, maLoaiMatHang);
	}

	public boolean delete(long maMatHang) throws MatHangNotFoundException {
		MatHang matHang = matHangRepository.findByMaMatHangAndDeletedFalse(maMatHang);
		if (matHang == null) {
			throw new MatHangNotFoundException("Mat Hang Not Found");
		}
		matHang.setDeleted(true);
		return true;
	}

	public boolean existMatHangByMaMatHang(long maMatHang) {
		return matHangRepository.existsById(maMatHang);
	}
}
