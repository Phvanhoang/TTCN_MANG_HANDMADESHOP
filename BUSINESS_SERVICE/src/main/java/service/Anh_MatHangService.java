package service;

import exception.Anh_MatHangNotFoundException;
import model.Anh_MatHang;

public interface Anh_MatHangService {
	void save(Anh_MatHang anh);
	boolean delete(long id) throws Anh_MatHangNotFoundException;
	boolean replace(long id, byte[] anh) throws Anh_MatHangNotFoundException;
	Anh_MatHang findOne(long id) throws Anh_MatHangNotFoundException;
}
