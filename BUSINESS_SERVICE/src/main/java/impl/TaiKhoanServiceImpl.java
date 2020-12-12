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
	public TaiKhoan findByMaTaiKhoanAndIsDeletedFalse(Long maTaiKhoan) {
		return taiKhoanRepository.findByMaTaiKhoanAndIsDeletedFalse(maTaiKhoan);
	}
	public void save(TaiKhoan taiKhoan) {
		taiKhoanRepository.save(taiKhoan);
		
	}
}
