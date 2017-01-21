<?php
	include 'connect.php';
	//The page URL
	set_time_limit(99999);
	
	$sql_select = "SELECT zz_id FROM person WHERE type = 2 AND selection = 22 AND age = 0";
	$result = $conn->query($sql_select) or die("ERRO!1  ---- ".$sql_select);

	while($row = $result->fetch_assoc()){
				$id_url = null; #player
			    $fullname = null;  #person
			    $bday = null;   #person
			    $blocal = null; #person
			    $duplanacional = null; #person
			    $nacional = null;  #person
			    $natural = null; #person
			    $pe = null; #player
			    $altura = null; #person
			    $peso = null;   #person
			    $parentescos = null;
			    $pos = array();
			    $posicao = null;
			    $agente = null;
			    $social_accounts = null;
			    $img = null;
			    //echo $row['zz_id'];
				$url = 'http://www.zerozero.pt/'.$row['zz_id'];   # player.php?id=39983&edicao_id=91952'; player.php?id=68587&edicao_id=91952'; 
				echo $url;
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
				
				$team = $xpath->query('//div[@id="page_rightbar"]//a[contains(@href,"equipa.php")]'); 

				
				$href = $team->item(0);
				$url = $href->getAttribute('href');
				$id = explode("=", $url);

				$id_url = $id[1];

			    $pessoais_total = $xpath->query('//div[@id="entity_bio"]//div[contains(@class,"bio")]');

			    //*[@id="entity_bio"]/div[2]/text()
			    //*[@id="entity_bio"]/div[2]/text()

			 	foreach($pessoais_total as $div){
			 			if(strpos($div->textContent, "Nome") !== False){
			 				$fullname = str_replace("Nome", "", $div->textContent); 			
			 			}
			 			if(strpos($div->textContent, "Nascimento") !== False){
			 				$b = str_replace("Nascimento", "", $div->textContent);
			 				$bdaylocal = str_replace("País de", "", $b);
			 				$bdaylocal = explode(" ",$bdaylocal);
			 			
			 				$blocal = $bdaylocal[2];
			 				$bb = explode("(",$bdaylocal[0]);
			 				$bday = $bb[0];
			 				
			 			}

			 			if(strpos($div->textContent, "Nacionalidade") !== False){
			 				$n = str_replace("Nacionalidade", "", $div->textContent);

			 				if(strpos($div->textContent, "Dupla") !== False){
			 					$dn = str_replace("Dupla", "", $n);
			 					$nss = explode(" ",$dn);
			 					$nacional = $nss[0];
			 					$duplanacional = $nss[1];
			 				}else{
			 					$nacional = $n;
			 					$duplanacional = null;
			 				}
			 				
			 			}

			 			 if(strpos($div->textContent, "Internacionalizações") !== False){
			 				$inter = str_replace("Internacionalizações A", "", $div->textContent);
			 			
			 			}

			 			 if(strpos($div->textContent, "Naturalidade") !== False){
			 				$natural = str_replace("Naturalidade", "", $div->textContent);
			 			
			 			}

			 			if(strpos($div->textContent, "Pé preferencial") !== False){
			 				$pe = str_replace("Pé preferencial", "", $div->textContent);
			 			}

			 			if(strpos($div->textContent, "Altura") !== False){
			 				$altura = str_replace("Altura", "", $div->textContent);
			 			}
			 			
			 			 if(strpos($div->textContent, "Peso") !== False){
			 				$peso = str_replace("Peso", "", $div->textContent);
			 			}

			 			if(strpos($div->textContent, "Parentescos") !== False){
			 				$parent = str_replace("Parentescos", "", $div->textContent);
			 				$parents = explode(",",$parent);

			 				$grau_parent = null;
			 				$nome_parent = null;
			 				$array_parents = array();
			 				print_r($parents);
			 				$num_id = 1;
			 				foreach ($parents as $par) {
			 					//echo $par.'<br>';
			 					
			 					$pares = explode(" de ",$par);
			 					
			 					if(array_key_exists($pares[0],$array_parents) == True){
			 						$array_parents[$pares[0]."_".$num_id] = $pares[1];
			 						$num_id++;
			 					}else{
			 						$array_parents[$pares[0]] = $pares[1];
			 					}
			 				}

			 				//obtem os a que contem hrefs dos parentescos
			 				$pare_url = $xpath->query('//div[@id="entity_bio"]//div[contains(@class,"bio")]//a[contains(@href,"person.php")]');
			 			

			 				/*for($p = 0; $p< $pare_url->length; $p++) {		
			 					
		 					
		 						$url1 = "http://zerozero.pt".$pare_url->item($p)->getAttribute('href');
		 						
		 						$ch = curl_init($url1);
								curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
								curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
								$html = curl_exec($ch);
								//echo curl_getinfo($ch, CURLINFO_EFFECTIVE_URL);

		 						$last_url = curl_getinfo($ch, CURLINFO_EFFECTIVE_URL);
		 						//print_r($last_url);
		 						if(strpos($last_url, "player.php") !== False || strpos($last_url, "coach.php") !== False || strpos($last_url, "dirigente.php") !== False){
		 								$type=0;
		 								if(strpos($last_url, "player.php")){
		 									$type=2;
		 								}
		 								if(strpos($last_url, "coach.php")){
		 									$type=1;
		 								}
		 								if(strpos($last_url, "dirigente.php")){
		 									$type=3;
		 								}

		 								$keys  = array_keys($array_parents);
		 								$parts_url = explode("/",$last_url);
		 								
		 								
		 								//guardar grau de parentesco e o URL respetivo
		 								//guardar para a DB , tabela person (só name e zz_id)
		 								if(count($array_parents) == 1){
		 									$parentescos = $keys[$p]."-->".$parts_url[3];
		 									
		 								}else{
		 									
		 									$parentescos .= $keys[$p]."-->".$parts_url[3].",";
		 								}
		 		
	 									$sql_select1 = "SELECT * FROM person WHERE zz_id = '".$parts_url[3]."'";	
										$result1 = $conn->query($sql_select1) or die("ERRO!2  ---- ".$sql_select1);

										if($result1->num_rows == 0){
											$sql_insert2 = "INSERT INTO person (name,type,zz_id) VALUES ('".$array_parents[$keys[$p]]."',".$type.",'".$parts_url[3]."')";							
												$result2 = $conn->query($sql_insert2) or die("ERRO!3  ---- ".$sql_insert2);
										}

		 						}
		 						sleep(30);
			 					
			 				}*/
			 			}

			 			if(strpos($div->textContent, "Posição") !== False){
			 				$posis = str_replace("Posição", "", $div->textContent);
			 				$pos = explode("/",$posis);
			 			}

			 			if(strpos($div->textContent, "Situação") !== False){
			 				$inter = str_replace("Situação", "", $div->textContent);

			 			}

			 			if(strpos($div->textContent, "Agente") !== False){
			 				$ag = str_replace("Agente", "", $div->textContent);
			 				$ag = explode(" ",$ag);
			 				$agente = $ag[0]." ".$ag[1];
			 			}

			 			if(strpos($div->textContent, "Clube Atual") !== False){
			 				$inter = str_replace("Clube Atual", "", $div->textContent);
			 				
			 			}


			 			/*$search = "/[^<span>](.*)[^<\/span>]/";
			 			$coisas = str_replace($search, "", $div->textContent);
			 			echo $coisas.'</br>';*/
			    		
			    }

			    $social = $xpath->query('//div[@id="entity_bio"]//div/ul[@class="social"]/a');

			    foreach ($social as $s) {
			    	if($social->length == 1){
			    		$social_accounts = $s->getAttribute('href');
			    	}else{
			    		$social_accounts .= $s->getAttribute('href').",";
			    	}
			    	
			    	//array_push($social_accounts, $s->getAttribute('href'));
			    }

			    $imagem = $xpath->query('//*[@id="page_header"]//div[@class="logo"]/a/img');
				$img = "http://www.zerozero.pt/".$imagem->item(0)->getAttribute("data-cfsrc");
			   
			    echo $fullname.'<br>';  #person
			    echo $bday.'<br>';   #person
			    $datanasc = explode("-",$bday);
			    $ano = date("Y");
			    $mes = date("m");
			    $dia = date("d");
			    $idade = $ano - $datanasc[0];
			    if($mes < $datanasc[1] || ($mes == $datanasc[1] && $dia < $datanasc[2])){
			    	$idade-=1;
			    }

			    echo $blocal.'<br>';  #person
			    echo $duplanacional.'<br>'; #person
			    echo $nacional.'<br>';  #person
			    echo $natural.'<br>'; #person
			    echo $pe.'<br>'; #player
			    echo $altura.'<br>';  #person
			    echo $peso.'<br>';   #person
			    //print_r($pos);  #player
			    echo '<br>'; 
			    
			   	if($pos[0] === "Guarda Redes"){
			   		$posicao = $pos[0];
			   	}else{
			   		$po = explode(" ",$pos[0]);
	    			$posicao = $po[0];
			   	}
	    		
	    		if($posicao == 'Guarda Redes'){
	    			$posicao = "Goalkeeper";
	    		}else if($posicao == 'Avançado'){
	    			$posicao = "Forward";
	    		}else if($posicao == 'Médio'){
	    			$posicao = "Midfield";
	    		}else if($posicao == 'Defesa'){
	    			$posicao = "Defender";
	    		}
	    

			    echo $posicao.'<br>';			    
			    echo "EquipaAtual zerozero: ".$id_url.'</br>'; #player
			    echo $agente.'<br>'; #player
			    echo $social_accounts.'<br>'; #person
			    echo $img.'<br>';
			    echo $parentescos;
			    echo '<br><br><br>';

				$sql_insert1 = 'UPDATE person SET bday = "'.$bday.'" , age = "'.$idade.'" , fullName = "'.$fullname.'", height = "'.$altura.'", weight = "'.$peso.'" ,family = "'.$parentescos.'" , IMG = "'.$img.'", blocal = "'.$blocal.'", nacional = "'.$nacional.'" , duplaNacional = "'.$duplanacional.'", naturality = "'.$natural.'", position = "'.$posicao.'", agent = "'.$agente.'", pe= "'.$pe.'", social = "'.$social_accounts.'" WHERE zz_id = "'.$row['zz_id'].'"';
					
				$result2 = $conn->query($sql_insert1) or die("ERRO!4  ---- ".$sql_insert1);

				sleep(60);
				//break;

	}

?>

