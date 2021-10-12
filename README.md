# Pokemon Sdk

Simple Pokemon SDK that interacts with the pokemon API that given a pokemon names, it returns a shakespearean description, also returns pokemon sprites.

## Built With

- Kotlin
- Android Studio
- Dependencises include
  1. Google Gson
  2. OkHttp
  3. RxJava
  4. Picaso
  5. Junit 5 for testing
  6. OkHttp Mockserver for testing
  


## Instalation

To Use this library simply. Import the library to your project by

Step 1. Add this to your root build.gradle at the end of repositories:
  ```
      allprojects {
      repositories {
        ...
        maven { url 'https://jitpack.io' }
      }
    }
  ```
  
Step 2. Add the dependency
  ```
      dependencies {
            implementation 'com.github.thankgodr:pokemon-sdk:develop-SNAPSHOT'
    }
    
  ```  


### Usage

#### To Get default Descriotion of a Pokemon

###### Kotlin
  ```
    Pokemon.getDescription(PokemonRequest("Pokemon Name"),object :
            PokemonAccessResult<DescriptionResponse, ApiError> {
            override fun onStart() {
                TODO("Not yet implemented")
            }

            override fun onNetworkrequest() {
                TODO("Not yet implemented")
            }

            override fun onSuccess(success: DescriptionResponse) {
                TODO("Not yet implemented")
            }

            override fun onError(error: ApiError) {
                TODO("Not yet implemented")
            }

            override fun complete() {
                TODO("Not yet implemented")
            }

        })
        
        //Or implement the callback to a class and override its member functions
        
        
        class MainActivity : AppCompatActivity() , PokemonAccessResult<DescriptionResponse, ApiError> {
          override fun onCreate(savedInstanceState: Bundle?) {
              super.onCreate(savedInstanceState)
              setContentView(R.layout.activity_main)
            
            //Just pass this as the second parameter.
            Pokemon.getDescription(PokemonRequest(""), this)
          }

          override fun onStart() {
              super.onStart()
          }

          override fun onNetworkrequest() {
              TODO("Not yet implemented")
          }

          override fun onSuccess(success: DescriptionResponse) {
              TODO("Not yet implemented")
          }

          override fun onError(error: ApiError) {
              TODO("Not yet implemented")
          }

          override fun complete() {
              TODO("Not yet implemented")
          }
      }
  ```
###### Java
  ```
    Pokemon.INSTANCE.getDescription(new PokemonRequest("Pokemon Name or ID"), 
                new PokemonAccessResult<DescriptionResponse, ApiError>() {
            @Override
            public void complete() {

            }

            @Override
            public void onError(ApiError error) {

            }

            @Override
            public void onSuccess(DescriptionResponse descriptionResponse) {

            }

            @Override
            public void onNetworkrequest() {

            }

            @Override
            public void onStart() {

            }
        });
  ```
  
 #### To Tranlate into Shakesparian using the funtranslations API
  
  ###### Kotlin
  
  ```
    Pokemon.ShakespeareanDescription(PokemonTranslateRequest("How Are you"), object :
            PokemonAccessResult<shakespeareRespnse, ApiError> {
            override fun onStart() {
                TODO("Not yet implemented")
            }

            override fun onNetworkrequest() {
                TODO("Not yet implemented")
            }

            override fun onSuccess(success: shakespeareRespnse) {
                TODO("Not yet implemented")
            }

            override fun onError(error: ApiError) {
                TODO("Not yet implemented")
            }

            override fun complete() {
                TODO("Not yet implemented")
            }

        })
  ```
  
###  To get Sprites
  
  ```
  Pokemon.getSprites(PokemonRequest, PokemonAccessResult<SpriteResponse, ApiError>)
  
  //The sprites images are memebers of SpriteResponse.sprites
  ```
  
  You can download the images from SpriteResponse using Pokemon.downloadSpritesImages(link, callback)
  
  ```
    Pokemon.downloadSpritesImages(SpriteResponse.sprites.front_default, PokemonAccessResult<Bitmap, ApiError>)
    
    //Or Use Glide or Picaso etc
  ```
  
  ### TO get all UI view that does all this for you is
  ```
     Pokemon.startUIforResult(activity)
     
  ```
  
  
  ## TODO
  - Finish Activity and Pass result to the activityResult
  - Material UI
  - Optimize  downloadSprites for catching
  - Persist Presenter when activity is Destroyed
  - Refresh RecylerAdapter on new search
 
 ## Architecture Used
  #### MVP 
  Model–view–presenter (MVP) is a derivation of the model–view–controller (MVC) architectural pattern which mostly used for building user interfaces. In MVP, the presenter assumes the functionality of the “middle-man”. In MVP, all presentation logic is pushed to the presenter. MVP advocates separating business and persistence logic out of the Activity and Fragment
 
  

## Authors

👤 **ThankGod Richard**

- GitHub: [@githubhandle](https://github.com/thankgodr)
- LinkedIn: [LinkedIn](http://www.linkedin.com/in/thankgodr)

## Show your support

Give a ⭐️ if you like this project!

[![](https://jitpack.io/v/thankgodr/pokemon-sdk.svg)](https://jitpack.io/#thankgodr/pokemon-sdk)

