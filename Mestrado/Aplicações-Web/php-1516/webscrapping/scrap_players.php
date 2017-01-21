<?php
	include 'connect.php';
	$sql = "SELECT cyid,name,czz_link FROM `country`";
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {
	    while($row = $result->fetch_assoc()) {
	       	$url = "http://www.zerozero.pt/".$row["czz_link"];
	       	//$url = "http://www.zerozero.pt/equipa.php?id=826&edicao_id=91952";
	       	echo $url."<br>";

			$curl = curl_init($url);
			curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
			$html = curl_exec($curl);
			curl_close($curl);

			$doc = new DOMDocument();

			libxml_use_internal_errors(true);
			$doc->loadHTML($html);
			libxml_clear_errors();

			$xpath = new DOMXpath($doc);

			
			$xResult = $xpath->query('//*[@id="page_header"]/div[1]/div[2]/div/div[1]/div/div[1]/a/img');
			$lil_img = "http://www.zerozero.pt/".$xResult->item(0)->getAttribute('data-cfsrc');

			$xResult = $xpath->query('//*[@id="page_header"]/div[1]/div[1]/div/a/img');
			$big_img = "http://www.zerozero.pt/".$xResult->item(0)->getAttribute('data-cfsrc');

			$sql = 'UPDATE country SET big_img = "'.$big_img.'", lil_img = "'.$lil_img.'" WHERE cyid = '.$row['cyid'];
			if ($conn->query($sql) === TRUE) {
				echo "<img src='".$big_img."'><br>";
			} else {
				echo "Error: " . $sql . "<br>" . $conn->error;
			}



			$xResult = $xpath->query('//*[@id="team_squad"]/table/tbody/tr/td[2]/div/div//a');

			foreach ($xResult as $club) {
				$name = $club->textContent;
				$href = $club->getAttribute('href');
				echo $name.'----->';
				echo $href.'<br>';

				$select = 'SELECT * FROM person WHERE name LIKE "'.$name.'"';

				$result2 = $conn->query($select);
				if ($result2->num_rows < 1) {
					$sql = 'INSERT INTO person ( name,type, selection, zz_id) VALUES ("'.$name.'",2,'.$row['cyid'].',"'.$href.'")';
					echo $name."<br>";
					echo '$sql1-->'.$sql.'$sql2-->'.$sql2."<br>";
					if ($conn->query($sql) === TRUE) {
						    echo "New record created successfully";
					} else {
					    echo "Error: " . $sql . "<br>" . $conn->error;
					}
				}
			}

			$xResult = $xpath->query('//*[@id="team_staff"]/div/div[2]/div/div[2]/div/div[2]/a');


			if($xResult != null){
				$name = $xResult->item(0)->textContent;
				$href = $xResult->item(0)->getAttribute('href');
				echo $name ."->".$href;
				$select = 'SELECT * FROM person WHERE name LIKE "'.$name.'"';

				$result2 = $conn->query($select);
				if ($result2->num_rows < 1) {


					$sql = 'INSERT INTO person ( name, type, selection, zz_id) VALUES ( "'.$name.'",1,'.$row['cyid'].',"'.$href.'")';
					if ($conn->query($sql) === TRUE) {
							    echo "New record created successfully";
					} else {
						echo "Error: " . $sql . "<br>" . $conn->error;
					}
				}
			}

	       	sleep(30);
	       	//break;
	    }
	} else {
    	echo "0 results";
	}



?>