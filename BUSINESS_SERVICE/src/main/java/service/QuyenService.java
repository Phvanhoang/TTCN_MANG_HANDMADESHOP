package service;

import java.util.ArrayList;
import java.util.List;

import model.DacQuyen;

public interface QuyenService {
	ArrayList<DacQuyen> getDanhSachDacQuyen();
	void save(DacQuyen dacQuyen);
	boolean checkTenDacQuyen(String tenDacQuyen);
}
