
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DomParserPractice {

    public static void child(NodeList nodeList, StringBuilder stringBuilder) {


        for (int i=0; i<nodeList.getLength();i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType()==Node.TEXT_NODE)
                stringBuilder.append(node.getTextContent());

            if (node.getNodeType()== Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                if (eElement.getTagName().equalsIgnoreCase("ABBR")) {
                    stringBuilder.append("<abbr title=\"");
                //    stringBuilder.append(HashMapParser.getAbbrMap().get(eElement.getAttribute("REFID")) + "\">");
                    stringBuilder.append((eElement.getTextContent()) + "</abbr>");
                }
                if (eElement.getTagName().equalsIgnoreCase("XREF")) {
                    stringBuilder.append("<a href=\"https://www.worldwhoswho.com/views/entry.html?id=");
                    stringBuilder.append((eElement.getAttribute("SLTARGETID").toLowerCase())+"\">");
                    stringBuilder.append(eElement.getTextContent());
                    stringBuilder.append("</a>");
                }

                if (node.hasChildNodes() && eElement.getTagName()!="ABBR" && eElement.getTagName()!="XREF") {
                    // loop again if has child nodes
                    child(node.getChildNodes(), stringBuilder);
                }
            }
        }

    }

    public static void main(String[] args) {

        try {
            File inputFile = new File("C:\\Users\\lenovo\\IdeaProjects\\x_json\\Dom_practice\\src\\main\\resources\\demo.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            //  System.out.println("Root element :" + doc.getDocumentElement().getNodeName());


            NodeList nodelist = doc.getElementsByTagName("entry");
            Node node = nodelist.item(0);
            StringBuilder stringBuilder = new StringBuilder();
            Element element = (Element) node;
            stringBuilder.append("<p>");
            child(element.getChildNodes(),stringBuilder);
            stringBuilder.append("</p>");
            System.out.println(stringBuilder.toString().trim());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
