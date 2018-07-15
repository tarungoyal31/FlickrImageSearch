Flickr Image Search
==================

Introduction
------------
This application provides user with an interface to search images with help of Flickr Image search APIs.
Images are shown in a 3-column endless scrollview.

Basic MVP architecture is being used here for better code readability and testability.

Features
--------
This app presents user with following features.
* **Search for images:** A search toolbar is added where user can type their search queries. Images gets filtered according to the user's search query.
    * At present, search suggestions are not provided to the user. These can be added by including recent searches done by user.
* **3-column image view:** This is achieved by using Gridlayout in Recyclerview.
* **Image Caching:** In-memory image caching is done using android LruCache
    * This can further be improved by using disk-cache.
* **Image loading:** Images are loaded by using a custom ImageLoader which uses Async task internally. AsyncTask#THREAD_POOL_EXECUTOR is used to load images in parallel.
    * Because of the small loading time of images, AsyncTasks are chosen over IntentServices here.
* **Networking:** For networking retrofit is being used.
* **JUnit tests**

Future Work and Enhancements
----------------------------
Future work includes the following tasks:
* Image detail view can be added on clicking images.
* Placeholder image functionality in ImageLoader.
* Caching of images in disk for better persistent.
* Search suggestions based on recent searches.
* Instrumentation tests.
* Cancel all the pending tasks on search term change.

Version support
---------------
 * **Minimum Android SDK**: Api level 15.
 * **Target Android SDK**: Api level 27.
 
Author
------
Tarun Goyal - @tarungoyal31 on Github








