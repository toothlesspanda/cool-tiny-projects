<?php
	include("connect.php");
	//The page URL
	$url = 'http://pt.uefa.com/uefaeuro/index.html';

	//cURL init, settings and execution
	$curl = curl_init($url);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
	$html = curl_exec($curl);
	curl_close($curl);

	//Creating a DOMDocument
	$doc = new DOMDocument();

	//Avoid the presentation of warnings due to ill-created HTML results
	libxml_use_internal_errors(true);
	$doc->loadHTML($html);
	libxml_clear_errors();

	//An alternative way to apply XPath to a result, in this case filtering a DOM tree
	$xpath = new DOMXpath($doc);

	//To get the path, we inspected the source code of d page
	
	$groups_teams = $xpath->query('//div[@class="group-container"]//h6');
	foreach($groups_teams as $group){
		
		$teams_group = $xpath->query('//div[@class="group-container"]/h6[text()="'.$group->nodeValue.'"]/following-sibling::a[@class="group-item"]'); 
		
		// The value of the DOMElement is echoed.
		for ($i = 0; $i < $teams_group->length; $i++){ 
			$g = explode(" ", $group->nodeValue);

			$href = $teams_group->item($i);
    			$url = $href->getAttribute('href');

			$url_array = explode("/", $url);
			
			
			$id_equipa = explode("=", $url_array[4]);
			
			$array = explode(" ", $teams_group->item($i)->nodeValue);
			
			for($k = 40 ; $k<43; $k++){
				if($array[$k] != " "){
					$nome .= $array[$k]." ";
				}
				
			}

			print $nome;
			
		
		
			$sql_select1 = "SELECT * FROM country WHERE name LIKE '".$nome."'";				
			$result4 = $conn->query($sql_select1) or die("ERRO!1");
			
			if($result4->num_rows == 0){
				$sql_insert1 = "INSERT INTO country (name,groups,cyid_uefa) VALUES ('".$nome."','".$g[1]."',".$id_equipa[1].")";

							
				$result = $conn->query($sql_insert1) or die("ERRO!2");
			}else{
				echo "<br>já existe";
				}

		 	$nome = null;  
		}
		 
	}	
	



?>

