<?php

// http://localhost/Thang/get_danhsachloaixe.php
require_once("connect.php");
connectToServer($conn);

//1. tạo class đối tượng 
	class danhsach_loaixe
	{
		function danhsach_loaixe( $MaHangSX,$TenHangSX)
		{
			$this->mahangsx = $MaHangSX;
			$this->tenhang = $TenHangSX;
		}
	}

//2. khởi tạo mảng kháo học quốc tế
$mangdanhsach_loaixe = array();

//3. Truy vấn dữ liệu và thêm phần tử vào mảng Thêm phần tử vào mảng
$q = "select MaHangSX ,TenHangSX from HangSanXuat";
$stmt = sqlsrv_query( $conn, $q);
if( $stmt)
{
	while ($row = sqlsrv_fetch_array( $stmt, SQLSRV_FETCH_ASSOC)) {
	 array_push($mangdanhsach_loaixe, new danhsach_loaixe(
	 	$row['MaHangSX'],
	 	$row['TenHangSX']
	 ));
	}
	
}else{
	echo " - Đang bị lõi truy vấn !!!!!!!!!";
	die( print_r( sqlsrv_errors(), true));
}

//4. chuyển định dạng của mảng sinhvien sang Json
// echo json_encode($mangTS,JSON_PRETTY_PRINT);
echo json_encode($mangdanhsach_loaixe);
?>