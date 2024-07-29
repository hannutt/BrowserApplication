Project keywords: Java, Lynx clone, webbrowser clone, WebView

This program emulates two types of programs, a modern web browser and a Lynx-style browser that only displays
the text of the web page the user is looking for.

MAIN FEATURES

WebView Browser

The program has a text field where you can write the desired url address. Once the address is written, press Enter to go to the page.
The method implementing the display of the url page receives as a parameter the keyEvent handler, i.e. the press of the enter key.
The address entered by the user is stored in a variable and is sent to the load method of the WebView class.
The end result is the display of the user's desired website in the program's user interface window.

Lynx clone browser

The Lynx browser displays web pages in text mode only. If the user wants to see only the text of web pages, this feature can be enabled
by clicking the "Switch to text mode" checkbox in the user interface. By clicking the check box, the program hides the webView element
using the SetOpacity method and displays the  text area element using the same method. The text fetched from the url is displayed in the text area element.

Searching for plain text from the url entered by the user is done with the ExcecuteScript method of the WenView class, 
which takes the expression "document.documentElement.innerText" as a value. The  Checkbox works dynamically. 
One click hides the webView element and shows the text area. a second click does the opposite.



THEMES

The program includes two ready-made themes, light and dark, which can be used to change the appearance of the browser. 
In addition, the user can enter the desired color code in the input field and the color of the appearance changes according
to the color code entered by the user. The selected theme is always saved in a text file, so the program remembers the last used theme.
Since the stored value is small, I decided to use a text file instead of a database.

Theme values ​​are stored in the settings.txt file. First, the program checks if the configuration file already exists, 
if not, the program creates it and if it exists, replaces the theme with the new value.
These properties are done using the File class and the FileWriter class.


ZOOM

When the user uses the webView browser, the content can be zoomed in and out by pressing the buttons.The feature is made using 
the setZoom method of the WebView class.
On each click, the SetZoom method receives a decimal value of 1.0 as a parameter, and depending on whether the user has pressed 
the Zoom In or Zoom Out button, the value returned as a parameter is either increased or decreased from its current zoom value.
The current zoom value is obtained with the getZoom method.

GO PREVIOUS OR NEXT PAGE

All URLs that the user types in the address field are stored in a list of strings. When the user wants to go to the previous page, 
the method implementing this function uses a variable whose value is always reduced by one with each click. 
Then the load method of the webview class is given the address list and the aforementioned variable as parameters. 
In practice, therefore, the previous address is searched from the list.

When moving to the next page, the value of the variable is increased by one, i.e. the next address stored there is retrieved from the list.



