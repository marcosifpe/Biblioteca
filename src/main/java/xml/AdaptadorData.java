/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author MASC
 */
public class AdaptadorData extends XmlAdapter<String, Date> {
    private SimpleDateFormat formatadorData  = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    public Date unmarshal(String dataStr) throws Exception {
        return formatadorData.parse(dataStr);
    }

    @Override
    public String marshal(Date data) throws Exception {
        return formatadorData.format(data);
    }
    
}
