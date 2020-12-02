<?php
// http://localhost/Thang/get_thongtinnguoidung.php

require_once("connect.php");
connectToServer($conn);

	$mstk = $_POST["mstk_ss"];
	// $mstk = 27;

	class ThongTin_nguoiDung
	{
		function ThongTin_nguoiDung ($MaKH, $TenKH, $DiaChi, $SDT, $Email, $CMND, $GPLX, $SoTaiKhoan, $Avatar, $MaLoaiKH )
		{
			$this->makh = $MaKH;
			$this->ten = $TenKH;
			$this->diachi = $DiaChi;
			$this->sdt = $SDT;
			$this->email = $Email;
			$this->cmnd = $CMND;
			$this->gplx = $GPLX;
			$this->stk = $SoTaiKhoan;
			$this->Hinh = $Avatar;
			$this->maloaikh = $MaLoaiKH;
		}
	}


	$mangThongTin_nguoiDung = array();
	$q = "select * from KhachHang where MaTK = '$mstk'";
	$ex = sqlsrv_query($conn , $q);

	if($ex){
		$params = array();
		$options =  array( "Scrollable" => SQLSRV_CURSOR_KEYSET );
		$stmt = sqlsrv_query( $conn, $q , $params, $options );
		$row_count = sqlsrv_num_rows( $stmt );
		if ($row_count>0) {
			while ($row = sqlsrv_fetch_array($stmt, SQLSRV_FETCH_ASSOC)) {
		 			array_push($mangThongTin_nguoiDung, new ThongTin_nguoiDung(
		 			$row['MaKH'],
		 			$row['TenKH'],
		 			$row['DiaChi'],
		 			$row['SDT'],
		 			$row['Email'],
		 			$row['CMND'],
		 			$row['GPLX'],
		 			$row['SoTaiKhoan'],
		 			$row['Avatar'],
		 			$row['MaLoaiKH'])
				);
				}
		}
	}else{
			echo "lõi server!!";
		}

	header('Content-Type: application/json');
	echo json_encode($mangThongTin_nguoiDung, JSON_PRETTY_PRINT);

?>