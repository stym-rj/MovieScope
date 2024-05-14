
# MovieScope

An Android App which shows you list of movies based on its genre.


## Tech Stack

**Technologies:** Kotlin, XML, Retrofit

**Platform:** Android Studio


## Optimizations

* Efficiently utilized HashMap Data Structure to map movies to its corresponding genres.
* Created another API that consisted of only genres id and its corresponding genre name and called it separately which helped in mapping the movies to its genres.

## Working

The app calls two APIs which I have hosted on my github ([MoviesData.json][2] and [GenreData.json][3]) using the [Retrofit Library][1] and maps it into a HashMap with key value pair as -> (genreID: listOf<MoviesData>()).
And then finally using a Recycler View, it shows all the data.




[1]: https://github.com/square/retrofit
[2]: https://github.com/stym-rj/MovieScope/blob/master/moviesData.json
[3]: https://github.com/stym-rj/MovieScope/blob/master/genreData.json
[4]: https://drive.google.com/file/d/1ITvhlcI0BeRKQKxWTmiRR8weA7MGdndz/view?usp=sharing
## Demo APK

[APK File Download Link][4]


## Authors

- [@stym-rj](https://www.github.com/stym-rj)

## Color Reference

| Color             | Hex                                                                |
| ----------------- | ------------------------------------------------------------------ |
| App Accent Color | ![#E74E35](https://via.placeholder.com/10/E74E35?text=+) #E74E35 |
| Rating Stars Color | ![#FFB520](https://via.placeholder.com/10/FFB520?text=+) #FFB520 |
| Gray | ![#575757](https://via.placeholder.com/10/575757?text=+) #575757 |


