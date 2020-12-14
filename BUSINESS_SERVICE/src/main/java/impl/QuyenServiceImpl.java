package impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.DacQuyen;
import repository.QuyenRepository;
import service.QuyenService;

@Service
public class QuyenServiceImpl implements QuyenService {
	@Autowired
	private QuyenRepository quyenRepository;

	public ArrayList<DacQuyen> getDanhSachDacQuyen() {
		return quyenRepository.findAll();
	}

}
