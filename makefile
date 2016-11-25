DIR = WEB-INF/classes/uci/zainabk
DB = database
TV = tvtropes
I = imdb
LIB = C:\Users\Zainab Khan\Desktop\EECS118\apache-tomcat-7.0.72\lib
LIB2 = C:\Users\Zainab Khan\Desktop\EECS118\apache-tomcat-7.0.72\webapps\movie_chooser\WEB-INF\lib
CP = -classpath
CLASSES = WEB-INF/classes

#doskey javar=java -classpath "..\lib\mysql-connector-java-5.0.8-bin.jar;../webapps/movie_chooser/WEB-INF/lib/gson-2.8.0.jar;../webapps/movie_chooser/WEB-INF/classes" $*

$(DB): 
	javac $(CP) "$(CLASSES)" $(DIR)/$(DB)/*.java

$(TV): 
	javac $(CP) "$(LIB)\jsp-api.jar;$(CLASSES)" $(DIR)/$(TV)/*.java

$(I): 
	javac $(CP) "$(LIB2)\gson-2.8.0.jar;$(CLASSES)" $(DIR)/$(I)/*.java

all: $(DB) $(TV) $(I)
