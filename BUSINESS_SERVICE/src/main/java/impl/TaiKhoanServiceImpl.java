package impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.TaiKhoanNotFoundException;
import model.TaiKhoan;
import repository.TaiKhoanRepository;
import service.TaiKhoanService;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {
	@Autowired
	private TaiKhoanRepository taiKhoanRepository;
	public TaiKhoan findByMaTaiKhoanAndDeletedFalse(Long maTaiKhoan) {
		return taiKhoanRepository.findByMaTaiKhoan(maTaiKhoan);
	}
	public TaiKhoan save(TaiKhoan taiKhoan) {
		return taiKhoanRepository.save(taiKhoan);
	}
	public boolean existsById(long id) {
		if (taiKhoanRepository.existsById(id)) return true;
		return false;
	}
	public TaiKhoan findByMaTaiKhoan(long id) {
		Optional<TaiKhoan> option = taiKhoanRepository.findById(id);
		if (option.isPresent()) return option.get();
		return null;
	}
	public TaiKhoan findTopByMaTaiKhoanDesc() {
		return taiKhoanRepository.findTopByOrderByMaTaiKhoanDesc();
	}
	public TaiKhoan findByTenDangNhapAndMatKhau(String tenDangNhap, String matKhau) {
		return taiKhoanRepository.findByTenDangNhapAndMatKhauAndDeletedFalse(tenDangNhap, matKhau);
	}
	public boolean existsByTenDangNhap(String tenDangNhap) {
		return taiKhoanRepository.existsByTenDangNhap(tenDangNhap);
	}
	public boolean lockTaiKhoan(long maTaiKhoan) throws TaiKhoanNotFoundException {
		if (!taiKhoanRepository.existsByMaTaiKhoanAndDeletedFalse(maTaiKhoan)) {
			throw new TaiKhoanNotFoundException("Khong ton tai tai khoan");
		}
		TaiKhoan taiKhoan = taiKhoanRepository.findByMaTaiKhoan(maTaiKhoan);
		taiKhoan.setLocked(true);
		taiKhoanRepository.save(taiKhoan);
		return true;
	}
	public boolean changPassword(long maTaiKhoan, String matKhau) throws TaiKhoanNotFoundException{
		if (!taiKhoanRepository.existsByMaTaiKhoanAndDeletedFalse(maTaiKhoan)) {
			throw new TaiKhoanNotFoundException("Khong ton tai tai khoan");
		}
		TaiKhoan taiKhoan = taiKhoanRepository.findByMaTaiKhoan(maTaiKhoan);
		taiKhoan.setMatKhau(matKhau);
		taiKhoanRepository.save(taiKhoan);
		return true;
	}
	public boolean unlockTaiKhoan(long maTaiKhoan) throws TaiKhoanNotFoundException {
		if (!taiKhoanRepository.existsByMaTaiKhoanAndDeletedFalse(maTaiKhoan)) {
			throw new TaiKhoanNotFoundException("Khong ton tai tai khoan");
		}
		TaiKhoan taiKhoan = taiKhoanRepository.findByMaTaiKhoan(maTaiKhoan);
		taiKhoan.setLocked(false);
		taiKhoanRepository.save(taiKhoan);
		return true;
	}
}
