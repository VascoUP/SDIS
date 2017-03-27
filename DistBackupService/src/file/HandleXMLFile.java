package file;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

import information.Chunk;

public class HandleXMLFile {
	
	private static final String xmlPath = "assets/data.xml";
	private static final String xmlGrammar = "metadata.dtd";

	public static Document initXML() throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(xmlPath);
	}
	
	public static void finalizeXML(Document document) throws Exception {
        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, xmlGrammar);
        StreamResult result = new StreamResult(xmlPath);
        transformer.transform(source, result);
	}
	
	public static void readDocument() throws Exception {
	    Element docElem = initXML().getDocumentElement();
	    NodeList nl = docElem.getChildNodes();
	    
	    if (nl != null) {
	        int length = nl.getLength();
	        for (int i = 0; i < length; i++) {
	            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
	                Element el = (Element) nl.item(i);
	                if(el.getNodeName().equals("chunk")) {
		                String fileID;
		                int chunkID;
		                
	                    fileID = el.getElementsByTagName("fileID").item(0).getTextContent();
	                    chunkID = Integer.parseInt(el.getAttribute("chunkID"));
	                    
	                    System.out.println(fileID + " - " + chunkID);
	                    
	                    Chunk c = new Chunk(fileID + "_" + chunkID, fileID, chunkID);
	                    c.storeAppInfo();
	                }
	                
	            }
	        }
	    }
    }
	
	public static void addChunk(Chunk chunk) throws Exception {
		Document document = initXML();
        Element root = document.getDocumentElement();
        
        // add Chunk
        Element newChunk = document.createElement("chunk");
        newChunk.setAttribute("chunkID", "" + chunk.getChunkId());
        
        Element fileID = document.createElement("fileID");
        fileID.appendChild(document.createTextNode(chunk.getFileId()));
        newChunk.appendChild(fileID);

        root.appendChild(newChunk);
        
        finalizeXML(document);
	}
}
