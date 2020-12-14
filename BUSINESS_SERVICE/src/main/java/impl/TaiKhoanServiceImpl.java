package impl;


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
	public TaiKhoan save(TaiKhoan taiKhoan) {
		return taiKhoanRepository.save(taiKhoan);
		
	}
}
