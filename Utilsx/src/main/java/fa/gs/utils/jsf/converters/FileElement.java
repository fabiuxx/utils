/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.gs.utils.jsf.converters;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author Fabio A. González Sosa
 */
@Data
public class FileElement implements Serializable {

    private String name;
    private String mimeType;
    private long size;
    private byte[] content;
}
