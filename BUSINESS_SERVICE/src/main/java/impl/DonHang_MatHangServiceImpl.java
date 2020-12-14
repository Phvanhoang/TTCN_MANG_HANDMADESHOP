package impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import model.DonHang_MatHang;
import repository.DonHang_MatHangRepository;
import service.DonHang_MatHangService;

@Service
@Transactional
public class DonHang_MatHangServiceImpl implements DonHang_MatHangService{
	@Autowired
	private DonHang_MatHangRepository donHang_MatHangRepository;
	
	public void save(DonHang_MatHang donHang_MatHang) {
		donHang_MatHangRepository.save(donHang_MatHang);
	}

}
