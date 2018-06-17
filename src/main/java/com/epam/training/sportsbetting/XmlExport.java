package com.epam.training.sportsbetting;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.epam.training.sportsbetting.domain.wager.Wager;

public class XmlExport {
//    public void toXml() {
//        List<Wager> wagers = getWagers();
//        File file = new File(env.getProperty("xmlFileName"));
//        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(Wager.class);
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//            jaxbMarshaller.marshal(wagers.get(0), file);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//        LOG.info("Xml file is ready");
//
//    }


}
