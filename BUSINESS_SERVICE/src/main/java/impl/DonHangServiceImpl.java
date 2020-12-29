package impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import exception.DonHangNotFoundException;
import model.DonHang;
import model.DonHang_MatHang;
import model.MatHang;
import model.TaiKhoan;
import model.TinhTrangDonHangNames;
import model.TrangThaiDonHang;
import repository.DonHangRepository;
import repository.DonHang_MatHangRepository;
import repository.MatHangRepository;
import repository.TaiKhoanRepository;
import repository.TrangThaiDonHangRepository;
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
	
	@Autowired
	private TaiKhoanRepository taiKhoanRepository;
	
	@Autowired
	private TrangThaiDonHangRepository trangThaiDonHangRepository;
	
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
		ArrayList<TrangThaiDonHang> dsTrangThaiDH = (ArrayList<TrangThaiDonHang>) trangThaiDonHangRepository.findAll();
		
		donHang.setGiaTongCong(sum);
		TrangThaiDonHang trangThaiDonHang = new TrangThaiDonHang();
		for(int i =0; i < dsTrangThaiDH.size(); i++) {
			if(TinhTrangDonHangNames.PENDING.equals(dsTrangThaiDH.get(i).getTenTrangThai())) {
				trangThaiDonHang.setMaTrangThai(dsTrangThaiDH.get(i).getMaTrangThai());
			}
		}
		donHang.setTrangThaiDonHang(trangThaiDonHang);
		donHangRepository.save(donHang);
	}

	public Page<DonHang> findByCreatedBy(Pageable pageable, long maTaiKhoan) {
		TaiKhoan taiKhoan = taiKhoanRepository.findByMaTaiKhoan(maTaiKhoan);
		return donHangRepository.findByDeletedFalseAndCreatedBy(pageable, taiKhoan);
	}

	public Page<DonHang> findByMatHang(Pageable pageable, long maMatHang) {
		MatHang matHang = matHangRepository.findByMaMatHangAndDeletedFalse(maMatHang);
		return donHangRepository.findByDeletedFalseAndMatHang(pageable, matHang);
	}

	public Page<DonHang> findByTrangThaiDonHang(Pageable pageable, long maTTDH) {
		TrangThaiDonHang trangThaiDonHang = trangThaiDonHangRepository.findById(maTTDH).get();
		return donHangRepository.findByDeletedFalseAndTrangThaiDonHangOrderByCreatedAtDesc(pageable, trangThaiDonHang);
	}

	public Page<DonHang> findOrderByCreateAtDesc(Pageable pageable) {
		return donHangRepository.findByDeletedFalseOrderByCreatedAtDesc(pageable);
	}

	public boolean changeTrangThaiDonHang(long maDonHang, long maTTDH) throws DonHangNotFoundException {
		TrangThaiDonHang trangThaiDonHang = trangThaiDonHangRepository.findById(maTTDH).get();
		if (!donHangRepository.existsById(maDonHang)) {
			throw new DonHangNotFoundException("Ma Don Hang khong ton tai");
		}
		DonHang donHang = donHangRepository.findById(maDonHang).get();
		donHang.setTrangThaiDonHang(trangThaiDonHang);
		donHangRepository.save(donHang);
		return true;
	}

	public boolean delete(long maDonHang) throws DonHangNotFoundException {
		if (!donHangRepository.existsByDeletedFalseAndMaDonHangEquals(maDonHang) ) {
			throw new DonHangNotFoundException("Ma Don Hang khong ton tai");
		}
		DonHang donHang = donHangRepository.findById(maDonHang).get();
		donHang.setDeleted(true);
		donHangRepository.save(donHang);
		return false;
	}

	public ArrayList<DonHang> findByThoiGian(TrangThaiDonHang trangThai, Date startDate, Date endDate) {
		return donHangRepository.findByDeletedFalseAndTrangThaiDonHangAndCreatedAtGreaterThanAndCreatedAtLessThan(trangThai, startDate, endDate);
	}

	public DonHang findByMaDonHang(long maDonHang) {
		if(!donHangRepository.existsByDeletedFalseAndMaDonHangEquals(maDonHang)) {
			return null;
		}
		return donHangRepository.findByMaDonHangAndDeletedFalse(maDonHang);
	}
}
