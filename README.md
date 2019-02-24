## Inspiration
We were brainstorming for an idea and when took a bathroom break. We saw the gender neutral bathroom in the building and decided to make an app that would find nearby gender neutral bathrooms. There's more functionality to the app but that's the essence of it.

## What it does
The app finds the nearest bathrooms to your current location or a specified location. The bathrooms can be filtered to only include bathrooms that are accessible to disabled or unisex persons. The icons on the top left and right represent the accessibility or lack of.

## How we built it
We used Android-Studio in Java to build our app. The bathroom data is pulled from the Refuge Restrooms API. We converted the manually inputted location into longitude and latitude using the Geocoding API from GCP. The current location is from the phone's GPS.

## Challenges we ran into
When using the Google APIs, the JSON is pre-formatted which makes it very hard to work with. We solved this by reformatting the text by parsing through it, line by line. We also tried to use Matrix Distance to get distance on the roads rather than a linear distance. The API did not like this so we had to scrap it. We also ran into issues with trying to save the state of the settings. Our settings would become null after passing them through two intents. We were unable to change units from miles to kilometers because of this issue.

## Accomplishments that we're proud of
We are proud of being able to put all these different concepts that we have learned to make one big project. 

## What we learned
We learned how to use the GCP Platform and its APIs. We also learned a lot about UI and how to make a nice looking theme with a proper mood and tone. In addition, we learned how to work with multiple APIs in one Activity and pass objects between intents.

## What's next for BathroomApp
We want to finish the matrix distance, add units, and add the ability to save the previous settings when you reopen the settings page.
