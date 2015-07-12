package service;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
 
class Client {
 
    public static void main(String args[]) throws Exception {
        URL url = new URL("http://127.0.0.1:9876/service?wsdl");
        QName qname = new QName("http://service/","BaseServService");//seg parametro e o {nome da classe+Service} 
        Service ws = Service.create(url, qname);
        IBaseService	 calc = ws.getPort(IBaseService.class);
 
        System.out.println("Soma (5+1): " + calc.getEntregasNaoConcluidas());
 
    }
}


