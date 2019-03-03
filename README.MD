# Assignment 1
**Student Name: Eoin Kelly**
**Student Number: 20074820**

For this assignment i was giving the task to make an app from scratch.
The app i choose to do was a bike rental locator app. The app as some feature like a list of city's 
were they have bike rental schemes and from these city's you are giving a list of station placed across these city's.
The app was built in Kotlin and only fragment were used for the first assignment.

The app feature are to pick a city then you get a list of stations with in that city when you select one of these city's
your are then presented with a map that give you the location of the station in the selected city

## Landing page
When entering the landing page you have an image and a spinner with a list of city's and also a search location button
so you must select the city's that you want then press the search button and you will be taking to the bike station list
for that selected city 

````kotlin 
var mSpinnerAdapter = ArrayAdapter.createFromResource(context, R.array.city_array,
    android.R.layout.simple_spinner_item)
mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
mCitySpinner.adapter = mSpinnerAdapter 
````
          
## Bike Station select 
This screen has just a recycler view and a filter edit text box were you can type in the name of a station it will start to show only stations with that name

````kotlin
fun filterList(text: String){
        var fliterBikeArray: ArrayList<Bike> = ArrayList()
        for (bike in bikeList){
            if (bike.address!!.toLowerCase().contains(text.toLowerCase())){
                fliterBikeArray.add(bike)
                mBikeRecyclerViewAdapter!!.filterList(fliterBikeArray)
            }
        }
    }
````

## Map screen 
The map screen will show you the position of the station selected with a marker placed on the map to the stations location

It also has a Floating Action Button that is used for favourites so you can add the selected station to you favourites for easy accessibility.
if the station is all ready in your favourites then the star will automatically be activated

below is some code to show you how i was checking the database for items that existed 
````java
public void checkDatabaseForMatch() {
        mDatabase.getReference().child("Favourites").orderByChild("stationName").equalTo(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    fab.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_on));
                    fab.setClickable(false);
                }else{
                    fab.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_off));
                    fab.setClickable(true);
                }
            }
            @Override
            public void onCancelled(@NonNull final DatabaseError databaseError) {

            }
        });

    }
````

## Favourites 
This screen just has a list of you favourite stations in a recycler view for easy viewing
a code sample to retrieve items from Firebase database
````kotlin
 override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    favList.clear()
                    for (fav in dataSnapshot.children) {
                        val myFavs = fav.getValue(Favourites::class.java)!!
                        favList.add(myFavs)
                        mFavRecyclerViewAdapter = FavouritesRecyclerViewAdapter(favList, context!!){
                            deleteFavourite(favList[it].favId!!)
                        }
````


## Video demo for assigment one
[Assignment 1 demo](https://vimeo.com/320969998)

## References
Api that was used for information [JCDecaux Developer](https://developer.jcdecaux.com/#/opendata/vls?page=getstarted)
Stack overflow for bug [Stack overflow](https://stackoverflow.com/)
Android developer  [Android developer docs](https://developer.android.com/)
Firebase for database, User Auth and Analytics [Firebase](https://firebase.google.com/)
Mapbox for the map api [Mapbox](https://docs.mapbox.com/)





 


