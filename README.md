# Morph-Projet

Web-app -> is not developed.

Android-app -> under development. (not even beta)

The Morph-project is a set of a web-app and an android app,

The android app uses an xml (lua code embedded) file in order to recreate an android activity, the xml is stored in a database and is updated without updating the android app. The core of this app is made using java and lua, the lua files of the system are also updated without updating the app. 

The web-app should allow users to create the xml activties files (including the embedded lua code), and store them in a database.

The android app uses the http://www.keplerproject.org/luajava/ in order to execute lua code in the app. Here is the repository of the project https://github.com/jasonsantos/luajava.

Folder distribution:

      1. Morph-DB: scripts for mysql (base).
      
      2. Morph-Lua: lua scripts of the system core.
      
      3. Morph TestAppsXml: testing app within basic fonctionalities (XML).
      
      4. Morph-WEB: web-app files (up to now, just a basic PHP files).
      
      5. Morph-documentation: android app and web app documentation.
      
      6. Morph: Android app, does not include test.

