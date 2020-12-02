<?php
// http://localhost/Thang/get_hinhslider.php
require_once("connect.php");
connectToServer($conn);
// $ma_xe=2;
$ma_xe=$_POST['ma_xe'];
//1. tạo class đối tượng 
	class danhsach_hinhslider
	{
		function danhsach_hinhslider( $URL)
		{
			$this->hinh = $URL;
		}
	}

//2. khởi tạo mảng kháo học quốc tế
$mangdanhsach_hinhslider = array();

//3. Truy vấn dữ liệu và thêm phần tử vào mảng Thêm phần tử vào mảng
$q = "select URL from HinhAnh where MaXe ='$ma_xe'
";
$stmt = sqlsrv_query( $conn, $q);
if( $stmt)
{
	while ($row = sqlsrv_fetch_array( $stmt, SQLSRV_FETCH_ASSOC)) {
	 array_push($mangdanhsach_hinhslider, new danhsach_hinhslider(
	 	$row['URL']
	 ));
	}
	
}else{
	echo " - Đang bị lõi truy vấn !!!!!!!!!";
	die( print_r( sqlsrv_errors(), true));
}

//4. chuyển định dạng của mảng sinhvien sang Json
// echo json_encode($mangTS,JSON_PRETTY_PRINT);
echo json_encode($mangdanhsach_hinhslider);
?>