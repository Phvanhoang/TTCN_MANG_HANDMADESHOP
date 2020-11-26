package impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.MatHang;
import repository.MatHangRepository;
import service.MatHangService;

@Service
public class MatHangServiceImpl implements MatHangService{
	@Autowired
	private MatHangRepository matHangRepository;
	
	public void save(MatHang matHang) {
		matHangRepository.save(matHang);
	}
	
	public MatHang findOne(long maMatHang) {
//		System.out.println(maMatHang);
		return matHangRepository.findById(maMatHang).get();
	}
}
