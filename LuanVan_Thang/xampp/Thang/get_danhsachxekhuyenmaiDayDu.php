<?php
// http://localhost/Thang/get_danhsachxekhuyenmai.php
require_once("connect.php");
connectToServer($conn);

//1. tạo class đối tượng 
	class DanhSach_xekm
	{
		function DanhSach_xekm( $MaXe, $Avatar, $Gia, $TenLoaiXe,$PhanTramGiam,$MaHangSX )
		{
			$this->ma_xe = $MaXe;
			$this->img = $Avatar;
			$this->gia = $Gia;
			$this->loaixe = $TenLoaiXe;
			$this->phantram_km = $PhanTramGiam;
			$this->ma_sx = $MaHangSX;
		}
	}

//2. khởi tạo mảng kháo học quốc tế
$mangDanhSach_xekm = array();

//3. Truy vấn dữ liệu và thêm phần tử vào mảng Thêm phần tử vào mảng
$q = "select MaXe, Avatar, Xe.Gia, TenLoaiXe, PhanTramGiam, MaHangSX from Xe left join GiaKhuyenMai as km on Xe.MaGiaKM = km.MaGiaKM, LoaiXe as lx where Xe.MaLoaiXe = lx.MaLoaiXe and Xe.MaGiaKM is not null and TinhTrang is null";
$stmt = sqlsrv_query( $conn, $q);
if( $stmt)
{
	while ($row = sqlsrv_fetch_array( $stmt, SQLSRV_FETCH_ASSOC)) {
	 array_push($mangDanhSach_xekm, new DanhSach_xekm(
	 	$row['MaXe'],
	 	$row['Avatar'],
	 	$row['Gia'],
	 	$row['TenLoaiXe'],
	 	$row['PhanTramGiam'],
	 	$row['MaHangSX']
	));
	}
	
}else{
	echo " - Đang bị lõi truy vấn !!!!!!!!!";
	die( print_r( sqlsrv_errors(), true));
}

//4. chuyển định dạng của mảng sinhvien sang Json
// echo json_encode($mangTS,JSON_PRETTY_PRINT);
echo json_encode($mangDanhSach_xekm);
?>