package service;

import model.GioiTinh;

public interface GioiTinhService {
	void save(GioiTinh gioiTinh);
	boolean checkTenGioiTinh(String tenGioiTinh);
}
