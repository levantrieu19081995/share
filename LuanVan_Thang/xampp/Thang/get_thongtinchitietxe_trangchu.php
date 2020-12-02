<?php
// http://localhost/Thang/get_thongtinchitietxetrangchu.php
require_once("connect.php");
connectToServer($conn);
// $ma_xe=15;
$ma_xe=$_POST['ma_xe'];
//1. tạo class đối tượng 
	class thongtin_chitietxe
	{
		function thongtin_chitietxe( $TenLoaiXe, $DiaChiGiaoXe, $SoKMToiDa,$PhiKMVuot,$Gia,$Avatar)
		{
			$this->ten_lx = $TenLoaiXe;
			$this->diachi_lx = $DiaChiGiaoXe;
			$this->sokmtoida_lx = $SoKMToiDa;
			$this->phikmvuot_lx = $PhiKMVuot;
			$this->gia_lx = $Gia;
			$this->hinh = $Avatar;
		
		}
	}

//2. khởi tạo mảng kháo học quốc tế
$mangthongtin_chitietxe = array();

//3. Truy vấn dữ liệu và thêm phần tử vào mảng Thêm phần tử vào mảng
$q = "select TenLoaiXe, DiaChiGiaoXe, SoKMToiDa, PhiKMVuot, Xe.Gia, Avatar  from Xe join LoaiXe as lx on Xe.MaLoaiXe = lx.MaLoaiXe where Xe.MaXe = '$ma_xe'";


$stmt = sqlsrv_query( $conn, $q);
if( $stmt)
{
	while ($row = sqlsrv_fetch_array( $stmt, SQLSRV_FETCH_ASSOC)) {
	 array_push($mangthongtin_chitietxe, new thongtin_chitietxe(
	 	$row['TenLoaiXe'],
	 	$row['DiaChiGiaoXe'],
	 	$row['SoKMToiDa'],
	 	$row['PhiKMVuot'],
	 	$row['Gia'],
	 	$row['Avatar']
	 ));
	}
	
}else{
	echo " - Đang bị lõi truy vấn !!!!!!!!!";
	die( print_r( sqlsrv_errors(), true));
}

//4. chuyển định dạng của mảng sinhvien sang Json
// echo json_encode($mangTS,JSON_PRETTY_PRINT);
echo json_encode($mangthongtin_chitietxe);
?>