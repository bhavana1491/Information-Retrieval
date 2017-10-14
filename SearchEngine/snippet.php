<?php
include 'simple_html_dom.php';

if(isset($_GET['id'])){
	$q_t = preg_split("/\s+/", $_GET['q']);
	$file = file_get_html("./CNNDownloadData/".$_GET['id']);
	$body = $file->plaintext;
	$spl_lines = explode(".",$body);
	$snip="";
	while (($stmt = next($spl_lines)) !== NULL) {
		if (strpos(strtolower($stmt), strtolower($_GET['q'])) !== false) {
		           $snip.=$stmt;
                    break;
                }

    }
}

echo $snip;
?>