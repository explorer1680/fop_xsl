package personal.fop.basic;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

public class FOPTestRun {

	public static void main(String[] args) throws IOException, FOPException, TransformerException {

		File xslFile = new ClassPathResource("personal/fop/basic/template.xsl").getFile();

		StreamSource xmlSource = new StreamSource(new ClassPathResource("personal/fop/basic/employees.xml").getInputStream());

		FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());

		FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

		OutputStream out = new FileSystemResource("/Users/echeng/work/test-temp/fop_test_run.pdf").getOutputStream();

		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(new StreamSource(xslFile));

		Result res = new SAXResult(fop.getDefaultHandler());

		transformer.transform(xmlSource, res);

		out.close();
		System.out.println("done");

		OutputStream out2 = new FileSystemResource("/Users/echeng/work/test-temp/fop_test_run.fo").getOutputStream();
		Result res2 = new StreamResult(out2);
//		File xslFile2 = new ClassPathResource("personal/fop/template.xsl").getFile();
//		Transformer transformer2 = factory.newTransformer(new StreamSource(xslFile2));
		StreamSource xmlSource2 = new StreamSource(new ClassPathResource("personal/fop/basic/employees.xml").getInputStream());

		transformer.transform(xmlSource2, res2);
		out2.close();
		System.out.println("done");
	}
}
