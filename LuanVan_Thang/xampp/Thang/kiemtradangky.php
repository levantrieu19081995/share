<?php
// http://localhost/Thang/kiemtradangky.php
	require_once("connect.php");

	$tendangnhap = $_POST["tendangnhap"];
	// $tendangnhap = "0931113414";
	
	class KiemTraDangKy
	{
		function KiemTraDangKy($ketquatruyvan)
		{
			$this->Ketquatruyvan = $ketquatruyvan;
		
		}
	}

	$mangKiemTraDangKy = array();
	$q = "select * from TaiKhoan where TenDangNhap = '$tendangnhap'";
	$ex = sqlsrv_query($conn , $q);

	if($ex){
		$params = array();
		$options =  array( "Scrollable" => SQLSRV_CURSOR_KEYSET );
		$stmt = sqlsrv_query( $conn, $q , $params, $options );
		$row_count = sqlsrv_num_rows( $stmt );
	if ($row_count>0) {
		array_push($mangKiemTraDangKy, new KiemTraDangKy(
	 			"tendangky_datontai"));
	}else{
		array_push($mangKiemTraDangKy, new KiemTraDangKy(
	 			"cothedangky"));
	}
		
	}else{
			echo "lõi server!!";
		}

	header('Content-Type: application/json');
	echo json_encode($mangKiemTraDangKy, JSON_PRETTY_PRINT);

?>
