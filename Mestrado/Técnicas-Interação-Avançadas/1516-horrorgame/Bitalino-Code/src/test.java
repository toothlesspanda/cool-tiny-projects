import java.util.Vector;

import javax.bluetooth.RemoteDevice;

import BITalino.BITalino;
import BITalino.Frame;
import BITalino.SensorDataConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;



public class test {
	
	public static Frame[] dataAcquired;
	private static final int ACC_MIN = 185;
	private static final int ACC_MAX = 275;
	//private static SocketIO socket = null;
	//private final static String URL = "http://localhost";
		
	public static void main(String[] args) throws Throwable {
		
		SensorDataConverter sc = new SensorDataConverter();
		int[] aChannels = {0,1,2,3,4};
		String samples = "1000";
		String s = "";
		
		while(true){
			System.out.println("What Sensor do You want read? (1-5)");
			System.out.println("1) Accelerometer");
			System.out.println("2) EMG");
			System.out.println("3) ECG");
			System.out.println("4) EDA");
			System.out.println("5) LUX");
			System.out.println("6) EMG Example");
			try{
			    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			     s = bufferRead.readLine();
			     switch(Integer.parseInt(s))
					{
						case 1: aChannels = new int[] {0,1,2};break;
						case 2: aChannels = new int[] {3};break; 
						case 3: aChannels = new int[] {4};break;
						case 4: aChannels = new int[] {4};break;
						case 5: aChannels = new int[] {4};break;
						case 6: aChannels = new int[] {4};break;
						default: aChannels = new int[] {0,1,2,3,4};break;
					}
			     if(s.compareTo("6") != 0)
			     {
			    	 System.out.println("How many samples to read? ");
			     	samples = bufferRead.readLine();
			     } else
			    	 samples = "500";
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			BITalino device = new BITalino();
			
			// find devices
			Vector<RemoteDevice> devices = device.findDevices();
			System.out.println(devices);
			
			// connect to BITalino device
			String macAddress = "98:D3:31:70:3D:D9";
			int SamplingRate = 1000;
			device.open(macAddress,SamplingRate);
		
			//get BITalino version
			String versionName = device.version();
			
			// start acquisition on analog channels 0 and 4
			device.start(aChannels);
			
			if(s.compareTo("3") == 0) {
				double max = 0.0;
				double meanValues = 0.0;
				double meanBPM = 0.0;
				double mv = 0.0;
				double sumValues = 0;
				int sumBPM;
				int count = 0;
				int sumNormalBeat = 0;
				int meanNormalBeat = 0;
				int pontos = 0;
				double heartBeat = 0;
				
				//Enquanto a pessoa está a jogar, os batimentos estao a ser registados 
				while(true){
					sumNormalBeat = 0;
					meanValues = 0;
					meanBPM = 0;
					sumValues =0;
					sumBPM = 0;
					pontos = 0;
					dataAcquired = device.read(Integer.parseInt(samples));
					PrintWriter writer = new PrintWriter("valoresbpm.txt", "UTF-8");
						
					if(count <= 15){
						//os primeiros 15 valores registados servem para saber os bpms normais
						for(Frame frame : dataAcquired) {
							sumNormalBeat += Math.abs(frame.analog[0]);
						}
						meanNormalBeat = ( meanNormalBeat + sumNormalBeat/Integer.parseInt(samples))/2;
						System.out.println(meanNormalBeat);
						count++;
					} else {
						for(Frame frame : dataAcquired) {
								sumValues += Math.abs(SensorDataConverter.scaleECG(4, frame.analog[0]));
								sumBPM += Math.abs(frame.analog[0]);
								System.out.println("raw data ECG: " + frame.analog[0]);
						}
						meanValues = sumValues/Integer.parseInt(samples); 
						meanBPM = sumBPM/Integer.parseInt(samples); // valor bpm médio obtido
						System.out.println("MediaBPM: "+ meanBPM);
						System.out.println("BPMNormal: " + meanNormalBeat);
						//enviar pontos se a media dos bpm for maior(+20) ou menor(-10) que o normal 
						pontos = 1;
						if(meanBPM > (meanNormalBeat+20) || meanBPM < (meanNormalBeat-20)){
							System.out.println("SUSTO!!!!!");
							pontos = 30;		//calcular pontos
							
							if(meanBPM > (meanNormalBeat+40)){
								//grande susto -> bonus de pontos
								System.out.println("SUSTO!!!!!!!!!!!!!!!!!!!!!!!!!!");
								pontos += 70;
								//enviar pontos acumulados com os de cima
							}	
						}
					}
					
					//so enviar valores acima de 50bpm
					if(meanBPM > 45){
						heartBeat = meanBPM;
						
					}
					
					writer.println("pontos:" + pontos);
					writer.println("heartBeat:" + heartBeat);
					writer.close();
				}
			}
		// stop acquisition
		//device.stop();
		
		//close bluetooth connection
		//device.close();
		
		}
	
	}
	
}