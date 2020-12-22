package impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.GioiTinh;
import repository.GioiTinhRepository;
import service.GioiTinhService;

@Service
public class GioiTinhServiceImpl implements GioiTinhService{
	@Autowired
	private GioiTinhRepository gioiTinhRepository;
	public void save(GioiTinh gioiTinh) {
		gioiTinhRepository.save(gioiTinh);
	}

	public boolean checkTenGioiTinh(String tenGioiTinh) {
		GioiTinh gioiTinh = gioiTinhRepository.findByTenGioiTinh(tenGioiTinh);
		if (gioiTinh != null) return true;
		return false;
	}

}
