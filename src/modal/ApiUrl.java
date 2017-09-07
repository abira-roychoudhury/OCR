package modal;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;

public class ApiUrl {
	public static String urlToReceiveAccessToken = "";
	public static String client_id = "";
	public static String client_secret = "";
	public static String grant_type = "";
	public static String username = "";
	public static String password = "";
	public static String urlToPushOCRResponse = "";
	public static String setEnviornment = "";
	
	public static void getURLs() {
		 try {

				File fXmlFile = new File("config.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();

				setEnviornment = doc.getElementsByTagName("enviornment").item(0).getTextContent();
				

				NodeList nList = doc.getElementsByTagName("url");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						
						if(eElement.getAttribute("enviornment").equals(setEnviornment)){
							System.out.println("URL Enviornment : " + eElement.getAttribute("enviornment"));
							
							urlToReceiveAccessToken = eElement.getElementsByTagName("urlToReceiveAccessToken").item(0).getTextContent()+"?";
							client_id = "client_id="+eElement.getElementsByTagName("client_id").item(0).getTextContent()+"&";
							client_secret = "client_secret="+eElement.getElementsByTagName("client_secret").item(0).getTextContent()+"&";
							grant_type = "grant_type="+eElement.getElementsByTagName("grant_type").item(0).getTextContent()+"&";
							username = "username="+eElement.getElementsByTagName("username").item(0).getTextContent()+"&";
							password = "password="+eElement.getElementsByTagName("password").item(0).getTextContent();
							urlToPushOCRResponse = eElement.getElementsByTagName("urlToPushOCRResponse").item(0).getTextContent();
						}

					}
				}
			 } catch (Exception e) {
				e.printStackTrace();
			}
	 }

}

