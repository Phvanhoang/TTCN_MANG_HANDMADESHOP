package impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import exception.MatHangNotFoundException;
import model.LoaiMatHang;
import model.MatHang;
import repository.MatHangRepository;
import service.MatHangService;

@Service
public class MatHangServiceImpl implements MatHangService {
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

	public Page<MatHang> findByLoaiMatHang(Pageable pageable, LoaiMatHang loaiMatHang) {
		return matHangRepository.findByDeletedFalseAndLoaiMatHang(pageable, loaiMatHang);
	}

	public boolean delete(long maMatHang) throws MatHangNotFoundException {
		MatHang matHang = matHangRepository.findByMaMatHangAndDeletedFalse(maMatHang);
		if (matHang == null) {
			throw new MatHangNotFoundException("Mat Hang Not Found");
		}
		matHang.setDeleted(true);
		matHangRepository.save(matHang);
		return true;
	}

	public boolean existMatHangByMaMatHang(long maMatHang) {
		return matHangRepository.existsById(maMatHang);
	}

	public Page<MatHang> findWithoutLoaiMatHang(Pageable pageable, String tenMatHang, long giaBatDau, long giaKetThuc) {
		return matHangRepository.findByDeletedFalseAndTenMatHangContainingAndGiaGreaterThanEqualAndGiaLessThanEqual(pageable, tenMatHang, giaBatDau, giaKetThuc);
	}

	public Page<MatHang> findWithFilter(Pageable pageable, LoaiMatHang loaiMatHang, String tenMatHang, long giaBatDau,
			long giaKetThuc) {
		return matHangRepository.findByDeletedFalseAndLoaiMatHangAndTenMatHangContainingAndGiaGreaterThanEqualAndGiaLessThanEqual(pageable, loaiMatHang, tenMatHang, giaBatDau, giaKetThuc);
	}

}
