package impl;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import model.DonHang;
import model.DonHang_MatHang;
import model.MatHang;
import model.NguoiDung;
import model.TrangThaiDonHang;
import repository.DonHangRepository;
import repository.MatHangRepository;
import service.DonHangService;

@Service
@Transactional
public class DonHangServiceImpl implements DonHangService{
	@Autowired
	private DonHangRepository donHangRepository;
	
	@Autowired
	private MatHangRepository matHangRepository;
	
	public Page<DonHang> findAllByNguoiDungAndDeletedFalse(Pageable pageable, NguoiDung nguoiDung) {
		return null;
//		return donHangRepository.findByNguoiDungAndDeletedFalse(pageable, nguoiDung);
	}

	@Transactional
	public void createDonHang(DonHang donHang) throws Exception {
		int sum = 0;
		Set<DonHang_MatHang> list = donHang.getDanhSachMatHang();
		Iterator<DonHang_MatHang> itr = list.iterator();
		while (itr.hasNext()) {
			DonHang_MatHang donHang_MatHang = itr.next();
			System.out.println(donHang_MatHang.getMatHang());
			MatHang matHang = matHangRepository.findById(donHang_MatHang.getMatHang().getMaMatHang()).get();
			sum += donHang_MatHang.getSoLuong() * matHang.getGia();
		}
		donHang.setGiaTongCong(sum);
//		String dateString = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
//		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		java.sql.Date ngay_convert = null;
//		try {
//			ngay_convert = new Date(simpleDateFormat.parse(dateString).getTime());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		donHang.setThoiGian(new java.util.Date());
		TrangThaiDonHang trangThaiDonHang = new TrangThaiDonHang();
		trangThaiDonHang.setMatrangThai(1);
		donHang.setTrangThaiDonHang(trangThaiDonHang);
		donHangRepository.save(donHang);
		throw new Exception();
	}

}
