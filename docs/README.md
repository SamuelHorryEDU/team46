# How to use the SQL Script

<!--toc:start-->
- [How to use the SQL Script](#how-to-use-the-sql-script)
  - [Prerequisites](#prerequisites)
  - [A note on updating the database schema](#a-note-on-updating-the-database-schema)
  - [Remember](#remember)
  - [Initialise the database](#initialise-the-database)
<!--toc:end-->

## Prerequisites

- First of all, make sure that you have your MySQL Workbench set up, and you are able to connect to it
- You'll probably have to set a new password
- Just follow the instructions from here: [https://moodle4.city.ac.uk/mod/url/view.php?id=1021391](https://moodle4.city.ac.uk/mod/url/view.php?id=1021391)
- Start your server. If you're stuck, check the instructions for your operating system in the docs:
[https://dev.mysql.com/doc/refman/8.0/en/installing.html](https://dev.mysql.com/doc/refman/8.0/en/installing.html)

## A note on updating the database schema

If you find that there is a problem with the layout of the database, like you need to change an int length, or need to change
certain columns, DON'T UPDATE THE LAYOUT IN THE WORKBENCH GUI.

If you do, the others won't get the changes that you have made when they run the script.

In order for everyone to stay on the same database layout, before starting your work, always pull from the github to get the most
up-to-date code.

If you need to update the database layout, make an edit to the schema.sql script, and once you've verified it all works,
push it to the github. MySQL Workbench should automatically detect that the schema.sql file has changed and will prompt you to
rerun the script.

## Remember

Always. Pull. From. GitHub. Before. Starting.

This makes sure that we are all up-to-date.

## Initialise the database

- Once you've set up and connected to your server, under the File, Edit, View etc. bar, there is a button that says
"Create a new SQL tab for executing queries"
- Click on this and it will open a query tab
- Once you've done this, in the query tab, click the icon that looks like a folder and says
"Open a script file in this editor"
- Open the schema.sql file that is in this folder
- Click the Lightning Bolt icon to execute the script
- Go to the Schemas tab on the left and reload the schemas, ipos_sa_db should now be there with all of the tables laid out!
