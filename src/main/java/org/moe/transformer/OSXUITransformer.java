/*
Copyright 2014-2016 Intel Corporation

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.moe.transformer;

import org.moe.transformer.UIOSxProcessor.OSXStringProcessor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class OSXUITransformer {

    public static void main(String[] args) throws TransformerException, IOException, ParserConfigurationException, XPathExpressionException, SAXException {
        System.out.println("OSXUITransformer exec");

        CmdParser.InitParser(args);

        String android_res_path = OSXStringProcessor.GetProjectLayoutPath();//CmdParser.GetParam("ixml_res_path");
        String xslt_rec_path = CmdParser.GetParam("xslt_res_path");
        String output_path = CmdParser.GetParam("out_path");
        String out_filename = CmdParser.GetParam("out_filename");
        String xslt_template = CmdParser.GetParam("xslt_template");
        String out_format = CmdParser.GetParam("out_format");

        System.out.println("ixml_res_path: " + android_res_path);
        System.out.println("out_path: " + output_path);
        System.out.println("out_filename: " + out_filename);
        System.out.println("xslt_template: " + xslt_template);

        File inputFilePath = new File(android_res_path);
        if (inputFilePath.exists()) {
            if (output_path != null && !output_path.isEmpty()) {
                File outDirectory = new File(output_path);
                if (!outDirectory.exists())
                    outDirectory.mkdirs();

                Document doc = OSXUITransformer.CreateInitXml(android_res_path);
                if (doc != null) {
                    Source xslDoc = new StreamSource(xslt_rec_path + "/" + xslt_template);
                    Source xmlDoc = new DOMSource(doc);
                    TransformerFactory tFactory = TransformerFactory.newInstance();
                    File outputFile = new File(outDirectory, out_filename + "." + out_format);

                    OutputStream xibFile = new FileOutputStream(outputFile);

                    try {
                        Transformer transform = tFactory.newTransformer(xslDoc);
                        transform.setOutputProperty(OutputKeys.INDENT, "no");
                        transform.transform(xmlDoc, new StreamResult(xibFile));

                        LayoutParser parser = new LayoutParser();
                        parser.build(outputFile.getAbsolutePath());
                        parser.saveXML();
                    } catch (TransformerException e) {
                        throw new TransformerException("Failed to transform ixml to storyboard!");
                    } finally {
                        if (xibFile != null) {
                            xibFile.close();
                        }
                    }
                } else {
                    throw new IOException("Can't find ixml files here: " + inputFilePath.getAbsolutePath());
                }

            } else {
                throw new IOException("Can't find output directory: " + output_path);
            }
        } else {
            throw new IOException("Can't find directory with ixml files: " + inputFilePath.getAbsolutePath());
        }
    }


    private static Document CreateInitXml(String path) throws ParserConfigurationException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element elemList = doc.createElement("list");
        doc.appendChild(elemList);

        File folder = new File(path);
        File[] list = folder.listFiles();
        if (list.length == 0) return null;
        int ixml_count = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i].isFile()) {
                String[] splStr = list[i].getName().split("\\.");
                if (splStr[splStr.length - 1].equals("ixml")) {
                    Element elemItem = doc.createElement("item");
                    elemItem.setAttribute("name", list[i].getName());
                    elemItem.setAttribute("absolute_path", list[i].getAbsolutePath());
                    elemList.appendChild(elemItem);
                    ixml_count++;
                }
            }
        }
        if (ixml_count == 0) return null;
        return doc;
    }
}
