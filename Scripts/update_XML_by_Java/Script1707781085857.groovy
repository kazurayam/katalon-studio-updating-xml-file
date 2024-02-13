import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

import org.w3c.dom.Document
import org.w3c.dom.Node

import com.kms.katalon.core.configuration.RunConfiguration

String inputFileName = 'data.xml'
String outputFileName = "updated_by_java.xml"

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path srcDir = projectDir.resolve("src")
Path buildDir = projectDir.resolve("build")
Files.createDirectories(buildDir)
File data = srcDir.resolve(inputFileName).toFile()
File out  = buildDir.resolve(outputFileName).toFile()

DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance()
DocumentBuilder db = dbFactory.newDocumentBuilder()
Document doc = db.parse(data)

XPath xpath = XPathFactory.newInstance().newXPath()
Node target =
    (Node)xpath.compile("/root/TestRemitList/TestRemittancy[2]/TestNumber")
        .evaluate(doc, XPathConstants.NODE)
target.setTextContent("abc987");

Transformer tr = TransformerFactory.newInstance().newTransformer();
tr.setOutputProperty(OutputKeys.INDENT, "no")
tr.setOutputProperty(OutputKeys.METHOD, "xml")
//tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")

DOMSource domSource = new DOMSource(doc)
StreamResult sr = new StreamResult(out)
tr.transform(domSource, sr)