package main.persistance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import main.midiInterface.Controls;

public class ControlsConverter {

	private static final String ROOT_ELEMENT = "controller_mapping";
	private static final String CONTROL_ELEMENT = "control";
	private static final String NAME_ATTRIBUTE = "name";
	private static final String CHANNEL_ATTRIBUTE = "channel";
	private static final String CC_ATTRIBUTE = "cc";

	private File mappingFile = new File("mappings/main.xml");

	public void saveControllerValues() throws IOException, ParserConfigurationException, TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(convertControls2Xml());

		FileWriter writer = new FileWriter(mappingFile);
		StreamResult result = new StreamResult(writer);

		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		transformer.transform(source, result);

	}

	public void loadControllerValues() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(mappingFile);
		convertXml2Controls(document);
	}

	public Document convertControls2Xml() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		Element root = document.createElement(ROOT_ELEMENT);
		document.appendChild(root);
		for (Controls control : Controls.values()) {
			Element controlElement = document.createElement(CONTROL_ELEMENT);
			controlElement.setAttribute(NAME_ATTRIBUTE, control.toString());
			controlElement.setAttribute(CHANNEL_ATTRIBUTE, Integer.toString(control.getChannel()));
			controlElement.setAttribute(CC_ATTRIBUTE, Integer.toString(control.getCC()));
			root.appendChild(controlElement);
		}
		return document;
	}

	public void convertXml2Controls(Document document) {
		Element root = document.getDocumentElement();
		for (int i = 0; i < root.getChildNodes().getLength(); i++) {
			if (root.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element valueElement = (Element) root.getChildNodes().item(i);
				Controls control = Controls.valueOf(valueElement.getAttribute(NAME_ATTRIBUTE));
				control.setChannel(Integer.valueOf(valueElement.getAttribute(CHANNEL_ATTRIBUTE)));
				control.setCC(Integer.valueOf(valueElement.getAttribute(CC_ATTRIBUTE)));
			}
		}
	}

}
