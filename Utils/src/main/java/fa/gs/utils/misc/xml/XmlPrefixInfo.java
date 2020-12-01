/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.xml;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author Fabio A. González Sosa
 */
@Data
public class XmlPrefixInfo implements Serializable {

    private String prefix;
    private String namespaceUri;

}
