<%@ page import="java.net.*" %>
<%@ page import="java.io.*" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%
	String hostname, address;
	hostname = "";
	address = "";
	try {
		InetAddress inetAddress;
		inetAddress = InetAddress.getLocalHost();
		hostname = inetAddress.getHostName();
		address = inetAddress.getHostAddress();
	} catch (UnknownHostException e) {
		e.printStackTrace();
	}
	
	ProcessBuilder pb;
	Process process;
	int errCode;
%>

<%!
	//Function 'output'
	private String output(InputStream inputStream) throws IOException {
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
%>

<html>
<head>
<title>Sample Application JSP Page</title>
</head>
<body bgcolor=white>

<table border="0">
<tr>
	<td align=center>
		<img src="images/tomcat.gif">
	</td>
	<td>
		<h1>Sample Application JSP Page</h1>
		This is the output of a JSP page that is part of the Hello, World
		application.
	</td>
</tr>
<tr>
	<td align=center>
		<h3>Container OS Release</h3>
	</td>
	<td>
		<%
			String jspPath = "/etc/";
			String fileName = "redhat-release";
			String txtFilePath = jspPath + fileName;
			BufferedReader reader = new BufferedReader(new FileReader(txtFilePath));
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = reader.readLine())!= null){
				sb.append(line+"\n");
			}
			//System.out.println(sb.toString()); 
		%>
		<h3><font color="red"><%=sb.toString()%></font></h3>
	</td>
</tr>

<tr>
	<td align=center>
		<h3>Container Java Version</h3>
	</td>
	<td>
		<%
			pb = new ProcessBuilder("java", "-version");
			pb.redirectErrorStream(true);
			process = pb.start();
		%>
		<h3><font color="red"><%=output(process.getInputStream())%></font></h3>
	</td>
</tr>

<tr>
	<td align=center>
		<h3>Container Tomcat Version</h3>
	</td>
	<td>
		<%
			pb = new ProcessBuilder("java", "-classpath", "/usr/share/java/tomcat/catalina.jar:/usr/share/java/tomcat6/catalina.jar", "org.apache.catalina.util.ServerInfo");
			pb.redirectErrorStream(true);
			process = pb.start();
		%>
		<h3><font color="red"><%=output(process.getInputStream())%></font></h3>
	</td>
</tr>

<tr>
	<td align=center>
		<h3>Container Hostname</h3>
	</td>
	<td>
		<h3><font color="red"><%=hostname%></font></h3>
	</td>
</tr>
<tr>
	<td align=center>
		<h3>Container IP</h3>
	</td>
	<td>
		<h3><font color="red"><%=address%></font></h3>
	</td>
</tr>
</table>

<!--
<%= new String("Hello!") %>
-->

</body>
</html>
