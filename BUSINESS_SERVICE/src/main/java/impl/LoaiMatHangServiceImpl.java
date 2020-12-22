package impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import model.LoaiMatHang;
import repository.LoaiMatHangRepository;
import service.LoaiMatHangService;

@Service
public class LoaiMatHangServiceImpl implements LoaiMatHangService{
	@Autowired
	private LoaiMatHangRepository loaiMatHangRepository;
	
	public void save(LoaiMatHang loaiMatHang) {
		loaiMatHangRepository.save(loaiMatHang);
	}

	public LoaiMatHang findByMaLoaiMatHang(long maLoaiMatHang) {
		return loaiMatHangRepository.findByMaLoaiMatHangAndDeletedFalse(maLoaiMatHang);
	}

	public ArrayList<LoaiMatHang> findAll() {
		return loaiMatHangRepository.findByDeletedFalse();
	}

	public void delete(LoaiMatHang loaiMatHang) {
		loaiMatHangRepository.delete(loaiMatHang);
	}

}
