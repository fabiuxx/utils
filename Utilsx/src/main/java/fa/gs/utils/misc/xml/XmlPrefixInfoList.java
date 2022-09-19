/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.xml;

import fa.gs.utils.collections.Lists;
import java.io.Serializable;
import java.util.Collection;
import java8.util.Objects;

/**
 *
 * @author Fabio A. González Sosa
 */
public class XmlPrefixInfoList implements Serializable {

    private final Collection<XmlPrefixInfo> prefixInfos;

    public XmlPrefixInfoList() {
        this.prefixInfos = Lists.empty();
    }

    public void add(XmlPrefixInfo prefixInfo) {
        Lists.add(prefixInfos, prefixInfo);
    }

    public String getNamespaceUri(String prefix) {
        return Lists.first(getNamespaceUris(prefix));
    }

    public Collection<String> getNamespaceUris(String prefix) {
        Collection<String> namespaceUris = Lists.empty();
        for (XmlPrefixInfo prefixInfo : prefixInfos) {
            if (Objects.equals(prefixInfo.getPrefix(), prefix)) {
                Lists.add(namespaceUris, prefixInfo.getNamespaceUri());
            }
        }
        return namespaceUris;
    }

    public String getPrefix(String namespaceUri) {
        return Lists.first(getPrefixes(namespaceUri));
    }

    public Collection<String> getPrefixes(String namespaceUri) {
        Collection<String> prefixes = Lists.empty();
        for (XmlPrefixInfo prefixInfo : prefixInfos) {
            if (Objects.equals(prefixInfo.getNamespaceUri(), namespaceUri)) {
                Lists.add(prefixes, prefixInfo.getPrefix());
            }
        }
        return prefixes;
    }

}
