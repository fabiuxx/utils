/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.xml;

import fa.gs.utils.collections.Maps;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Units;
import fa.gs.utils.misc.text.Strings;
import fa.gs.utils.misc.text.Text;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Xml {

    public static Document parse(File file) throws IOException {
        return parse(file, true);
    }

    public static Document parse(String text) throws IOException {
        return parse(text, true);
    }

    public static Document parse(byte[] bytes) throws IOException {
        return parse(bytes, true);
    }

    public static Document parse(InputStream is) throws IOException {
        return parse(is, true);
    }

    public static Document parse(File file, boolean namespaceAware) throws IOException {
        InputStream is = new FileInputStream(file);
        return parse(is, namespaceAware);
    }

    public static Document parse(String text, boolean namespaceAware) throws IOException {
        byte[] bytes = Strings.getBytes(text);
        return parse(bytes, namespaceAware);
    }

    public static Document parse(byte[] bytes, boolean namespaceAware) throws IOException {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        return parse(is, namespaceAware);
    }

    public static Document parse(InputStream is, boolean namespaceAware) throws IOException {
        try {
            // Datos de entrada.
            Reader reader = new InputStreamReader(is, "UTF-8");
            InputSource iso = new InputSource(reader);
            iso.setEncoding("UTF-8");

            // Parseo.
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(namespaceAware);
            return dbf.newDocumentBuilder().parse(iso);
        } catch (Throwable thr) {
            throw new IOException(thr);
        }
    }

    public static String toString(Node doc) throws IOException {
        try {
            // Datos de entrada.
            DOMSource is = new DOMSource(doc, "http://ekuatia.set.gov.py/sifen/xsd");

            // Datos de salida.
            StringWriter writer = new StringWriter();
            StreamResult sr = new StreamResult(writer);

            // Transformacion.
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.transform(is, sr);

            return writer.getBuffer().toString();
        } catch (Throwable thr) {
            throw new IOException(thr);
        }
    }

    public static String cleanup(String xml) {
        xml = xml.trim();
        xml = Text.replaceAll(xml, "\r", "");
        xml = Text.replaceAll(xml, "\n", "");
        xml = Text.replaceAll(xml, "  ", "");
        xml = Text.replaceAll(xml, "> ", ">");
        xml = Text.replaceAll(xml, " >", ">");
        xml = Text.replaceAll(xml, "< ", "<");
        xml = Text.replaceAll(xml, " <", "<");
        xml = Text.replaceAll(xml, "> <", "><");
        return xml;
    }

    public static String readTagValue(Document document, String tagName, String fallback) throws IOException {
        NodeList list = document.getElementsByTagName(tagName);
        if (list.getLength() == 0) {
            return fallback;
        }

        Element tag = (Element) list.item(0);
        return readTagValue(document, tag, fallback);
    }

    public static String readTagValue(Document document, Element tag, String fallback) throws IOException {
        if (tag == null) {
            return fallback;
        }

        String value = tag.getFirstChild().getNodeValue();
        if (value == null) {
            value = fallback;
        }

        return value.trim();
    }

    public static Node createTextNode(Document document, String tagName, String value) throws IOException {
        Node n0 = document.createElement(tagName);
        Node n1 = document.createTextNode(value);
        n0.appendChild(n1);
        return n0;
    }

    public static XPath xpath(Document document) {
        XmlPrefixInfoList prefixesInfo = readPrefixes(document);
        return xpath(prefixesInfo);
    }

    public static XPath xpath(XmlPrefixInfoList prefixesInfo) {
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(new XmlXpathNamespaceContext(prefixesInfo));
        return xpath;
    }

    public static Node readNode(XPath xpath, String expression, Node xml) {
        return Units.execute(() -> {
            Node node = (Node) xpath.compile(expression).evaluate(xml, XPathConstants.NODE);
            return node;
        });
    }

    public static NodeList readNodes(XPath xpath, String expression, Node xml) {
        return Units.execute(() -> {
            NodeList node = (NodeList) xpath.compile(expression).evaluate(xml, XPathConstants.NODESET);
            return node;
        });
    }

    public static String readNodeTexContent(XPath xpath, String expression, Node xml) {
        return readNodeTexContent(xpath, expression, xml, "");
    }

    public static String readNodeTexContent(XPath xpath, String expression, Node xml, String fallback) {
        try {
            Node node = readNode(xpath, expression, xml);
            if (node == null) {
                return fallback;
            } else {
                String text = node.getTextContent();
                return (Assertions.stringNullOrEmpty(text)) ? fallback : text;
            }
        } catch (Throwable thr) {
            return fallback;
        }
    }

    public static XmlPrefixInfoList readPrefixes(Document document) {
        // Visitar totalidad de documento y obtener cada prefijo definido.
        Map<String, String> map = Maps.empty();
        readPrefixes(map, document);

        XmlPrefixInfoList prefixes = new XmlPrefixInfoList();
        if (!Assertions.isNullOrEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                XmlPrefixInfo prefix = new XmlPrefixInfo();
                prefix.setPrefix(entry.getKey());
                prefix.setNamespaceUri(entry.getValue());
                prefixes.add(prefix);
            }
        }
        return prefixes;
    }

    private static void readPrefixes(final Map<String, String> map, Node element) {
        // Prefijo y URI de namespace para nodo actual.
        String prefix = element.getPrefix();
        String namespaceUri = element.getNamespaceURI();
        if (!Assertions.stringNullOrEmpty(prefix) && !Assertions.stringNullOrEmpty(namespaceUri)) {
            map.put(prefix, namespaceUri);
        }

        // Visitar nodos hijos.
        NodeList childs = element.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++) {
            Node child = childs.item(i);
            readPrefixes(map, child);
        }
    }

}
