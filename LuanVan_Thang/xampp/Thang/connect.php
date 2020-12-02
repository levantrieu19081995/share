<?php
// http://localhost/Thang/get_thongtinxechothue.php
$serverName = "DESKTOP-DEG6M2T\SQLEXPRESS"; 
$connection = array( "Database"=>"QLTHUEXE", "CharacterSet" => "UTF-8");
$conn = sqlsrv_connect( $serverName, $connection);

// hàm kết nối tới cơ sở dữ liệu
		function connectToServer($conn){
				if( $conn ) {
				     // echo "Connect Thành công cớ sở lữ liệu của thằng thắng";
				}else{
				     echo "Connect Thất bại!";
				     die( print_r( sqlsrv_errors(), true));
				}
		}

		function closeConnectServer($conn){
			sqlsrv_close( $conn );
		}

// kiểm tra connect thành công không
// connectToServer($conn);

?>


