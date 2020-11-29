package service;

import model.NguoiDung;

public interface NguoiDungService {
	void save(NguoiDung nguoiDung);
	NguoiDung findOne(long maNguoiDung);
}
