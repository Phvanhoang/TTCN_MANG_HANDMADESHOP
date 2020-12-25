package impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.Anh_MatHangNotFoundException;
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

	public boolean delete(long id) throws Anh_MatHangNotFoundException {
		Optional<Anh_MatHang> optional = anh_MatHangRepository.findById(id);
		if (!optional.isPresent()) {
			throw new Anh_MatHangNotFoundException("Anh khong ton tai");
		}
		anh_MatHangRepository.deleteById(id);
		return true;
	}

	public boolean replace(long id, byte[] anh) throws Anh_MatHangNotFoundException {
		Optional<Anh_MatHang> optional = anh_MatHangRepository.findById(id);
		if (!optional.isPresent()) {
			throw new Anh_MatHangNotFoundException("Anh khong ton tai");
		}
		Anh_MatHang anh_MH = optional.get();
		anh_MH.setAnh(anh);
		anh_MatHangRepository.save(anh_MH);
		return true;
	}

	public Anh_MatHang findOne(long id) throws Anh_MatHangNotFoundException {
		Optional<Anh_MatHang> optional = anh_MatHangRepository.findById(id);
		if (!optional.isPresent()) {
			throw new Anh_MatHangNotFoundException("Anh khong ton tai");
		}
		return optional.get();
	}
}
