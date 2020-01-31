
## **Application Installation Procedure:**

1. Install *'Notes.apk'* Available outside *'app'* Package.
2. Set Allow from this Resource in your settings.

## **Screens and their function in Application**
**1. Home Screen**
</br>Here All the saved Notes will be shown and if no note is available then the Screen will look like this:

<img src="https://github.com/dishaverma3/Notes/blob/master/Screenshots/HomePage.jpg" width="350" height="600" />

**2. Create New Note Screen**
</br>Here a new Note can be created/updated.

<img src="https://github.com/dishaverma3/Notes/blob/master/Screenshots/Create%20Note.jpg" width="350" height="600" />

**3. Note Details Screen**
</br>This Screen shows the details of the saved Notes and options to delete or update particular note.

<img src="https://github.com/dishaverma3/Notes/blob/master/Screenshots/Note%20Details.jpg" width="350" height="600" />   <img src="https://github.com/dishaverma3/Notes/blob/master/Screenshots/Extended%20Buttons.jpg" width="350" height="600" />

**Home Screen After Saving the Notes**
</br><img src="https://github.com/dishaverma3/Notes/blob/master/Screenshots/Notes%20List.jpg" width="350" height="600" />

## **Project Details**
This android project follows *MVVM* architecture. This project is a 3 activity app and used following architecture components : 

- ***Room***
- ***ViewModel***
- ***LiveData***


Following are the third party dependencies used in the app : 

- ***Fab-Speed-Dial*** - To implement extended floating buttons
- ***Lottie*** - For animations
- ***gson*** - For serializing and deserializing JSON/MODEL data
- ***Circle Image View*** - To add Images in Circular Image View

For testing, the project has used following dependencies :

- ***JUnit*** - For unit testing.
- ***Roboelectri*** - Unit testing framework for fast testing.
- ***Mockito*** - For mocking objects.


This app contains following package structure :

- ***data*** - It contains code logic related to data like database, preference and remote data source
- ***model*** - It contains code related to Model Classes
- ***ui*** - It contains all the **ui* related classes as well as *ViewModels** for business logic
- ***util*** - It contains **extension* classes as well as *utilities** classes
- ***MainActivity class*** - It contains the **NotesApp** application instance

