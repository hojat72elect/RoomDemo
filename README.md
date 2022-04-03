## Room Demo

This is my template app for implementing all CRUD operations of a local SQLite database with the help of Androidx Room
library. This project showcases these technologies:

<ul>
<li>MVVM architecture</li>
<li>ViewModel</li>
<li>Room Database</li>
<li>RecyclerView</li>
<li>Kotlin Coroutines</li>
<li>DataBinding</li>
<li>LiveData</li>
</ul>

----------------------------------

The picture below depicts the specific flavor of MVVM architecture which was used for this project:

<p align="center">
<img alt="MVVM architecture" src="readme_resources/MVVM architecture.png" width="500" height="500"/>
</p>

The connection between ViewModel and all various entities of Model layer (local Room database, remote web services
accessed via Retrofit , and caches) is handled through "Repository" class which acts as an access point between the 2
layers.

For the sake of simplicity, this app only has a local Room database with a single table shown below:

<p align="center">
<img alt="subscriber table" src="readme_resources/database chart.jpg" width="500" height="300"/>
</p>

And moreover, the DataBinding, LiveData & Observer technologies are used for easy/maintainable connection between View
and ViewModel layers.

----------------------------------

**The source code of this project is well documented and is the best template for implementing local Room database in an
Android app 🤓.**