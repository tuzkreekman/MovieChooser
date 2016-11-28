DIR = WEB-INF/classes/uci/zainabk
DB = database
TV = tvtropes
I = imdb
M = movies
LIB = C:\Users\Zainab Khan\Desktop\EECS118\apache-tomcat-7.0.72\lib
LIB2 = C:\Users\Zainab Khan\Desktop\EECS118\apache-tomcat-7.0.72\webapps\movie_chooser\WEB-INF\lib
CP = -classpath
CLASSES = WEB-INF/classes

all: $(I) $(DB) $(TV) $(M)

$(DB):
	javac $(CP) "$(CLASSES)" $(DIR)/$(DB)/*.java

$(M): 
	javac $(CP) "$(LIB2)\gson-2.8.0.jar;$(CLASSES)" $(DIR)/$(M)/*.java

$(TV): 
	javac $(CP) "$(LIB)\jsp-api.jar;$(LIB2)\jsoup-1.10.1.jar;$(CLASSES)" $(DIR)/$(TV)/*.java

$(I): 
	javac $(CP) "$(LIB2)\gson-2.8.0.jar;$(CLASSES)" $(DIR)/$(I)/*.java

