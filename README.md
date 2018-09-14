A: Top part: It’s a slideshow with 6 pages. Each page will display a looped video. The video urls can be fetched from the API. You can swipe left/right to change the video. 

Make sure that the displayed video should be cropped, in landscape mode, with an aspect ratio of width:height =  1:1. You don't have to crop the video source itself. As long as the displayed video respects the required aspect ratio without distortion nor white border, it is ok.

Nice to have but not mandatory: offline mode: once the video is already synced locally from the cloud, display the video immediately even when there is no internet. 

B: Bottom part: It’s a horizontally scrollable carousel. There are the 6 images. The image urls can be fetched in the API. You should only display 3 images maximum on the screen, which means all the other images are off-screen. The middle image should be larger than the others, indicating that it is the focused item. At the beginning, the first image should be centered horizontally on the screen. That means, there is no image on the left side at the beginning, until you scroll the images to the left. 

Bonus: When an image becomes focused, the size will be bigger, or smaller when it loses the focus. This size change can be smoother by using zoom-in and zoom-out animations. 

C: When swiping the top part, the bottom part should be automatically scrolled, to make sure that the focused image on the bottom part has the same id as the video on the top part. 

D: When scrolling the bottom part, the top part should swipe automatically and display the video that has the same id as the currently focused image on the bottom part.

E: Make an Android library for the Mini Clips UI and publish to jCenter. This library does not do data synchronization, but should be able to display the above UI when provided with a list of pairs of [video url, image url]. Make a demo app that fetches the video and images from the web service, and use this library via gradle implementation ('your name:mini-clips-ui:0.0.1') to display the UI.

F: Nice to have but not mandatory:  Make an Android library for the Data Synchronization and publish to jCenter. This library does not have UI, and should be able to provide all necessary data for the main app. Make a demo app that implements both the library in E and F.