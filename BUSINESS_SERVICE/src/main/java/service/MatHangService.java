package service;

import model.MatHang;

public interface MatHangService {
	void save(MatHang matHang);
	MatHang findOne(long maMatHang);
}
