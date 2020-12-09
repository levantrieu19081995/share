<?php
// http://localhost/Thang/dangky.php
	require_once("connect.php");

	$tennguoidung_dk = $_POST["tennguoidung_dk"];
	$sdt_dk = $_POST["sdt_dk"];
	$email_dk = $_POST["email_dk"];
	$pass_dk = $_POST["pass_dk"];

	// $tennguoidung_dk = "t";
	// $sdt_dk = "2";
	// $email_dk = "2";
	// $pass_dk = "123";

	class DangKy
	{
		function DangKy($ketqua)
		{
			$this->ketqua = $ketqua;
		}
	}
	$ma_tk = "";
	$mangDangKy = array();

	$q = "insert TaiKhoan(TenDangNhap, MatKhau, MaLoaiTK, TrangThai) values('$sdt_dk', '$pass_dk', N'2','1')";
	$ex = sqlsrv_query($conn , $q);

	if($ex){
		$q2 = "select MaTK from TaiKhoan where TenDangNhap='$sdt_dk'";
		$ex2 = sqlsrv_query($conn , $q2);
		while ($rows = sqlsrv_fetch_array($ex2, SQLSRV_FETCH_ASSOC)) {
	 			$ma_tk = $rows['MaTK'];
			}

		$q3 = "insert KhachHang(TenKH, SDT, Email, MaTK) values(N'$tennguoidung_dk','$sdt_dk','$email_dk','$ma_tk')";
		$ex3 = sqlsrv_query($conn , $q3);
		if($ex3){
			array_push($mangDangKy, new DangKy(
		 			"thanhcong"));
		}else{
			array_push($mangDangKy, new DangKy(
		 			"thatbai"));
		}
		
	}else{
			echo "lỗi server!!";
		}

	header('Content-Type: application/json');
	echo json_encode($mangDangKy, JSON_PRETTY_PRINT);

?>

