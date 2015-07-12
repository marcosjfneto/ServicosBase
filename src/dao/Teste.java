package dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Teste {

	public static void main(String[] args) {
		Date data = Calendar.getInstance().getTime();
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(data.toString());
		System.out.println(formatador.format(data));
		
	}
}
