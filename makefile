DIR = WEB-INF/classes/uci/zainabk
DB = database
TV = tvtropes
I = imdb
M = movies
CSM = csm
LIB = C:\Users\Zainab Khan\Desktop\EECS118\apache-tomcat-7.0.72\webapps\movie_chooser\WEB-INF\lib
CP = -classpath
CLASSES = WEB-INF/classes

all: $(I) $(DB) $(TV) $(CSM) $(M)

$(DB):
	javac $(CP) "$(CLASSES)" $(DIR)/$(DB)/*.java

$(M): 
	javac $(CP) "$(LIB)\gson-2.8.0.jar;$(CLASSES)" $(DIR)/$(M)/*.java

$(TV): 
	javac $(CP) "$(LIB)\jsoup-1.10.1.jar;$(CLASSES)" $(DIR)/$(TV)/*.java

$(I): 
	javac $(CP) "$(LIB)\gson-2.8.0.jar;$(CLASSES)" $(DIR)/$(I)/*.java

$(CSM):
	javac $(CP) "$(LIB)\jsoup-1.10.1.jar;$(CLASSES)" $(DIR)/$(CSM)/*.java

