Android Game Explorer App

This repository contains an Android mobile application called "Game Explorer" that allows users to explore and favorite games. The app fetches data from the RAWG API and provides a user-friendly interface to browse through various games.

Introduction

The app consists of three screens: a search screen with two tabs, a favorites tab, and a game detail screen. The app uses the RAWG API to fetch game data and allows users to favorite games to view them later.

Features

Search for games by name using a search bar.
Display search results in a paginated list with game details (image, name, genre, metacritic ratings).
Mark previously viewed game cells with a specific background color.
Favorite games to add them to the favorites tab.
Remove games from the favorites tab.
View detailed information about a selected game, including a description.
Expand/collapse game description on the detail screen.
Redirect to the game's website or Reddit page for more information.

Tech Stack

The app is built using the following technologies:

Jetpack Compose: For UI design and rendering.

Retrofit: For making network requests to the RAWG API.

ViewModel: For managing and handling data during configuration changes.

Coroutines: For managing asynchronous tasks and thread handling.

Room Database: For storing favorite games locally on the device.

Navigation Components: For managing screen transitions and navigation.

Glide: For loading and displaying images.

Detekt: For static code analysis and writing clean code.

Architecture: MVVM (Model-View-ViewModel) architecture pattern.

Setup

To run the app, follow these steps:

Clone the repository to your local machine.
Open the project in Android Studio.
Build and run the app on an Android emulator or a physical device.
API Key
To use the app, you need an API key from the RAWG API. You can generate your API key by visiting the following link: https://rawg.io/apidocs

Once you have the API key, update it in the local.properties file:

properties

api_key=YOUR_API_KEY_HERE

Contact

For any questions or inquiries about the app, you can reach me at: www.linkedin.com/in/özge-canlı-08b8a715a
