# Pipedrive Person Details

## Architecture
Basically I used a mixed MVVM - [Flux](https://facebook.github.io/flux/docs/overview.html) approach, because it perfectly fits for thin-clients like this application and Room+RxJava works perfectly as the dispatcher.
The application separated into 4 modules. The *domain* module holds the models required for the presentation layer, the *data* module handles the data flow, the *common* module contains the base classes and common bindings and the *app* module holds the presentation layer.

## API key
In order to use application you have to provide your own API key. You can get it from the [Pipedrive](https://pipedrive.com) website by creating a free trial account.
When you obtain the keys, you can provide them to the app by creating a `config/keys.properties` file with the following content:

```
pipedriveApiKey=<insert>
```

## Places of improvement
* Tests
* Feature modules
* Mock flavour
