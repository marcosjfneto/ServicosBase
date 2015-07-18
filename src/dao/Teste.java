package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Teste {

	public static void main(String[] args) {
		Date data = Calendar.getInstance().getTime();
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(data.toString());
		System.out.println(formatador.format(data));
		
//		String dataStr = "1/1/15"; //com hoje -1
//		String dataStrTeste = "1/11/14"; //com 1
//		String dataStrIgual = "1/1/15"; //com 0
		
		String data0 = "18/07/15"; //
		String data1 = "18/08/15"; //com hoje -1
		String data2 = "17/08/15"; //com 1
		String dataStrIgual = "1/1/15"; //com 0
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date d0 = format.parse(data0);
			Date d1 = format.parse(data1);
			Date d2 = format.parse(data2);
//			Date dataHoj = format.parse(dataHoje);
			diferenca(d0, d1);
			diferenca(d1, d2);
//			System.out.println("data: "+d0.toGMTString() +"\n"+"hoje: "+data.toGMTString()+"\n" + data.compareTo(dataHoj));
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
	}
	
	public static boolean diferenca (Date d1, Date d2){
		
System.out.println((d2.getTime() - d1.getTime())/(24*60*60*1000));
long miliSegundosDia = 24*60*60*1000;
return ( (d2.getTime() - d1.getTime())/(miliSegundosDia) <= 30 );
		//		return d1.getDay() - 
	}
}
