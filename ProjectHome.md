This is a data browser for the popular Hibernate framework
http://www.hibernate.org/ that is used to persist Java objects.

It's NonPolymorphic because it displays objects under their
exact class instead of displaying objects that extend that class.

This browser is needed because it makes it easy to browse
hibernate objects even if their data is stored in normal form
in the database.

I hope someone will take this code and do something awesome.

The default target of the build file should populate a database and then display it. The whole thing should be self contained.

Here are some pictures. The first shows basic usage and the second shows an HQL query and the third shows the schema in UML



![http://hibernatebrowser.googlecode.com/svn/trunk/HibernateBrowser/basicUsage.png](http://hibernatebrowser.googlecode.com/svn/trunk/HibernateBrowser/basicUsage.png)


![http://hibernatebrowser.googlecode.com/svn/trunk/HibernateBrowser/HQLQuery.png](http://hibernatebrowser.googlecode.com/svn/trunk/HibernateBrowser/HQLQuery.png)


![http://hibernatebrowser.googlecode.com/svn/trunk/HibernateBrowser/classdiagram.png](http://hibernatebrowser.googlecode.com/svn/trunk/HibernateBrowser/classdiagram.png)

-By Joseph Paul Cohen