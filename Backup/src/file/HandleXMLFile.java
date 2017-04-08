package file;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

import information.ChunkBackedUp;
import information.ChunkStored;
import information.FileInfo;

/**
 * 
 * This class builds the XML's handler
 *
 */
public class HandleXMLFile {
	private static Lock lock = new ReentrantLock(); //Creates an instance of ReentrantLock

	/**
	 * Adds the backed up chunks to the XML file
	 * @param chunk Backed up chunk
	 * @throws Exception Indicates conditions that a reasonable application might want to catch
	 */
	public static void addBackedUpChunk(ChunkBackedUp chunk) throws Exception {
		lock.lock(); //Acquires the lock
		try {
			Document document = initXML(); //Initiates the XML
	        Element root = document.getDocumentElement();

	        Element newChunk = document.createElement(FileConst.BACKED_UP_CHUNK_ELEM);
	        newChunk.setAttribute(FileConst.CHUNK_ID_ELEM, "" + chunk.getChunkId());
	        newChunk.setAttribute(FileConst.SERVICE_ID_ELEM, "" + chunk.getServiceID());
	        newChunk.setAttribute(FileConst.DREPEG_ELEM, "" + chunk.getDRepDeg());
	        newChunk.setAttribute(FileConst.PREPEG_ELEM, "" + chunk.getPRepDeg());
	        
	        Element fileID = document.createElement(FileConst.FILE_ID_ELEM);
	        fileID.appendChild(document.createTextNode(chunk.getFileId()));
	        newChunk.appendChild(fileID);
	        
	        Element filePath = document.createElement(FileConst.FILE_PATH_ELEM);
	        filePath.appendChild(document.createTextNode(chunk.getStorePath()));
	        newChunk.appendChild(filePath);
	
	        root.appendChild(newChunk);
	        
	        finalizeXML(document);
		} finally {
			lock.unlock(); //Releases the lock
		}
	}

	/**
	 * Adds the stored chunks to the XML file
	 * @param chunk Stored chunk that will be stored
	 * @throws Exception Indicates conditions that a reasonable application might want to catch
	 */
	public static void addStoreChunk(ChunkStored chunk) throws Exception {
		lock.lock(); //Acquires the lock
		try {
			Document document = initXML(); //Initiates the XML file
	        Element root = document.getDocumentElement();
	        
	        //Adds the stored chunk and its information
	        Element newChunk = document.createElement(FileConst.STORED_CHUNK_ELEM);
	        newChunk.setAttribute(FileConst.CHUNK_ID_ELEM, "" + chunk.getChunkId());
	        newChunk.setAttribute(FileConst.PREPEG_ELEM, "" + chunk.getPRepDeg());
	        newChunk.setAttribute(FileConst.SIZE_ELEM, "" + chunk.getSize());
	        
	        Element fileID = document.createElement(FileConst.FILE_ID_ELEM);
	        fileID.appendChild(document.createTextNode(chunk.getFileId()));
	        newChunk.appendChild(fileID);

	        root.appendChild(newChunk);
	        
	        finalizeXML(document);
		} finally {
			lock.unlock(); //Releases the lock
		}
	}

	/**
	 * Reads the XML document
	 * @throws Exception Indicates conditions that a reasonable application might want to catch
	 */
	public static void readDocument() throws Exception {
		lock.lock(); //Acquires the lock
		try {
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
		} finally {
			lock.unlock(); //Releases the lock
		}
    }
	
	/*public static void removeBackedUpChunk(String fileID, String chunkID, String path) throws Exception {
		lock.lock();
		try {
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
		} finally {
			lock.unlock();
		}
    }*/
	
	/**
	 * Removes the backed up chunks from the XML file
	 * @param path Chunk's pathname
	 * @throws Exception Indicates conditions that a reasonable application might want to catch
	 */
	public static void removeBackedUpFile(String path) throws Exception {
		lock.lock(); //Acquires the lock
		try {
	        Document doc = initXML(); //Initiates the XML file
	        
	        NodeList nodes = doc.getElementsByTagName(FileConst.BACKED_UP_CHUNK_ELEM);
	        for (int i = 0; i < nodes.getLength(); i++) {
	            Element chunk = (Element)nodes.item(i);
	            Element sPathElement = (Element)chunk.getElementsByTagName(FileConst.FILE_PATH_ELEM).item(0);
	            String sPath = sPathElement.getTextContent();
	            if(sPath.equals(path)) {
	            	chunk.getParentNode().removeChild(chunk);
		            i--;
	            }
	        }
	        
	        finalizeXML(doc);
		} finally {
			lock.unlock(); //Releases the lock
		}
    }

	/**
	 * Removes the stored chunks from the XML file using the fileID
	 * @param fileID Chunk's file ID
	 * @throws Exception Indicates conditions that a reasonable application might want to catch
	 */
	public static void removeStoredFile(String fileID) throws Exception {
		lock.lock(); //Acquires the lock 
		try {
	        Document doc = initXML(); //Initiates the XML file

	        NodeList nodes = doc.getElementsByTagName(FileConst.STORED_CHUNK_ELEM);
	        for (int i = 0; i < nodes.getLength(); i++) {   
	            Element chunk = (Element)nodes.item(i);
	            Element sPathElement = (Element)chunk.getElementsByTagName(FileConst.FILE_ID_ELEM).item(0);
	            String sfileID = sPathElement.getTextContent();
	            if(sfileID.equals(fileID)) {
	            	chunk.getParentNode().removeChild(chunk);
		            i--;
	            }
	        }
	        
	        finalizeXML(doc);
		} finally {
			lock.unlock(); //Releases the lock
		}
    }

	/**
	 * Removes the stored chunks from the XML file using the fileID and chunkID
	 * @param fileID Chunk's file ID
	 * @param chunkID Chunk's ID
	 * @throws Exception Indicates conditions that a reasonable application might want to catch
	 */
	public static void removeStoredChunk(String fileID, String chunkID) throws Exception {
		lock.lock(); //Acquires the lock
		try {
	        Document doc = initXML(); //Initiates the XML file

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
		} finally {
			lock.unlock(); //Releases the lock
		}
    }

	/**
	 * Finalizes the analysis of the XML file
	 * @param document XML document
	 * @throws Exception Indicates conditions that a reasonable application might want to catch
	 */
	private static void finalizeXML(Document document) throws Exception {
        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, FileConst.XMLGRAMMAR);
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(FileConst.XMLPATH);
        transformer.transform(source, result);
	}
	
	/**
	 * Initiates the XML file
	 * @return The document parsed
	 * @throws Exception Indicates conditions that a reasonable application might want to catch
	 */
	private static Document initXML() throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();        
        return documentBuilder.parse(FileConst.XMLPATH);
	}
	
	/**
	 * Stores a element into the XML file
	 * @param el Element that will be stored
	 */
	private static void storeElement(Element el) {
        String fileID;
        int chunkID;
        int prepdeg;
        
        fileID = el.getElementsByTagName(FileConst.FILE_ID_ELEM).item(0).getTextContent();
        chunkID = Integer.parseInt(el.getAttribute(FileConst.CHUNK_ID_ELEM));
        prepdeg = Integer.parseInt(el.getAttribute(FileConst.PREPEG_ELEM));
        
        if(el.getNodeName().equals(FileConst.STORED_CHUNK_ELEM)) {
        	int size = Integer.parseInt(el.getAttribute(FileConst.SIZE_ELEM));
        	
        	ChunkStored c = new ChunkStored(fileID + "_" + chunkID, fileID, chunkID, prepdeg, size);
            FileInfo.storeChunk(c);
            
        } else if (el.getNodeName().equals(FileConst.BACKED_UP_CHUNK_ELEM)) {
            String filePath;
            int drepdeg;
            int serviceID;
            
            filePath = el.getElementsByTagName(FileConst.FILE_PATH_ELEM).item(0).getTextContent();
            drepdeg = Integer.parseInt(el.getAttribute(FileConst.DREPEG_ELEM));
            serviceID = Integer.parseInt(el.getAttribute(FileConst.SERVICE_ID_ELEM));
            
            ChunkBackedUp c = new ChunkBackedUp(serviceID, filePath, fileID, chunkID, prepdeg, drepdeg);
            FileInfo.backupChunk(c);
        } 
	}
}
