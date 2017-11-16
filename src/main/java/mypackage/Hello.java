/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mypackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Simple servlet to validate that the Hello, World example can
 * execute servlets.  In the web application deployment descriptor,
 * this servlet must be mapped to correspond to the link in the
 * "index.html" file.
 *
 * @author Craig R. McClanahan <Craig.McClanahan@eng.sun.com>
 */
 
/**
 * HOWTO: 
 * This file has been customized to be used in a demonstration with Docker/OpenShift in a Tomcat container.
 * $CATALINA_HOME value is set in the tomcat configuration file (e.g. 'tomcat.conf')
 * 1 - Download the 'sample.war' file.
 * 2 - Extract the 'sample.war' file:
 * # mkdir sample && unzip sample.war -d sample
 * 3 - You must obtain this tree:
 * # tree --charset=ascii sample
 * sample
 * |-- hello.jsp
 * |-- images
 * |   `-- tomcat.gif
 * |-- index.html
 * |-- META-INF
 * |   `-- MANIFEST.MF
 * `-- WEB-INF
 *     |-- classes
 *     |   `-- mypackage
 *     |       `-- Hello.class
 *     |-- lib
 *     `-- web.xml
 * 
 * 6 directories, 6 files
 * 
 * 
 * 4 - Compile this 'Hello.java' file to obtain a new 'Hello.class' file (needs 'javac' compiler from JDK):
 * # javac -cp $CATALINA_HOME/lib/servlet-api-7.0.70.jar Hello.java
 * 5 - Copy new 'Hello.class' to 'sample/WEB-INF/classes/mypackage/' directory:
 * # cp Hello.class sample/WEB-INF/classes/mypackage/Hello.class
 * 6 - Create new 'sample.war' file direcly in the tomcat 'webapps' directory:
 * # cd sample && jar cvf $CATALINA_HOME/webapps/sample.war .
 * 7 - Go to '<your tomcat server>:<your tomcat port>/sample/hello' to see the result of the servlet
 * # curl localhost:8080/sample/hello
 */

public final class Hello extends HttpServlet {


    /**
     * Respond to a GET request for the content produced by
     * this servlet.
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are producing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
      throws IOException, ServletException {

	InetAddress ip = InetAddress.getLocalHost();
	String hostname = ip.getHostName();
	String address = ip.getHostAddress();
	
	String filename = "/etc/redhat-release";
	BufferedReader br = new BufferedReader(new FileReader(filename));
	
	response.setContentType("text/html");
	PrintWriter writer = response.getWriter();

	writer.println("<html>");
	writer.println("<head>");
	writer.println("<title>Sample Application Servlet Page</title>");
	writer.println("</head>");
	writer.println("<body bgcolor=white>");

	writer.println("<table border=\"0\">");
	
	writer.println("<tr>");
		writer.println("<td>");
			writer.println("<img src=\"images/tomcat.gif\">");
		writer.println("</td>");
		writer.println("<td>");
			writer.println("<h1>Sample Application Servlet</h1>");
			writer.println("This is the output of a servlet that is part of");
			writer.println("the Hello, World application.");
			//writer.println("Your current Hostname/IP address : " + ip);
			//writer.println("Your current Hostname : " + hostname);
		writer.println("</td>");
	writer.println("</tr>");
	
	/*
	writer.println("<tr>");
		writer.println("<td align=center>");
			writer.println("<h3>Container Hostname/IP address</h3>");
		writer.println("</td>");
		writer.println("<td>");
			writer.println("<h3><font color=\"red\">" + ip + "</font></h3>");
		writer.println("</td>");
	writer.println("</tr>");
	*/

	writer.println("<tr>");
		writer.println("<td align=center>");
			writer.println("<h3>Container OS Release</h3>");
		writer.println("</td>");
		writer.println("<td>");
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {
			writer.println("<h3><font color=\"red\">" + sCurrentLine + "</font></h3>");
		}
		writer.println("</td>");
	writer.println("</tr>");

	ProcessBuilder pb;
	Process process;
	int errCode;
	
	writer.println("<tr>");
		writer.println("<td align=center>");
			writer.println("<h3>Container Java Version</h3>");
		writer.println("</td>");
		writer.println("<td>");	
			pb = new ProcessBuilder("java", "-version");
			pb.redirectErrorStream(true);
			process = pb.start();
			//errCode = waitFor();
			writer.println("<h3><font color=\"red\">" + output(process.getInputStream()) + "</font></h3>");
		writer.println("</td>");
	writer.println("</tr>");
	
	writer.println("<tr>");
		writer.println("<td align=center>");
			writer.println("<h3>Container Tomcat Version</h3>");
		writer.println("</td>");
		writer.println("<td>");	
			pb = new ProcessBuilder("tomcat8", "version");
			pb.redirectErrorStream(true);
			process = pb.start();
			//errCode = waitFor();
			writer.println("<h3><font color=\"red\">" + output(process.getInputStream()) + "</font></h3>");
		writer.println("</td>");
	writer.println("</tr>");
	
	writer.println("<tr>");
		writer.println("<td align=center>");
			writer.println("<h3>Container Hostname</h3>");
		writer.println("</td>");
		writer.println("<td>");
			writer.println("<h3><font color=\"red\">" + hostname + "</font></h3>");
		writer.println("</td>");
	writer.println("</tr>");
	
	writer.println("<tr>");
		writer.println("<td align=center>");
			writer.println("<h3>Container IP</h3>");
		writer.println("</td>");
		writer.println("<td>");
			writer.println("<h3><font color=\"red\">" + address + "</font></h3>");
		writer.println("</td>");
	writer.println("</tr>");
	
	writer.println("</table>");

	writer.println("</body>");
	writer.println("</html>");
    }
	
	private static String output(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = br.readLine()) != null) {
                //sb.append(line + System.getProperty("line.separator"));
				sb.append(line + "<br>");
            }
        } finally {
            br.close();
        }
        return sb.toString();
    }

}
