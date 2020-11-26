package impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import model.Anh_MatHang;
import repository.Anh_MatHangRepository;
import service.Anh_MatHangService;

@Service
public class Anh_MatHangServiceImpl implements Anh_MatHangService{
	@Autowired
	private Anh_MatHangRepository anh_MatHangRepository;
	
	public void save(Anh_MatHang anh) {
		anh_MatHangRepository.save(anh);
	}
}
