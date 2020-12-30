package service;

import java.util.ArrayList;

import model.GioiTinh;

public interface GioiTinhService {
	void save(GioiTinh gioiTinh);
	boolean checkTenGioiTinh(String tenGioiTinh);
	ArrayList<GioiTinh> getDanhSachGioiTinh();
}
