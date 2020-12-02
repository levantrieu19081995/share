<?php
// http://localhost/Thang/get_danhsachxelienquan.php
require_once("connect.php");
connectToServer($conn);
// $ma_xe=15;
// $ma_sx=1;
$ma_xes = $_POST['ma_xe'];
$ma_sxs = $_POST['ma_sx'];
//1. tạo class đối tượng 
	class DanhSach_xelienquan
	{
		function DanhSach_xelienquan($MaXe, $DiaChiGiaoXe, $Avatar, $TenLoaiXe,$Gia, $MaHangSX )
		{
			$this->ma_xe = $MaXe;
			$this->diachi = $DiaChiGiaoXe;
			$this->hinh = $Avatar;
			$this->loaixe = $TenLoaiXe;
			$this->gia = $Gia;
			$this->ma_sx = $MaHangSX;
		}
	}

//2. khởi tạo mảng kháo học quốc tế
$mangDanhSach_xelienquan = array();

//3. Truy vấn dữ liệu và thêm phần tử vào mảng Thêm phần tử vào mảng
$q = "select MaXe, DiaChiGiaoXe, Avatar, TenLoaiXe, Gia , MaHangSX from Xe join LoaiXe as lx on Xe.MaLoaiXe = lx.MaLoaiXe where lx.MaHangSX = '$ma_sxs' and Xe.MaXe not in (select MaXe from Xe where MaXe = '$ma_xes')";
$stmt = sqlsrv_query( $conn, $q);
if( $stmt)
{
	while ($row = sqlsrv_fetch_array( $stmt, SQLSRV_FETCH_ASSOC)) {
	 array_push($mangDanhSach_xelienquan, new DanhSach_xelienquan(
	 	$row['MaXe'],
	 	$row['DiaChiGiaoXe'],
	 	$row['Avatar'],
	 	$row['TenLoaiXe'],
	 	$row['Gia'],
	 	$row['MaHangSX']


	 ));
	}
	
}else{
	echo " - Đang bị lõi truy vấn !!!!!!!!!";
	die( print_r( sqlsrv_errors(), true));
}

//4. chuyển định dạng của mảng sinhvien sang Json
// echo json_encode($mangTS,JSON_PRETTY_PRINT);
echo json_encode($mangDanhSach_xelienquan);
?>