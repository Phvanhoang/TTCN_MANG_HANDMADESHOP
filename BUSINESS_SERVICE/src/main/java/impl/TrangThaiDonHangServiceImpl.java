package impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.TrangThaiDonHang;
import repository.TrangThaiDonHangRepository;
import service.TrangThaiDonHangService;

@Service
public class TrangThaiDonHangServiceImpl implements TrangThaiDonHangService{
	@Autowired
	private TrangThaiDonHangRepository trangThaiDonHangRepository;
	public boolean checkTenTrangThai(String tenTrangThai) {
		TrangThaiDonHang trangThaiDonHang = trangThaiDonHangRepository.findByTenTrangThai(tenTrangThai);
		if (trangThaiDonHang != null) return true;
		return false;
	}

	public void save(TrangThaiDonHang trangThaiDonHang) {
		trangThaiDonHangRepository.save(trangThaiDonHang);
	}

	public List<TrangThaiDonHang> getAll() {
		return (List<TrangThaiDonHang>) trangThaiDonHangRepository.findAll();
	}
}
