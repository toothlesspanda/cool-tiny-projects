<?php
	include("connect.php");
	//The page URL
	set_time_limit(9999);

	$sql_select = "SELECT name,czz_link FROM country ";				
	$result = $conn->query($sql_select) or die("ERRO!1");
	while($row = $result->fetch_assoc()){
		$url = 'http://www.zerozero.pt/'.$row['czz_link'];
			echo "<br><br>".$row['name']."<br><br><br><br><br><br>";

			
		
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
			
			$team = $xpath->query('//div[@id="team_squad"]/table/tbody/tr/td[2]/div/div[2]/a');

			
			foreach($team as $r){
				$url_player = $r->getAttribute('href');
				$name_player= $r->textContent;
				//preg_replace('~\n~', "", subject)
				//echo "!".$name_player."!";

				if($row['name'] === "Portugal"){
					echo $name_player."<br>";
				}
				$sql_insert1 = 'UPDATE person SET zz_id = "'.$url_player.'" WHERE name LIKE "'.$name_player.'"';
				echo $sql_insert1;		
				$result2 = $conn->query($sql_insert1) or die("ERRO!2  ---- ".$sql_insert1);
			}
	

			sleep(30);	
		}
	//cURL init, settings and execution


?>

