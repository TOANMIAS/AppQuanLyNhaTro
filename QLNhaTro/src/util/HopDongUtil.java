package util;

import model.PhongTro;
import model.KhachThue;

import java.text.NumberFormat;
import java.util.Locale;

public class HopDongUtil {

	public static String taoNoiDungHopDong(PhongTro phong, KhachThue khach) {

		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

		return """
				HỢP ĐỒNG THUÊ PHÒNG TRỌ

				1. THÔNG TIN KHÁCH THUÊ
				Họ tên      : %s
				CMND/CCCD   : %s
				Quê quán    : %s
				Nghề nghiệp : %s

				2. THÔNG TIN PHÒNG TRỌ
				Mã phòng    : %s
				Thông tin   : %s
				Số người    : %d
				Giá thuê    : %s / tháng

				3. ĐIỀU KHOẢN THUÊ
				1. Khách thuê sử dụng phòng đúng mục đích ở.
				2. Không cho thuê lại khi chưa được sự đồng ý của chủ trọ.
				3. Thanh toán tiền thuê đúng hạn hàng tháng.
				4. Giữ gìn tài sản, bồi thường nếu làm hư hỏng.
				5. Báo trước 30 ngày khi chấm dứt hợp đồng.

				4. CAM KẾT
				Hai bên đã đọc kỹ và đồng ý với các điều khoản trên.

				Khách thuê ký tên
				(Ký và ghi rõ họ tên)
				""".formatted(khach.getTenKhach(), khach.getCmnd(), khach.getQueQuan(), khach.getNgheNghiep(),
				phong.getMaPhong(), phong.getThongTinPhong(), phong.getSoNguoi(), nf.format(phong.getGiaThue()));
	}
}
