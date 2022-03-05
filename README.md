This is my template app demoing these technologies in an easy-to-understand fashion:

<ol>
<li>MVVM architecture</li>
<li>Android Room (as the back end technology)</li>
<li>Kotlin Coroutines (for performing CRUD operations in ViewModel layer)</li>
<li>DataBinding, LiveData & Observer for easy communication between View and ViewModel layers of the
app.</li>
</ol>

The picture below depicts the specific flavor of MVVM architecture which was used for this project:

<p align="center">
<img alt="MVVM architecture" src="readme_resources/MVVM architecture.png" width="500" height="500"/>
</p>

The connection between ViewModel and all various entities of Model layer (local Room database,
remote web services accessed via Retrofit , and caches) is handled through "Repository" class which
acts as an access point between the 2 layers.

For the sake of simplicity, this app only has a local Room database with a single table shown below:

<p align="center">
<img alt="subscriber table" src="readme_resources/database chart.jpg" width="500" height="300"/>
</p>

And moreover, the DataBinding, LiveData & Observer technologies are used for easy/maintainable
connection between View and ViewModel layers. 