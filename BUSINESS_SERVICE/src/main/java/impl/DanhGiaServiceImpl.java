package impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import exception.DanhGiaNotFoundException;
import model.DanhGia;
import model.MatHang;
import repository.DanhGiaRepository;
import repository.MatHangRepository;
import service.DanhGiaService;

@Service
@Transactional
public class DanhGiaServiceImpl implements DanhGiaService{
	@Autowired
	private DanhGiaRepository danhGiaRepository;
	
	@Autowired
	private MatHangRepository matHangRepository;
	
	public Page<DanhGia> findAll(Pageable pageable) {
		danhGiaRepository.findByDeletedFalse(pageable);
		return null;
	}

	public Page<DanhGia> findByMatHang(Pageable pageable, long maMatHang) {
		danhGiaRepository.findByDeletedFalseAndMatHang(pageable, maMatHang);
		return null;
	}

	public Page<DanhGia> findByCreatedBy(Pageable pageable, long maTaiKhoan) {
		danhGiaRepository.findByDeletedFalseAndCreatedBy(pageable, maTaiKhoan);
		return null;
	}

	@Transactional
	public DanhGia save(DanhGia danhGia) throws DanhGiaNotFoundException {
		if (danhGia.getMaDanhGia() > 0 && !danhGiaRepository.existsById(danhGia.getMaDanhGia())) {
			throw new DanhGiaNotFoundException("Not Found Danh Gia");
		}
		MatHang matHang = matHangRepository.findByMaMatHangAndDeletedFalse(danhGia.getMatHang().getMaMatHang());
		int rate = (matHang.getRate() * matHang.getSoLuotDanhGia() + danhGia.getSoSao()) / (matHang.getSoLuotDanhGia() + 1);
		matHang.setRate(rate);
		matHang.setSoLuotDanhGia(matHang.getSoLuotDanhGia() + 1);
		matHang.getDanhSachDanhGia().add(danhGia);
		danhGia.setMatHang(matHang);
		matHangRepository.save(matHang);
		return danhGiaRepository.save(danhGia);
	}

	public boolean exist(long maTaiKhoan) {
		return danhGiaRepository.existsById(maTaiKhoan);
	}

	public boolean delete(long maDanhGia) throws DanhGiaNotFoundException {
		if (danhGiaRepository.existsByDeletedFalseAndMaDanhGiaEquals(maDanhGia)) {
			throw new DanhGiaNotFoundException("Not Found Danh Gia");
		}
		DanhGia danhgia = danhGiaRepository.findById(maDanhGia).get();
		danhgia.setDeleted(true);
		danhGiaRepository.save(danhgia);
		return false;
	}

}
