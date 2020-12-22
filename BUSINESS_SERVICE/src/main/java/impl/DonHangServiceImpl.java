package impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import model.DonHang;
import model.DonHang_MatHang;
import model.DonHang_MatHang_Key;
import model.MatHang;
import model.NguoiDung;
import model.TrangThaiDonHang;
import repository.DonHangRepository;
import repository.DonHang_MatHangRepository;
import repository.MatHangRepository;
import service.DonHangService;

@Service
@Transactional
public class DonHangServiceImpl implements DonHangService{
	@Autowired
	private DonHangRepository donHangRepository;
	
	@Autowired
	private MatHangRepository matHangRepository;
	
	@Autowired
	private DonHang_MatHangRepository donHang_MatHangRepository;
	
	public Page<DonHang> findAllByNguoiDungAndDeletedFalse(Pageable pageable, NguoiDung nguoiDung) {
		return null;
//		return donHangRepository.findByNguoiDungAndDeletedFalse(pageable, nguoiDung);
	}

	@Transactional
	public void createDonHang(DonHang donHang) {
		int sum = 0;
		Set<DonHang_MatHang> list = donHang.getDanhSachMatHang();
		Iterator<DonHang_MatHang> itr = list.iterator();
		while (itr.hasNext()) {
			DonHang_MatHang donHang_MatHang = itr.next();
			MatHang matHang = matHangRepository.findByMaMatHangAndDeletedFalse(donHang_MatHang.getMatHang().getMaMatHang());
			donHang_MatHang.setMatHang(matHang);
			donHang_MatHang.setDonHang(donHang);
			donHang.getDanhSachMatHang().add(donHang_MatHang);
			int subNumber = donHang_MatHang.getSoLuong();
			matHang.setSoLuong(matHang.getSoLuong() - subNumber);
			matHang.setSoLuongDaBan(matHang.getSoLuongDaBan() + subNumber);
			sum += donHang_MatHang.getSoLuong() * matHang.getGia();
			matHangRepository.save(matHang);
		}
		donHang.setGiaTongCong(sum);
//		donHang.setThoiGian(new Date());
		TrangThaiDonHang trangThaiDonHang = new TrangThaiDonHang();
		trangThaiDonHang.setMatrangThai(1);
		donHang.setTrangThaiDonHang(trangThaiDonHang);
		donHangRepository.save(donHang);
	}

}
