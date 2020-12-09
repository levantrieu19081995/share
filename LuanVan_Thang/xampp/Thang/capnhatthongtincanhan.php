<?php
// http://localhost/Thang/capnhatthongtincanhan.php
	require_once("connect.php");

	$hoten = $_POST["hoten"];
	$giaypheplx = $_POST["giaypheplx"];
	$diachi = $_POST["diachi"];
	$cmnd = $_POST["cmnd"];
	$makh = $_POST["makh"];

	// $hoten = "t";
	// $giaypheplx = "2";
	// $diachi = "2";
	// $cmnd = "123";
	// $makh = "18";

	class CapNhatThongTin
	{
		function CapNhatThongTin($ketqua)
		{
			$this->ketqua = $ketqua;
		}
	}
	$mangCapNhatThongTin = array();

	$q = "update KhachHang set TenKH=N'$hoten', GPLX=N'$giaypheplx', DiaChi=N'$diachi', CMND=N'$cmnd' where MaKH ='$makh'";
	$ex = sqlsrv_query($conn , $q);

	if($ex){
			array_push($mangCapNhatThongTin, new CapNhatThongTin( "thanhcong"));
	}else{
			array_push($mangCapNhatThongTin, new CapNhatThongTin( "thatbai"));
		}

	header('Content-Type: application/json');
	echo json_encode($mangCapNhatThongTin, JSON_PRETTY_PRINT);

?>

