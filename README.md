##  This app parses html pages using JSOUP library
##  @Author Reneuby
##  @email kutuginr@gmail.com
##  First page is the original, second is the sample 
##  The original contains a button with attribute 
##  id="make-everything-ok-button". 
##  This id is the only exact criteria, 
##  to find the target element in the sample file 
##
##  To create binary execute the following in maven console: compile assembly:single
##  output file will be in target folder
##
##  To run binary:
##  Go to binary folder,
##  type in terminal:
##  java -jar SmartXMLAnalyzer-1.0-SNAPSHOT-jar-with-de
##  pendencies.jar origin_file_path sample_file_path id
##
##  Example:
##  java -jar SmartXMLAnalyzer-1.0-SNAPSHOT-jar-with-de
##  pendencies.jar C:\Users\Reneuby\Desktop\SmartXMLAnalyzer\src\pages\sample-0-origin.html C:\Users\Ren
##  euby\Desktop\SmartXMLAnalyzer\src\pages\sample-1-evil-gemini.html make-everything-ok-button
##
##  You can find sample files in {ProjectFolder}src\pages folder
##
##  Good luck!
