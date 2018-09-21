# FamilyMap
An Android app and its server, for a customizable interactive explorer of family history events and relationships

[See some of the **Android** code](https://github.com/spartahawk/FamilyMap/tree/Server-revert_plus_App-added/app/src/main/java/hawkes/fmc/ui)

[See some of the **server** code](https://github.com/spartahawk/FamilyMap/tree/Server-revert_plus_App-added/server/src/main/java/doughawkes/fmserver)

[[Family Map demo](https://i.imgur.com/Br7SotR.png?1)](https://www.youtube.com/embed/qWGlRpVgEJs?rel=0)

Family Map is an application that provides a geographical view of your family history. One of the most exciting aspects of researching family history is discovering your origins. Family Map provides a detailed view of where you came from. It does so by displaying information about important events in your ancestors’ lives (birth, marriage, death, etc.), and plotting their locations on Google Maps.

Family Map uses a client/server architecture. The client is an Android app that lets a user view and interact with their family history information. The server is a nonAndroid Java program that runs at a publicly-accessible location in the cloud. When a user runs the Family Map client app, they are first asked to log in. After authenticating the user’s identity with the Family Map server, the client app retrieves the user’s family history data from the server. The server is responsible for maintaining user accounts as well as dispensing family history data for Family Map users.

The Family Map Android application consists of six main views:
- Main Activity (Login and Top-level map)
- Event Activity (Lower-level maps)
- Person Activity
- Settings Activity
- Filter Activity
- Search Activity

The Family Map Client application uses two external services:
    - Family Map Server - Used for user management, data generation, and requesting data
    - Google Maps v2 for Android - Used for displaying maps
    
    <a href="https://imgur.com/lp5WaQp"><img src="https://i.imgur.com/lp5WaQp.png" title="Basic design"/></a>
    <a href="https://imgur.com/mgM8RqW"><img src="https://i.imgur.com/mgM8RqW.png" width="400" height="712"/></a>
    <a href="https://imgur.com/UY9TDV9"><img src="https://i.imgur.com/UY9TDV9.png" width="400" height="712"/></a>
    <a href="https://imgur.com/b3MSnBN"><img src="https://i.imgur.com/b3MSnBN.png" width="400" height="712"/></a>
    <a href="https://imgur.com/xxlYP5v"><img src="https://i.imgur.com/xxlYP5v.png" width="400" height="712"/></a>
    <a href="https://imgur.com/Xi1qzs9"><img src="https://i.imgur.com/Xi1qzs9.png" width="400" height="712"/></a>
    <a href="https://imgur.com/mZdiXOy"><img src="https://i.imgur.com/mZdiXOy.png" width="400" height="712"/></a>
    
