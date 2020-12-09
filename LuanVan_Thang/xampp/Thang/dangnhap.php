<?php
// http://localhost/Thang/dangnhap.php
	require_once("connect.php");

	$sdt_dn = $_POST["sodienthoai"];
	$mk_dn = $_POST["passss"];	
	// $sdt_dn = "123";
	// $mk_dn = "123";

	$sv_mssv="";
	class Dangnhap
	{
		function Dangnhap($ketquatruyvan,$MaTK)
		{
			$this->Ketquatruyvan = $ketquatruyvan;
			$this->ma_tk = $MaTK;
		}
	}

	$mangDangNhap = array();
	$q = "select * from TaiKhoan where TenDangNhap like '$sdt_dn' and MatKhau like '$mk_dn'";
	$ex = sqlsrv_query($conn , $q);

	if($ex){
		$params = array();
		$options =  array( "Scrollable" => SQLSRV_CURSOR_KEYSET );
		$stmt = sqlsrv_query( $conn, $q , $params, $options );
		$row_count = sqlsrv_num_rows( $stmt );
	if ($row_count>0) {
		$dangnhap= sqlsrv_query($conn, $q);
		while ($rows = sqlsrv_fetch_array($stmt, SQLSRV_FETCH_ASSOC)) {
	 			array_push($mangDangNhap, new Dangnhap(
	 			"thanhcong",
	 			$rows['MaTK'])
			);
			}
	}else{
		array_push($mangDangNhap, new Dangnhap(
	 			"thatbai",
				""));
	}
		
	}else{
			echo "lõi server!!";
		}

	header('Content-Type: application/json');
	echo json_encode($mangDangNhap, JSON_PRETTY_PRINT);

?>

