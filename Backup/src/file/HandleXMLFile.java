package file;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import information.Chunk;

public class HandleXMLFile {

	public static void addBackedUpChunk(Chunk chunk) throws Exception {
		Document document = initXML();
        Element root = document.getDocumentElement();
        System.out.println("Adding info to xml");
        // add Chunk
        Element newChunk = document.createElement(FileConst.BACKED_UP_CHUNK_ELEM);
        newChunk.setAttribute(FileConst.CHUNK_ID_ELEM, "" + chunk.getChunkId());
        
        Element fileID = document.createElement(FileConst.FILE_ID_ELEM);
        fileID.appendChild(document.createTextNode(chunk.getFileId()));
        newChunk.appendChild(fileID);
        
        Element filePath = document.createElement(FileConst.FILE_PATH_ELEM);
        filePath.appendChild(document.createTextNode(chunk.getStorePath()));
        newChunk.appendChild(filePath);

        root.appendChild(newChunk);
        
        finalizeXML(document);
	}
	
	public static void addStoreChunk(Chunk chunk) throws Exception {
		Document document = initXML();
        Element root = document.getDocumentElement();
        
        // add Chunk
        Element newChunk = document.createElement(FileConst.STORED_CHUNK_ELEM);
        newChunk.setAttribute(FileConst.CHUNK_ID_ELEM, "" + chunk.getChunkId());
        
        Element fileID = document.createElement(FileConst.FILE_ID_ELEM);
        fileID.appendChild(document.createTextNode(chunk.getFileId()));
        newChunk.appendChild(fileID);

        root.appendChild(newChunk);
        
        finalizeXML(document);
	}
	
	public static void finalizeXML(Document document) throws Exception {
        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, FileConst.XMLGRAMMAR);
        StreamResult result = new StreamResult(FileConst.XMLPATH);
        transformer.transform(source, result);
	}
	
	public static Document initXML() throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(FileConst.XMLPATH);
	}
	
	public static void readDocument() throws Exception {
	    Element docElem = initXML().getDocumentElement();
	    NodeList nl = docElem.getChildNodes();
	    
	    if (nl != null) {
	        int length = nl.getLength();
	        for (int i = 0; i < length; i++) {
	            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
	                Element el = (Element) nl.item(i);
	                storeElement(el);	                
	            }
	        }
	    }
    }
	
	public static void removeBackedUpChunk(String fileID, String chunkID, String path) throws Exception{
        Document doc = initXML();

        NodeList nodes = doc.getElementsByTagName(FileConst.BACKED_UP_CHUNK_ELEM);
        for (int i = 0; i < nodes.getLength(); i++) {       
            Element chunk = (Element)nodes.item(i);
            Element fIDElement = (Element)chunk.getElementsByTagName(FileConst.FILE_ID_ELEM).item(0);
            String fID = fIDElement.getTextContent();
            Element sPathElement = (Element)chunk.getElementsByTagName(FileConst.FILE_PATH_ELEM).item(0);
            String sPath = sPathElement.getTextContent();
            String cID = chunk.getAttribute(FileConst.CHUNK_ID_ELEM);
            
            if(fID.equals(fileID) && cID.equals(chunkID) && sPath.equals(path)) {
            	chunk.getParentNode().removeChild(chunk);
            	break ;
            }
        }
        
        finalizeXML(doc);
    }
	
	public static void removeBackedUpFile(String path) throws Exception{
        Document doc = initXML();

        NodeList nodes = doc.getElementsByTagName(FileConst.BACKED_UP_CHUNK_ELEM);
        for (int i = 0; i < nodes.getLength(); i++) {       
            Element chunk = (Element)nodes.item(i);
            Element sPathElement = (Element)chunk.getElementsByTagName(FileConst.FILE_PATH_ELEM).item(0);
            String sPath = sPathElement.getTextContent();
            
            if(sPath.equals(path)) {
            	chunk.getParentNode().removeChild(chunk);
            	break ;
            }
        }
        
        finalizeXML(doc);
    }
	

	public static void removeStoredChunk(String fileID, String chunkID) throws Exception{
        Document doc = initXML();

        NodeList nodes = doc.getElementsByTagName(FileConst.STORED_CHUNK_ELEM);
        for (int i = 0; i < nodes.getLength(); i++) {       
            Element chunk = (Element)nodes.item(i);
            Element fIDElement = (Element)chunk.getElementsByTagName(FileConst.FILE_ID_ELEM).item(0);
            String fID = fIDElement.getTextContent();
            String cID = chunk.getAttribute(FileConst.CHUNK_ID_ELEM);
            
            if(fID.equals(fileID) && cID.equals(chunkID)) {
            	chunk.getParentNode().removeChild(chunk);
            	break ;
            }
        }
        
        finalizeXML(doc);
    }
	
	public static void storeElement(Element el) {
        String fileID;
        int chunkID;
        
        fileID = el.getElementsByTagName(FileConst.FILE_ID_ELEM).item(0).getTextContent();
        chunkID = Integer.parseInt(el.getAttribute(FileConst.CHUNK_ID_ELEM));
        
        if(el.getNodeName().equals(FileConst.STORED_CHUNK_ELEM)) {
            Chunk c = new Chunk(fileID + "_" + chunkID, fileID, chunkID);
            c.storeAppInfo();
            
        } else if (el.getNodeName().equals(FileConst.BACKED_UP_CHUNK_ELEM)) {
            String filePath;
            
            filePath = el.getElementsByTagName(FileConst.FILE_PATH_ELEM).item(0).getTextContent();
            
            Chunk c = new Chunk(filePath, fileID, chunkID);
            c.backUpAppInfo();
        } 
	}
}
