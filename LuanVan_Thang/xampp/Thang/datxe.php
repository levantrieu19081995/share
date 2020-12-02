<?php
// http://localhost/Thang/datxe.php
	require_once("connect.php");

	$ThueXeTuNgay = $_POST["ngaybd"];
	$ThueXeDenNgay = $_POST["ngaykt"];
	$MaKH=$_POST["makh"];
	$MaXe=$_POST["maxe"];
	$NoiDung=$_POST["noidung"];
	$GiaHD=$_POST["gia"];

	// $ThueXeTuNgay = "2020/01/01";
	// $ThueXeDenNgay = "2020/01/01";
	// $MaKH="1";
	// $MaXe="14";
	// $NoiDung="5";
	// $GiaHD="7";

	class DatXe
	{
		function DatXe($ketquatruyvan)
		{
			$this->Ketquatruyvan = $ketquatruyvan;
		}
	}

	$mangDatXe = array();
	$q = "insert HopDong (ThueXeTuNgay, ThueXeDenNgay, MaKH, MaXe, NoiDung, GiaHD) values ('$ThueXeTuNgay','$ThueXeDenNgay','$MaKH','$MaXe',N'$NoiDung','$GiaHD')";
	$ex = sqlsrv_query($conn , $q);

	if($ex){
		$q2 = "update Xe set TinhTrang = N'Đang thuê' where MaXe = '$MaXe'";
		$ex = sqlsrv_query($conn , $q2);
		array_push($mangDatXe, new DatXe("thanhcong") );
			
	}else{
		array_push($mangDatXe, new DatXe(
	 			"thatbai"));
	}
		
	header('Content-Type: application/json');
	echo json_encode($mangDatXe, JSON_PRETTY_PRINT);

?>

