package com.vnpts.tracebility_v2.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseConfig {
    private static ResponseConfig instance;

    private Map<String, String> map;

    public static ResponseConfig getInstance() {
        if (instance == null) instance = new ResponseConfig();
        return instance;
    }

    private ResponseConfig() {
        map = new HashMap<>();
        addResponse();
    }

    public String getMess(String code) {
        return map.get(code);
    }

    private void addResponse() {
        map.put("200", "Thành công");
        map.put("999", "Lỗi hệ thống");
        map.put("800", "Sai chữ ký");
        map.put("007", "Không có quyền thực hiện chức năng");
        map.put("100", "Partner không tồn tại");
        map.put("005", "Token hết hạn");
        map.put("006", "Token lỗi, không thể decode");
        map.put("011", "Lỗi input giá trị");
        map.put("012", "File vượt quá 10MB hoặc không phải excel");
        map.put("013", "Bạn không có quyền thao tác với bản ghi này");
        map.put("014", "Bản ghi đã được sử dụng");
        map.put("101", "Tên đăng nhập đã tồn tại, vui lòng chọn 1 tên đăng nhập khác");
        map.put("102", "Email đã được đăng ký, vui lòng chọn 1 email khác");
        map.put("103", "Thông tin về tỉnh/thành phố, quận/huyện, phường/xã không chính xác");
        map.put("104", "Vai trò trùng lặp hoặc không chính xác");
        map.put("105", "Kích hoạt thất bại");
        map.put("106", "Tài khoản bị khóa vì kích hoạt không chính xác nhiều lần");
        map.put("107", "Vui lòng chọn chính xác sản phẩm gốc");
        map.put("550", "Tên truy cập đã được sử dụng");
        map.put("UPLOAD_AVATAR_01", "Không tìm thấy file");
        map.put("UPLOAD_AVATAR_02", "File không đúng định dạng");
        map.put("UPLOAD_AVATAR_03", "File vượt quá dung lượng");
        map.put("COMMON_USER_NOT_FOUND", "Không tìm thấy thông tin người dùng");
        map.put("COMMON_USER_WAITING_ACTIVE", "Tài khoản này chưa được kích hoạt qua email. Vui lòng xác nhận qua email và quay lại đây sau");
        map.put("COMMON_USER_WAITING_ACCEPT", "Tài khoản này đang chờ phê duyệt. Vui lòng theo dõi email để biết kết quả phê duyệt và quay lại đây sau");
        map.put("COMMON_MORE_THAN_ONE_USER", "Thông tin người dùng bị trùng lặp");
        map.put("COMMON_USER_NOT_ACTIVE", "Tài khoản đã bị khóa");
        map.put("CHANGE_PASSWORD_01", "Mật khẩu cũ không chính xác");
        map.put("INSERT_GUESS_01", "Tên đăng nhập đã được sử dụng");
        map.put("INSERT_GUESS_02", "Số điện thoại đã được sử dụng");
        map.put("INSERT_GUESS_03", "Email đã được sử dụng");
        map.put("INSERT_GUESS_04", "Mã Giấy đăng ký kinh doanh đã được sử dụng");
        map.put("PRODUCER_SEND_DELIVERY_01", "Dãy serial không tồn tại");
        map.put("PRODUCER_SEND_DELIVERY_02", "Dãy serial đã được sử dụng hoặc không có quyền");
        map.put("PRODUCER_SEND_DELIVERY_03", "Công ty không thuộc chuỗi sản xuất nào");
        map.put("REGISTER_01", "Tên đăng nhập đã được sử dụng");
        map.put("REGISTER_02", "Vai trò không hợp lệ");
        map.put("RESEND_REGISTER_EMAIL_01", "Tài khoản không tồn tại hoặc đã được kích hoạt");
        map.put("300", "Trùng tên vai trò");
        map.put("301", "Không thể tác động vai trò mặc định");
        map.put("302", "Không thể thay đổi sản phẩm con");
        map.put("303", "Không thể thay đổi sản phẩm gốc");
        map.put("304", "Tồn tại sản phẩm con, không thể hủy");
        map.put("305", "Loại quy trình không chính xác");
        map.put("306", "Sản phẩm chọn không chính xác");
        map.put("307", "Các bước của quy trình không đúng định dạng");
        map.put("308", "Quy trình không bao gồm các bước");
        map.put("309", "Quy trình đã công bố rộng rãi không thể sửa");
        map.put("310", "Công khai hoặc hủy công khai không đúng");
        map.put("311", "Thông tin truyền lên bị trùng lặp");
        map.put("312", "Quy trình không chính xác");
        map.put("313", "Vai trò không chính xác");
        map.put("314", "Trạng thái của thành viên không chính xác");
        map.put("315", "Không thể xóa thành viên đã thực hiện hành động trong kế hoạch, vui lòng tải lại trang");
        map.put("316", "Chủ thể kế hoạch không thể là thành viên");
        map.put("317", "Trạng thái lời mời không phải là đang chờ");
        map.put("318", "Không đúng kế hoạch sản xuất");
        map.put("319", "Vui lòng chọn đúng thông tin");
        map.put("320", "Lô thu hoạch không chính xác");
        map.put("321", "Khu vực sản xuất đã thu hoạch thành phẩm, không thể thu hoạch tiếp");
        map.put("322", "Serial không chính xác");
        map.put("323", "Lệnh in không chính xác");
        map.put("324", "Không đúng trạng thái của lệnh in");
        map.put("325", "Lô thu hoạch không được xác nhận");
        map.put("326", "Không có quyền tạo quy trình bắt buộc");
        map.put("327", "Kế hoạch không thể không có thành viên");
        map.put("328", "Lời mời không tồn tại hoặc đã bị hủy");
        map.put("329", "Lô thu hoạch đã được sử dụng, không thể tác động");
        map.put("330", "Kế hoạch đã được tạo khu vực sản xuất, không thể hủy");
        map.put("331", "Mã số sản phẩm phải là định dạng 13 chữ số");
        map.put("332", "Mã số sản phẩm không đúng định dạng EAN13");
        map.put("333", "Không thể xóa người dùng đã tạo sản phẩm");
        map.put("334", "Không thể xóa người dùng đã tạo kế hoạch");
        map.put("335", "Không thể xóa người dùng đã tham gia kế hoạch");
        map.put("336", "Không thể xóa người dùng đã tạo quy trình");
        map.put("533", "Không gian mã không đủ, vui lòng liên hệ quản trị viên");
        map.put("404", "Không tìm thấy thông tin");
        map.put("APP_LOGIN_WRONG_BUSINESS_TYPE", "Vai trò của bạn không có quyền truy cập vào app!");
        map.put("LOGIN_USER_NOT_FOUND", "Tài khoản hoặc mật khẩu không đúng");
        map.put("USER_NOT_FOUND", "Không tìm thấy thông tin người dùng");
        map.put("CHANGE_ROLE_CHAIN_REQUEST_01", "Một yêu cầu đã được gửi đi trước đó của ban chưa được duyệt, xin vui lòng chờ hoặc liên hệ với quản trị!");
    }
}
