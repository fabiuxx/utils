/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.xml;

import java.util.Collections;
import java.util.Iterator;
import java8.util.Objects;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

/**
 *
 * @author Fabio A. González Sosa
 */
public class XmlXpathNamespaceContext implements NamespaceContext {

    private final XmlPrefixInfoList prefixInfos;

    public XmlXpathNamespaceContext(XmlPrefixInfoList info) {
        this.prefixInfos = info;
    }

    @Override
    public String getNamespaceURI(String prefix) {
        if (Objects.equals(prefix, XMLConstants.DEFAULT_NS_PREFIX)) {
            return XMLConstants.XML_NS_URI;
        } else {
            return prefixInfos.getNamespaceUri(prefix);
        }
    }

    @Override
    public String getPrefix(String namespaceURI) {
        return null;
    }

    @Override
    public Iterator getPrefixes(String uri) {
        if (Objects.equals(uri, XMLConstants.XML_NS_URI)) {
            return Collections.singleton(XMLConstants.DEFAULT_NS_PREFIX).iterator();
        } else {
            return prefixInfos.getPrefixes(uri).iterator();
        }
    }

}
