# FamilyMap
A graphical client and its server for a customizable interactive explorer of family history events and relationships

[![Family Map demo](https://i.imgur.com/r64jlfn.png)](https://www.youtube.com/watch?v=qWGlRpVgEJs)

Family Map is an application that provides a geographical view of your family history. One of the most exciting aspects of researching family history is discovering your origins. Family Map provides a detailed view of where you came from. It does so by displaying information about important events in your ancestors’ lives (birth, marriage, death, etc.), and plotting their locations on a Google or Amazon map.

Family Map uses a client/server architecture, which means it consists of two separate programs (a “client” program and a “server” program). The Family Map client is an Android app that lets a user view and interact with their family history information (see image on the left). The Family Map server is a regular, nonAndroid Java program that runs at a publicly-accessible location in the cloud. When a user runs the Family Map client app, they are first asked to log in. After authenticating the user’s identity with the Family Map server, the client app retrieves the user’s family history data from the server. The server is responsible for maintaining user accounts as well as dispensing family history data for Family Map users. Family Map’s client/server architecture is typical of many real-world applications (Facebook, Twitter, etc.)

The Family Map Android application consists of six main views:
- Main Activity (Login and Top-level map)
- Event Activity (Lower-level maps) Person Activity
- Settings Activity
- Filter Activity
- Search Activity The Family Map Client application uses two external services:
    - Family Map Server - Used for user management, data generation, and requesting data
    - Google Maps v2 for Android or Amazon Maps v2 for Kindle - Used for displaying maps
