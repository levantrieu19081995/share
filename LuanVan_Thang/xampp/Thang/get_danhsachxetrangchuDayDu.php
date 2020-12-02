<?php
// http://192.168.1.13/Thang/get_danhsachxetrangchuDayDu.php

require_once("connect.php");
connectToServer($conn);

//1. tạo class đối tượng 
	class DanhSach_xechothue
	{
		function DanhSach_xechothue( $Avatar, $Gia, $TenLoaiXe, $MaXe,$MaHangSX)
		{
			$this->img = $Avatar;
			$this->gia = $Gia;
			$this->loaixe = $TenLoaiXe;
			$this->ma_xe = $MaXe;
			$this->ma_sx = $MaHangSX;
		}
	}

//2. khởi tạo mảng kháo học quốc tế
$mangDanhSach_xechothue = array();

//3. Truy vấn dữ liệu và thêm phần tử vào mảng Thêm phần tử vào mảng
$q =  "select  Avatar, Gia, TenLoaiXe, MaXe, MaHangSX from LoaiXe as lx join Xe on lx.MaLoaiXe = Xe.MaLoaiXe where TinhTrang is null";
$stmt = sqlsrv_query( $conn, $q);
if( $stmt)
{
	while ($row = sqlsrv_fetch_array( $stmt, SQLSRV_FETCH_ASSOC)) {
	 array_push($mangDanhSach_xechothue, new DanhSach_xechothue(
	 	$row['Avatar'],
	 	$row['Gia'],
	 	$row['TenLoaiXe'],
	 	$row['MaXe'],
	 	$row['MaHangSX']
	 ));
	}
	
}else{
	echo " - Đang bị lõi truy vấn !!!!!!!!!";
	die( print_r( sqlsrv_errors(), true));
}

//4. chuyển định dạng của mảng sinhvien sang Json
// echo json_encode($mangTS,JSON_PRETTY_PRINT);
echo json_encode($mangDanhSach_xechothue);
?>