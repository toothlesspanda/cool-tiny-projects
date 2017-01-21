<?php
	//ini_set('max_execution_time', 99999);
	$url = 'http://www.zerozero.pt/dirigentes.php?conf_id=1';

	#$agent= 'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.2 (KHTML, like Gecko) Chrome/22.0.1216.0 Safari/537.2';


	
	$curl = curl_init($url);
	#curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);
	#curl_setopt($curl, CURLOPT_VERBOSE, true);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
	#curl_setopt($curl, CURLOPT_USERAGENT, $agent);
	#curl_setopt($curl, CURLOPT_URL,$url);
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
	$array_uriIDs = array();
	$array_numDiri = array();
	$ids_paises = $xpath->query('//div[@id="page_main"]/table//table//div[@class="text"]/a/following-sibling::a');
	

	#percorre e encontra os paises nos quais existem registos de dirigentes
	for($id =0 ; $id <$ids_paises->length;$id++) {
		if(strpos($ids_paises->item($id)->getAttribute('href'),"nac") !== False){

			preg_match('^\((.*?)\)^', $ids_paises->item($id)->textContent, $ouput);
	
			array_push($array_numDiri,$ouput[1]);
			array_push($array_uriIDs,$ids_paises->item($id)->getAttribute('href'));
			
			//print_r($array_uriIDs);
		}
	}


	//print_r( $array_numDiri);
	//print_r($array_uriIDs);
	echo count($array_uriIDs);
	#nova query, novo url para ir buscar os dirigentes
	//for($i = 0 ; $i < count($array_uriIDs);$i++){

		
		$url_c = 'http://www.zerozero.pt/'.$array_uriIDs[0];
		
		#$agent= 'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.2 (KHTML, like Gecko) Chrome/22.0.1216.0 Safari/537.2';


		
		$curl = curl_init($url_c);
		#curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);
		#curl_setopt($curl, CURLOPT_VERBOSE, true);
		curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
		#curl_setopt($curl, CURLOPT_USERAGENT, $agent);
		#curl_setopt($curl, CURLOPT_URL,$url_c);
		//cURL init, settings and execution
		$html1 = curl_exec($curl);
		curl_close($curl);

		//Creating a DOMDocument
		$doc2 = new DOMDocument();

		//Avoid the presentation of warnings due to ill-created HTML results
		libxml_use_internal_errors(true);
		$doc2->loadHTML($html1);
		libxml_clear_errors();

		//An alternative way to apply XPath to a result, in this case filtering a DOM tree
		$xpath1 = new DOMXpath($doc2);

		//To get the path, we inspected the source code of d page
		$tr_dirigentes = $xpath1->query('//div[@id="page_content"]//div[@id="page_main"]//table//tr/td');
		$i = 1;
		foreach ($tr_dirigentes as $tr) {
			echo $tr->textContent."</br>";
			$i++;
		}
	
		
	//}
?>

