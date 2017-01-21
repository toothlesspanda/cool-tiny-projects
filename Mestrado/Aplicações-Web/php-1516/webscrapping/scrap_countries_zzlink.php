<?php
	include 'connect.php';
	//ini_set('max_execution_time', 99999);
	$url = 'http://www.zerozero.pt/edition_teams.php?id=66233';

	#$agent= 'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.2 (KHTML, like Gecko) Chrome/22.0.1216.0 Safari/537.2';


	
	$curl = curl_init($url);
	#curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);
	#curl_setopt($curl, CURLOPT_VERBOSE, true);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
	#curl_setopt($curl, CURLOPT_USERAGENT, $agent);
	#curl_setopt($curl, CURLOPT_URL,$url);
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

	//To get the path, we inspected the source code of d page
	$array_uriIDs = array();
	$array_numDiri = array();
	
	$ids_paises = $xpath->query('//div[@id="page_content"]//div[@id="page_main"]/table//table//div[@class="text"]/a');
	
	foreach($ids_paises as $id){
		$country_name = $id->textContent;	
		$country_link = str_replace("66233", "91952", $id->getAttribute('href'));

		$sql_insert1 = "UPDATE country SET czz_link = '".$country_link."' WHERE name LIKE '".$country_name."'";
		//echo $sql_insert1;		
		$result = $conn->query($sql_insert1) or die("ERRO!2  ---- ".$sql_insert1);

	}
	#percorre e encontra os paises nos quais existem registos de dirigentes
	/*for($id =0 ; $id <$ids_paises->length;$id++) {
		if(strpos($ids_paises->item($id)->getAttribute('href'),"nac") !== False){

			preg_match('^\((.*?)\)^', $ids_paises->item($id)->textContent, $ouput);
	
			array_push($array_numDiri,$ouput[1]);
			array_push($array_uriIDs,$ids_paises->item($id)->getAttribute('href'));
			
			//print_r($array_uriIDs);
		}
	}*/


	//print_r( $array_numDiri);
	//print_r($array_uriIDs);
	//echo count($array_uriIDs);


	$result = $xpath->query('//*[@id="page_main"]/table/tbody/tr/td/div/table/tbody/tr[1]/td[1]/div/div[1]/a/img');

	echo $result->length;
	foreach($result as $id){
		echo $id->textContent."%%%%";
	}
		
	#nova query, novo url para ir buscar os dirigentes




	function getRandomUserAgent(){
	

			$user_agents = array('Mozilla/5.0 (compatible; U; ABrowse 0.6; Syllable) AppleWebKit/420+ (KHTML, like Gecko)',
						'Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; Acoo Browser 1.98.744; .NET CLR 3.5.30729)',
						'Mozilla/4.0 (compatible; MSIE 8.0; AOL 9.0; AOLBuild 4327.5201; Windows NT 6.0; WOW64; Trident/4.0; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.30729; .NET CLR 3.5.30729)',
						'Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.2 Safari/537.36',
						'Mozilla/5.0 (X11; U; OpenBSD amd64; en; rv:1.8.1.6) Gecko/20070817 Epiphany/2.18 Firefox/2.0.0.6',
						'Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20120403211507 Firefox/12.0',
						'Mozilla/5.0 (X11; U; SunOS sun4u; en-US; rv:1.8.1.12) Gecko/20080210 Firefox/2.0.0.12',
						'Opera/9.80 (Windows NT 6.1; U; es-ES) Presto/2.9.181 Version/12.00',
						'Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.7 (KHTML, like Gecko) RockMelt/0.8.36.128 Chrome/7.0.517.44 Safari/534.7',
						'Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)',
						'Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; .NET CLR 2.7.58687; SLCC2; Media Center PC 5.0; Zune 3.4; Tablet PC 3.6; InfoPath.3)',
						'Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Iron/6.0.475.1 Chrome/6.0.475.1 Safari/534',
						'Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.0.9) Gecko/2009042410 Firefox/3.0.9 Wyzo/3.0.3',
						'Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.0.9) Gecko/2009042410 Firefox/3.0.9 Wyzo/3.0.3',
						'Mozilla/5.0 (Windows; U; Windows NT 5.2; en-US; rv:1.9.0.9) Gecko/2009042410 Firefox/3.0.9 Wyzo/3.0.3',
						'Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.9) Gecko/2009042410 Firefox/3.0.9 Wyzo/3.0.3',
						'Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.0.9) Gecko/2009042318 Firefox/3.0.9 Wyzo/3.0.3',
						'Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.0.9) Gecko/2009042318 Firefox/3.0.9 Wyzo/3.0.3 GTB6',
						'Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.0; Trident/5.0; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.2; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET CLR 1.1.4322; AskTB5.6; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; Tablet PC 2.0; InfoPath.1; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.2; Tablet PC 2.0; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET CLR 1.1.4322; Tablet PC 2.0; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; InfoPath.3; .NET4.0C; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; InfoPath.3; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; GTB6.5; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; InfoPath.2; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C; .NET4.0E; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; InfoPath.2; .NET CLR 2.0.50727; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; GTB6.6; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; AskTbFXTV5/5.9.1.14019; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; InfoPath.2; .NET4.0C; .NET4.0E; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; (R1 1.6); .NET CLR 2.0.50727; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; SV1; TheWorld)',
						'Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; TheWorld)',
						'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)',
						'Googlebot/2.1 (+http://www.googlebot.com/bot.html)',
						'Googlebot/2.1 (+http://www.google.com/bot.html)');

		$UA_id = mt_rand(0,(count($user_agents)-1));
		return $user_agents[$UA_id];
	}

?>

