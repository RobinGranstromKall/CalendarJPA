# Exempel efter
* http://www.mastertheboss.com/jboss-frameworks/maven-tutorials/maven-hibernate-jpa/maven-and-jpa-tutorial
* Men det mesta omgjort nu!

# Skapades f.b. med
<pre>mvn archetype:generate -DarchetypeGroupId=com.github.lalyos -DarchetypeArtifactId=standalone-jpa-eclipselink-archetype -DgroupId=com.mastertheboss -DartifactId=EclipseJPAExample -Dversion=1.0-SNAPSHOT -Dpackage=com.mastertheboss -DinteractiveMode=false
</pre>
* Men koordinater, importer och enkla tester ändrade nu
* och en del nytt har tillkommit

# Rensa databasen
* Enklast är att ta bort katalogen simpleDb
* Detta behöver också göras om man ändrar signaturerna på entiteterna. Dvs lägger till eller tar bort fält.
* Testerna rensar delar av databsen genom ta bort innehåll i tabeller via entity managern. Men man kan ändå behöva rensa hela databasen ibland...