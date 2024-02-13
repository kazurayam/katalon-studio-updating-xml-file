import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kms.katalon.core.configuration.RunConfiguration

import groovy.xml.*

String inputFileName = 'data.xml'
String outputFileName = "updated.xml"

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path srcDir = projectDir.resolve("src")
Path buildDir = projectDir.resolve("build")
Files.createDirectories(buildDir)
File data = srcDir.resolve(inputFileName).toFile()
File out  = buildDir.resolve(outputFileName).toFile()

def root = new XmlSlurper().parse(data)

println "before: " + root.TestRemitList.TestRemittancy[1].TestNumber.text()
root.TestRemitList.TestRemittancy[1].TestNumber.replaceNode {
	TestNumber("xyz123")
}
println "after : " + root.TestRemitList.TestRemittancy[1].TestNumber.text()

out.write(XmlUtil.serialize(root));