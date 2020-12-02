<?php

require_once("connect.php");
connectToServer($conn);
$ma_loaixe=4;
// $ma_loaixe = $_POST['maloai_xe'];
//1. tạo class đối tượng 
	class DatDiem_xe
	{
		function DatDiem_xe( $SoCho, $TruyenDong, $LoaiNhienLieu )
		{
			$this->socho = $SoCho;
			$this->truyendong = $TruyenDong;
			$this->nhienlieu = $LoaiNhienLieu;
		
		}
	}

//2. khởi tạo mảng kháo học quốc tế
$mangDatDiem_xe = array();

//3. Truy vấn dữ liệu và thêm phần tử vào mảng Thêm phần tử vào mảng
$q = "select SoCho, TruyenDong, LoaiNhienLieu from LoaiXe where MaLoaiXe ='$ma_loaixe'";
$stmt = sqlsrv_query( $conn, $q);
if( $stmt)
{
	while ($row = sqlsrv_fetch_array( $stmt, SQLSRV_FETCH_ASSOC)) {
	 array_push($mangDatDiem_xe, new DatDiem_xe(
	 	$row['SoCho'],
	 	$row['TruyenDong'],
	 	$row['LoaiNhienLieu']
	 ));
	}
	
}else{
	echo " - Đang bị lõi truy vấn !!!!!!!!!";
	die( print_r( sqlsrv_errors(), true));
}

//4. chuyển định dạng của mảng sinhvien sang Json
// echo json_encode($mangTS,JSON_PRETTY_PRINT);
echo json_encode($mangDatDiem_xe);
?>