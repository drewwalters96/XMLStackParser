/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adwcn6xmlparser;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author drew
 */
public class XMLParser {
  
    public static XMLNode parse(File file) {
        XMLNode root = new XMLNode();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            
            DefaultHandler dh = new DefaultHandler() {
                Stack<XMLNode> stack = new Stack<>();
                
                public void startDocument(String uri, String localName, String qName, Attributes attributes) {
                    // Create root node and set name
                    root.setName(qName);
                    
                    // Add attributes if any exist
                    for (int i = 0; i < attributes.getLength(); i++) {
                        root.addAttribute(attributes.getLocalName(i), attributes.getValue(i));
                    }
                    
                    // Add root node to stack
                    stack.add(root);
                }
               
                @Override
               public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    // Create child node and set name
                    XMLNode child = new XMLNode();
                    child.setName(qName);
                    
                    // Add attributes if any exist
                    for (int i = 0; i < attributes.getLength(); i++) {
                        root.addAttribute(attributes.getLocalName(i), attributes.getValue(i));
                    }
                    
                    // Add child node to stack
                    stack.add(child);
               }
               
               public void endElement(String uri, String localName, String qName, Attributes attributes) {
                   // Get current element
                   XMLNode currentElement = stack.pop();
                   
                   // Get previous element and append current element
                   XMLNode prevElement = stack.peek();
                   currentElement.addElement(currentElement.getName(), currentElement);
               }
               
               public XMLNode endDocument(String uri, String localName, String qName, Attributes attributes) throws Exception {
                   return root;
               }
               
                @Override
               public void characters(char[] ch, int start, int length) throws SAXException {
                   // Get current element from stack and add content
                   XMLNode currentElement = stack.peek();
                   String content = new String(ch, start, length);
                   if (!content.equals('\n')) {
                       currentElement.setContent(new String(ch, start, length));
                   }
               }
            };
            parser.parse(file, dh);
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return root;
    }
}