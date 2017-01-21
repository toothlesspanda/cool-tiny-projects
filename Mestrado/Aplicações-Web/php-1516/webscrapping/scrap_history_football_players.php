<?php

	//The page URL
	$url = 'http://www.zerozero.pt/player.php?id=298';
	//cURL init, settings and execution
	$curl = curl_init($url);
	curl_setopt($curl,CURLOPT_USERAGENT,'Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.13) Gecko/20080311 Firefox/2.0.0.13');
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
	$html = curl_exec($curl);
	curl_close($curl);

	//echo $html;

	//Creating a DOMDocument
	$doc = new DOMDocument();

	//Avoid the presentation of warnings due to ill-created HTML results
	libxml_use_internal_errors(true);
	$doc->loadHTML($html);
	libxml_clear_errors();

	//An alternative way to apply XPath to a result, in this case filtering a DOM tree
	$xpath = new DOMXpath($doc);

	//To get the path, we inspected the source code of the page
	$boxs = $xpath->query('//div[@class="factsheet"]'); 
	
	foreach ($boxs as $titulo) {

		//opter nome e numero do jogador
		$string = $titulo->firstChild->firstChild->textContent; 
		list ($number, $name) = explode( ".", $string );
		echo "Numero do Jogador: " . $number . "<br>";
		echo "Nome do Jogador: " . $name . "<br>" ;

		//Opter Pais de Origem
		$country = $titulo->lastChild->firstChild->firstChild->lastChild->textContent;
		echo "Pais de Origem: " . $country . "<br>";

		//Opter Idade e posição
		$string = $titulo->lastChild->lastChild->textContent;
		list ($null,$age, $pos) = explode( "m", $string );
		echo "Idade: " . $age . "<br>";
		echo "Posição: " . $pos . "<br>";

	}



	//Opter clube Actual
	$club = $xpath->query('//*[@id="page_rightbar"]/div[1]/div[2]/div[2]/div/div[2]/a');
	foreach ($club as $elem) {
		echo "Clube Actual: ".$elem->textContent. "<br>" ;
	}




	//Opter Clubes anteriores
	echo "<h1>Historico de carreira</h1>";
	$biblioBox = $xpath->query('//*[@id="coach_career"]/table[1]/tbody/tr');
	$lastSesson = "";
	foreach ($biblioBox as $elem) {
		$tdNumber = 0;
		foreach ($elem->childNodes as $col){

			//Imprimir Epoca caso esteja no td
			if($tdNumber == 1 && strcmp($col->textContent, "") != 0 ){
				$lastSesson = $col->textContent;
				echo "EPOCA-> ".$lastSesson;
			}
			//Imprimir Epoca caso faça parte do td anterior
			else if ( $tdNumber == 1 && strcmp($col->textContent, "") == 0 ){
				echo "EPOCA-> ".$lastSesson;
			}
			//Imprime clube 
			else if ($tdNumber == 2){
				echo " Clube-> ".$col->textContent;
			}	
			$tdNumber = $tdNumber + 1;	
		}
		echo "<br>";
	}
	

	//opter Titulos e Premios 
	$titlesBox= $xpath->query('//*[@id="coach_titles"]');
	foreach ($titlesBox as $container) {
		foreach ($container->childNodes as $innerbox) {
			foreach ($innerbox->childNodes as $divs) {
				if(strcmp ( $divs->getAttribute('class'), "title") == 0){
					echo "<br><h1>".$divs->textContent."</h1>";
				}
				else if (strcmp ( $divs->getAttribute('class'), "trophy_line") == 0 ||
							strcmp ( $divs->getAttribute('class'), "awards") == 0  ){
					foreach ($divs->childNodes as $trophy) {
						echo $trophy->firstChild->textContent.": ";
						echo $trophy->lastChild->textContent."<br>";
					}
				}		
			}
			
		}
	}
	echo "<br> FIM !!!!";

?>