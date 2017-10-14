<?php
ini_set('memory_limit','9000M');
include 'SpellCorrector.php';

$q_terms  = explode(" ",$_GET['query']);

$Str="";
foreach($q_terms as $v){
    $words = SpellCorrector::correct($v);
	$Str.=" ".$words;
}
header("Access-Control-Allow-Origin: *");
echo $Str;
//it will output *october*
?>