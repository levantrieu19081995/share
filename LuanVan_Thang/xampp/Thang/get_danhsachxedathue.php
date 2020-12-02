<?php
// http://localhost/Thang/get_danhsachxedathue.php
require_once("connect.php");
connectToServer($conn);

	$ma_kh = $_POST["ma_kh"];	
	// $ma_kh = 1;

//1. tạo class đối tượng 
	class DanhSach_xedathue
	{
		function DanhSach_xedathue( $Avatar, $TenLoaiXe, $GiaHD )
		{
			$this->img = $Avatar;
			$this->tenxe = $TenLoaiXe;
			$this->tonggia = $GiaHD;
		}
	}

//2. khởi tạo mảng kháo học quốc tế
$mangDanhSach_xedathue = array();

//3. Truy vấn dữ liệu và thêm phần tử vào mảng Thêm phần tử vào mảng
$q = "select Avatar, TenLoaiXe, GiaHD from Xe join HopDong as hd on Xe.MaXe = hd.MaXe, LoaiXe as lx where Xe.MaLoaiXe = lx.MaLoaiXe and hd.MaKH = '$ma_kh'";
$stmt = sqlsrv_query( $conn, $q);
if( $stmt)
{
	while ($row = sqlsrv_fetch_array( $stmt, SQLSRV_FETCH_ASSOC)) {
	 array_push($mangDanhSach_xedathue, new DanhSach_xedathue(
	 	$row['Avatar'],
	 	$row['TenLoaiXe'],
	 	$row['GiaHD']));
	}
	
}else{
	echo " - Đang bị lõi truy vấn !!!!!!!!!";
	die( print_r( sqlsrv_errors(), true));
}

//4. chuyển định dạng của mảng sinhvien sang Json
// echo json_encode($mangTS,JSON_PRETTY_PRINT);
echo json_encode($mangDanhSach_xedathue);
?>