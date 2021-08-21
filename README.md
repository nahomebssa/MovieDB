# Project Phase 3

This project uses Apache Tomcat 8.5 and Java Servlets

### TL;DR
<!-- to setup see this link https://www.youtube.com/watch?v=qImCqaxuhgQ -->
- To setup see this link https://www.youtube.com/watch?v=pKMgr8uNvGM
- Install from here: https://tomcat.apache.org/download-80.cgi
- Install eclipse ide for java ee developers
- Import the project into eclipse
- Right click on the project in the project explorer
- Click Run As -> 1 Run on Server


```
Clear out delete and uninstall all previous versions of the JDK, JRE, Tomcat, Eclipse, whatever
Delete all variables and paths to JDK or JRE in Env. Variables
CLEAR OUT EVERYTHING – you do not want any confusing junk left behind while you are doing the stuff below.
Go to the Oracle Install page:  https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
Download BOTH the Windows X64 installer and the Windows X64 Compressed Archive.
Go to the folder where the installer is (most likely the Downloads folder) and run the .exe.
Go to C:\Program Files\Java\jdk-11.0.8 and make sure there are folders and files in there (bin, conf, etc.)
Go to Environmental Variables 
Create a variable called JAVA_HOME and point it at C:\Program Files\Java\jdk-11.0.8
Add an element to path C:\Program Files\Java\jdk-11.0.8\bin
OK and save everything in Environmental Variables
Go to the command prompt:

"Microsoft Windows [Version 10.0.18363.1082]
(c) 2019 Microsoft Corporation. All rights reserved.
C:\Users\-redacted->set JAVA_HOME
JAVA_HOME=C:\Program Files\Java\jdk-11.0.8
C:\Users\-redacted->java -version
java version "11.0.8" 2020-07-14 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.8+10-LTS)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.8+10-LTS, mixed mode)"

Download Tomcat 8 from https://tomcat.apache.org/download-80.cgi
Click the 64-bit Windows zip (pgp, sha512) 
Extract the files directly to C drive (for example, the path to \bin should just end up being C:\apache-tomcat-8.5.58\bin
Open the configuration file "web.xml" in the Tomcat "conf" folder (edit with plain old Notepad so as not to introduce any formatting). 
Enable the directory listing by changing "listings" from "false" to "true" for the "default" servlet.
Open the Tomcat "server.xml" file and make sure the default port is set to 8080
'<Connector port="8080" protocol="HTTP/1.1"'
Test the Tomcat server installation by going into the bin folder and clicking the
startup.bat file then point your browser at "http://localhost:8080" - the default Apache Tomcat page should appear
Turn the server off by going into the bin folder and clicking the shutdown.bat file
At this point if you hit F5 on the tomcat page on your browser, the browser will return "unable to connect"

DOWNLOAD ECLIPSE Java EE IDE "Eclipse IDE for Enterprise Java Developers"
Download the zip file and extract it directly to the C drive (for example, the path to the configuration folder should be "C:\eclipse\configuration")

Make sure everything is working by creating and running a simple servlet
Run Eclipse by going into the, right-clicking on Eclipse.exe, and clicking "Run as Administrator"
Go ahead and click Launch for whatever Workspace that Eclipse suggests
Close the Welcome tab that appears by default on Eclipse and make sure you can see
the "Project Explorer" on the left
Now to connect Eclipse and Tomcat.
In Eclipse, click on Window -> Preferences; in Preferences, expand the Server list
Click on Runtime Environments then click the Add button - a list of Apache Tomcat servers 
should be available 
Select the server you want (this should be Tomcat 8.5 if you are following this guide)
Click the Next button
On the next page, click Browse and select the tomcat folder on your C drive
Click Finish then Apply and Close
So at this point Tomcat is hooked up with Eclipse. For example, Eclipse will start and 
stop Tomcat for you when in the future you want to run one of your applications

Now, click File -> New -> Dynamic Web Project
In Project Name just type "Test00" - leave everything else on that page at its default and click Next.
Do not change anything on the next page where it says "Source folders on build path" - just click Next.
On the next page, check the box that says "Generate web.xml deployment descriptor" then click Finish
After several moments, the dialogue will close and you will see "Test00" over on the left in the Project Explorer
Now let's go ahead and create a servlet
Expand the Java Resources folder and you will see the src folder
Right-click on the src folder, then click New -> Servlet
In the Create Servlet dialogue, enter "testservlet" into the Class name field
Delete the text int he Superclass field - we don't need it for now
Click next - in the URL mapping field where it says "/testservlet" change it to say "/welcome" then click Next
Don't click anything on the next page - just click Finish
OK now Eclipse has open the "testservlet.java" file you just created
Here's where it gets a little tricky:
Highlight everything below "public class testservlet implements Servlet {" and replace it just wiht  a "}"
Click on the red 'X' that appears to the left of "public class testservlet implements Servlet"
and double-click on the "Add unimplemented methods" option
Your code should now look like this (NOTE update the two methods Init and Service by adding some writeline commands!):


  import java.io.IOException;
  import javax.servlet.Servlet;
  import javax.servlet.ServletConfig;
  import javax.servlet.ServletException;
  import javax.servlet.ServletRequest;
  import javax.servlet.ServletResponse;
  import javax.servlet.annotation.WebServlet;

  /**
   * Servlet implementation class testservlet
   */
  @WebServlet("/welcome")
  public class testservlet implements Servlet {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("This is printed from Init");
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("This is printed from Service");
	}
  }

You now must update web.xml with the servlet for this to work.
In the Project Explorer under Web Content -> WEB Inf -> lib  click on the web.xml file
When the web.xml file opens, down at the bottom click the Source tab 
It starts out looking like this:

  <?xml version="1.0" encoding="UTF-8"?>
  <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd" id="WebApp_ID" version="3.1">
    <display-name>Test00</display-name>
    <welcome-file-list>
      <welcome-file>index.html</welcome-file>
      <welcome-file>index.htm</welcome-file>
      <welcome-file>index.jsp</welcome-file>
      <welcome-file>default.html</welcome-file>
      <welcome-file>default.htm</welcome-file>
      <welcome-file>default.jsp</welcome-file>
    </welcome-file-list>
  </web-app>

Change it to look like this:

  <?xml version="1.0" encoding="UTF-8"?>

  <element>

  <servlet>
  <servlet-name>whatever</servlet-name>
  <servlet-class>testservlet</servlet-class>
  </servlet>

  <servlet-mapping>
  <servlet-name>whatever</servlet-name>
  <url-patter>/welcome</url-patter>
  </servlet-mapping>

  <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd" id="WebApp_ID" version="3.1">
    <display-name>Test00</display-name>
    <welcome-file-list>
      <welcome-file>index.html</welcome-file>
      <welcome-file>index.htm</welcome-file>
      <welcome-file>index.jsp</welcome-file>
      <welcome-file>default.html</welcome-file>
      <welcome-file>default.htm</welcome-file>
      <welcome-file>default.jsp</welcome-file>
    </welcome-file-list>
  </web-app>

  </element>

OK that's it. Right-click on Test00 in the Project Explorer and click Run As -> 1 Run on Server
On the Run on Server dialogue, just click Next
On the Add and Remove dialogue, just click Finish
This will fire up Tomcat and a browser session will start up right inside of Eclipse. 
It will not look like much but if you change the url from "http://localhost:8080/Test00/" to "http://localhost:8080/Test00/welcome" then
hit the refresh button, you will see output in the Console tab (by that I mean the Console tab down at the bottom of Eclipse over to the right of Snippets)
In Console you now should see "This is printed from Init    This is printed from Service"
If you keep hitting the refresh button, you will see new lines of "This is printed from Service" appear
That's it!  You got a java servlet working!  Now, lets get a little more complicated....

Hit the red button in Eclipse to stop Tomcat running.
 
Click File -> New -> Dynamic Web Project
In Project Name just type "Test01" - leave everything else on that page at its default and click Next
Do not change anything on the next page where it says "Source folders on build path" - just click Next
On the next page, check the box that says "Generate web.xml deployment descriptor" then click Finish
After several moments, the dialogue will close, and you will see "Test01" over on the left in the Project Explorer.

Right-click on src folder (in the Java Resources folder) and click New -> servlet
In Class name type "samplepage"; change NOTHING else and click Next, Next, Finish
Your samplepage.java file should look like this:



  import java.io.IOException;
  import javax.servlet.ServletException;
  import javax.servlet.annotation.WebServlet;
  import javax.servlet.http.HttpServlet;
  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;

  /**
   * Servlet implementation class samplepage
   */
  @WebServlet("/samplepage")
public class samplepage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public samplepage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
  }

Do Run As and make sure the server fires up as expected

Now, go back to the servlet and add "import java.io.PrintWriter;"

Update the doGet method as follows:

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
        out.println("Hello World!");
       
        response.setContentType("text/html");
        PrintWriter out1 = response.getWriter();

        // send HTML page to client
        out1.println("<html>");
        out1.println("<head><title>A Test Servlet</title></head>");
        out1.println("<body>");
        out1.println("<h1>Test</h1>");
        out1.println("<p>This page is not from an HTML file. Rather, it is from a servlet!</p>");
        out1.println("<form><textarea id=\"w3review\" name=\"w3review\" rows=\"4\" cols=\"50\">\r\n"
        + "At w3schools.com you will learn how to make a website. They offer free tutorials in all web development technologies.\r\n"
        + "</textarea></form>");
        out1.println("</body></html>");
	}

Save, then go back to the Eclipse browser tab and extend the url to this: http://localhost:8080/Test01/samplepage
Give it a second to update then hit the refresh button on the Eclipse browser. You should now see this:

  Hello World!  
  Test

  This page is not from an HTML file. Rather, it is from a servlet!

  At w3schools.com you will learn how to make a website. They offer free tutorials in all web development technologies.

Go ahead and stop the server at this point. You are ready to create your WAR file. 
The WAR file is the "bridge", if you will, between you creating a working java servlet app on your own computer and getting your work
to appear on a web page generated by AWS.

Right-click on Test01 and click Export. Create the WAR file with the WAR Export wizard and just save it to a folder somewhere.
Log on to your AWS console, go to Elastic Beanstalk, click the Create Application button, follow the wizard, and import the WAR file
from your machine when prompted.
Once the AWS service is running and the web page it generates is running in your browser, extend the browser with "/samplepage" to see 
the HTML that the servlet is producing.
```