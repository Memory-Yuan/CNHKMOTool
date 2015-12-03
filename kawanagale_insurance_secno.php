<?php
$today = date('ymd');
$filename = "./log/insurance_secno.txt";
$content = "";
if(file_exists($filename)){
    $content = file_get_contents($filename);
    $arr = json_decode($content, true);
    if(strtotime($today) > strtotime($arr["date"])){
        $content = sprintf('{"no":-1,"date":"%s"}', $today);
    }
}else{
    $insFile = fopen($filename, "w");
    fclose($insFile);
    $content = sprintf('{"no":-1,"date":"%s"}', $today);
}

if(isset($_POST["insno"])){
    $arr = json_decode($content, true);
    $arr["no"] = $_POST["insno"];
    file_put_contents($filename, json_encode($arr));
}else{
    echo $content;
}
