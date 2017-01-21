<?php

	for($i=1;$i<9999999;$i++){
			//The page URL
		$url = 'http://www.zerozero.pt/equipa.php?id='.$i;

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
		
		$team = $xpath->query('//div[@id="page_header"]//h1/span');

		foreach ($team as $t) {
		 	
		 	$nome_equipa = explode(" ",$t->textContent);
		 	

		 	if(count($nome_equipa) == 1 || $nome_equipa[1] == 'B'){
		 		echo "ID:".$i. " - ".$t->textContent;
		 	}
		} 

	}

?>