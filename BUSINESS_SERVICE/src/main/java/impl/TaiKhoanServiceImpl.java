package impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void save(TaiKhoan taiKhoan) {
		taiKhoanRepository.save(taiKhoan);
		
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
}
